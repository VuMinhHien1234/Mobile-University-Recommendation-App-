package com.example.myapplication.ui.sinhvien.hoatdongsinhvien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class HoatDongSinhVienAdapter extends RecyclerView.Adapter<HoatDongSinhVienViewHolder> {
    private final List<ItemHoatDongSinhVien> list;
    private final Context context;

    public HoatDongSinhVienAdapter(List<ItemHoatDongSinhVien> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HoatDongSinhVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HoatDongSinhVienViewHolder(LayoutInflater.from(context).inflate(R.layout.item_hoatdongsinhvien, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HoatDongSinhVienViewHolder holder, int position) {
        ItemHoatDongSinhVien item = list.get(position);
        holder.bind(item);  // Gọi bind để gán data
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}