package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.KiHoc;

import java.util.ArrayList;
import java.util.List;

public class KiHocAdapter extends RecyclerView.Adapter<KiHocAdapter.KiHocViewHolder> {
    private final List<KiHoc> kiHocList = new ArrayList<>();

    public KiHocAdapter() {
    }

    public void setData(List<KiHoc> list) {
        kiHocList.clear();
        kiHocList.addAll(list);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public KiHocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_kyhoc, parent, false);
        return new KiHocViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KiHocViewHolder holder, int position) {
        KiHoc kiHoc = kiHocList.get(position);
        holder.tvTenKi.setText(kiHoc.getTen());
        MonHocAdapter monHocAdapter = new MonHocAdapter(kiHoc.getMonHocList());
        holder.rvMonHoc.setLayoutManager(
                new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        holder.rvMonHoc.setAdapter(monHocAdapter);
    }

    @Override
    public int getItemCount() {
        return kiHocList.size();
    }

    static class KiHocViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenKi;
        RecyclerView rvMonHoc;

        public KiHocViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenKi = itemView.findViewById(R.id.tvTenKy);
            rvMonHoc = itemView.findViewById(R.id.rvMonHoc);
        }
    }
}