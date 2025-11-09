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
import com.example.myapplication.model.ProgramScore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ThptViewModel extends AndroidViewModel {

    private static final String TAG = "AdmissionScoreVM";
    private final MutableLiveData<List<ProgramScore>> programScores = new MutableLiveData<>();
    private final RequestQueue requestQueue;

    public ThptViewModel(@NonNull Application application) {
        super(application);
        requestQueue = Volley.newRequestQueue(application);
    }

    public LiveData<List<ProgramScore>> getProgramScores() {
        return programScores;
    }

    public void loadAdmissionScores(String schoolId) {
        String url = "http://10.0.2.2:3000/universities/" + schoolId + "/admission-scores";
        Log.d(TAG, "Fetching admission scores from: " + url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    Log.d(TAG, "✅ API Response: " + response.toString());
                    try {
                        JSONArray scoresArray = response.getJSONArray("admissionScores");
                        programScores.setValue(parseScoresList(scoresArray));
                    } catch (Exception e) {
                        Log.e(TAG, "❌ JSON Parsing Error", e);
                        programScores.setValue(new ArrayList<>());
                    }
                },
                error -> {
                    Log.e(TAG, "❌ Volley Error", error);
                    programScores.setValue(new ArrayList<>());
                }
        );
        requestQueue.add(request);
    }

    private List<ProgramScore> parseScoresList(JSONArray scoresArray) {
        List<ProgramScore> list = new ArrayList<>();
        try {
            for (int i = 0; i < scoresArray.length(); i++) {
                JSONObject major = scoresArray.getJSONObject(i);

                String name = major.getString("majorName");
                String code = major.getString("id");

                // Đi vào object con để lấy điểm và tổ hợp
                JSONObject scoresData = major.getJSONObject("scores").getJSONObject("2025");
                String score = String.valueOf(scoresData.getDouble("score"));

                // Chuyển mảng tổ hợp thành một chuỗi String duy nhất
                JSONArray combinationsArray = scoresData.getJSONArray("combinations");
                StringJoiner comboJoiner = new StringJoiner("; ");
                for (int j = 0; j < combinationsArray.length(); j++) {
                    comboJoiner.add(combinationsArray.getString(j));
                }
                String combo = comboJoiner.toString();

                list.add(new ProgramScore(name, code, combo, score));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error parsing scores list", e);
        }
        return list;
    }
}