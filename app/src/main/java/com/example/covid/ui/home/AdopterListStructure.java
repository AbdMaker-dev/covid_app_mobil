package com.example.covid.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.covid.R;
import com.example.covid.models.Structure;

import java.util.List;

public class AdopterListStructure extends BaseAdapter {

    List<Structure> structures;
    Context context;

    public AdopterListStructure(Context context,List<Structure> structures){
        this.context =context;
        this.structures =structures;
    }

    @Override
    public int getCount() {
        return structures.size();
    }

    @Override
    public Object getItem(int position) {
        return structures.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(this.context).inflate(R.layout.item_list_structure, parent, false);
        TextView struc_nom, struc_contact, struc_adresse;
        ImageView struc_img;
        struc_nom = convertView.findViewById(R.id.struc_nom);
        struc_contact = convertView.findViewById(R.id.struc_contact);
        struc_adresse = convertView.findViewById(R.id.struc_adresse);
        struc_img = convertView.findViewById(R.id.struc_img);
        struc_img.setImageResource(R.drawable.hospiss);

        struc_nom.setText(structures.get(position).nom);
        struc_contact.setText("Tel: "+structures.get(position).contact);
        struc_adresse.setText(structures.get(position).adresse);
        return convertView;
    }
}
