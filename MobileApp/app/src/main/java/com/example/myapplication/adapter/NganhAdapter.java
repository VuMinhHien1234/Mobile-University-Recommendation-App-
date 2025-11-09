package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Nganh;

import java.util.List;

public class NganhAdapter extends RecyclerView.Adapter<NganhAdapter.ViewHolder> {

    public interface OnNganhClickListener {
        void onNganhClick(Nganh nganh);
    }

    private final List<Nganh> nganhList;
    private int selectedPosition = 0;
    private final Context context;
    private final OnNganhClickListener listener;

    public NganhAdapter(Context context, List<Nganh> nganhList, OnNganhClickListener listener) {
        this.context = context;
        this.nganhList = nganhList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_nganh, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Nganh nganh = nganhList.get(position);
        holder.btnNganh.setText(nganh.getName());
        holder.btnNganh.setSelected(selectedPosition == position);
        holder.btnNganh.setOnClickListener(v -> {
            int previous = selectedPosition;
            selectedPosition = holder.getBindingAdapterPosition();
            notifyItemChanged(previous);
            notifyItemChanged(selectedPosition);
            listener.onNganhClick(nganh);
        });
    }

    @Override
    public int getItemCount() {
        return nganhList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button btnNganh;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnNganh = itemView.findViewById(R.id.btnNganh);
        }
    }
}
