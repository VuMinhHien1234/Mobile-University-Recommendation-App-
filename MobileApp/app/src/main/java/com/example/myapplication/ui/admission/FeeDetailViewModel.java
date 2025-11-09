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
import com.example.myapplication.model.SemesterFee;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FeeDetailViewModel extends AndroidViewModel {

    private static final String TAG = "FeeDetailViewModel";
    private final MutableLiveData<List<SemesterFee>> semesterFeeList = new MutableLiveData<>();
    private final RequestQueue requestQueue;

    public FeeDetailViewModel(@NonNull Application application) {
        super(application);
        requestQueue = Volley.newRequestQueue(application);
    }

    public LiveData<List<SemesterFee>> getSemesterFeeList() {
        return semesterFeeList;
    }

    public void loadSemesterFees(String schoolId, String majorId) {
        String url = "http://10.0.2.2:3000/universities/" + schoolId + "/programs";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray programArray = response.getJSONArray("program");
                        semesterFeeList.setValue(parseSemestersForMajor(programArray, majorId));
                    } catch (Exception e) {
                        Log.e(TAG, "❌ JSON Parsing Error", e);
                        semesterFeeList.setValue(new ArrayList<>());
                    }
                },
                error -> {
                    Log.e(TAG, "❌ Volley Error", error);
                    semesterFeeList.setValue(new ArrayList<>());
                }
        );
        requestQueue.add(request);
    }

    private List<SemesterFee> parseSemestersForMajor(JSONArray programArray, String majorId) {
        List<SemesterFee> list = new ArrayList<>();
        try {
            // 1. Tìm đúng ngành học trong danh sách
            for (int i = 0; i < programArray.length(); i++) {
                JSONObject program = programArray.getJSONObject(i);
                if (majorId.equals(program.optString("id"))) {

                    // 2. Lấy mảng chi tiết học phí của các kỳ
                    JSONArray semesterDetails = program.getJSONObject("tuitionInfo")
                            .getJSONObject("2025") // Giả sử năm 2025
                            .getJSONArray("semesterDetails");

                    // 3. Lặp qua từng kỳ và tạo đối tượng SemesterFee
                    for (int j = 0; j < semesterDetails.length(); j++) {
                        JSONObject semester = semesterDetails.getJSONObject(j);

                        // Mapping dữ liệu theo yêu cầu của bạn
                        String ki = "Kì " + semester.getInt("semester");
                        long feePerCredit = semester.getLong("feePerCredit");
                        int totalCredits = semester.getInt("totalCredits");
                        long totalSemesterFee = semester.getLong("totalSemesterFee");

                        list.add(new SemesterFee(ki, feePerCredit, totalCredits, totalSemesterFee));
                    }
                    // Tìm thấy ngành rồi thì thoát khỏi vòng lặp
                    break;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error parsing semester details", e);
        }
        return list;
    }
}