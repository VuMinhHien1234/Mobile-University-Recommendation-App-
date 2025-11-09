package com.example.myapplication.ui.truong.Program;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.helper.TableMohinhHelper;
import com.example.myapplication.model.Mohinh;

import java.util.Arrays;
import java.util.List;

public class AfterUniversityPhdActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private TableLayout tableLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saudaihoctiensi);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
        List<Mohinh> data = Arrays.asList(
                new Mohinh("7480201", "Công nghệ thông tin", "20", "20", "Toàn quốc"),
                new Mohinh("7480201", "Công nghệ tin học", "20", "20", "Toàn quốc"),
                new Mohinh("7480201", "Truyền thông đa phuong tiện", "20", "20", "Toàn quốc"),
                new Mohinh("7480201", "Quản trị kinh doanh", "20", "20", "Toàn quốc"),
                new Mohinh("7480201", "Kế toán", "20", "20", "Toàn quốc")
        );
        tableLayout = findViewById(R.id.tableLayout);
        TableMohinhHelper.populateNganhTable(tableLayout,data);
    }
}
