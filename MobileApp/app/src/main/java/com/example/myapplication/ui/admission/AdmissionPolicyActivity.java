package com.example.myapplication.ui.admission;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.PolicyAdapter;

import java.util.Arrays;
import java.util.List;

public class AdmissionPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission_policy);

        ImageButton back = findViewById(R.id.viewTopbar).findViewById(R.id.btnBack);
        if (back != null) back.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        RecyclerView rv = findViewById(R.id.rvPolicies);
        rv.setLayoutManager(new LinearLayoutManager(this));

        PolicyAdapter adapter = new PolicyAdapter();
        rv.setAdapter(adapter);

        List<String> data = Arrays.asList(
                "Phương thức xét tuyển sử dụng điểm ĐGNL HN",
                "Phương thức xét tuyển TN THPT",
                "Phương thức xét tuyển điểm ĐGNL HCM",
                "Phương thức xét tuyển điểm ĐG tư duy",
                "Phương thức xét tuyển chứng chỉ ĐGNL quốc tế",
                "Phương thức sử dụng điểm ĐGNL ĐH Sư Phạm HN"
        );
        adapter.submitList(data);

        // Lắng nghe click: (title, position)
        adapter.setOnItemClick((title, position) -> {
            switch (position) {
                case 0: { // "Phương thức xét tuyển sử dụng điểm ĐGNL HN"
                    Intent intent = new Intent(this, HsaActivity.class);
                    startActivity(intent);
                    break;
                }
                case 1: { // "Phương thức xét tuyển TN THPT"
                    Intent intent = new Intent(this, AdmissionMethodThptActivity.class);
                    startActivity(intent);
                    break;
                }
                case 2: { // "Phương thức xét tuyển điểm ĐGNL HCM"
                    Intent intent = new Intent(this, AptActivity.class);
                    startActivity(intent);
                    break;
                }
                case 3: { // "Phương thức xét tuyển điểm ĐG tư duy"
                    Intent intent = new Intent(this, TsaActivity.class);
                    startActivity(intent);
                    break;
                }
                case 4: { // "Phương thức xét tuyển chứng chỉ ĐGNL quốc tế"
                    Intent intent = new Intent(this, SatActivity.class);
                    startActivity(intent);
                    break;
                }
                case 5: { // "Phương thức xét tuyển điểm ĐGNL ĐH Sư Phạm HN"
                    Intent intent = new Intent(this, SptActivity.class);
                    startActivity(intent);
                    break;
                }
                default:
                    Toast.makeText(this, "Mục \"" + title + "\" đang cập nhật", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }
}
