from typing import List, Optional
import os
from pathlib import Path
import logging
from dotenv import load_dotenv
import faiss
from langchain_community.docstore import InMemoryDocstore
from langchain_core.documents import Document
from langchain_openai import OpenAIEmbeddings, ChatOpenAI
from langchain_community.vectorstores import FAISS
from langchain_community.retrievers import BM25Retriever
from langchain.retrievers import EnsembleRetriever
from langchain.retrievers.document_compressors import LLMChainExtractor
from langchain.retrievers import ContextualCompressionRetriever
from src.retrieval_enhancer.query_enhancer import QueryEnhancer

load_dotenv()
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

class AdvancedVectorStore:
    def __init__(
        self,
        persist_directory: str = "./vectorstore/faiss_index",
        embedding_model: str = "text-embedding-3-small",
        llm_model: str = "gpt-4o-mini",
        temperature: float = 0.0
    ):
        self.api_key = os.getenv("OPENAI_API_KEY")
        self.persist_directory = Path(persist_directory)
        self.persist_directory.mkdir(parents=True, exist_ok=True)
        self.embeddings = OpenAIEmbeddings(model=embedding_model, api_key=self.api_key)
        self.llm = ChatOpenAI(model=llm_model, temperature=temperature, api_key=self.api_key)
        self.vectorstore: Optional[FAISS] = None
        self.dense_retriever = None
        self.sparse_retriever: Optional[BM25Retriever] = None
        self.hybrid_retriever = None
        self.reranker = None

    def load_vectorstore(self) -> bool:
        try:
            self.vectorstore = FAISS.load_local(
                folder_path=str(self.persist_directory),
                embeddings=self.embeddings,
                allow_dangerous_deserialization=True
            )
            docs = list(self.vectorstore.docstore._dict.values())
            self._build_retrievers(docs)
            logger.info("FAISS index loaded successfully.")
            return True
        except Exception as e:
            logger.warning(f"Failed to load FAISS store: {e}")
            self.vectorstore = None
            return False

    def create_or_load_vectorstore(self, documents: List[Document]) -> None:
        index_path = self.persist_directory / "index.faiss"
        if index_path.exists():
            if self.load_vectorstore():
                return
        dimension = 1536
        index = faiss.IndexFlatL2(dimension)
        self.vectorstore = FAISS(
            embedding_function=self.embeddings.embed_query,
            index=index,
            docstore=InMemoryDocstore({}),
            index_to_docstore_id={}
        )
        self.vectorstore.save_local(str(self.persist_directory))
        self._build_retrievers(documents)

    def _build_retrievers(self, documents: List[Document]):
        self.dense_retriever = self.vectorstore.as_retriever(
            search_type="mmr",
            search_kwargs={"k": 6, "fetch_k": 20}
        )
        valid_texts = [doc.page_content for doc in documents if doc.page_content.strip()]
        try:
            if valid_texts:
                self.sparse_retriever = BM25Retriever.from_texts(valid_texts)
                self.sparse_retriever.k = 6
            else:
                self.sparse_retriever = None
        except Exception as e:
            logger.warning(f"BM25 initialization failed: {e}")
            self.sparse_retriever = None
        if self.sparse_retriever:
            self.hybrid_retriever = EnsembleRetriever(
                retrievers=[self.dense_retriever, self.sparse_retriever],
                weights=[0.7, 0.3]
            )
        else:
            self.hybrid_retriever = self.dense_retriever

    def rerank_documents(self, docs: List[Document], query: str) -> List[Document]:
        if not docs:
            return []
        try:
            compressor = LLMChainExtractor.from_llm(self.llm)
            reranker = ContextualCompressionRetriever(
                base_compressor=compressor,
                base_retriever=None
            )
            reranked = reranker.compress_documents(docs, query=query)
            return reranked if reranked else docs
        except Exception as e:
            logger.warning(f"Reranking failed: {e}")
            return docs

    def add_documents(self, documents: List[Document]):
        if not documents:
            return
        if not self.vectorstore:
            self.create_or_load_vectorstore([])
        self.vectorstore.add_documents(documents)
        self.vectorstore.save_local(str(self.persist_directory))
        texts = [d.page_content.strip() for d in documents if d.page_content.strip()]
        try:
            if not self.sparse_retriever:
                self.sparse_retriever = BM25Retriever.from_texts(texts)
            else:
                self.sparse_retriever.add_texts(texts)
            self.sparse_retriever.k = 6
        except Exception as e:
            logger.warning(f"BM25 update failed: {e}")
            self.sparse_retriever = None
        self.hybrid_retriever = (
            EnsembleRetriever(
                retrievers=[self.dense_retriever, self.sparse_retriever],
                weights=[0.7, 0.3]
            )
            if self.sparse_retriever
            else self.dense_retriever
        )


    def retrieve_hybrid(self, query: str, k: int = 4, use_reranking: bool = True) -> List[Document]:
        if not self.vectorstore:
            self.create_or_load_vectorstore([])
        docs = self.hybrid_retriever.invoke(query)[:k*2]
        return self.rerank_documents(docs, query)[:k] if use_reranking else docs[:k]

    def retrieve_enhanced(self, query: str, k: int = 4, enhancer: Optional[QueryEnhancer] = None) -> List[Document]:
        if not self.vectorstore:
            self.create_or_load_vectorstore([])
        if not enhancer:
            return self.retrieve_hybrid(query, k=k, use_reranking=True)
        enhanced_queries = enhancer.get_enhanced_queries(query)
        all_docs: List[Document] = []
        seen = set()
        for q in enhanced_queries:
            docs = self.retrieve_hybrid(q, k=k, use_reranking=False)
            for doc in docs:
                h = hash(doc.page_content[:200])
                if h not in seen:
                    seen.add(h)
                    all_docs.append(doc)
        if len(all_docs) > k:
            return self.rerank_documents(all_docs, query)[:k]
        return all_docs[:k]

    def smart_retrieve(self, query: str, k: int = 4, enhancer: Optional[QueryEnhancer] = None) -> List[Document]:
        if len(query.split()) < 10:
            return self.retrieve_enhanced(query, k=k, enhancer=enhancer)
        else:
            return self.retrieve_hybrid(query, k=k, use_reranking=True)
