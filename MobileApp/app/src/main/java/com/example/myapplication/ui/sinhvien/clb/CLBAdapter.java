package com.example.myapplication.ui.sinhvien.clb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class CLBAdapter extends RecyclerView.Adapter<CLBViewHolder> {
    private final List<ItemCLB> clbList;
    private final Context context;

    public CLBAdapter(List<ItemCLB> clbList, Context context) {
        this.clbList = clbList;
        this.context = context;
    }

    @NonNull
    @Override
    public CLBViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CLBViewHolder(LayoutInflater.from(context).inflate(R.layout.item_clb, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CLBViewHolder holder, int position) {
        ItemCLB item = clbList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return clbList.size();
    }
}