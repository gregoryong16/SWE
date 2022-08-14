package com.example.cz2006.ui.fragments;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.cz2006.classes.Response;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class SharedViewModel extends ViewModel {
    private String url = "http://10.0.2.2:3000";
    private String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private MutableLiveData<Response> response = new MutableLiveData<>();

    public LiveData<Response> getResponse() {
        return response;
    }

    public StringRequest getRequest(int type, String value) {
        String request = null;
        switch (type)
        {
            case 0:
                request = "/setWaterBudget/";
                response.getValue().getUserData().setWaterBudget(Integer.parseInt(value));
                break;
            case 1:
                request = "/setElectricityBudget/";
                response.getValue().getUserData().setElectricityBudget(Integer.parseInt(value));
                break;
            case 2:
                request = "/setElectricitySupplier/";
                response.getValue().getUserData().setElectricitySupplier(value);
                break;
            case 3:
                request = "/api/";
                break;
        }
        request = url + request + id + "/" + value;
        Log.i("AAAAAAAAA", request);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, request,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String responseStr) {
                        try {
                            if(type == 3) {
                                Gson gson = new Gson();
                                JSONObject jsonResponse = new JSONObject(responseStr);
                                response.setValue(gson.fromJson(jsonResponse.toString(), Response.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("SUCCESS", "PASSED");
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ERROR", String.valueOf(error));
            }
        });
        return stringRequest;
    }
}