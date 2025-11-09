package com.example.myapplication.ui.module2;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.model.Module2Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Module2ViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Module2Data>> module2DataListLiveData = new MutableLiveData<>();
    private final RequestQueue requestQueue;

    public Module2ViewModel(@NonNull Application application) {
        super(application);
        requestQueue = Volley.newRequestQueue(application);
    }

    public LiveData<List<Module2Data>> getModule2DataListLiveData() {
        return module2DataListLiveData;
    }

    public void sendDatatoServer(String toan, String van, String mon3, String mon4, String mon5,
                                 String tenmon3, String tenmon4, String tenmon5, String diemcong,
                                 String hsa, String tsa, String mien, String tp, String nganh, String dh) {

        try {
            String url = "http://10.0.2.2:5001/recommend";
            JSONObject js = new JSONObject();
            JSONObject thpt = new JSONObject();

            if(mon3==""){
                mon3="0";
            }
            if(tenmon3==""){
                tenmon3="Tiếng Anh";
            }
            if(mon4==""){
                mon4="0";
            }
            if(tenmon4==""){
                tenmon4="Tiếng Anh";
            }
            if(mon5==""){
                mon5="0";
            }
            if(tenmon5==""){
                tenmon5="Tiếng Anh";
            }
            if(mien==""){
                mien="Miền Bắc";
            }
            if(tp==""){
                tp="Hà Nội";
            }
            if(diemcong==""){
                diemcong="0";
            }
            if(hsa==""){
                hsa="10";
            }
            if(tsa==""){
                tsa="10";
            }

            thpt.put("Toán", Double.parseDouble(toan));
            thpt.put("Ngữ văn", Double.parseDouble(van));
            thpt.put(tenmon3, Double.parseDouble(mon3));
            thpt.put(tenmon4, Double.parseDouble(mon4));
            thpt.put(tenmon5, Double.parseDouble(mon5));
            thpt.put("plus", Double.parseDouble(diemcong));

            js.put("THPT", thpt);
            js.put("HSA", Double.parseDouble(hsa));
            js.put("TSA", Double.parseDouble(tsa));
            js.put("Region", mien);
            js.put("City", tp);
            js.put("Expected_major", nganh);
            js.put("Expected_university", dh);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    js,
                    response -> {
                    try {
                        JSONArray resultsArray = response.getJSONArray("results");
                        List<Module2Data> dataList = parseTruong(resultsArray);
                        module2DataListLiveData.setValue(dataList);
                        Log.d("Module2ViewModel", "Parsed " + dataList );
                    } catch (Exception e) {
                        module2DataListLiveData.setValue(null);
                    }
                    },null
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    return headers;
                }
            };
            requestQueue.add(request);
        } catch (Exception e) {
            Log.e("Module2ViewModel", "Error creating JSON: " + e.getMessage());
        }
    }
    private List<Module2Data> parseTruong(JSONArray resultsArray) throws JSONException {
        List<Module2Data> dataList = new ArrayList<>();
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject item = resultsArray.getJSONObject(i);
                Module2Data data = new Module2Data(
                    String.valueOf(item.getDouble("academic_fit_score")),
                    String.valueOf(item.getDouble("benmark")),
                    item.getString("combination"),
                    item.getString("major_name"),
                    String.valueOf(item.getDouble("final_score")),
                    item.getString("method"),
                    String.valueOf(item.getDouble("major_simlarity_score")),
                    String.valueOf(item.getDouble("suitable_rate")),
                    item.getString("universityId"),
                    String.valueOf(item.getDouble("user_score")),
                    String.valueOf(item.getDouble("university_score")),
                    String.valueOf(item.getDouble("score_match"))

                );
                dataList.add(data);
            }
        return dataList;
    }
}