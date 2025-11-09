package com.example.myapplication.ui.admission;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.PartnersAdapter;

public class PartnersActivity extends AppCompatActivity {

    private static final String TAG = "PartnersActivity";
    private PartnersViewModel viewModel;
    private RecyclerView rvPartners;
    private PartnersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners);

        viewModel = new ViewModelProvider(this).get(PartnersViewModel.class);

        setupToolbar();
        setupRecyclerView();
        observeViewModel();

        // Lấy schoolId từ Intent và yêu cầu ViewModel tải dữ liệu
        // Ví dụ: String schoolId = getIntent().getStringExtra("SCHOOL_ID");
        String schoolId = "PTA"; // Tạm thời dùng ID cứng để test
        viewModel.loadPartners(schoolId);
    }

    private void setupToolbar() {
        ImageButton back = findViewById(R.id.viewTopbar).findViewById(R.id.btnBack);
        if (back != null) back.setOnClickListener(v -> onBackPressed());
    }

    private void setupRecyclerView() {
        rvPartners = findViewById(R.id.rvPartners);
        rvPartners.setLayoutManager(new LinearLayoutManager(this));
    }

    private void observeViewModel() {
        viewModel.getPartnersList().observe(this, partners -> {
            if (partners != null) {
                Log.d(TAG, "✅ Observer received " + partners.size() + " partners.");
                adapter = new PartnersAdapter(partners, (p, pos) -> {
                    // Xử lý sự kiện click vào một item ở đây
                    Log.d(TAG, "Clicked on: " + p.name);
                });
                rvPartners.setAdapter(adapter);
            } else {
                Log.w(TAG, "⚠️ Observer received a null list.");
            }
        });
    }
}