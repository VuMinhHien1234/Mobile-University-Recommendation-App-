package com.example.myapplication.ui.admission;


import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.model.DgnlProgramScore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HsaViewModel extends AndroidViewModel {

    private static final String TAG = "HsaViewModel";
    private final MutableLiveData<List<DgnlProgramScore>> hsaScores = new MutableLiveData<>();
    private final RequestQueue requestQueue;

    public HsaViewModel(@NonNull Application application) {
        super(application);
        requestQueue = Volley.newRequestQueue(application);
    }

    public LiveData<List<DgnlProgramScore>> getHsaScores() {
        return hsaScores;
    }

    public void loadHsaScores(String schoolId) {
        String url = "http://10.0.2.2:3000/universities/" + schoolId + "/admission-scores";
        Log.d(TAG, "Fetching HSA scores from: " + url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    Log.d(TAG, "✅ API Response: " + response.toString());
                    try {
                        JSONArray scoresArray = response.getJSONArray("admissionScores");
                        hsaScores.setValue(parseHsaScoresList(scoresArray));
                    } catch (Exception e) {
                        Log.e(TAG, "❌ JSON Parsing Error", e);
                        hsaScores.setValue(new ArrayList<>());
                    }
                },
                error -> {
                    Log.e(TAG, "❌ Volley Error", error);
                    hsaScores.setValue(new ArrayList<>());
                }
        );
        requestQueue.add(request);
    }

    private List<DgnlProgramScore> parseHsaScoresList(JSONArray scoresArray) {
        List<DgnlProgramScore> list = new ArrayList<>();
        try {
            for (int i = 0; i < scoresArray.length(); i++) {
                JSONObject major = scoresArray.getJSONObject(i);

                String name = major.getString("majorName");
                String code = major.getString("id");

                // Đi vào object con để lấy điểm HSA
                JSONObject scoresData = major.getJSONObject("scores").getJSONObject("2025");

                // Kiểm tra xem trường HSA có tồn tại không
                if (scoresData.has("HSA")) {
                    String score = String.valueOf(scoresData.getDouble("HSA"));
                    list.add(new DgnlProgramScore(name, code, score));
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error parsing HSA scores list", e);
        }
        return list;
    }
}