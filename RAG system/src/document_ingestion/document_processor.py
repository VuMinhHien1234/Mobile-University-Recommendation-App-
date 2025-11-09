
# Supports: URL, PDF, TXT, DOCX, CSV + Recursive/Semantic Splitting

from typing import List, Union, Literal
from pathlib import Path
import logging
from langchain.schema import Document
from langchain_community.document_loaders import (
    WebBaseLoader,
)
from langchain_text_splitters import RecursiveCharacterTextSplitter
from unstructured.partition.auto import partition 
from sentence_transformers import SentenceTransformer
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np


logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

class SemanticChunker:
    def __init__(self, model_name: str = "all-MiniLM-L6-v2", threshold: float = 0.75):
        self.model = SentenceTransformer(model_name)
        self.threshold = threshold

    def split_text(self, text: str) -> List[str]:
        if not text.strip():
            return []
        text = text.replace("\n", " ").strip()
        sentences = [s.strip() for s in text.split(".") if s.strip()]
        if len(sentences) <= 1:
            return [text.strip()]

        embeddings = self.model.encode(sentences, normalize_embeddings=True)
        chunks = []
        current_chunk = [sentences[0]]

        for i in range(1, len(sentences)):
            sim = cosine_similarity([embeddings[i-1]], [embeddings[i]])[0][0]
            if sim >= self.threshold:
                current_chunk.append(sentences[i])
            else:
                chunks.append(". ".join(current_chunk) + ".")
                current_chunk = [sentences[i]]
        
        if current_chunk:
            chunks.append(". ".join(current_chunk) + ".")
        return chunks

    def split_documents(self, documents: List[Document]) -> List[Document]:
        result = []
        for doc in documents:
            chunks = self.split_text(doc.page_content)
            for i, chunk in enumerate(chunks):
                if not chunk.strip():
                    continue
                new_doc = Document(
                    page_content=chunk.strip(),
                    metadata={
                        **doc.metadata,
                        "chunk_index": i,
                        "total_chunks": len(chunks),
                        "chunking_method": "semantic",
                        "similarity_threshold": self.threshold
                    }
                )
                result.append(new_doc)
        return result

class DocumentProcessor:
    def __init__(
        self,
        chunk_size: int = 1000,
        chunk_overlap: int = 100,
        chunking_method: Literal["recursive", "semantic"] = "recursive",
        semantic_model: str = "all-MiniLM-L6-v2",
        semantic_threshold: float = 0.75
    ):
        self.chunk_size = chunk_size
        self.chunk_overlap = chunk_overlap
        self.chunking_method = chunking_method
        self.semantic_model = semantic_model
        self.semantic_threshold = semantic_threshold

        self.recursive_splitter = RecursiveCharacterTextSplitter(
            chunk_size=chunk_size,
            chunk_overlap=chunk_overlap,
            length_function=len,
            add_start_index=True,
        )

        self.semantic_chunker: SemanticChunker | None = None

    def _load_with_unstructured(self, file_path: Union[str, Path]) -> List[Document]:
        path = Path(file_path)
        try:
            elements = partition(filename=str(path))
            docs = []
            for i, elem in enumerate(elements):
                text = (elem.text or "").strip()
                if not text:
                    continue
                doc = Document(
                    page_content=text,
                    metadata={
                        "source": str(path),
                        "source_type": path.suffix.lower().lstrip("."),
                        "element_index": i,
                        "element_type": elem.category,
                        "page_number": getattr(elem.metadata, "page_number", None)
                    }
                )
                docs.append(doc)
            return docs
        except Exception as e:
            logger.error(f"Failed to load {path}: {e}")
            return []

    def load_from_url(self, url: str) -> List[Document]:
        try:
            print(f"Loading URL: {url}")
            loader = WebBaseLoader(url)
            docs = loader.load()
            for doc in docs:
                doc.metadata.update({"source_type": "url", "source": url})
            return docs
        except Exception as e:
            logger.error(f"URL load failed {url}: {e}")
            return []

    def load_from_pdf(self, file_path: Union[str, Path]) -> List[Document]:
        return self._load_with_unstructured(file_path)

    def load_from_txt(self, file_path: Union[str, Path]) -> List[Document]:
        return self._load_with_unstructured(file_path)

    def load_from_docx(self, file_path: Union[str, Path]) -> List[Document]:
        return self._load_with_unstructured(file_path)

    def load_from_csv(self, file_path: Union[str, Path]) -> List[Document]:
        return self._load_with_unstructured(file_path)
    def load_from_pdf_dir(self, directory: Union[str, Path]) -> List[Document]:
        path = Path(directory)
        pdf_files = list(path.glob("*.pdf"))
        if not pdf_files:
            logger.warning(f"No PDFs in directory: {path}")
            return []
        print(f"Loading {len(pdf_files)} PDFs from: {path}")
        docs = []
        for pdf in pdf_files:
            docs.extend(self.load_from_pdf(pdf))
        return docs

    def load_documents(self, sources: List[str]) -> List[Document]:
        if not sources:
            return []
        
        docs: List[Document] = []
        print(f"Processing {len(sources)} source(s)...\n")
        for src in sources:
            src = src.strip()
            if not src:
                continue
            try:
                if src.startswith(("http://", "https://")):
                    loaded = self.load_from_url(src)
                else:
                    path = Path(src).expanduser().resolve()
                    if not path.exists():
                        logger.warning(f"Path not found: {src}")
                        continue
                    if path.is_dir():
                        loaded = self.load_from_pdf_dir(path)
                    elif path.is_file():
                        suffix = path.suffix.lower()
                        if suffix == ".pdf":
                            loaded = self.load_from_pdf(path)
                        elif suffix == ".txt":
                            loaded = self.load_from_txt(path)
                        elif suffix == ".docx":
                            loaded = self.load_from_docx(path)
                        elif suffix == ".csv":
                            loaded = self.load_from_csv(path)
                        else:
                            logger.warning(f"Unsupported file: {suffix} ({path.name})")
                            continue
                    else:
                        logger.warning(f"Invalid path: {src}")
                        continue
                docs.extend(loaded)
            except Exception as e:
                logger.error(f"Error processing {src}: {e}")
        return docs


    def split_documents(self, documents: List[Document]) -> List[Document]:
        print(f"Splitting {len(documents)} documents using HYBRID (recursive + semantic) method...")
        base_chunks = self.recursive_splitter.split_documents(documents)
        print(f"Recursive step created {len(base_chunks)} base chunks.")
        if self.semantic_chunker is None:
            self.semantic_chunker = SemanticChunker(
                model_name=self.semantic_model,
                threshold=self.semantic_threshold
            )

        final_chunks = []
        for base_chunk in base_chunks:
            text = base_chunk.page_content.strip()
            if len(text.split()) > 500:
                semantic_subchunks = self.semantic_chunker.split_text(text)
                for i, sub in enumerate(semantic_subchunks):
                    if not sub.strip():
                        continue
                    final_chunks.append(
                        Document(
                            page_content=sub.strip(),
                            metadata={
                                **base_chunk.metadata,
                                "chunking_method": "hybrid-semantic",
                                "base_chunk_index": i
                            }
                        )
                    )
            else:
                base_chunk.metadata.update({
                    "chunking_method": "hybrid-recursive"
                })
                final_chunks.append(base_chunk)

        valid_chunks = [c for c in final_chunks if c.page_content.strip()]
        print(f" Created {len(valid_chunks)} valid hybrid chunk(s).")
        return valid_chunks


    def process(self, sources: List[str]) -> List[Document]:
        print("Start processing ")
        raw_docs = self.load_documents(sources)
        if not raw_docs:
            return []
        chunks = self.split_documents(raw_docs)
        print(f"\Processing completed: {len(chunks)} chunks ready for embedding.")
        return chunks