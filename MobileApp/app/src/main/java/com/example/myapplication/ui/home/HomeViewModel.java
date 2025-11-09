package com.example.myapplication.ui.home;
import android.app.Application;
import android.util.Log;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.model.Nganh;
import com.example.myapplication.model.Truong;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jspecify.annotations.NonNull;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Nganh>> nganhList = new MutableLiveData<>();
    private final MutableLiveData<List<Truong>> truongList = new MutableLiveData<>();
    private final RequestQueue requestQueue;
    private List<Truong> allTruong = new ArrayList<>();


    public HomeViewModel(@NonNull Application application) {
        super(application);
        requestQueue = Volley.newRequestQueue(application);
        List<Nganh> list = new ArrayList<>();
        list.add(new Nganh("Tất cả"));
        list.add(new Nganh("Công nghệ thông tin"));
        list.add(new Nganh("Kinh tế"));
        list.add(new Nganh("Kỹ thuật"));
        list.add(new Nganh("Y dược"));
        list.add(new Nganh("Luật"));
        list.add(new Nganh("Ngôn ngữ Anh"));
        nganhList.setValue(list);
        loadTatCaTruong();
    }
    public LiveData<List<Nganh>> getNganhList() {
        return nganhList;
    }
    public LiveData<List<Truong>> getTruongList() {
        return truongList;
    }

    public void loadTatCaTruong() {
        String url = "http://10.0.2.2:3000/universities";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    allTruong = parseTruongList(response);
                    truongList.setValue(allTruong);
                },
                error -> {
                    truongList.setValue(new ArrayList<>());
                }
        );
        requestQueue.add(request);
    }
    public void loadTruongTheoNganh(String tenNganh) {
        if (allTruong == null) return;
        if (tenNganh.equalsIgnoreCase("Tất cả")) {
            truongList.setValue(allTruong);
            return;
        }
        List<Truong> filtered = new ArrayList<>();
        for (Truong t : allTruong) {
            if (t.getGroup().equalsIgnoreCase(tenNganh)) {
                filtered.add(t);
            }
        }
        truongList.setValue(filtered);
    }
    public void getTruongName(String tenTruong){
        String tmp = tenTruong.toLowerCase().trim();

        List<Truong> filetered = new ArrayList<>();
        for(Truong t : allTruong){
            if(t.getTen().toLowerCase().contains(tmp)){
                filetered.add(t);
            }
        }
        truongList.setValue(filetered);
    }

    private List<Truong> parseTruongList(JSONArray response) {
        List<Truong> ds = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                String code = obj.optString("code");
                String name = obj.optString("name");
                String description = obj.optString("description");
                String tuition = obj.optString("tuition");
                String position = obj.optString("Position");
                float rating = Float.parseFloat(obj.optString("Rating", "0"));
                String img = obj.optString("img_avartar");
                String group = obj.optString("group");
                JSONArray img_list = obj.getJSONArray("img_list");
                String highlight = obj.optString("highlight");
                String environment = obj.optString("environment");
                String network = obj.optString("network");
                String teaching_method = obj.optString("teaching_method");
                List<String> dsAnh = new ArrayList<>();
                for(int j =0 ; j < img_list.length();j++){
                    dsAnh.add(img_list.getString(j));
                }
                ds.add(new Truong(
                                code,
                                name,
                                img,
                                description,
                                tuition,
                                group,
                                rating,
                                new ArrayList<>(),
                                dsAnh,
                                position,
                                environment,
                                highlight,
                                network,
                                teaching_method
                        )
                );
            }
        } catch (Exception e) {
            Log.e("PARSE_ERROR", "Lỗi parse JSON: " + e.getMessage());
        }
        return ds;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        requestQueue.stop();
    }
}