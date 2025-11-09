from typing import List, Dict, Any
from langchain_openai import ChatOpenAI
from langchain_core.prompts import ChatPromptTemplate
from langchain_core.output_parsers import JsonOutputParser
import logging

logger = logging.getLogger(__name__)

class QueryEnhancer:
    def __init__(self, model_name: str = "gpt-4o-mini", temperature: float = 0.0):
        self.llm = ChatOpenAI(model=model_name, temperature=temperature)
        self.parser = JsonOutputParser()

    def enhance(self, query: str, use_expansion: bool = False) -> Dict[str, Any]: 
        if not use_expansion:
            return {"hyde": query, "sub_questions": [query], "keywords": []}

        prompt = ChatPromptTemplate.from_template(
            """
            Bạn là chuyên gia tìm kiếm. Hãy thực hiện 3 nhiệm vụ dưới đây cho câu hỏi:
            "{query}"
             **HyDE**: Viết một đoạn văn ngắn (100-150 từ) trả lời câu hỏi như chuyên gia.
             **Decomposition**: Phân rã câu hỏi thành tối đa 3 câu hỏi con đơn giản hơn.
             **Expansion**: Đề xuất 5 từ khóa hoặc cụm từ mở rộng liên quan chặt chẽ đến nội dung.

            Trả về **JSON** đúng định dạng sau:
            {{
              "hyde": "Đoạn văn trả lời...",
              "sub_questions": ["câu 1", "câu 2", ...],
              "keywords": ["từ khóa 1", "từ khóa 2", ...]
            }}
            """
        )
        chain = prompt | self.llm | self.parser
        try:
            result = chain.invoke({"query": query})
            logger.info(" Query enhanced thành công.")
            return result
        except Exception as e:
            logger.error(f" Query enhancement thất bại: {e}")
            return {"hyde": query, "sub_questions": [query], "keywords": []}
        
        
    def get_enhanced_queries(self, query: str, use_expansion: bool = False, num_variations: int = 3) -> List[str]:
        result = self.enhance(query, use_expansion=use_expansion)
        hyde_text = result.get("hyde", "")
        subs = result.get("sub_questions", [])
        keywords = result.get("keywords", [])
        expanded = [query]
        if hyde_text.strip() and hyde_text != query:
            expanded.append(hyde_text)

        for s in subs:
            if s.strip() and s not in expanded:
                expanded.append(s)

        for kw in keywords:
            new_q = f"{query} {kw}".strip()
            if new_q not in expanded:
                expanded.append(new_q)
        expanded = expanded[:num_variations]

        logger.info(f"Tổng số truy vấn mở rộng: {len(expanded)}")
        return expanded

