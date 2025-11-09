package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.util.List;

public class AnhAdapter extends RecyclerView.Adapter<AnhAdapter.AnhViewHolder> {

    private final Context context;
    private final List<String> list;

    public AnhAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AnhViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_anh, parent, false);
        return new AnhViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnhViewHolder holder, int position) {
        String imgPath = list.get(position);
        try {
            int resId = Integer.parseInt(imgPath);
            Glide.with(context).load(resId).into(holder.img);
        } catch (NumberFormatException e) {
            Glide.with(context).load(imgPath).into(holder.img);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class AnhViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public AnhViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
        }
    }
}
