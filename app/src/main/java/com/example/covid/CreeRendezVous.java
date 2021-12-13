package com.example.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid.services.AuthService;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreeRendezVous extends AppCompatActivity {
    int idStructure;
    String nomStructure;
    Button button_valid;
    TextInputEditText heur, date;
    TextView nom_hospis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_cree_rendez_vous);
        idStructure = getIntent().getIntExtra("idStructure",0);
        nomStructure = getIntent().getStringExtra("nomStructure");
        button_valid = findViewById(R.id.button_valid);
        heur = findViewById(R.id.heur);
        date = findViewById(R.id.date);
        nom_hospis = findViewById(R.id.nom_hospis);

        nom_hospis.setText("Prendre rendez-vous a "+ nomStructure);

        button_valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRendezVous(idStructure,date.getText().toString(),heur.getText().toString());
            }
        });
    }

    void createRendezVous(int structureSante, String date, String heur){
        String API_URL = "http://192.168.1.101:8001/api/";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject postData = new JSONObject();
        try {
            postData.put("date", date);
            postData.put("heur", heur);
            postData.put("structureSante", structureSante);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API_URL+"rv-create", postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                Intent i = new Intent(CreeRendezVous.this, Acceul.class);
                startActivity(i);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                AuthService authService = new AuthService(CreeRendezVous.this);
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+authService.getToken());
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}