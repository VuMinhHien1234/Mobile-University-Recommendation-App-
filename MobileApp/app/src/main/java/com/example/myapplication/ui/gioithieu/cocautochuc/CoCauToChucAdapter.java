package com.example.myapplication.ui.gioithieu.cocautochuc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.util.List;

public class CoCauToChucAdapter extends RecyclerView.Adapter<CoCauToChucViewHolder> {

    List<CoCauToChuc> coCauToChuc;
    Context context;

    public CoCauToChucAdapter(List<CoCauToChuc> coCauToChuc, Context context) {
        this.coCauToChuc = coCauToChuc;
        this.context = context;
    }

    @NonNull
    @Override
    public CoCauToChucViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CoCauToChucViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cocautochuc, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CoCauToChucViewHolder holder, int position) {
        CoCauToChuc coCauToChuc = this.coCauToChuc.get(position);
        Glide.with(context).load(coCauToChuc.getLogoUrl()).into(holder.imageCCTC);
        holder.textTitleCCTC.setText(coCauToChuc.getTitleCCTC());
        holder.textCCTCPhone.setText(coCauToChuc.getPhoneCCTC());
        holder.textCCTCMail.setText(coCauToChuc.getEmailCCTC());
        holder.textCCTCLocation.setText(coCauToChuc.getAddressCCTC());
    }

    @Override
    public int getItemCount() {
        return coCauToChuc.size();
    }
}