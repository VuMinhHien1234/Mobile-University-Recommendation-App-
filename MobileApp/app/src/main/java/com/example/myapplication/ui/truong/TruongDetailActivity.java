package com.example.myapplication.ui.truong;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.AnhAdapter;
import com.example.myapplication.model.Truong;
import com.example.myapplication.ui.home.HomeViewModel;
import com.example.myapplication.ui.truong.Program.AllProgramActivity;

public class TruongDetailActivity extends AppCompatActivity {

    private TextView tvTen, tvMoTa, tvHocPhi, tvDanhGia,tvFeature, tvEnvironment, tvNetworking, tvTeaching;
    private RecyclerView rvAnh;
    private ImageButton btnBack;
    private Button btnDetail;
    private HomeViewModel viewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truong_detail);

        tvTen = findViewById(R.id.tvTenTruong);
        tvMoTa = findViewById(R.id.tvMoTa);
        tvHocPhi = findViewById(R.id.tvHocPhi);
        tvDanhGia = findViewById(R.id.tvDanhGia);
        rvAnh = findViewById(R.id.rvAnh);
        tvFeature = findViewById(R.id.feature);
        tvEnvironment = findViewById(R.id.environment);
        tvNetworking = findViewById(R.id.networking);
        tvTeaching = findViewById(R.id.teaching);

        btnBack = findViewById(R.id.viewTopbar).findViewById(R.id.btnBack);
        btnDetail = findViewById(R.id.btnDetail);
        btnDetail.setOnClickListener(v -> {
            String truongCode = getIntent().getStringExtra("truong_code");
            Intent intent = new Intent(TruongDetailActivity.this, AllProgramActivity.class);
            intent.putExtra("truong_code", truongCode);
            startActivity(intent);
        });

        btnBack.setOnClickListener(v -> finish());
        rvAnh.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        String truongCode = getIntent().getStringExtra("truong_code");
        viewModel.getTruongList().observe(this, list -> {
            if (list == null || truongCode == null) return;
            for (Truong t : list) {
                if (t.getCode().equalsIgnoreCase(truongCode)) {
                    Log.d("TruongDetailActivity", "Đã lấy được trường: " + t.getTen());
                    tvTen.setText(t.getTen());
                    tvMoTa.setText(t.getMoTa());
                    tvHocPhi.setText(t.getHocPhi());
                    tvDanhGia.setText(String.valueOf(t.getRating()));
                    rvAnh.setAdapter(new AnhAdapter(this, t.getDsAnh()));
                    tvFeature.setText(t.getHighlight());
                    tvTeaching.setText(t.getTeaching_method());
                    tvNetworking.setText(t.getNetwork());
                    tvEnvironment.setText(t.getEnvironment());
                    break;
                }
            }
        });
    }

}
