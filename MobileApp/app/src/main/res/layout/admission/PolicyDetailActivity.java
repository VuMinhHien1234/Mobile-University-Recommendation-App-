package com.example.myapplication.ui.admission;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.example.myapplication.R;

public class PolicyDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy_detail);

        // Topbar back
        ImageButton back = findViewById(R.id.viewTopbar).findViewById(R.id.btnBack);
        if (back != null) back.setOnClickListener(v -> onBackPressed());

        // Title trên màn
        String title = getIntent().getStringExtra("title");
        TextView tvTopTitle = findViewById(R.id.tvTopTitle);
        tvTopTitle.setText(title == null || title.isEmpty()
                ? getString(R.string.app_name)
                : title);

        // Nội dung
        int id = getIntent().getIntExtra("policy_id", -1);
        TextView tvContent = findViewById(R.id.tvContent);

        int htmlResId;
        switch (id) {
            case 0: // ví dụ: item "Phương thức xét tuyển ĐGNL HN"
                htmlResId = R.string.policy_hsa_html;   // dùng string HTML
                break;
            case 1: // ví dụ: item "Phương thức xét tuyển TN THPT"
                htmlResId = R.string.policy_tnthpt_html;   // dùng string HTML
                break;
            case 2: // ví dụ: item "Phương thức xét tuyển ĐGNL HCM"
                htmlResId = R.string.policy_hcm_html;   // dùng string HTML
                break;
            case 3: // ví dụ: item "Phương thức xét tuyển ĐG tư duy"
                htmlResId = R.string.policy_tsa_html;   // dùng string HTML
                break;
            case 4: // ví dụ: item "Phương thức xét tuyển chứng chỉ ĐGNL quốc tế"
                htmlResId = R.string.policy_sat_html;   // dùng string HTML
                break;
            case 5: // ví dụ: item "Phương thức sử dụng điểm ĐGNL ĐH Sư Phạm HN"
                htmlResId = R.string.policy_spt_html;   // dùng string HTML
                break;

            default:
                htmlResId = R.string.policy_default;  // nội dung mặc định
                break;
        }

        // Render HTML vào TextView (không dùng WebView)
        CharSequence spanned = HtmlCompat.fromHtml(
                getString(htmlResId),
                HtmlCompat.FROM_HTML_MODE_LEGACY
        );
        tvContent.setText(spanned);

        // Nếu có <a href="..."> trong HTML, cho phép click link
        tvContent.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
