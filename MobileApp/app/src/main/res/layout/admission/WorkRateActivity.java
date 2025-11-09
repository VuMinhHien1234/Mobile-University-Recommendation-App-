package com.example.myapplication.ui.admission;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.example.myapplication.R;

public class WorkRateActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy_detail);

        ImageButton back = findViewById(R.id.viewTopbar).findViewById(R.id.btnBack);
        if (back != null) back.setOnClickListener(v -> onBackPressed());

        TextView tvTopTitle = findViewById(R.id.tvTopTitle);
        tvTopTitle.setText(getIntent().getStringExtra("title"));

        TextView tvContent = findViewById(R.id.tvContent);
        CharSequence html = HtmlCompat.fromHtml(
                getString(R.string.ty_le_viec_lam_html),
                HtmlCompat.FROM_HTML_MODE_LEGACY
        );
        tvContent.setText(html);
        // để link <a> bấm được:
        tvContent.setMovementMethod(LinkMovementMethod.getInstance());
    }
}

