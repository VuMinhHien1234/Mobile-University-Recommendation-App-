package com.example.myapplication.ui.admission;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.model.Professor;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ProfessorViewModel extends AndroidViewModel {

    // TAG dùng để lọc log trong Logcat
    private static final String TAG = "ProfessorViewModel";

    private final MutableLiveData<List<Professor>> professorList = new MutableLiveData<>();
    public LiveData<List<Professor>> getProfessorList() {
        return professorList;
    }

    public final RequestQueue requestQueue;

    public ProfessorViewModel(@NonNull Application application) {
        super(application);
        requestQueue = Volley.newRequestQueue(application);
    }

    public void loadProfessors(String schoolId) {
        String url = "http://10.0.2.2:3000/universities/" + schoolId + "/professors";

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    // Log khi API gọi thành công
                    Log.d(TAG, "✅ API Response: " + response.toString());
                    try {
                        JSONArray professorArray = response.optJSONArray("professors");
                        List<Professor> ds = parseProfessorList(professorArray);

                        // Log số lượng item đã parse được
                        if (ds != null) {
                            Log.d(TAG, "✅ Parsed list size: " + ds.size());
                        }

                        professorList.setValue(ds);
                    } catch (Exception e) {
                        Log.e(TAG, "❌ JSON Parsing Error: ", e);
                        professorList.setValue(new ArrayList<>());
                    }
                },
                error -> {
                    // Log khi API gọi thất bại
                    Log.e(TAG, "❌ Volley Error: ", error);
                    professorList.setValue(new ArrayList<>());
                }
        );
        requestQueue.add(request);
    }

    private List<Professor> parseProfessorList(JSONArray response) {
        List<Professor> ds = new ArrayList<>();
        if (response == null) {
            return ds;
        }
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                String id = obj.optString("id");
                String name = obj.optString("name");
                String major = obj.optString("major");

                Professor professor = new Professor(id, name, major);
                ds.add(professor);
            }
        } catch (Exception e) {
            return null;
        }
        return ds;
    }

    public Professor getProfessorById(String id) {
        List<Professor> currentList = professorList.getValue();
        if (currentList == null || id == null) {
            return null;
        }
        for (Professor p : currentList) {
            if (id.equals(p.id)) {
                return p;
            }
        }
        return null;
    }
}