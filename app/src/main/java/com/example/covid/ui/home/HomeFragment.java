package com.example.covid.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid.R;
import com.example.covid.databinding.FragmentHomeBinding;
import com.example.covid.models.Structure;
import com.example.covid.services.AuthService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private ListView listStructures;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listStructures = binding.listStructures;
        displayStructureFromAPI();
        return root;
    }
    void displayStructureFromAPI(){
        String API_URL = "http://192.168.1.101:8001/api/";
        List<Structure> structures = new LinkedList<Structure>();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API_URL+"structure_santes", null, new Response.Listener<JSONObject>() {
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
                    AdopterListStructure listAdopter = new AdopterListStructure(getContext(), structures);
                    listStructures.setAdapter(listAdopter);
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
                AuthService authService = new AuthService(getActivity());
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+authService.getToken());
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

   /*
   @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
}