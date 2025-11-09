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
import com.example.myapplication.model.Partner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PartnersViewModel extends AndroidViewModel {

    private static final String TAG = "PartnersViewModel";
    private final MutableLiveData<List<Partner>> partnersList = new MutableLiveData<>();
    private final RequestQueue requestQueue;

    public PartnersViewModel(@NonNull Application application) {
        super(application);
        requestQueue = Volley.newRequestQueue(application);
    }

    public LiveData<List<Partner>> getPartnersList() {
        return partnersList;
    }

    public void loadPartners(String schoolId) {
        String url = "http://10.0.2.2:3000/universities/" + schoolId + "/partners";
        Log.d(TAG, "Fetching partners from: " + url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    Log.d(TAG, "✅ API Response: " + response.toString());
                    try {
                        JSONArray partnersArray = response.getJSONArray("partners");
                        partnersList.setValue(parsePartnersList(partnersArray));
                    } catch (Exception e) {
                        Log.e(TAG, "❌ JSON Parsing Error: ", e);
                        partnersList.setValue(new ArrayList<>());
                    }
                },
                error -> {
                    Log.e(TAG, "❌ Volley Error: ", error);
                    partnersList.setValue(new ArrayList<>());
                }
        );
        requestQueue.add(request);
    }

    private List<Partner> parsePartnersList(JSONArray jsonArray) {
        List<Partner> list = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String imageUrl = jsonObject.getString("img"); // Lấy trường "img" từ API
                list.add(new Partner(name, imageUrl));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error parsing partners list", e);
        }
        return list;
    }
}
