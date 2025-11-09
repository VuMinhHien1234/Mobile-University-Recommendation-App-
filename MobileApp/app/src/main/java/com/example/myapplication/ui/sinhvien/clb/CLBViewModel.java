package com.example.myapplication.ui.sinhvien.clb;

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

public class CLBViewModel extends AndroidViewModel {
    private final MutableLiveData<List<ItemCLB>> clbList = new MutableLiveData<>();
    private static final String TAG = "CLBViewModel";
    private final RequestQueue requestQueue;

    public LiveData<List<ItemCLB>> getCLBList() {
        return clbList;
    }

    public CLBViewModel(@NonNull Application application) {
        super(application);
        requestQueue = Volley.newRequestQueue(application);
    }

    public void loadData(String id) {
        String url = "http://10.0.2.2:3000/universities/" + id + "/CLB";
        Log.d(TAG, "Loading data from: " + url);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    Log.d(TAG, "Received response: " + response.toString());
                    try {
                        JSONArray clbArray = response.optJSONArray("CLB");
                        if (clbArray != null) {
                            List<ItemCLB> clubs = parseCLBList(clbArray);
                            clbList.setValue(clubs);
                        } else {
                            Log.w(TAG, "No 'CLB' array found in response.");
                            clbList.setValue(new ArrayList<>());
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error parsing JSON", e);
                        clbList.setValue(new ArrayList<>());
                    }
                },
                error -> {
                    Log.e(TAG, "Error loading data: " + error.toString());
                    clbList.setValue(new ArrayList<>());
                }
        );
        requestQueue.add(request);
    }

    private List<ItemCLB> parseCLBList(JSONArray array) {
        List<ItemCLB> clubs = new ArrayList<>();
        if (array == null) return clubs;

        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Log.d(TAG, "Processing club: " + obj.toString());

                String logo = obj.optString("imgclb", "");
                String facebookLink = obj.optString("linkface", "");
                String leaderName = obj.optString("namechunghiem", "");
                String clubName = obj.optString("nameclb", "");

                clubs.add(new ItemCLB(logo, clubName, leaderName, facebookLink));
                Log.d(TAG, "Added club: " + clubName);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error parsing club object", e);
            return new ArrayList<>();
        }
        return clubs;
    }
}