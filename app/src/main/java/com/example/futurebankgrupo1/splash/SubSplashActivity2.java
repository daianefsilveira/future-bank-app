package com.example.futurebankgrupo1.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.futurebankgrupo1.databinding.ActivitySubSplash2Binding;
import com.example.futurebankgrupo1.usuario.CadastroActivity;
import com.example.futurebankgrupo1.usuario.LoginActivity;

public class SubSplashActivity2 extends AppCompatActivity {

    private ActivitySubSplash2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubSplash2Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        /*//botão login
        binding.btnLogin.setOnClickListener(v -> {
            Security biometria = new Security();
            //boolean habilitar = biometria.habilitarBiometria();
            if (biometria.habilitarBiometria()) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(getApplicationContext(), LoginSemBiometria.class);
                startActivity(intent);
            }
        });*/


        //botão login
        binding.btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });

        //botão cadastro
        binding.btnCadastrar.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
            startActivity(intent);
        });

        /*new Handler().postDelayed(() -> {
            startActivity(new Intent(SubSplashActivity2.this, CadastroActivity.class));
            finish();
        },2000);*/
    }
}