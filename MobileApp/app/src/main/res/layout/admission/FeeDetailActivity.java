package com.example.myapplication.ui.admission;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SemesterFeeAdapter;

public class FeeDetailActivity extends AppCompatActivity {

    private static final String EXTRA_SCHOOL_ID = "extra_school_id";
    private static final String EXTRA_MAJOR_ID = "extra_major_id";
    private static final String EXTRA_MAJOR_NAME = "extra_major_name";

    private FeeDetailViewModel viewModel;
    private SemesterFeeAdapter adapter;
    private RecyclerView rvSemester;

    public static Intent newIntent(Context ctx, String schoolId, String majorId, String majorName) {
        Intent i = new Intent(ctx, FeeDetailActivity.class);
        i.putExtra(EXTRA_SCHOOL_ID, schoolId);
        i.putExtra(EXTRA_MAJOR_ID, majorId);
        i.putExtra(EXTRA_MAJOR_NAME, majorName);
        return i;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_detail);

        viewModel = new ViewModelProvider(this).get(FeeDetailViewModel.class);

        // Lấy dữ liệu từ Intent
        String schoolId = getIntent().getStringExtra(EXTRA_SCHOOL_ID);
        String majorId = getIntent().getStringExtra(EXTRA_MAJOR_ID);
        String majorName = getIntent().getStringExtra(EXTRA_MAJOR_NAME);

        setupToolbar();
        ((TextView) findViewById(R.id.tvMajor)).setText("Ngành " + (majorName != null ? majorName : ""));

        setupRecyclerView();
        observeViewModel();

        // Yêu cầu ViewModel tải dữ liệu chi tiết
        if (schoolId != null && majorId != null) {
            viewModel.loadSemesterFees(schoolId, majorId);
        }
    }

    private void setupToolbar() {
        ImageButton back = findViewById(R.id.viewTopbar).findViewById(R.id.btnBack);
        if (back != null) back.setOnClickListener(v -> onBackPressed());
    }

    private void setupRecyclerView() {
        rvSemester = findViewById(R.id.rvSemester);
        rvSemester.setLayoutManager(new LinearLayoutManager(this));
    }

    private void observeViewModel() {
        viewModel.getSemesterFeeList().observe(this, semesterFees -> {
            if (semesterFees != null) {
                adapter = new SemesterFeeAdapter(semesterFees, item -> {
                    Toast.makeText(this, "Chi tiết cho " + item.getKi(), Toast.LENGTH_SHORT).show();
                });
                rvSemester.setAdapter(adapter);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}