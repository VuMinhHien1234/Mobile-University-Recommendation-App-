package com.example.myapplication.ui.sinhvien.cauhoithuonggap;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class FAQActivity extends AppCompatActivity {

    private FAQViewModel faqViewModel;
    private FAQAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        // Setup RecyclerView
        RecyclerView recyclerView = findViewById(R.id.faq_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FAQAdapter();
        recyclerView.setAdapter(adapter);

        // Setup ViewModel
        faqViewModel = new ViewModelProvider(this).get(FAQViewModel.class);

        // Observe LiveData
        faqViewModel.getFaqList().observe(this, faqItems -> {
            adapter.submitList(faqItems);
        });

        // Load data
        // Replace "PTA" with the actual school ID you need
        faqViewModel.loadData("PTA");
    }
}
