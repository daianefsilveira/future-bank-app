package com.example.futurebankgrupo1.transacoes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.futurebankgrupo1.HomeActivity;
import com.example.futurebankgrupo1.databinding.ActivityGerenciarChavesPixBinding;
import com.example.futurebankgrupo1.transacoes.CadastrarChavePixActivity;
import com.example.futurebankgrupo1.transacoes.ConsultarChavePixActivity;

public class GerenciarChavesPixActivity extends AppCompatActivity {

    private ActivityGerenciarChavesPixBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGerenciarChavesPixBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //cadastrar chaves pix
        binding.ivPixRosa.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CadastrarChavePixActivity.class);
            startActivity(intent);
        });

        binding.tvTxtCadastrarChave.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CadastrarChavePixActivity.class);
            startActivity(intent);
        });

        binding.ivArrowForward1.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CadastrarChavePixActivity.class);
            startActivity(intent);
        });

        //consultar chaves pix
        binding.ivConsultaChave.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ConsultarChavePixActivity.class);
            startActivity(intent);
        });

        binding.tvTxtConsultarChave.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ConsultarChavePixActivity.class);
            startActivity(intent);
        });

        binding.ivArrowForward2.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ConsultarChavePixActivity.class);
            startActivity(intent);
        });

        //voltar para a home
        binding.icClear.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        });
    }
}