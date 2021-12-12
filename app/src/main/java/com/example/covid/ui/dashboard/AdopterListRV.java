package com.example.covid.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.covid.R;
import com.example.covid.models.RendezVous;
import com.example.covid.models.Structure;

import java.util.List;

public class AdopterListRV extends BaseAdapter {

    List<RendezVous> rendezVous;
    Context context;

    public AdopterListRV(Context context,List<RendezVous> rendezVous){
        this.context = context;
        this.rendezVous = rendezVous;
    }

    @Override
    public int getCount() {
        return rendezVous.size();
    }

    @Override
    public Object getItem(int position) {
        return rendezVous.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(this.context).inflate(R.layout.item_list_rv, parent, false);
        TextView rv_heur, rv_date, struc_adresse;
        ImageView rv_img;

        rv_heur = convertView.findViewById(R.id.rv_heur);
        rv_date = convertView.findViewById(R.id.rv_date);
        rv_img = convertView.findViewById(R.id.rv_img);
        rv_img.setImageResource(R.drawable.coronavirus);
        rv_heur.setText("Heur: "+rendezVous.get(position).heur);
        rv_date.setText(rendezVous.get(position).date.toString());
        return convertView;
    }
}
