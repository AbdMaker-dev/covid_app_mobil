package com.example.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.covid.models.Structure;
import com.example.covid.services.AuthService;
import com.example.covid.services.StructureService;

import java.util.LinkedList;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (new AuthService(this).getToken().compareTo("")==0){
            startActivity(new Intent(Home.this, LoginPage.class));
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);
        StructureService structureService = new StructureService(this);
        LinkedList<Structure> structures = (LinkedList<Structure>) structureService.getAllStructure();

    }
}