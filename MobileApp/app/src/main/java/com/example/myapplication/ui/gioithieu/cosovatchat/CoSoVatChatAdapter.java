package com.example.myapplication.ui.gioithieu.cosovatchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.util.List;

public class CoSoVatChatAdapter extends RecyclerView.Adapter<CoSoVatChatViewHolder> {

    List<ItemCoSoVatChat> list;
    Context context;

    public CoSoVatChatAdapter(List<ItemCoSoVatChat> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CoSoVatChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CoSoVatChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cosovatchat, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CoSoVatChatViewHolder holder, int position) {
        ItemCoSoVatChat item = list.get(position);
        Glide.with(context).load(item.getImageUrl()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}