package com.example.covid.ui.dashboard;

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
import com.example.covid.databinding.FragmentDashboardBinding;
import com.example.covid.models.RendezVous;
import com.example.covid.models.Structure;
import com.example.covid.services.AuthService;
import com.example.covid.ui.home.AdopterListStructure;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    private ListView list_rv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        list_rv = binding.listRv;
        displayRVFromAPI();
        return root;
    }

    void displayRVFromAPI(){
        String API_URL = "http://192.168.1.101:8001/api/";
        List<RendezVous> rendezVous = new LinkedList<RendezVous>();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API_URL+"rv-users", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        Date date =  new SimpleDateFormat("dd-MM-yyyy").parse(jsonObject.getString("date"));
                        String heur = jsonObject.getString("heur");
                        int structureId = jsonObject.getJSONObject("structureSante").getInt("id");
                        String structureName = jsonObject.getJSONObject("structureSante").getString("nom");
                        rendezVous.add(new RendezVous(id, date, heur,structureId, structureName));
                    }
                    AdopterListRV listAdopter = new AdopterListRV(getContext(), rendezVous);
                    list_rv.setAdapter(listAdopter);
                } catch (JSONException | ParseException e) {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}