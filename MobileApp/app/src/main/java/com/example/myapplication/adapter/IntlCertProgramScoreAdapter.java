package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.IntlCertProgramScore;

import java.util.List;

public class IntlCertProgramScoreAdapter extends RecyclerView.Adapter<IntlCertProgramScoreAdapter.VH> {
    private final List<IntlCertProgramScore> data;

    public IntlCertProgramScoreAdapter(List<IntlCertProgramScore> data) {
        this.data = data;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_program_score_intl, parent, false);
        return new VH(v);
    }

    @Override public void onBindViewHolder(@NonNull VH h, int position) {
        IntlCertProgramScore it = data.get(position);
        h.tvName.setText(it.name);
        h.tvCode.setText(it.code);
        h.tvSat.setText(it.sat);
        h.tvAct.setText(it.act);
    }

    @Override public int getItemCount() { return data == null ? 0 : data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        final TextView tvName, tvCode, tvSat, tvAct;
        VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvCode = itemView.findViewById(R.id.tvCode);
            tvSat  = itemView.findViewById(R.id.tvSat);
            tvAct  = itemView.findViewById(R.id.tvAct);
        }
    }
}
