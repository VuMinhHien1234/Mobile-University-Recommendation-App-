package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Professor;

import java.util.List;

public class ProfessorAdapter extends RecyclerView.Adapter<ProfessorAdapter.VH> {

    private final List<Professor> data;

    public ProfessorAdapter(List<Professor> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_professor_row, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        // Lấy đối tượng Professor từ class model riêng
        Professor p = data.get(position);
        h.name.setText(p.name);
        h.major.setText(p.major);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView name, major;
        VH(@NonNull View itemView) {
            super(itemView);

            name  = itemView.findViewById(R.id.tvName);
            major = itemView.findViewById(R.id.tvMajor);
        }
    }
}