package com.example.myapplication.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ThongTinTaiKhoanActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtintaikhoan);

        TextView edit = findViewById(R.id.textEditAccount);
        edit.setOnClickListener(v -> {
            Intent intent = new Intent(this, ThayDoiThongTinTaiKhoanActivity.class);
            startActivity(intent);
        });
    }
}
