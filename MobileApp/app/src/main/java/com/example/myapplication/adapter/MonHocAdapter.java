package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.MonHoc;

import java.util.List;

public class MonHocAdapter extends RecyclerView.Adapter<MonHocAdapter.MonHocViewHolder> {

    private final List<MonHoc> monHocList;

    public MonHocAdapter(List<MonHoc> monHocList) {
        this.monHocList = monHocList;
    }

    @NonNull
    @Override
    public MonHocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mon, parent, false);
        return new MonHocViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonHocViewHolder holder, int position) {
        MonHoc monHoc = monHocList.get(position);
        holder.tvTinChi.setText(monHoc.getCredits() + " tín chỉ");
        holder.tvTenMon.setText(monHoc.getName());
    }

    @Override
    public int getItemCount() {
        return monHocList.size();
    }

    static class MonHocViewHolder extends RecyclerView.ViewHolder {
        TextView tvTinChi, tvTenMon;

        public MonHocViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTinChi = itemView.findViewById(R.id.tvTinChi);
            tvTenMon = itemView.findViewById(R.id.tvTenMon);
        }
    }
}