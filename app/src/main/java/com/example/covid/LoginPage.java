package com.example.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.covid.services.AuthService;
import com.google.android.material.textfield.TextInputEditText;

public class LoginPage extends AppCompatActivity {

    TextInputEditText log_password,log_username;
    Button button_log, button_ins;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login_page);
        log_username = findViewById(R.id.log_username);
        log_password = findViewById(R.id.log_password);
        button_log = findViewById(R.id.button_log);
        button_ins = findViewById(R.id.button_ins);

        button_ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this,SinIngPage.class);
                startActivity(intent);
            }
        });
        button_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthService authService = new AuthService(LoginPage.this);
                final int[] result = {0};
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        result[0] = authService.login(log_username.getText().toString(), log_password.getText().toString());
                    }
                }, 1500);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        System.out.println(result[0]);
                        Intent intent = new Intent(LoginPage.this,Acceul.class);
                        startActivity(intent);
                    }
                }, 1500);

            }
        });

    }
}