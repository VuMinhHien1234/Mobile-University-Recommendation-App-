package com.example.myapplication.ui.sinhvien.hocbong;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HocBongViewModel extends AndroidViewModel {
    private final MutableLiveData<List<ItemHocBong>> hbList = new MutableLiveData<>();
    private final RequestQueue requestQueue;
    private final String TAG = "HocBongViewModel";

    public LiveData<List<ItemHocBong>> getHBList() {
        return hbList;
    }

    public HocBongViewModel(@NonNull Application application) {
        super(application);
        requestQueue = Volley.newRequestQueue(application);
    }

    public void loadData(String schoolId) {
        String url = "http://10.0.2.2:3000/universities/" + schoolId + "/HocBong";
        Log.d(TAG, "Loading data from: " + url);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    Log.d(TAG, "Received response: " + response.toString());
                    try {
                        JSONArray hbArray = response.optJSONArray("HocBong");
                        if (hbArray != null) {
                            List<ItemHocBong> schoolarship = parseHBList(hbArray);
                            hbList.setValue(schoolarship);
                        } else {
                            Log.w(TAG, "No 'HocBong' array found.");
                            hbList.setValue(new ArrayList<>());
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error parsing JSON", e);
                        hbList.setValue(new ArrayList<>());
                    }
                },
                error -> {
                    Log.e(TAG, "Error loading data: " + error.toString());
                    hbList.setValue(new ArrayList<>());
                }
        );
        requestQueue.add(request);
    }

    private List<ItemHocBong> parseHBList(JSONArray array) {
        List<ItemHocBong> schoolarship = new ArrayList<>();
        if (array == null) return schoolarship;

        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Log.d(TAG, "Processing schoolarship: " + obj.toString());

                String date = obj.optString("datehb", "");
                String imageUrl = obj.optString("imghb", "");
                String des = obj.optString("texthb", "");

                schoolarship.add(new ItemHocBong(imageUrl, date, des));
                Log.d(TAG, "Added schoolarship: " + des);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error parsing schoolarship object", e);
            return new ArrayList<>();
        }
        return schoolarship;
    }
}
