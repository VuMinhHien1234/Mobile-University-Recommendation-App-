package com.example.myapplication.ui.login;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton, signUpButton;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.input_name);
        passwordEditText = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.btnDangnhap);
        signUpButton = findViewById(R.id.btn_sign_up);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        viewModel.getLoginResult().observe(this, result -> {
            Toast.makeText(LoginActivity.this, result, Toast.LENGTH_LONG).show();
            if (result.startsWith("Đăng nhập thành công")) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        signUpButton.setOnClickListener(v->{
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Nhập email và mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }
            viewModel.login(email, password);
        });

        signUpButton.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "Chuyển sang màn hình đăng ký", Toast.LENGTH_SHORT).show();
             Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
             startActivity(intent);
        });
    }
}