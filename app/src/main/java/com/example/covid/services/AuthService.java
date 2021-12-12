package com.example.covid.services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.example.covid.LoginPage;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthService {

    private String API_URL;
    private Activity activity;

    public AuthService(Activity activity) {
        this.activity = activity;
        this.API_URL = "http://192.168.1.101:8001/api/";
    }

    public int login(String username, String password)  {
        final int[] result = {0};
        RequestQueue requestQueue = Volley.newRequestQueue(this.activity);
        JSONObject postData = new JSONObject();
        try {
            postData.put("username", username);
            postData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, this.API_URL+"login", postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String token = response.getString("token");
                    if (token.compareTo("")!=0){
                        result[0] =1;
                    }
                    saveDataInShared("token", token, 1);
                    decodeEndSaveTokenDataInShared(token);
                    System.out.println("aaaaaaaaaaaaaaa BbbbbbbbbGGGGG  GGGGGGGGGkkKKK");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
        return result[0];
    }

    public void sinIng(String prenom, String nom,String username, String password){
        RequestQueue requestQueue = Volley.newRequestQueue(this.activity);
        JSONObject postData = new JSONObject();
        try {
            postData.put("nom", nom);
            postData.put("prenom", prenom);
            postData.put("username", username);
            postData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, this.API_URL+"users", postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                Log.i("POST DATA", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void decodeEndSaveTokenDataInShared(String jwtToken){
        JWT parsedJWT = new JWT(jwtToken);
        Claim subscriptionMetaData = parsedJWT.getClaim("username");
        String parsedValue = subscriptionMetaData.asString();
        saveDataInShared("username", parsedValue, 1);
        System.out.println(parsedValue);
    }

    private void saveDataInShared(String key, String value, int type){
        SharedPreferences sharedpreferences=  this.activity.getSharedPreferences("TODOAPP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if (type==1){
            editor.putString(key, value);
        }else {
            editor.putInt(key, Integer.parseInt(value));
        }
        editor.commit();
    }

    public String getToken(){
        SharedPreferences sharedpreferences=  this.activity.getSharedPreferences("TODOAPP", Context.MODE_PRIVATE);
        return  sharedpreferences.getString("token", "");
    }


}
