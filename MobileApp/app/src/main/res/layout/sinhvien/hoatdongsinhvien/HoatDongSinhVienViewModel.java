package com.example.myapplication.ui.sinhvien.hoatdongsinhvien;

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
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HoatDongSinhVienViewModel extends AndroidViewModel {
    private final MutableLiveData<List<ItemHoatDongSinhVien>> hdsvList = new MutableLiveData<>();
    private static final String TAG = "HoatDongSinhVienViewModel";
    private final RequestQueue requestQueue;

    public LiveData<List<ItemHoatDongSinhVien>> getHDSVList() {
        return hdsvList;
    }

    public HoatDongSinhVienViewModel(@NonNull Application application) {
        super(application);
        requestQueue = Volley.newRequestQueue(application);
    }

    public void loadData(String schoolId) {
        String url = "http://10.0.2.2:3000/universities/" + schoolId + "/HoatDongSinhVien";
        Log.d(TAG, "Loading data from: " + url);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    Log.d(TAG, "Received response: " + response.toString());
                    try {
                        JSONArray hdsvArray = response.optJSONArray("HoatDongSinhVien");
                        if (hdsvArray != null) {
                            List<ItemHoatDongSinhVien> activities = parseHDSList(hdsvArray);
                            hdsvList.setValue(activities);
                        } else {
                            Log.w(TAG, "No 'HoatDongSinhVien' array found.");
                            hdsvList.setValue(new ArrayList<>());
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error parsing JSON", e);
                        hdsvList.setValue(new ArrayList<>());
                    }
                },
                error -> {
                    Log.e(TAG, "Error loading data: " + error.toString());
                    hdsvList.setValue(new ArrayList<>());
                }
        );
        requestQueue.add(request);
    }

    private List<ItemHoatDongSinhVien> parseHDSList(JSONArray array) {
        List<ItemHoatDongSinhVien> activities = new ArrayList<>();
        if (array == null) return activities;

        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Log.d(TAG, "Processing activity: " + obj.toString());

                String date = obj.optString("datehdsv", "");
                String imageUrl = obj.optString("imghdsv", "");
                String des = obj.optString("texthdsv", "");
                int iconCalendar = R.drawable.ic_calendar;  // Icon cố định

                activities.add(new ItemHoatDongSinhVien(imageUrl, iconCalendar, date, des));
                Log.d(TAG, "Added activity: " + des);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error parsing activity object", e);
            return new ArrayList<>();
        }
        return activities;
    }
}