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
import com.example.myapplication.model.ProgramScore;

import java.util.List;

public class AdmissionMethodThptActivity extends AppCompatActivity {

    private ThptViewModel viewModel;
    private ViewGroup scoresContainer; // Dùng ViewGroup (LinearLayout) thay cho RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission_method_thpt);

        viewModel = new ViewModelProvider(this).get(ThptViewModel.class);

        setupToolbar();

        // Ánh xạ tới LinearLayout container
        scoresContainer = findViewById(R.id.llScoresContainer);

        observeViewModel();

        String schoolId = "PTA"; // Lấy ID trường từ Intent
        viewModel.loadAdmissionScores(schoolId);
    }

    private void setupToolbar() {
        ImageButton back = findViewById(R.id.viewTopbar).findViewById(R.id.btnBack);
        if (back != null) back.setOnClickListener(v -> onBackPressed());
        ((TextView) findViewById(R.id.tvTitle)).setText("Phương thức xét tuyển TN THPT");
    }

    private void observeViewModel() {
        viewModel.getProgramScores().observe(this, programScores -> {
            if (programScores != null) {
                // Gọi hàm mới để điền dữ liệu vào LinearLayout
                populateScores(programScores);
            }
        });
    }

    /**
     * Hàm này sẽ điền các dòng điểm vào LinearLayout container.
     * @param scores Danh sách điểm chuẩn từ ViewModel.
     */
    private void populateScores(List<ProgramScore> scores) {
        scoresContainer.removeAllViews(); // Xóa dữ liệu cũ
        LayoutInflater inflater = LayoutInflater.from(this);

        for (ProgramScore score : scores) {
            // "Vẽ" layout cho một dòng
            View rowView = inflater.inflate(R.layout.item_program_score, scoresContainer, false);

            // Ánh xạ các TextView trong dòng đó
            TextView tvName = rowView.findViewById(R.id.tvName);
            TextView tvCode = rowView.findViewById(R.id.tvCode);
            TextView tvCombo = rowView.findViewById(R.id.tvCombo);
            TextView tvScore = rowView.findViewById(R.id.tvScore);

            // Gán dữ liệu
            tvName.setText(score.name);
            tvCode.setText(score.code);
            tvCombo.setText(score.combo);
            tvScore.setText(score.score);

            // Thêm dòng đã hoàn thiện vào container
            scoresContainer.addView(rowView);
        }
    }
}