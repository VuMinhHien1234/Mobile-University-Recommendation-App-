from flask import Flask, request, jsonify, send_from_directory
import os, sys, time
from pathlib import Path
sys.path.append(str(Path(__file__).parent.parent))

from src.vectorstore.vectorstore import AdvancedVectorStore
from src.retrieval_enhancer.query_enhancer import QueryEnhancer
from src.graph_builder.graph_builder import GraphBuilder
from src.document_ingestion.document_processor import DocumentProcessor

app = Flask(__name__, static_folder='.', static_url_path='')
def get_rag_system():
    try:
        vector_store = AdvancedVectorStore(
            persist_directory="./vectorstore/faiss_index",
            embedding_model="text-embedding-3-small",
            llm_model="gpt-4o-mini"
        )
        enhancer = QueryEnhancer()
        graph_builder = GraphBuilder(
            vector_store=vector_store,
            llm=vector_store.llm,
            enhancer=enhancer
        )
        graph = graph_builder.build()
        processor = DocumentProcessor(chunking_method="semantic")
        return {"vector_store": vector_store, "enhancer": enhancer, "graph": graph, "processor": processor}
    except Exception as e:
        print(f"Init failed: {e}")
        return None

rag_system = get_rag_system()

@app.route('/')
def serve_html():
    return send_from_directory('.', 'index.html')

@app.route('/upload', methods=['POST'])
def upload_files():
    if 'files[]' not in request.files:
        return jsonify({"error": "No files uploaded"}), 400
    files = request.files.getlist('files[]')
    docs = []
    for file in files:
        file_path = os.path.join("/tmp", file.filename)
        file.save(file_path)
        chunks = rag_system["processor"].process([file_path])
        docs.extend(chunks)

    if docs:
        rag_system["vector_store"].add_documents(docs)
        return jsonify({"message": f"Added {len(docs)} chunks!"})
    return jsonify({"message": "No documents processed"})

@app.route('/ask', methods=['POST'])
def ask_question():
    data = request.get_json()
    question = data.get("question", "")
    if not question:
        return jsonify({"error": "Missing question"}), 400
    start = time.time()
    result = rag_system["graph"].invoke({"question": question})
    elapsed = time.time() - start
    answer = result.get("final_answer", "No answer.")
    docs = result.get("retrieved_docs", [])
    doc_previews = [
        {"source": d.metadata.get("source", "Unknown"), "content": d.page_content[:800]}
        for d in docs[:3]
    ]
    return jsonify({"answer": answer, "elapsed": round(elapsed, 2), "sources": doc_previews})

if __name__ == '__main__':
    app.run(debug=True, port=5000)
