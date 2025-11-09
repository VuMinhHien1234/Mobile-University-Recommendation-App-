from typing import List, Optional
from src.state.rag_state import RAGState
from src.retrieval_enhancer.query_enhancer import QueryEnhancer
from langchain_core.documents import Document
from langchain_core.tools import Tool
from langchain_core.messages import HumanMessage, SystemMessage
from langgraph.prebuilt import create_react_agent
from langchain_community.utilities import WikipediaAPIWrapper
from langchain_community.tools.wikipedia.tool import WikipediaQueryRun
from src.vectorstore.vectorstore import AdvancedVectorStore

class RAGNodes:
    def __init__(self, vector_store: AdvancedVectorStore, llm, enhancer: Optional[QueryEnhancer] = None):
        self.vector_store = vector_store
        self.llm = llm
        self.enhancer = enhancer
        self._agent = None

    def retrieve_docs(self, state: RAGState) -> RAGState:
        query = state.question
        if self.enhancer:
            enhanced_queries = self.enhancer.get_enhanced_queries(query, use_expansion=True)
        else:
            enhanced_queries = [query]
        all_docs: List[Document] = []
        seen = set()

        for q in enhanced_queries:
            docs = self.vector_store.smart_retrieve(q, enhancer=self.enhancer)
            for doc in docs:
                key = hash(doc.page_content[:200])
                if key not in seen:
                    seen.add(key)
                    all_docs.append(doc)

        return RAGState(
            question=query,
            retrieved_docs=all_docs[:12]
        )

    def _build_tools(self) -> List[Tool]:
        def rag_search(query: str) -> str:
            docs = self.vector_store.smart_retrieve(query, enhancer=self.enhancer)
            if not docs:
                return "Không tìm thấy tài liệu liên quan."
            return "\n\n".join([
                f"[{i+1}] {d.metadata.get('source', 'Unknown')}\n{d.page_content[:1200]}"
                for i, d in enumerate(docs)
            ])
        rag_tool = Tool(
            name="rag_search",
            description="Tìm thông tin trong cơ sở dữ liệu nội bộ (PDFs, URLs, docs). Dùng công cụ này trước khi hỏi Wikipedia.",
            func=rag_search,
        )
        wiki = WikipediaQueryRun(api_wrapper=WikipediaAPIWrapper(top_k_results=2))
        wikipedia_tool = Tool(
            name="wikipedia",
            description="Tìm thông tin chung trên Wikipedia.",
            func=wiki.run,
        )

        return [rag_tool, wikipedia_tool]

    def _build_agent(self):
        tools = self._build_tools()
        system_prompt = (
            "Bạn là một tác nhân RAG thông minh. "
            "Hãy sử dụng công cụ rag_search để trả lời câu hỏi dựa trên tài liệu của người dùng. "
            "Chỉ sử dụng công cụ wikipedia khi cần tra cứu kiến thức chung. "
            "Hãy suy nghĩ từng bước một. Trả về câu trả lời cuối cùng rõ ràng, súc tích."
        )
        system_msg = SystemMessage(content=system_prompt)
        self._agent = create_react_agent(
            self.llm,
            tools=tools,
            messages_modifier=system_msg
        )

    def generate_answer(self, state: RAGState) -> RAGState:
        if self._agent is None:
            self._build_agent()
        if state.retrieved_docs:
            result = []
            for i, d in enumerate(state.retrieved_docs):
                result.append(f"[{i+1}] {d.page_content[:1500]}")
            context = "\n\n".join(result)
        else:
            context = "No documents."

        user_prompt = f"""Dùng tài liệu sau để trả lời.
Tài liệu:
{context}
Câu hỏi: {state.question}
Suy nghĩ từng bước và chỉ trả lời kết quả cuối cùng."""

        result = self._agent.invoke({
            "messages": [HumanMessage(content=user_prompt)]
        })

        answer = result["messages"][-1].content if result.get("messages") else "No answer."

        return RAGState(
            question=state.question,
            retrieved_docs=state.retrieved_docs,
            final_answer=answer
        )
