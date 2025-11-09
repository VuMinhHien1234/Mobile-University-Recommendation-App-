package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.DgnlProgramScore;

import java.util.List;

public class HsaProgramScoreAdapter extends RecyclerView.Adapter<HsaProgramScoreAdapter.VH> {
    private final List<DgnlProgramScore> data;

    public HsaProgramScoreAdapter(List<DgnlProgramScore> data) { this.data = data; }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_program_score_dgnlhn, parent, false);
        return new VH(v);
    }

    @Override public void onBindViewHolder(@NonNull VH h, int position) {
        DgnlProgramScore it = data.get(position);
        h.tvName.setText(it.name);
        h.tvCode.setText(it.code);
        h.tvScore.setText(it.score);
    }

    @Override public int getItemCount() { return data == null ? 0 : data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        final TextView tvName, tvCode, tvScore;
        VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvCode = itemView.findViewById(R.id.tvCode);
            tvScore = itemView.findViewById(R.id.tvScore);
        }
    }
}
