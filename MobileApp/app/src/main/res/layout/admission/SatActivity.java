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
import com.example.myapplication.model.IntlCertProgramScore;

import java.util.List;

public class SatActivity extends AppCompatActivity {

    private SatViewModel viewModel;
    private ViewGroup scoresContainer; // Dùng ViewGroup (LinearLayout) thay cho RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission_method_intl_cert);

        viewModel = new ViewModelProvider(this).get(SatViewModel.class);

        setupToolbar();

        // Ánh xạ tới LinearLayout container mới
        scoresContainer = findViewById(R.id.llScoresContainer);

        observeViewModel();

        String schoolId = "PTA"; // Lấy ID trường từ Intent
        viewModel.loadSatScores(schoolId);
    }

    private void setupToolbar() {
        ImageButton back = findViewById(R.id.viewTopbar).findViewById(R.id.btnBack);
        if (back != null) back.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        ((TextView) findViewById(R.id.tvTitle)).setText("Phương thức xét tuyển chứng chỉ ĐGNL quốc tế");
    }

    private void observeViewModel() {
        viewModel.getSatScores().observe(this, satScores -> {
            if (satScores != null) {
                // Gọi hàm mới để điền dữ liệu vào LinearLayout
                populateScores(satScores);
            }
        });
    }

    /**
     * Hàm này sẽ tự "vẽ" các dòng điểm vào LinearLayout container.
     */
    private void populateScores(List<IntlCertProgramScore> scores) {
        scoresContainer.removeAllViews(); // Xóa dữ liệu cũ để tránh trùng lặp
        LayoutInflater inflater = LayoutInflater.from(this);

        for (IntlCertProgramScore score : scores) {
            // "Vẽ" layout cho một dòng từ file item_program_score_intl.xml (tên layout item của bạn)
            View rowView = inflater.inflate(R.layout.item_program_score_intl, scoresContainer, false);

            // Ánh xạ các TextView trong dòng đó
            TextView tvName = rowView.findViewById(R.id.tvName);
            TextView tvCode = rowView.findViewById(R.id.tvCode);
            TextView tvSat = rowView.findViewById(R.id.tvSat);
            TextView tvAct = rowView.findViewById(R.id.tvAct);

            // Gán dữ liệu
            tvName.setText(score.name);
            tvCode.setText(score.code);
            tvSat.setText(score.sat);
            tvAct.setText(score.act);

            // Thêm dòng đã hoàn thiện vào container
            scoresContainer.addView(rowView);
        }
    }
}