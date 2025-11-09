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
import com.example.myapplication.model.TableRowItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EmploymentStatsViewModel extends AndroidViewModel {

    private static final String TAG = "EmploymentStatsVM";
    private final RequestQueue requestQueue;

    // Bốn LiveData riêng biệt cho bốn khối dữ liệu
    private final MutableLiveData<List<TableRowItem>> graduateStats = new MutableLiveData<>();
    private final MutableLiveData<List<TableRowItem>> workTypeStats = new MutableLiveData<>();
    private final MutableLiveData<List<TableRowItem>> locationStats = new MutableLiveData<>();
    private final MutableLiveData<List<TableRowItem>> incomeStats = new MutableLiveData<>();

    public EmploymentStatsViewModel(@NonNull Application application) {
        super(application);
        requestQueue = Volley.newRequestQueue(application);
    }

    // Getters cho Activity lắng nghe
    public LiveData<List<TableRowItem>> getGraduateStats() { return graduateStats; }
    public LiveData<List<TableRowItem>> getWorkTypeStats() { return workTypeStats; }
    public LiveData<List<TableRowItem>> getLocationStats() { return locationStats; }
    public LiveData<List<TableRowItem>> getIncomeStats() { return incomeStats; }

    public void loadEmploymentData(String schoolId) {
        String url = "http://10.0.2.2:3000/universities/" + schoolId + "/employment";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    Log.d(TAG, "✅ API Response: " + response.toString());
                    try {
                        JSONArray employmentArray = response.getJSONArray("employment");
                        processAndDistributeData(employmentArray);
                    } catch (Exception e) {
                        Log.e(TAG, "❌ JSON Parsing Error", e);
                    }
                },
                error -> Log.e(TAG, "❌ Volley Error", error)
        );
        requestQueue.add(request);
    }

    private void processAndDistributeData(JSONArray data) {
        // Tạo 4 danh sách tạm thời
        List<TableRowItem> graduateList = new ArrayList<>();
        List<TableRowItem> workTypeList = new ArrayList<>();
        List<TableRowItem> locationList = new ArrayList<>();
        List<TableRowItem> incomeList = new ArrayList<>();

        try {
            for (int i = 0; i < data.length(); i++) {
                JSONObject item = data.getJSONObject(i);
                String id = item.getString("id");
                String col1 = item.getString("col1");
                String col2 = item.getString("col2");

                // Dựa vào ID để phân loại dữ liệu vào đúng danh sách
                if (id.startsWith("graduate")) {
                    graduateList.add(new TableRowItem(graduateList.size() + 1, col1, col2));
                } else if (id.startsWith("type")) {
                    workTypeList.add(new TableRowItem(workTypeList.size() + 1, col1, col2));
                } else if (id.startsWith("true")) { // Giả sử "true" là id cho khối 3
                    locationList.add(new TableRowItem(locationList.size() + 1, col1, col2));
                } else if (id.startsWith("income")) {
                    incomeList.add(new TableRowItem(incomeList.size() + 1, col1, col2));
                }
            }

            // Cập nhật giá trị cho các LiveData sau khi đã xử lý xong
            graduateStats.setValue(graduateList);
            workTypeStats.setValue(workTypeList);
            locationStats.setValue(locationList);
            incomeStats.setValue(incomeList);

        } catch (Exception e) {
            Log.e(TAG, "❌ Error distributing data", e);
        }
    }
}