package com.example.myapplication.ui.sinhvien.cauhoithuonggap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.FAQViewHolder> {

    private List<FAQItem> faqList = new ArrayList<>();

    @NonNull
    @Override
    public FAQViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq, parent, false);
        return new FAQViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FAQViewHolder holder, int position) {
        FAQItem currentItem = faqList.get(position);
        holder.bind(currentItem);
    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }

    public void submitList(List<FAQItem> list) {
        faqList = list;
        notifyDataSetChanged();
    }

    static class FAQViewHolder extends RecyclerView.ViewHolder {
        private final TextView questionText;
        private final TextView answerText;

        public FAQViewHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.question_text);
            answerText = itemView.findViewById(R.id.answer_text);
        }

        public void bind(FAQItem item) {
            questionText.setText(item.getQuestion());
            answerText.setText(item.getAnswer());

            // Logic to expand/collapse the answer
            answerText.setVisibility(View.GONE);
            questionText.setOnClickListener(v -> {
                boolean isVisible = answerText.getVisibility() == View.VISIBLE;
                answerText.setVisibility(isVisible ? View.GONE : View.VISIBLE);
            });
        }
    }
}
