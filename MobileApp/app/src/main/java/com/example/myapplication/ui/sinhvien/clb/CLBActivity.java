package com.example.myapplication.ui.sinhvien.clb;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CLBActivity extends AppCompatActivity {
    private CLBViewModel clbViewModel;
    private CLBAdapter clbAdapter;
    private final List<ItemCLB> clbList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clb);
        RecyclerView rvCLB = findViewById(R.id.rvCLB);

        rvCLB.setLayoutManager(new GridLayoutManager(this, 2));
        clbAdapter = new CLBAdapter(clbList, this);
        rvCLB.setAdapter(clbAdapter);

        clbViewModel = new ViewModelProvider(this).get(CLBViewModel.class);

        clbViewModel.getCLBList().observe(this, itemCLBS -> {
            clbList.clear();
            clbList.addAll(itemCLBS);
            clbAdapter.notifyDataSetChanged();
        });

        clbViewModel.loadData("PTA");
    }
}
