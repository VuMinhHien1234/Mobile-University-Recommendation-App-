package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class PolicyAdapter extends ListAdapter<String, PolicyAdapter.PolicyVH> {

    public interface OnItemClick {
        void onClick(String title, int position);
    }

    private OnItemClick onItemClick;

    public PolicyAdapter() {
        super(DIFF_CALLBACK);
    }

    public void setOnItemClick(OnItemClick l) {
        this.onItemClick = l;
    }

    private static final DiffUtil.ItemCallback<String> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<String>() {
                @Override
                public boolean areItemsTheSame(@NonNull String oldI, @NonNull String newI) {
                    return oldI.equals(newI);
                }

                @Override
                public boolean areContentsTheSame(@NonNull String oldI, @NonNull String newI) {
                    return oldI.equals(newI);
                }
            };

    @NonNull
    @Override
    public PolicyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_policy, parent, false);
        return new PolicyVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PolicyVH h, int position) {
        String title = getItem(position);
        h.title.setText(title);
        h.root.setOnClickListener(v -> {
            if (onItemClick != null) {
                int pos = h.getBindingAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    onItemClick.onClick(title, pos);
                }
            }
        });
    }

    static class PolicyVH extends RecyclerView.ViewHolder {
        final View root;
        final TextView title;

        PolicyVH(@NonNull View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.root1);
            title = itemView.findViewById(R.id.tvTitle1);
        }
    }
}
