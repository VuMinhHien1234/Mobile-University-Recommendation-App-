package com.example.myapplication.ui.truong.Program;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.myapplication.model.KiHoc;
import com.example.myapplication.model.MonHoc;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;


public class EducationProgramDetailViewModel extends AndroidViewModel {
    private MutableLiveData<List<KiHoc>> kiHocLiveData = new MutableLiveData<>();
    public final RequestQueue requestQueue;
    public EducationProgramDetailViewModel(@NonNull Application application)
    {
        super(application);
        requestQueue = Volley.newRequestQueue(application);
    }
    public LiveData<List<KiHoc>> getMonhoc(){
        return kiHocLiveData;
    }
    public void loadCurriculum(String schoolId, String programId) {
        String url = "http://10.0.2.2:3000/universities/"+ schoolId+ "/programs/"+programId +"/curriculum";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        JSONArray curriculumArray = response.optJSONArray("curriculum");
                        List<KiHoc> kiHocList = parseKiHoc(curriculumArray);
                        kiHocLiveData.setValue(kiHocList);
                    } catch (Exception e) {
                        kiHocLiveData.setValue(new ArrayList<>());
                    }
                },
                error -> {
                    Log.e("API_ERROR", "Lỗi khi gọi API: " + error.toString());
                    kiHocLiveData.setValue(new ArrayList<>());
                }
        );

        requestQueue.add(request);
    }
    private List<KiHoc> parseKiHoc(JSONArray response){
        List<KiHoc> ds = new ArrayList<>();
        try{
            for(int i =0 ; i < response.length();i++){
                JSONObject obj = response.getJSONObject(i);
                String kyId = "Kỳ" + obj.optString("ky_id");
                JSONArray monList = obj.optJSONArray("subjects");
                List<MonHoc> monHocList = new ArrayList<>();
                if (monList != null) {
                    for (int j = 0; j < monList.length(); j++) {
                        JSONObject subObj = monList.getJSONObject(j);
                        String name = subObj.optString("name");
                        int credits = subObj.optInt("credits");
                        monHocList.add(new MonHoc(name, credits));
                    }
                }
                ds.add(new KiHoc(kyId,monHocList));
            }
        }catch (Exception e){
            return null;
        }
        return ds;

    }
}