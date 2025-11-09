package com.example.myapplication.ui.truong.Program;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.model.Nganh;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class EducationProgramViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Nganh>> nganhList = new MutableLiveData<>();
    public LiveData<List<Nganh>> getNganhList() {
        return nganhList;
    }
    public final RequestQueue requestQueue;
    public EducationProgramViewModel(@NonNull Application application) {
        super(application);
        requestQueue = Volley.newRequestQueue(application);
    }
    public void loadData(String id) {
        String url = "http://10.0.2.2:3000/universities/PTA" + id + "/programs";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        JSONArray nganhArray = response.optJSONArray("program");
                        List<Nganh> ds = parseNganhList(nganhArray);
                        nganhList.setValue(ds);
                    } catch (Exception e) {
                        nganhList.setValue(new ArrayList<>());
                    }
                },
                error -> {
                    nganhList.setValue(new ArrayList<>());
                }
        );
        requestQueue.add(request);
    }
    private List<Nganh> parseNganhList(JSONArray response){
        List<Nganh> ds = new ArrayList<>();
        try{
            for(int i = 0; i < response.length();i++){
                JSONObject obj = response.getJSONObject(i);
                String id = obj.optString("id");
                String intakeSemester= obj.optString("intakeSemester");
                String duration = obj.optString("duration");
                String campus = obj.optString("campus");
                JSONArray  arr = obj.optJSONArray("programNames");
                String name = arr.getString(0);
                Nganh nganh = new Nganh(id,name,duration, campus, intakeSemester);
                Log.d("EducationProgram", "Nganh ID: " + nganh.getId() + ", Ten: " + nganh.getName());
                ds.add(nganh);
            }
        }catch (Exception e){
           return null;
        }
        return ds;
    }
    public Nganh getNganhById(String id) {
        for (Nganh n : nganhList.getValue()) {
            if (id.equals(n.getId())) {
                return n;
            }
        }
        return null;
    }
}