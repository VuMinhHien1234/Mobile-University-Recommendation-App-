package com.example.myapplication.ui.gioithieu.cosovatchat;

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

public class CoSoVatChatViewModel extends AndroidViewModel {
    private final MutableLiveData<ItemCoSoVatChatData> coSoVatChatData = new MutableLiveData<>();
    private final RequestQueue requestQueue;
    private final String TAG = "CoSoVatChatViewModel";

    public LiveData<ItemCoSoVatChatData> getCoSoVatChatData() {
        return coSoVatChatData;
    }

    public CoSoVatChatViewModel(@NonNull Application application) {
        super(application);
        requestQueue = Volley.newRequestQueue(application);
    }

    public void loadData(String schoolId) {
        String url = "http://10.0.2.2:3000/universities/" + schoolId + "/cosovatchat";
        Log.d(TAG, "Đang tải dữ liệu từ: " + url);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    Log.d(TAG, "Nhận được phản hồi: " + response.toString());
                    try {
                        JSONObject csvcData = response.optJSONObject("cosovatchat");
                        if (csvcData != null) {
                            String description = csvcData.optString("description", "");
                            String mainImageUrl = csvcData.optString("mainImageUrl", "");
                            JSONArray imagesArray = csvcData.optJSONArray("images");

                            List<String> imageUrls = new ArrayList<>();
                            if (imagesArray != null) {
                                for (int i = 0; i < imagesArray.length(); i++) {
                                    JSONObject imgObj = imagesArray.getJSONObject(i);
                                    imageUrls.add(imgObj.optString("imageUrl", ""));
                                }
                            }

                            coSoVatChatData.setValue(new ItemCoSoVatChatData(description, mainImageUrl, imageUrls));
                        } else {
                            Log.w(TAG, "Không tìm thấy dữ liệu 'cosovatchat'.");
                            coSoVatChatData.setValue(new ItemCoSoVatChatData("", "", new ArrayList<>()));
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Lỗi phân tích JSON", e);
                        coSoVatChatData.setValue(new ItemCoSoVatChatData("", "", new ArrayList<>()));
                    }
                },
                error -> {
                    Log.e(TAG, "Lỗi tải dữ liệu: " + error.toString());
                    coSoVatChatData.setValue(new ItemCoSoVatChatData("", "", new ArrayList<>()));
                }
        );
        requestQueue.add(request);
    }
}

class ItemCoSoVatChatData {
    private final String description;
    private final String mainImageUrl;
    private final List<String> imageUrls;

    public ItemCoSoVatChatData(String description, String mainImageUrl, List<String> imageUrls) {
        this.description = description;
        this.mainImageUrl = mainImageUrl;
        this.imageUrls = imageUrls;
    }

    public String getDescription() {
        return description;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }
}