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
import com.example.myapplication.model.TableRowItem;

import java.util.List;

public class EmploymentStatsActivity extends AppCompatActivity {

    private EmploymentStatsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employment_stats);

        viewModel = new ViewModelProvider(this).get(EmploymentStatsViewModel.class);

        setupToolbar();
        observeViewModel();

        String schoolId = "PTA"; // Lấy ID trường từ Intent
        viewModel.loadEmploymentData(schoolId);
    }

    private void setupToolbar() {
        ImageButton back = findViewById(R.id.viewTopbar).findViewById(R.id.btnBack);
        if (back != null) back.setOnClickListener(v -> onBackPressed());

        // Tiêu đề của topbar nằm trong file layout, tiêu đề chính ở đây
        // (Bạn có thể bỏ dòng này nếu không cần thiết)
        ((TextView) findViewById(R.id.tvTitle))
                .setText("Tỷ lệ có việc làm sau tốt nghiệp");
    }

    private void observeViewModel() {
        // Lắng nghe dữ liệu cho từng khối và cập nhật LinearLayout tương ứng
        viewModel.getGraduateStats().observe(this, data -> setupSection(R.id.llSection1Container, data));
        viewModel.getWorkTypeStats().observe(this, data -> setupSection(R.id.llSection2Container, data));
        viewModel.getLocationStats().observe(this, data -> setupSection(R.id.llSection3Container, data));
        viewModel.getIncomeStats().observe(this, data -> setupSection(R.id.llSection4Container, data));
    }

    /**
     * Hàm này giờ sẽ điền các view con vào một ViewGroup (LinearLayout)
     * thay vì dùng Adapter.
     */
    private void setupSection(int containerId, List<TableRowItem> data) {
        if (data == null || data.isEmpty()) return;

        ViewGroup container = findViewById(containerId);
        container.removeAllViews(); // Xóa các view cũ trước khi thêm mới
        LayoutInflater inflater = LayoutInflater.from(this);

        for (TableRowItem item : data) {
            // Inflate layout của một dòng
            View rowView = inflater.inflate(R.layout.item_table_row, container, false);

            // Ánh xạ và gán dữ liệu
            TextView tvSTT = rowView.findViewById(R.id.tvSTT);
            TextView tvCol1 = rowView.findViewById(R.id.tvCol1);
            TextView tvCol2 = rowView.findViewById(R.id.tvCol2);

            tvSTT.setText(String.valueOf(item.getStt()));
            tvCol1.setText(item.getCol1());
            tvCol2.setText(item.getCol2());

            // Thêm view của dòng này vào container
            container.addView(rowView);
        }
    }
}