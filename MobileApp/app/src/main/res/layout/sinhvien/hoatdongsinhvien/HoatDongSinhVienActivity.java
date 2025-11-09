package com.example.myapplication.ui.sinhvien.hoatdongsinhvien;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class HoatDongSinhVienActivity extends AppCompatActivity {
    private HoatDongSinhVienViewModel viewModel;
    private HoatDongSinhVienAdapter adapter;
    private final List<ItemHoatDongSinhVien> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoatdongsinhvien);

        RecyclerView recyclerView = findViewById(R.id.rvHoatDongSinhVien);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HoatDongSinhVienAdapter(items, this);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(HoatDongSinhVienViewModel.class);

        // Observe data từ ViewModel (bản chất: khi data fetch xong, update UI)
        viewModel.getHDSVList().observe(this, newItems -> {
            items.clear();
            items.addAll(newItems);
            adapter.notifyDataSetChanged();
        });

        // Load data cho school "PTA"
        viewModel.loadData("PTA");
    }
}