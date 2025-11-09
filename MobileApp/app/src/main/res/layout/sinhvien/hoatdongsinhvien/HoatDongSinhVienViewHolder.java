package com.example.myapplication.ui.sinhvien.hoatdongsinhvien;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

public class HoatDongSinhVienViewHolder extends RecyclerView.ViewHolder {
    ImageView imageEvent;
    ImageView imageCalendar;
    TextView textDateHDSV;
    TextView textDesHDSV;

    public HoatDongSinhVienViewHolder(@NonNull View itemView) {
        super(itemView);
        imageEvent = itemView.findViewById(R.id.imageHDSV);
        imageCalendar = itemView.findViewById(R.id.imageDateHDSV);
        textDateHDSV = itemView.findViewById(R.id.textDateHDSV);
        textDesHDSV = itemView.findViewById(R.id.textDesHDSV);
    }

    public void bind(ItemHoatDongSinhVien item) {
        textDateHDSV.setText(item.getDate());
        textDesHDSV.setText(item.getDes());
        imageCalendar.setImageResource(item.getIconCalendar());

        if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
            Picasso.get()
                    .load(item.getImageUrl())
                    .error(R.drawable.ic_error_image)
                    .into(imageEvent);
        } else {
            imageEvent.setImageResource(R.drawable.ic_default_image);
        }
    }
}