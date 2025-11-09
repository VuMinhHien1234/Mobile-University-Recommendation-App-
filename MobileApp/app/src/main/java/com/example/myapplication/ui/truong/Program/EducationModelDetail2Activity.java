package com.example.myapplication.ui.truong.Program;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.helper.TableTohopHelper;
import com.example.myapplication.model.ToHop;

import java.util.Arrays;
import java.util.List;

public class EducationModelDetail2Activity extends AppCompatActivity {
    private TableLayout tableLayout;
    private ImageButton btnBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mohinhdaotaotuxa);
        btnBack = findViewById(R.id.btnBack);
        tableLayout = findViewById(R.id.tableLayout);
        btnBack.setOnClickListener(v -> finish());
        List<ToHop> tohop = Arrays.asList(
                new ToHop("7520274", "Công nghệ thông tin", "A00,A01,X06,X26")
        );
        TableTohopHelper.polulateTohopTable(tableLayout,tohop);
    }
}
