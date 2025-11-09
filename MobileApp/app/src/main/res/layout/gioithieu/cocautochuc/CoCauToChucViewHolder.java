package com.example.myapplication.ui.gioithieu.cocautochuc;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class CoCauToChucViewHolder extends RecyclerView.ViewHolder {

    ImageView imageCCTC;
    TextView textTitleCCTC, textCCTCPhone, textCCTCMail, textCCTCLocation;

    public CoCauToChucViewHolder(@NonNull View itemView) {
        super(itemView);
        imageCCTC = itemView.findViewById(R.id.imageCCTC);
        textTitleCCTC = itemView.findViewById(R.id.textTitleCCTC);
        textCCTCPhone = itemView.findViewById(R.id.textCCTCPhone);
        textCCTCMail = itemView.findViewById(R.id.textCCTCMail);
        textCCTCLocation = itemView.findViewById(R.id.textCCTCLocation);
    }
}