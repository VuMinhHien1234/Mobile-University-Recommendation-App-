package com.example.myapplication.ui.sinhvien.hocbong;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

public class HocBongViewHolder extends RecyclerView.ViewHolder {
    ImageView imgHB;
    TextView dateHB;
    TextView desHB;

    public HocBongViewHolder(@NonNull View itemView) {
        super(itemView);
        imgHB = itemView.findViewById(R.id.imageHB);
        dateHB = itemView.findViewById(R.id.textDateHB);
        desHB = itemView.findViewById(R.id.textDesHB);
    }

    public void bind(ItemHocBong item){
        dateHB.setText(item.getDateHB());
        desHB.setText(item.getDesHB());
        if(item.getImgHB() != null && !item.getImgHB().isEmpty()){
            Picasso.get()
                    .load(item.getImgHB())
                    .error(R.drawable.ic_error_image)
                    .into(imgHB);
        }else{
            imgHB.setImageResource(R.drawable.ic_default_image);
        }
    }
}
