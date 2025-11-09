package com.example.myapplication.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.Partner;

import java.util.List;

public class PartnersAdapter extends RecyclerView.Adapter<PartnersAdapter.VH> {

    public interface OnClick {
        void onItem(Partner p, int pos);
    }

    private final List<Partner> data;
    private final OnClick onClick;

    public PartnersAdapter(List<Partner> data, OnClick onClick) {
        this.data = data;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_partner, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Partner p = data.get(position);
        h.tv.setText(p.name);
        h.tv.setPaintFlags(h.tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Dùng Glide để tải ảnh từ URL (String)
        Glide.with(h.img.getContext())
                .load(p.imageUrl) // <-- Tải từ URL
                .placeholder(R.drawable.ic_launcher_background) // Ảnh hiển thị trong khi tải
                .error(R.drawable.ic_launcher_background)       // Ảnh hiển thị nếu lỗi
                .into(h.img);

        h.itemView.setOnClickListener(v -> {
            if (onClick != null) {
                onClick.onItem(p, h.getBindingAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tv;

        VH(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgLogo);
            tv = itemView.findViewById(R.id.tvName);
        }
    }
}