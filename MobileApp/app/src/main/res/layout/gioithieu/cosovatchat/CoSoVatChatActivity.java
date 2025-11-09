package com.example.myapplication.ui.gioithieu.cosovatchat;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CoSoVatChatActivity extends AppCompatActivity {
    private CoSoVatChatViewModel viewModel;
    private RecyclerView recyclerView;
    private CoSoVatChatAdapter adapter;
    private final List<ItemCoSoVatChat> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosovatchat);

        TextView textCsvc = findViewById(R.id.textcsvc);
        ImageView imageCsvc1 = findViewById(R.id.imagecsvc1);
        recyclerView = findViewById(R.id.rvCSVC);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new CoSoVatChatAdapter(list, this);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(CoSoVatChatViewModel.class);

        viewModel.getCoSoVatChatData().observe(this, data -> {
            if (data != null && !data.getDescription().isEmpty()) {
                textCsvc.setText(data.getDescription());

                if (!data.getMainImageUrl().isEmpty()) {
                    Glide.with(this).load(data.getMainImageUrl()).into(imageCsvc1);
                }

                list.clear();
                for (String url : data.getImageUrls()) {
                    list.add(new ItemCoSoVatChat(url));
                }
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Không tìm thấy dữ liệu cơ sở vật chất", Toast.LENGTH_SHORT).show();
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