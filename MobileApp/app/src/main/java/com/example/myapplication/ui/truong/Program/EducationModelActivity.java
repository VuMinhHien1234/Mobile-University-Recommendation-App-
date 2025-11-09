package com.example.myapplication.ui.truong.Program;

import android.content.Intent;
import android.os.Bundle;

import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class EducationModelActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private LinearLayout vuahocvualam,tuxa;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mohinhdaotao);
        btnBack = findViewById(R.id.viewTopbar).findViewById(R.id.btnBack);
        vuahocvualam = findViewById(R.id.vuahocvualam);
        vuahocvualam.setOnClickListener(v->{
            Intent intent = new Intent(this,EducationModeDetailActivity.class);
            startActivity(intent);
        });
        tuxa = findViewById(R.id.tuxa);
        tuxa.setOnClickListener(v->{
            Intent intent = new Intent(this, EducationModelDetail2Activity.class);
            startActivity(intent);
        });

        btnBack.setOnClickListener(v -> finish());

    }
}
