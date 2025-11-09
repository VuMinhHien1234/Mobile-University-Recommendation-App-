package com.example.myapplication.ui.xettuyen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.R;


public class XetTuyenSomActivity extends AppCompatActivity {
    private ImageButton btnBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xettuyennganh);
        btnBack = findViewById(R.id.viewTopbar).findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

    }
}
