package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class ThongTinAdapter extends RecyclerView.Adapter<ThongTinAdapter.ThongTinViewHolder> {
    private final Context context;
    private final List<String> list;

    public ThongTinAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ThongTinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thongtin, parent, false);
        return new ThongTinViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ThongTinViewHolder holder, int position) {
        holder.tv.setText(list.get(position));
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    static class ThongTinViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public ThongTinViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tvThongTin);
        }
    }
}