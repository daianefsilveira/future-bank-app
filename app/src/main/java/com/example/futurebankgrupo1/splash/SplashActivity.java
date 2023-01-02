package com.example.futurebankgrupo1.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.futurebankgrupo1.HomeActivity;
import com.example.futurebankgrupo1.R;
import com.example.futurebankgrupo1.usuario.LoginActivity;
import com.example.futurebankgrupo1.usuario.Security;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

//    public boolean habilitar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            new Handler().postDelayed(() -> {

                Executor executor = ContextCompat.getMainExecutor(this);

                BiometricPrompt biometricPrompt = new BiometricPrompt(SplashActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        Toast.makeText(SplashActivity.this, "Tente entrar utilizando seu login e senha.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), SubSplashActivity2.class));
                    }

                    @Override
                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        Toast.makeText(getApplicationContext(), "Autenticação realizada com sucesso!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        Toast.makeText(SplashActivity.this, "Tente entrar utilizando seu login e senha.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), SubSplashActivity2.class));
                    }
                });

                BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Login")
                        .setDescription("Use sua digital para autenticar o login no App.")
                        .setNegativeButtonText("Cancelar")
                        .build();


                biometricPrompt.authenticate(promptInfo);
            }, 2000);
        } else {
            new Handler().postDelayed(() -> {

                startActivity(new Intent(SplashActivity.this, SubSplashActivity2.class));
                finish();
            }, 2000);
        }



//        new Handler().postDelayed(() -> {
//
//            startActivity(new Intent(SplashActivity.this, SubSplashActivity2.class));
//            finish();
//        },2000);

        /*Security security = new Security();
//        habilitar = security.habilitarBiometria(true);
        SharedPreferences preferences = getSharedPreferences("save", MODE_PRIVATE);
        preferences.getBoolean("value", false);

        if (security.habilitarBiometria(true)) {
            new Handler().postDelayed(() -> {
                *//*SharedPreferences preferences = getSharedPreferences("save", MODE_PRIVATE);
                preferences.getBoolean("value", true);*//*
                Toast.makeText(getApplicationContext(), "true", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            },2000);
        }else *//*(security.desabilitarBiometria(true))*//*{
            new Handler().postDelayed(() -> {
                *//*SharedPreferences preferences = getSharedPreferences("save", MODE_PRIVATE);
                preferences.getBoolean("value", false);*//*
                Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SplashActivity.this, SubSplashActivity2.class));
                finish();
            },2000);
        }*/

    }
}
