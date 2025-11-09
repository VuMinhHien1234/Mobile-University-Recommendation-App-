package com.example.myapplication.ui.favourite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {

    private List<FavouriteItem> items = new ArrayList<>();

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite, parent, false);
        return new FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        FavouriteItem item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<FavouriteItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    static class FavouriteViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView nameSchoolView;
        private final TextView textFavouriteView;

        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageFavourite);
            nameSchoolView = itemView.findViewById(R.id.textNameSchool);
            // Sửa ID tại đây
            textFavouriteView = itemView.findViewById(R.id.textFavourite);
        }

        public void bind(FavouriteItem item) {
            // Giả sử getTitle() là tên trường và getDescription() là địa chỉ
            nameSchoolView.setText(item.getTitle());
            textFavouriteView.setText(item.getDescription());

            Glide.with(itemView.getContext())
                    .load(item.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_background) // Ảnh tạm thời
                    .error(R.drawable.ic_launcher_foreground) // Ảnh khi có lỗi
                    .into(imageView);
        }
    }
}
