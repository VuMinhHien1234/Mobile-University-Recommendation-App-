package com.example.myapplication.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ThongTinCaNhanActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtincanhan);

        TextView edit = findViewById(R.id.textEditProfile);
        edit.setOnClickListener(v -> {
            Intent intent = new Intent(this, ThayDoiThongTinCaNhanActivity.class);
            startActivity(intent);
        });
    }
}
