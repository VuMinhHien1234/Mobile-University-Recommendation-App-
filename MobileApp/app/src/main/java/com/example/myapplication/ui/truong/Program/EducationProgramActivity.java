package com.example.myapplication.ui.truong.Program;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ChuongTrinhDaoTaoAdapter;
import com.example.myapplication.model.Nganh;

import java.util.ArrayList;
import java.util.List;

public class EducationProgramActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private RecyclerView rvProgams;
    private ChuongTrinhDaoTaoAdapter adapter;
    private EducationProgramViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuongtrinhdaotao);
        btnBack = findViewById(R.id.viewTopbar).findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
        String truongCode = getIntent().getStringExtra("truong_code");
        Log.d("EducationProgram", "Truong code: " + truongCode);
        rvProgams = findViewById(R.id.rvPrograms);
        rvProgams.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new ChuongTrinhDaoTaoAdapter(new ArrayList<>(),nganh->{
            Intent intent = new Intent(EducationProgramActivity.this,EducationProgramDetailActivity.class);
            intent.putExtra("truong_code", truongCode);
            intent.putExtra("nganh_code",nganh.getId());
            startActivity(intent);
        });
        rvProgams.setAdapter(adapter);
        viewModel = new ViewModelProvider(this).get(EducationProgramViewModel.class);
        viewModel.loadData(truongCode);
        viewModel.getNganhList().observe(this, list -> {
            adapter.setData(list);
        });
    }
}
