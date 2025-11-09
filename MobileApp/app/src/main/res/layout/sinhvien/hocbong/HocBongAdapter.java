package com.example.myapplication.ui.sinhvien.hocbong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class HocBongAdapter extends RecyclerView.Adapter<HocBongViewHolder>{

    List<ItemHocBong> list;
    Context context;

    public HocBongAdapter(List<ItemHocBong> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HocBongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HocBongViewHolder(LayoutInflater.from(context).inflate(R.layout.item_hocbong, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HocBongViewHolder holder, int position) {
        ItemHocBong item = list.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
