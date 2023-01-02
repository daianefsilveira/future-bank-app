package com.example.futurebankgrupo1.splash;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.futurebankgrupo1.R;

public class SubSplashAcitivty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_splash_acitivty);

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SubSplashAcitivty.this, SubSplashActivity2.class));
                finish();
            }
        },2000);*/
    }
}