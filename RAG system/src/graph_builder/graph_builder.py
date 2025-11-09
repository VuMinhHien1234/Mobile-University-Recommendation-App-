from langgraph.graph import StateGraph, END
from src.state.rag_state import RAGState
from src.node.nodes import RAGNodes


class GraphBuilder:
    def __init__(self, vector_store, llm, enhancer=None):
        self.nodes = RAGNodes(vector_store, llm, enhancer)
        self.graph = None

    def build(self):
        builder = StateGraph(RAGState)
        builder.add_node("retrieve", self.nodes.retrieve_docs)
        builder.add_node("generate", self.nodes.generate_answer)  
        builder.set_entry_point("retrieve")
        builder.add_edge("retrieve", "generate")  
        builder.add_edge("generate", END)         
        self.graph = builder.compile()
        return self.graph

    def run(self, question: str) -> dict:
        if not self.graph:
            self.build()
        result = self.graph.invoke(RAGState(question=question))
        self._last_run = result  
        return result