package com.example.myapplication.ui.gioithieu.cosovatchat;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class CoSoVatChatViewHolder extends RecyclerView.ViewHolder {

    ImageView image;

    public CoSoVatChatViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.imageCSVC);
    }
}