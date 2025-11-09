package com.example.myapplication.ui.sinhvien.clb;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

public class CLBViewHolder extends RecyclerView.ViewHolder {
    ImageView logoImage;
    TextView clubNameText;
    TextView leaderNameText;
    TextView facebookLinkText;

    public CLBViewHolder(@NonNull View itemView) {
        super(itemView);
        logoImage = itemView.findViewById(R.id.imageCLB);
        clubNameText = itemView.findViewById(R.id.textNameCLB);
        leaderNameText = itemView.findViewById(R.id.textNameCN);
        facebookLinkText = itemView.findViewById(R.id.textLinkFace);
    }

    public void bind(ItemCLB item) {
        clubNameText.setText(item.getClubName());
        leaderNameText.setText(item.getLeaderName());
        facebookLinkText.setText(item.getFacebookLink());
        if (item.getLogoImage() != null && !item.getLogoImage().isEmpty()) {
            Picasso.get()
                    .load(item.getLogoImage())
                    .error(R.drawable.ic_error_image)
                    .into(logoImage);
        } else {
            logoImage.setImageResource(R.drawable.ic_default_image);
        }
    }
}