package com.example.myapplication.ui.sinhvien.cauhoithuonggap;

import android.app.Application;

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

public class FAQViewModel extends AndroidViewModel {

    private final MutableLiveData<List<FAQItem>> faqList = new MutableLiveData<>();
    private final RequestQueue requestQueue;

    public FAQViewModel(@NonNull Application application) {
        super(application);
        requestQueue = Volley.newRequestQueue(application);
    }

    public LiveData<List<FAQItem>> getFaqList() {
        return faqList;
    }

    public void loadData(String schoolId) {
        String url = "http://10.0.2.2:3000/universities/" + schoolId + "/faq";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        JSONArray faqJsonArray = response.getJSONArray("faq");
                        List<FAQItem> items = new ArrayList<>();
                        for (int i = 0; i < faqJsonArray.length(); i++) {
                            JSONObject faqObject = faqJsonArray.getJSONObject(i);
                            String question = faqObject.getString("question");
                            String answer = faqObject.getString("answer");
                            items.add(new FAQItem(question, answer));
                        }
                        faqList.setValue(items);
                    } catch (Exception e) {
                        faqList.setValue(new ArrayList<>()); // Set empty list on error
                    }
                },
                error -> {
                    faqList.setValue(new ArrayList<>()); // Set empty list on network error
                }
        );
        requestQueue.add(request);
    }
}
