package com.example.myapplication.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.model.Truong;
import com.bumptech.glide.Glide;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
public class TruongAdapterTotal extends RecyclerView.Adapter<TruongAdapterTotal.VH> {

    private final int itemLayout;
    private final List<Truong> data = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Truong truong);
    }

    public TruongAdapterTotal(int itemLayout) {
        this.itemLayout = itemLayout;
    }
    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress,tvRating,tvHocPhi;
        ImageView ivAvatar;

        VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvTenTruong);
            tvAddress = itemView.findViewById(R.id.tvDiaChi);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvHocPhi = itemView.findViewById(R.id.tvHocPhi);
            ivAvatar = itemView.findViewById(R.id.imgTruong);

        }
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        this.listener = l;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Truong t = data.get(position);
        holder.tvName.setText(t.getTen());
        holder.tvRating.setText(String.valueOf(t.getRating()));
        holder.tvHocPhi.setText("Học phí: " +t.getHocPhi());
        holder.tvAddress.setText("Địa chỉ: " +t.getDiachi());
        if (t.getHinhAnhResId() != null && !t.getHinhAnhResId().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(t.getHinhAnhResId())
                    .centerCrop()
                    .into(holder.ivAvatar);
        } else {
            holder.ivAvatar.setImageResource(R.drawable.ic_launcher_background);
        }
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(t);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Truong> list) {
        data.clear();
        if (list != null && !list.isEmpty()) {
            data.addAll(list);
        }
        notifyDataSetChanged();
    }

}