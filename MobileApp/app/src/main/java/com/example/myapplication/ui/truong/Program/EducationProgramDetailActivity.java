package com.example.myapplication.ui.truong.Program;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.KiHocAdapter;
import com.example.myapplication.model.Nganh;
import com.example.myapplication.ui.xettuyen.XetTuyenSomActivity;

public class EducationProgramDetailActivity extends AppCompatActivity {
    private EducationProgramDetailViewModel viewModel;
    private EducationProgramViewModel nganhViewModel;
    private ImageButton btnBack;
    private TextView tvTenNganh, tvMoTa, tvDieuKien, tvCoHoi, tvMaNganh, tvThoiGian;
    private RecyclerView rvKiHoc;
    private Button btnDetails;
    private KiHocAdapter kiHocAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_itemprogramdetails);
        String schoolId = getIntent().getStringExtra("truong_code");
        String nganhId = getIntent().getStringExtra("nganh_code");

        nganhViewModel = new ViewModelProvider(this).get(EducationProgramViewModel.class);
        viewModel = new ViewModelProvider(this).get(EducationProgramDetailViewModel.class);

        btnBack = findViewById(R.id.viewTopbar).findViewById(R.id.btnBack);
        btnDetails = findViewById(R.id.btnDetail);
        tvTenNganh = findViewById(R.id.tvTenNganh);
        tvMoTa = findViewById(R.id.tvChitietTongquan);
        tvDieuKien = findViewById(R.id.tvChitietDKTuyensinh);
        tvCoHoi = findViewById(R.id.tvChitietNgheNghiep);
        tvMaNganh = findViewById(R.id.tvMaNganh);
        tvThoiGian = findViewById(R.id.tvThoiGian);
        rvKiHoc = findViewById(R.id.rvKyHoc);

        kiHocAdapter = new KiHocAdapter();
        rvKiHoc.setLayoutManager(new LinearLayoutManager(this));
        rvKiHoc.setAdapter(kiHocAdapter);

        btnBack.setOnClickListener(v -> {
            finish();
        });
        btnDetails.setOnClickListener(v -> {
            Intent intent = new Intent(this, XetTuyenSomActivity.class);
            startActivity(intent);
        });

        nganhViewModel.loadData(schoolId);
        nganhViewModel.getNganhList().observe(this, nganhs -> {Nganh nganh = nganhViewModel.getNganhById(nganhId);
            tvTenNganh.setText(nganh.getName());
            tvMoTa.setText("Description");
            tvDieuKien.setText("Điều kiện: " );
            tvCoHoi.setText("Cơ hội nghề nghiệp: " );
            tvMaNganh.setText(nganh.getId());
            tvThoiGian.setText("Thoi gian hoc :"+nganh.getDuration());

        });
        viewModel.loadCurriculum(schoolId, nganhId);
        viewModel.getMonhoc().observe(this, kiHocList -> {

            if (kiHocList != null && !kiHocList.isEmpty()) {
                kiHocAdapter.setData(kiHocList);
            } else {
                Toast.makeText(this, "Không tải được dữ liệu chương trình học", Toast.LENGTH_SHORT).show();
            }
        });

    }
}