package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.TableRowItem;

import java.util.List;

public class TableRowAdapter extends RecyclerView.Adapter<TableRowAdapter.VH> {

    private final List<TableRowItem> data;

    public TableRowAdapter(List<TableRowItem> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_table_row, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        TableRowItem item = data.get(position);
        holder.tvSTT.setText(String.valueOf(item.getStt()));
        holder.tvCol1.setText(item.getCol1());
        holder.tvCol2.setText(item.getCol2());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvSTT, tvCol1, tvCol2;

        VH(@NonNull View v) {
            super(v);
            tvSTT = v.findViewById(R.id.tvSTT);
            tvCol1 = v.findViewById(R.id.tvCol1);
            tvCol2 = v.findViewById(R.id.tvCol2);
        }
    }
}
