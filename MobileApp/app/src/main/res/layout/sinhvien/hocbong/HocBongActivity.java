package com.example.myapplication.ui.sinhvien.hocbong;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class HocBongActivity extends AppCompatActivity {
    private HocBongAdapter adapter;
    private final List<ItemHocBong> items = new ArrayList<>();

    private HocBongViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hocbong);
        RecyclerView recyclerView = findViewById(R.id.rvHocBong);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HocBongAdapter(items, this);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(HocBongViewModel.class);
        viewModel.getHBList().observe(this, newItems -> {
            items.clear();
            items.addAll(newItems);
            adapter.notifyDataSetChanged();
        });
        viewModel.loadData("PTA");
    }
}
