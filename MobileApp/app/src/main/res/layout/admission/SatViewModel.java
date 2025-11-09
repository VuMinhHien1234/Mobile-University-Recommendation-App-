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
import com.example.myapplication.model.IntlCertProgramScore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SatViewModel extends AndroidViewModel {

    private static final String TAG = "SatViewModel";
    private final MutableLiveData<List<IntlCertProgramScore>> satScores = new MutableLiveData<>();
    private final RequestQueue requestQueue;

    public SatViewModel(@NonNull Application application) {
        super(application);
        requestQueue = Volley.newRequestQueue(application);
    }

    public LiveData<List<IntlCertProgramScore>> getSatScores() {
        return satScores;
    }

    public void loadSatScores(String schoolId) {
        String url = "http://10.0.2.2:3000/universities/" + schoolId + "/admission-scores";
        Log.d(TAG, "Fetching SAT/ACT scores from: " + url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    Log.d(TAG, "✅ API Response: " + response.toString());
                    try {
                        JSONArray scoresArray = response.getJSONArray("admissionScores");
                        satScores.setValue(parseSatScoresList(scoresArray));
                    } catch (Exception e) {
                        Log.e(TAG, "❌ JSON Parsing Error", e);
                        satScores.setValue(new ArrayList<>());
                    }
                },
                error -> {
                    Log.e(TAG, "❌ Volley Error", error);
                    satScores.setValue(new ArrayList<>());
                }
        );
        requestQueue.add(request);
    }

    private List<IntlCertProgramScore> parseSatScoresList(JSONArray scoresArray) {
        List<IntlCertProgramScore> list = new ArrayList<>();
        try {
            for (int i = 0; i < scoresArray.length(); i++) {
                JSONObject major = scoresArray.getJSONObject(i);

                String name = major.getString("majorName");
                String code = major.getString("id");

                // Đi vào object con để lấy điểm SAT và ACT
                JSONObject scoresData = major.getJSONObject("scores").getJSONObject("2025");

                // Kiểm tra xem các trường điểm có tồn tại không
                if (scoresData.has("SAT") && scoresData.has("ACT")) {
                    String sat = String.valueOf(scoresData.getDouble("SAT"));
                    String act = String.valueOf(scoresData.getDouble("ACT"));
                    list.add(new IntlCertProgramScore(name, code, sat, act));
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error parsing SAT/ACT scores list", e);
        }
        return list;
    }
}
