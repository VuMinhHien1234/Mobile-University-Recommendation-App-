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
import com.example.myapplication.model.MajorFee;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FeeViewModel extends AndroidViewModel {

    private static final String TAG = "FeeViewModel";
    private final MutableLiveData<List<MajorFee>> majorFeeList = new MutableLiveData<>();
    private final RequestQueue requestQueue;

    public FeeViewModel(@NonNull Application application) {
        super(application);
        requestQueue = Volley.newRequestQueue(application);
    }

    public LiveData<List<MajorFee>> getMajorFeeList() {
        return majorFeeList;
    }

    public void loadMajorFees(String schoolId) {
        // API endpoint để lấy danh sách các chương trình/ngành học
        String url = "http://10.0.2.2:3000/universities/" + schoolId + "/programs";
        Log.d(TAG, "Fetching major fees from: " + url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    Log.d(TAG, "✅ API Response: " + response.toString());
                    try {
                        // Lấy mảng "program" từ JSON object trả về
                        JSONArray programArray = response.getJSONArray("program");
                        majorFeeList.setValue(parseMajorFeeList(programArray));
                    } catch (Exception e) {
                        Log.e(TAG, "❌ JSON Parsing Error: ", e);
                        majorFeeList.setValue(new ArrayList<>());
                    }
                },
                error -> {
                    Log.e(TAG, "❌ Volley Error: ", error);
                    majorFeeList.setValue(new ArrayList<>());
                }
        );
        requestQueue.add(request);
    }

    private List<MajorFee> parseMajorFeeList(JSONArray programArray) {
        List<MajorFee> list = new ArrayList<>();
        try {
            for (int i = 0; i < programArray.length(); i++) {
                JSONObject program = programArray.getJSONObject(i);

                // Trích xuất dữ liệu theo yêu cầu
                String maNganh = program.getString("id");

                // Lấy tên ngành từ phần tử đầu tiên của mảng programNames
                String tenNganh = program.getJSONArray("programNames").getString(0);

                // Đi sâu vào object để lấy học phí dự kiến cho năm 2025
                // Lưu ý: "2025" ở đây là cứng, cần điều chỉnh nếu API có nhiều năm
                long hocPhi = program.getJSONObject("tuitionInfo")
                        .getJSONObject("2025")
                        .getLong("totalEstimatedFee");

                // Tạo đối tượng MajorFee và thêm vào danh sách
                list.add(new MajorFee(tenNganh, maNganh, hocPhi));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error parsing major fee list", e);
        }
        return list;
    }
}