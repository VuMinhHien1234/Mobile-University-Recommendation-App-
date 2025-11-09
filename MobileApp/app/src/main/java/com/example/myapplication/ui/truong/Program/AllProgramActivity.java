package com.example.myapplication.ui.truong.Program;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.ui.admission.AdmissionPolicyActivity;
import com.example.myapplication.ui.admission.EmploymentStatsActivity;
import com.example.myapplication.ui.admission.FeeActivity;
import com.example.myapplication.ui.admission.PartnersActivity;
import com.example.myapplication.ui.admission.ProfessorCouncilActivity;
import com.example.myapplication.ui.gioithieu.cocautochuc.CoCauToChucActivity;
import com.example.myapplication.ui.gioithieu.cosovatchat.CoSoVatChatActivity;
import com.example.myapplication.ui.gioithieu.nguonnhanluc.NguonNhanLucActivity;
import com.example.myapplication.ui.sinhvien.cauhoithuonggap.FAQActivity;
import com.example.myapplication.ui.sinhvien.clb.CLBActivity;
import com.example.myapplication.ui.sinhvien.hoatdongsinhvien.HoatDongSinhVienActivity;
import com.example.myapplication.ui.sinhvien.hocbong.HocBongActivity;

public class AllProgramActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private LinearLayout item1_1,item1_2,saudaihoc,item3_1,item3_2,item3_3,item4_1,item4_2,hoat_dong_sinh_vien,hoc_bong,clb,faq,co_so_vat_chat,co_cau_to_chuc,nguon_nhan_luc;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        String truongCode = getIntent().getStringExtra("truong_code");
        setContentView(R.layout.activity_program);
        btnBack = findViewById(R.id.viewTopbar).findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
        item1_1 = findViewById(R.id.item1_1);
        item1_1.setOnClickListener(v -> {
            Intent intent = new Intent(this, EducationModelActivity.class);
            intent.putExtra("truong_code", truongCode);
            startActivity(intent);
        });
        item1_2 = findViewById(R.id.item1_2);
        item1_2.setOnClickListener(v->{
            Intent intent = new Intent(this,EducationProgramActivity.class);
            intent.putExtra("truong_code", truongCode);
            startActivity(intent);
        });
        saudaihoc = findViewById(R.id.saudaihoc);
        saudaihoc.setOnClickListener(v->{
            Intent intent = new Intent(this, AfterUniversityActivity.class);
            intent.putExtra("truong_code", truongCode);
            startActivity(intent);
        });
        item3_1 = findViewById(R.id.item5_1);
        item3_1.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdmissionPolicyActivity.class);
            startActivity(intent);
        });

         item3_2 = findViewById(R.id.item5_2);
        item3_2.setOnClickListener(v -> {
            Intent i = new Intent(this, FeeActivity.class);
            i.putExtra("title", "Học phí");
            startActivity(i);
        });

        item3_3 = findViewById(R.id.item5_3);
        item3_3.setOnClickListener(v -> {
            Intent i = new Intent(this, EmploymentStatsActivity.class);
            i.putExtra("title", "Tỷ lệ có việc làm sau tốt nghiệp");
            startActivity(i);
        });

         item4_1 = findViewById(R.id.item6_1);
        item4_1.setOnClickListener(v -> {
            Intent i = new Intent(this, ProfessorCouncilActivity.class);
            startActivity(i);
        });

         item4_2 = findViewById(R.id.item6_2);
        item4_2.setOnClickListener(v -> {
            startActivity(new Intent(this, PartnersActivity.class));
        });
         hoat_dong_sinh_vien = findViewById(R.id.hdsv);
        hoat_dong_sinh_vien.setOnClickListener(v -> {
            Intent intent = new Intent(this, HoatDongSinhVienActivity.class);
            startActivity(intent);
        });

         hoc_bong = findViewById(R.id.hb);
        hoc_bong.setOnClickListener(v -> {
            Intent intent = new Intent(this, HocBongActivity.class);
            startActivity(intent);
        });

         clb = findViewById(R.id.clb);
        clb.setOnClickListener(v -> {
            Intent intent = new Intent(this, CLBActivity.class);
            startActivity(intent);
        });

         faq = findViewById(R.id.faq);
        faq.setOnClickListener(v -> {
            Intent intent = new Intent(this, FAQActivity.class);
            startActivity(intent);
        });

         co_so_vat_chat = findViewById(R.id.csvc);
        co_so_vat_chat.setOnClickListener(v -> {
            Intent intent = new Intent(this, CoSoVatChatActivity.class);
            startActivity(intent);
        });

         co_cau_to_chuc = findViewById(R.id.cctc);
        co_cau_to_chuc.setOnClickListener(v -> {
            Intent intent = new Intent(this, CoCauToChucActivity.class);
            startActivity(intent);
        });

         nguon_nhan_luc = findViewById(R.id.nnl);
        nguon_nhan_luc.setOnClickListener(v -> {
            Intent intent = new Intent(this, NguonNhanLucActivity.class);
            startActivity(intent);
        });
    }
    }

