package com.example.myapplication.ui.gioithieu.cocautochuc;

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

public class CoCauToChucViewModel extends AndroidViewModel {
    private final MutableLiveData<CoCauToChucData> coCauToChucData = new MutableLiveData<>();
    private final RequestQueue requestQueue;
    private final String TAG = "CoCauToChucViewModel";

    public LiveData<CoCauToChucData> getCoCauToChucData() {
        return coCauToChucData;
    }

    public CoCauToChucViewModel(@NonNull Application application) {
        super(application);
        requestQueue = Volley.newRequestQueue(application);
    }

    public void loadData(String schoolId) {
        String url = "http://10.0.2.2:3000/universities/" + schoolId + "/cocautochuc";
        Log.d(TAG, "Đang tải dữ liệu từ: " + url);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    Log.d(TAG, "Nhận được phản hồi: " + response.toString());
                    try {
                        JSONObject ctcData = response.optJSONObject("cocautochuc");
                        if (ctcData != null) {
                            String mainImageUrl = ctcData.optString("mainImageUrl", "");
                            JSONArray unitsArray = ctcData.optJSONArray("units");

                            List<CoCauToChuc> units = new ArrayList<>();
                            if (unitsArray != null) {
                                for (int i = 0; i < unitsArray.length(); i++) {
                                    JSONObject unitObj = unitsArray.getJSONObject(i);
                                    units.add(new CoCauToChuc(
                                            unitObj.optString("logoUrl", ""),
                                            unitObj.optString("title", ""),
                                            unitObj.optString("phone", ""),
                                            unitObj.optString("email", ""),
                                            unitObj.optString("address", "")
                                    ));
                                }
                            }
                            coCauToChucData.setValue(new CoCauToChucData(mainImageUrl, units));
                        } else {
                            Log.w(TAG, "Không tìm thấy dữ liệu 'cocautochuc'.");
                            coCauToChucData.setValue(new CoCauToChucData("", new ArrayList<>()));
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Lỗi phân tích JSON", e);
                        coCauToChucData.setValue(new CoCauToChucData("", new ArrayList<>()));
                    }
                },
                error -> {
                    Log.e(TAG, "Lỗi tải dữ liệu: " + error.toString());
                    coCauToChucData.setValue(new CoCauToChucData("", new ArrayList<>()));
                }
        );
        requestQueue.add(request);
    }
}

class CoCauToChucData {
    private final String mainImageUrl;
    private final List<CoCauToChuc> units;

    public CoCauToChucData(String mainImageUrl, List<CoCauToChuc> units) {
        this.mainImageUrl = mainImageUrl;
        this.units = units;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public List<CoCauToChuc> getUnits() {
        return units;
    }
}