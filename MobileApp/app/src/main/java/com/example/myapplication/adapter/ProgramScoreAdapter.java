package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.ProgramScore;

import java.util.List;

public class ProgramScoreAdapter extends RecyclerView.Adapter<ProgramScoreAdapter.VH> {
    private final List<ProgramScore> data;

    public ProgramScoreAdapter(List<ProgramScore> data) {
        this.data = data;
    }

    @NonNull
    @Override public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_program_score, parent, false);
        return new VH(v);
    }

    @Override public void onBindViewHolder(@NonNull VH h, int position) {
        ProgramScore it = data.get(position);
        h.tvName.setText(it.name);
        h.tvCode.setText(it.code);
        h.tvCombo.setText(it.combo);
        h.tvScore.setText(it.score);
    }

    @Override public int getItemCount() { return data == null ? 0 : data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        final TextView tvName, tvCode, tvCombo, tvScore;
        VH(@NonNull View itemView) {
            super(itemView);
            tvName  = itemView.findViewById(R.id.tvName);
            tvCode  = itemView.findViewById(R.id.tvCode);
            tvCombo = itemView.findViewById(R.id.tvCombo);
            tvScore = itemView.findViewById(R.id.tvScore);
        }
    }
}

