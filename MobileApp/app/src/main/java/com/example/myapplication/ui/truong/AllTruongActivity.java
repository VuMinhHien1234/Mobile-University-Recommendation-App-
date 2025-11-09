package com.example.myapplication.ui.truong;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.TruongAdapterTotal;
import com.example.myapplication.ui.home.HomeViewModel;

public class AllTruongActivity extends AppCompatActivity{
    private HomeViewModel viewModel;
    private TruongAdapterTotal adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_truong);
        RecyclerView rcv = findViewById(R.id.recyclerViewTruong);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TruongAdapterTotal(R.layout.fragment_truongtotal2);
        rcv.setAdapter(adapter);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.getTruongList().observe(this, list -> {
            adapter.setData(list);
        });
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
        adapter.setOnItemClickListener(truong -> {
            Intent intent = new Intent(this, TruongDetailActivity.class);
            intent.putExtra("truong_code", truong.getCode());
            startActivity(intent);
        });
    }
}