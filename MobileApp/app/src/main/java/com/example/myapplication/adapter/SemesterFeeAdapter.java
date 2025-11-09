package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.SemesterFee;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class SemesterFeeAdapter extends RecyclerView.Adapter<SemesterFeeAdapter.VH> {

    public interface OnSemesterClickListener {
        void onSemesterClick(SemesterFee item);
    }

    private final List<SemesterFee> data;
    private final NumberFormat vnd = NumberFormat.getInstance(new Locale("vi", "VN"));
    private final OnSemesterClickListener listener;

    public SemesterFeeAdapter(List<SemesterFee> data, OnSemesterClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_semester_fee, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        SemesterFee s = data.get(position);
        h.tvKi.setText(s.getKi());
        h.tvDaDK.setText(vnd.format(s.getTongTinChiDaDK()));
        h.tvSoTC.setText(String.valueOf(s.getTongSoTinChi()));
        h.tvHocPhi.setText(vnd.format(s.getTongHocPhi()));
        h.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onSemesterClick(s);
        });
    }

    @Override public int getItemCount() { return data == null ? 0 : data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvKi, tvDaDK, tvSoTC, tvHocPhi;
        VH(@NonNull View v) {
            super(v);
            tvKi    = v.findViewById(R.id.tvKi);
            tvDaDK  = v.findViewById(R.id.tvTongTCDaDK);
            tvSoTC  = v.findViewById(R.id.tvTongSoTC);
            tvHocPhi= v.findViewById(R.id.tvTongHocPhi);
        }
    }
}

