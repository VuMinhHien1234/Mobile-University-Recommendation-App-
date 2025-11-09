package com.example.myapplication.ui.admission;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.model.DgnlProgramScore;

import java.util.List;

public class SptActivity extends AppCompatActivity {

    private SptViewModel viewModel;
    private ViewGroup scoresContainer; // Dùng ViewGroup (LinearLayout) thay cho RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission_spt);

        viewModel = new ViewModelProvider(this).get(SptViewModel.class);

        setupToolbar();

        // Ánh xạ tới LinearLayout container mới
        scoresContainer = findViewById(R.id.llScoresContainer);

        observeViewModel();

        String schoolId = "PTA"; // Lấy ID trường từ Intent
        viewModel.loadSptScores(schoolId);
    }

    private void setupToolbar() {
        ImageButton back = findViewById(R.id.viewTopbar).findViewById(R.id.btnBack);
        if (back != null) back.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        ((TextView) findViewById(R.id.tvTitle)).setText("Phương thức xét tuyển DGNL ĐH Sư Phạm HN");
    }

    private void observeViewModel() {
        viewModel.getSptScores().observe(this, sptScores -> {
            if (sptScores != null) {
                // Gọi hàm mới để điền dữ liệu vào LinearLayout
                populateScores(sptScores);
            }
        });
    }

    /**
     * Hàm này sẽ tự "vẽ" các dòng điểm vào LinearLayout container.
     */
    private void populateScores(List<DgnlProgramScore> scores) {
        scoresContainer.removeAllViews(); // Xóa dữ liệu cũ để tránh trùng lặp
        LayoutInflater inflater = LayoutInflater.from(this);

        for (DgnlProgramScore score : scores) {
            // "Vẽ" layout cho một dòng từ file item_program_score_dgnl.xml (tên layout item của bạn)
            View rowView = inflater.inflate(R.layout.item_program_score_dgnlhn, scoresContainer, false);

            // Ánh xạ các TextView trong dòng đó
            TextView tvName = rowView.findViewById(R.id.tvName);
            TextView tvCode = rowView.findViewById(R.id.tvCode);
            TextView tvScore = rowView.findViewById(R.id.tvScore);

            // Gán dữ liệu
            tvName.setText(score.name);
            tvCode.setText(score.code);
            tvScore.setText(score.score);

            // Thêm dòng đã hoàn thiện vào container
            scoresContainer.addView(rowView);
        }
    }
}