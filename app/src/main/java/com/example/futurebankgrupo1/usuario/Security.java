package com.example.futurebankgrupo1.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.futurebankgrupo1.R;
import com.example.futurebankgrupo1.configuracoes.TelaConfiguracoesActivity;
import com.example.futurebankgrupo1.databinding.ActivitySecurityBinding;

public class Security extends AppCompatActivity {

    private ActivitySecurityBinding binding;
    Switch switchCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecurityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        switchCompat = findViewById(R.id.switch_biometria);

        /*SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        switchCompat.setChecked(sharedPreferences.getBoolean("value", false));*/


        /*switchCompat.setOnClickListener(v -> {
            if (switchCompat.isChecked()) {
                SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                editor.putBoolean("value", true);
                editor.apply();
                switchCompat.setChecked(true);
                habilitarBiometria(true);
                Toast.makeText(getApplicationContext(), "true", Toast.LENGTH_SHORT).show();
            }else {
                SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                editor.putBoolean("value", false);
                editor.apply();
                switchCompat.setChecked(false);
                habilitarBiometria(false);
                Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_SHORT).show();
            }
        });*/

        binding.icBack.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TelaConfiguracoesActivity.class);
            startActivity(intent);
        });

        binding.ivArrowForward.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ResetarSenha.class));
        });

    }

    /*public boolean habilitarBiometria(boolean b) {
        return b;
    }

    public boolean desabilitarBiometria(boolean b) {
        return b;
    }*/
}