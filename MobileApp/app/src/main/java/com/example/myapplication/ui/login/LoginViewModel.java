package com.example.myapplication.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginViewModel extends AndroidViewModel {
    private final MutableLiveData<String> loginResult = new MutableLiveData<>();
    private final RequestQueue requestQueue;
    public LoginViewModel(@NonNull Application application) {
        super(application);
        requestQueue = Volley.newRequestQueue(application);
    }
    public LiveData<String> getLoginResult() {
        return loginResult;
    }
    public void login(String email, String password) {
        String url = "http://10.0.2.2:3000/users/login";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonBody,
        new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            if ("Đăng nhập thành công".equals(message)) {
                                loginResult.setValue("Đăng nhập thành công");
                            } else {
                                loginResult.setValue(message);
                            }
                        } catch (Exception e) {
                            loginResult.setValue(e.getMessage());
                        }
                    }
                },
              null
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };
        requestQueue.add(request);
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        requestQueue.stop();
    }
}
