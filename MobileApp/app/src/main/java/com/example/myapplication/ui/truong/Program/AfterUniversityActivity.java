package com.example.myapplication.ui.truong.Program;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.serialization.InternalNavType;

import com.example.myapplication.R;

public class AfterUniversityActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private LinearLayout thacsi,tiensi;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saudaihoc);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
        thacsi = findViewById(R.id.thacsi);

        tiensi = findViewById(R.id.tiensi);

        thacsi.setOnClickListener(v ->{
            Intent intent = new Intent(this, AfterUniverisityMasterActivity.class);
            startActivity(intent);
        });
        tiensi.setOnClickListener(v ->{
            Intent intent = new Intent(this, AfterUniversityPhdActivity.class);
            startActivity(intent);
        });


    }
}
