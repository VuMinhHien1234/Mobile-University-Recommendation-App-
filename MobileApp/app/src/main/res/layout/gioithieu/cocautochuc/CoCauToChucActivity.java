package com.example.myapplication.ui.gioithieu.cocautochuc;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CoCauToChucActivity extends AppCompatActivity {
    private CoCauToChucViewModel viewModel;
    private RecyclerView recyclerView;
    private CoCauToChucAdapter adapter;
    private final List<CoCauToChuc> coCauToChuc = new ArrayList<>();
    private ImageView imageSoDoToChuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocautochuc);

        imageSoDoToChuc = findViewById(R.id.imageSoDoToChuc);
        recyclerView = findViewById(R.id.rvCCTC);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new CoCauToChucAdapter(coCauToChuc, this);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(CoCauToChucViewModel.class);

        viewModel.getCoCauToChucData().observe(this, data -> {
            if (data != null) {
                if (!data.getMainImageUrl().isEmpty()) {
                    Glide.with(this).load(data.getMainImageUrl()).into(imageSoDoToChuc);
                }
                coCauToChuc.clear();
                coCauToChuc.addAll(data.getUnits());
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Không tìm thấy dữ liệu cơ cấu tổ chức", Toast.LENGTH_SHORT).show();
            }
        });

        // Tải dữ liệu với schoolId mặc định là "PTA" nếu không có từ Intent
        String schoolId = getIntent().getStringExtra("schoolId");
        if (schoolId != null && !schoolId.isEmpty()) {
            viewModel.loadData(schoolId);
        } else {
            viewModel.loadData("PTA");
        }
    }
}