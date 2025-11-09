package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.MajorFee;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class FeeAdapter extends RecyclerView.Adapter<FeeAdapter.FeeVH> {

    // ====== Thêm callback click ======
    public interface OnItemClickListener {
        void onItemClick(MajorFee item);
    }

    private final List<MajorFee> data;
    private final OnItemClickListener listener;   // giữ listener
    private final NumberFormat vndFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

    // Constructor mới: nhận cả listener
    public FeeAdapter(List<MajorFee> data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FeeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_major_fee, parent, false);
        return new FeeVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FeeVH h, int position) {
        MajorFee m = data.get(position);
        h.tvTen.setText(m.getTenNganh());
        h.tvMa.setText(m.getMaNganh());
        h.tvHocPhi.setText(vndFormat.format(m.getHocPhi()));

        h.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(m);
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class FeeVH extends RecyclerView.ViewHolder {
        TextView tvTen, tvMa, tvHocPhi;
        FeeVH(@NonNull View itemView) {
            super(itemView);
            tvTen   = itemView.findViewById(R.id.tvTenNganh);
            tvMa    = itemView.findViewById(R.id.tvMaNganh);
            tvHocPhi= itemView.findViewById(R.id.tvHocPhi);
        }
    }
}
