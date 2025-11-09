package com.example.myapplication.ui.gioithieu.nguonnhanluc;

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

import org.json.JSONObject;

public class NguonNhanLucViewModel extends AndroidViewModel {
    private final MutableLiveData<ItemNguonNhanLuc> nguonNhanLucData = new MutableLiveData<>();
    private final RequestQueue requestQueue;
    private final String TAG = "NguonNhanLucViewModel";

    public LiveData<ItemNguonNhanLuc> getNguonNhanLucData() {
        return nguonNhanLucData;
    }

    public NguonNhanLucViewModel(@NonNull Application application) {
        super(application);
        requestQueue = Volley.newRequestQueue(application);
    }

    public void loadData(String schoolId) {
        // Thay thế bằng URL API thực tế của bạn
        String url = "http://10.0.2.2:3000/universities/" + schoolId + "/nguonnhanluc";
        Log.d(TAG, "Đang tải dữ liệu từ: " + url);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    Log.d(TAG, "Nhận được phản hồi: " + response.toString());
                    try {
                        JSONObject hrData = response.optJSONObject("nguonnhanluc");
                        if (hrData != null) {
                            String description = hrData.optString("description", "");
                            String imageUrl = hrData.optString("imageUrl", "");
                            if (description.isEmpty() && imageUrl.isEmpty()) {
                                Log.w(TAG, "Dữ liệu nguồn nhân lực trống.");
                                nguonNhanLucData.postValue(null);
                            } else {
                                nguonNhanLucData.postValue(new ItemNguonNhanLuc(description, imageUrl));
                            }
                        } else {
                            Log.w(TAG, "Không tìm thấy đối tượng 'nguonnhanluc' trong phản hồi.");
                            nguonNhanLucData.postValue(null);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Lỗi phân tích JSON", e);
                        nguonNhanLucData.postValue(null);
                    }
                },
                error -> {
                    Log.e(TAG, "Lỗi tải dữ liệu: " + error.toString());
                    nguonNhanLucData.postValue(null);
                }
        );
        requestQueue.add(request);
    }
}

class ItemNguonNhanLuc {
    private final String description;
    private final String imageUrl;

    public ItemNguonNhanLuc(String description, String imageUrl) {
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
