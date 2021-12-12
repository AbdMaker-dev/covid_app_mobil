package com.example.covid.services;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid.models.Structure;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.AbstractOwnableSynchronizer;

public class StructureService {

    private String API_URL;
    private Activity activity;

    public StructureService(Activity activity) {
        this.activity = activity;
        this.API_URL = "http://192.168.1.101:8001/api/";
    }


    public synchronized List<Structure>  getAllStructure(){
        List<Structure> structures = new LinkedList<Structure>();
        RequestQueue requestQueue = Volley.newRequestQueue(this.activity);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, this.API_URL+"structure_santes", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               try {
                    JSONArray jsonArray = response.getJSONArray("hydra:member");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        double lattitude = jsonObject.getDouble("lattitude");
                        double longitude = jsonObject.getDouble("longitude");
                        String nom = jsonObject.getString("nom");
                        String contact = jsonObject.getString("contact");
                        String adresse = jsonObject.getString("adresse");
                        structures.add(new Structure(id,lattitude,longitude,nom,contact,adresse));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                AuthService authService = new AuthService(activity);
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+authService.getToken());
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
        return structures;
    }
}
