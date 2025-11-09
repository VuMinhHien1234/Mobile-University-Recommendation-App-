package com.example.myapplication.ui.admission;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ProfessorAdapter;

public class ProfessorCouncilActivity extends AppCompatActivity {

    // TAG dùng để lọc log trong Logcat
    private static final String TAG = "ProfessorActivity";

    private ProfessorViewModel professorViewModel;
    private ProfessorAdapter adapter;
    private RecyclerView professorsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_council);

        professorViewModel = new ViewModelProvider(this).get(ProfessorViewModel.class);

        setupToolbar();
        setupRecyclerView();
        observeViewModel();

        String schoolId = "PTA";
        Log.i(TAG, "Requesting data for schoolId: " + schoolId);
        professorViewModel.loadProfessors(schoolId);
    }

    private void setupToolbar() {
        ImageButton backButton = findViewById(R.id.viewTopbar).findViewById(R.id.btnBack);
        if (backButton != null) {
            backButton.setOnClickListener(v -> onBackPressed());
        }
    }

    private void setupRecyclerView() {
        professorsRecyclerView = findViewById(R.id.rvProfessors);
        professorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        professorsRecyclerView.setHasFixedSize(true);
    }

    private void observeViewModel() {
        professorViewModel.getProfessorList().observe(this, professors -> {
            if (professors != null) {
                // Log dữ liệu nhận được từ ViewModel
                Log.d(TAG, "✅ Observer received " + professors.size() + " professors.");

                adapter = new ProfessorAdapter(professors);
                professorsRecyclerView.setAdapter(adapter);
            } else {
                Log.w(TAG, "⚠️ Observer received a null list.");
            }
        });
    }
}