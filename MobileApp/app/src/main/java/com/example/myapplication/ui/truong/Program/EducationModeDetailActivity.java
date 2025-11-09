package com.example.myapplication.ui.truong.Program;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.helper.TableMohinhHelper;
import com.example.myapplication.helper.TableTohopHelper;
import com.example.myapplication.model.Mohinh;
import com.example.myapplication.model.ToHop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EducationModeDetailActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private TableLayout tableLayout,tableLayout2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mohinhdaotaochitiet);
        btnBack =findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
        tableLayout = findViewById(R.id.tableLayout);
        tableLayout2 = findViewById(R.id.tableLayout2);

        List<Mohinh> data = Arrays.asList(
                new Mohinh("7480201", "Công nghệ thông tin", "20", "20", "Toàn quốc"),
                new Mohinh("7480201", "Công nghệ tin học", "20", "20", "Toàn quốc"),
                new Mohinh("7480201", "Truyền thông đa phuong tiện", "20", "20", "Toàn quốc"),
                new Mohinh("7480201", "Quản trị kinh doanh", "20", "20", "Toàn quốc"),
                new Mohinh("7480201", "Kế toán", "20", "20", "Toàn quốc")
        );
        TableMohinhHelper.populateNganhTable(tableLayout,data);

        List<ToHop> tohop = Arrays.asList(
                new ToHop("7520274","Kỹ thuật Điện tử viễn thông","A00,A01,X06,X26"),
                new ToHop("7480201", "Công nghệ tin học","A00,A01,X06,X26"),
                new ToHop("7340101", "Quản trị kinh doanh","A00,A01,X06,X26"),
                new ToHop("7340301","Kế toán", "A00,A01,X06,X26"),
                new ToHop("7320101","Truyền thông đa phuong tiện", "A00,A01,X06,X26")
                );
        TableTohopHelper.polulateTohopTable(tableLayout2,tohop);


    }
}

