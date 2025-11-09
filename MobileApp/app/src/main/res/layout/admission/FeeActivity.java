package com.example.myapplication.ui.admission;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.FeeAdapter;

public class FeeActivity extends AppCompatActivity {

    private static final String TAG = "FeeActivity";
    private String schoolId = "PTA";
    private FeeViewModel viewModel;
    private RecyclerView rvFees;
    private FeeAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee);

        // 1. Khởi tạo ViewModel
        viewModel = new ViewModelProvider(this).get(FeeViewModel.class);

        setupToolbar();
        setupRecyclerView();
        observeViewModel();

        // 2. Yêu cầu ViewModel tải dữ liệu từ API
        // schoolId này nên được truyền từ màn hình trước qua Intent
        String schoolId = "PTA"; // Tạm thời dùng ID cứng để test
        viewModel.loadMajorFees(schoolId);
    }

    private void setupToolbar() {
        ImageButton back = findViewById(R.id.viewTopbar).findViewById(R.id.btnBack);
        if (back != null) back.setOnClickListener(v -> onBackPressed());
    }

    private void setupRecyclerView() {
        rvFees = findViewById(R.id.rvFees);
        rvFees.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Lắng nghe dữ liệu từ ViewModel và cập nhật giao diện khi có thay đổi.
     */
    private void observeViewModel() {
        viewModel.getMajorFeeList().observe(this, majorFees -> {
            if (majorFees != null) {
                Log.d(TAG, "✅ Observer received " + majorFees.size() + " majors.");
                // 3. Tạo adapter với dữ liệu từ ViewModel
                adapter = new FeeAdapter(majorFees, item -> {
                    startActivity(FeeDetailActivity.newIntent(
                            FeeActivity.this,
                            schoolId,
                            item.getMaNganh(), // Gửi ID ngành
                            item.getTenNganh() // Vẫn gửi tên ngành để hiển thị
                    ));
                    Log.d(TAG, "Clicked on: " + item.getTenNganh());
                });
                rvFees.setAdapter(adapter);
            } else {
                Log.w(TAG, "⚠️ Observer received a null list.");
            }
        });
    }

    // Bạn có thể giữ hoặc bỏ hàm này tùy vào cách bạn xử lý nút back trên ActionBar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}