package com.example.myapplication.ui.gioithieu.nguonnhanluc;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

public class NguonNhanLucActivity extends AppCompatActivity {
    private NguonNhanLucViewModel viewModel;
    private TextView descriptionTextView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguonnhanluc);

        // Khởi tạo UI elements
        descriptionTextView = findViewById(R.id.textNguonNhanLuc);
        imageView = findViewById(R.id.imageNguonNhanLuc);

        // Khởi tạo ViewModel
        viewModel = new ViewModelProvider(this).get(NguonNhanLucViewModel.class);

        // Quan sát dữ liệu từ ViewModel
        viewModel.getNguonNhanLucData().observe(this, item -> {
            if (item != null) {
                descriptionTextView.setText(item.getDescription());
                if (!item.getImageUrl().isEmpty()) {
                    Glide.with(this)
                            .load(item.getImageUrl())
                            .into(imageView);
                }
            } else {
                Toast.makeText(this, "Không tìm thấy dữ liệu nguồn nhân lực", Toast.LENGTH_SHORT).show();
            }
        });

        // Tải dữ liệu với schoolId mặc định là "PTA" nếu không có từ Intent
        String schoolId = getIntent().getStringExtra("schoolId");
        if (schoolId != null && !schoolId.isEmpty()) {
            viewModel.loadData(schoolId);
        } else {
            viewModel.loadData("PTA"); // Mặc định sử dụng "PTA" nếu không có schoolId
        }
    }
}