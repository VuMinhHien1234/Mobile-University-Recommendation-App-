#!/bin/bash
echo "Khởi động RAG System (venv + Local FAISS)..."

# Kích hoạt venv
source .venv/bin/activate

# Chạy app
streamlit run streamlit_app.py