package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Nganh;

import org.jspecify.annotations.NonNull;

import java.util.List;

public class ChuongTrinhDaoTaoAdapter extends RecyclerView.Adapter<ChuongTrinhDaoTaoAdapter.ChuongTrinhDaoTaoViewHolder> {

    private List<Nganh> nganhList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Nganh nganh);
    }
    static class ChuongTrinhDaoTaoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenNganh, tvMaNganh;

        public ChuongTrinhDaoTaoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenNganh = itemView.findViewById(R.id.tvTenNganh);
            tvMaNganh = itemView.findViewById(R.id.tvMaNganh);
        }
    }

    public ChuongTrinhDaoTaoAdapter(List<Nganh> nganhList, OnItemClickListener listener) {
        this.nganhList = nganhList;
        this.listener = listener;
    }

    public void setData(List<Nganh> newList) {
        this.nganhList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChuongTrinhDaoTaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chuongtrinhdaotao, parent, false);
        return new ChuongTrinhDaoTaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChuongTrinhDaoTaoViewHolder holder, int position) {
        Nganh nganh = nganhList.get(position);
        holder.tvTenNganh.setText(nganh.getName());
        holder.tvMaNganh.setText(nganh.getId());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(nganh);
        });
    }

    @Override
    public int getItemCount() {
        return  nganhList.size();
    }
}
