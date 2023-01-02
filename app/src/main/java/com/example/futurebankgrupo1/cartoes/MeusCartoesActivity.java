package com.example.futurebankgrupo1.cartoes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.futurebankgrupo1.HomeActivity;
import com.example.futurebankgrupo1.databinding.ActivityMeusCartoesBinding;

public class MeusCartoesActivity extends AppCompatActivity {

    private ActivityMeusCartoesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMeusCartoesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.icClear.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        });

        binding.btnAcessarCartaoComum.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CartaoComumActivity.class);
            startActivity(intent);
        });

        binding.btnContratarCartaoSupreme.setOnClickListener(v -> {
                Toast.makeText(this, "Cartão Supreme contratado!", Toast.LENGTH_SHORT).show();
        });

        binding.btnContratarCartaoPremium.setOnClickListener(v -> {
            Toast.makeText(this, "Cartão Premium contratado!", Toast.LENGTH_SHORT).show();
        });

        //btn adicionar cartao
        binding.btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AdicionarCartaoActivity.class);
            startActivity(intent);
        });
    }
}