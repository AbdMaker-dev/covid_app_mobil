package com.example.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.covid.services.AuthService;
import com.google.android.material.textfield.TextInputEditText;

public class SinIngPage extends AppCompatActivity {

    TextInputEditText nom, prenom,sing_password,sing_username;
    Button button_sing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sin_ing_page);
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        sing_password = findViewById(R.id.sing_password);
        sing_username = findViewById(R.id.sing_username);
        button_sing = findViewById(R.id.button_sing);
        button_sing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthService authService = new AuthService(SinIngPage.this);
                authService.sinIng(prenom.getText().toString(),nom.getText().toString(), sing_username.getText().toString(), sing_password.getText().toString());
                Intent intent = new Intent(SinIngPage.this,LoginPage.class);
                startActivity(intent);
            }
        });
    }
}