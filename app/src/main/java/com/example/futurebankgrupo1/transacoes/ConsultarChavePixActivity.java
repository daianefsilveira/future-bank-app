package com.example.futurebankgrupo1.transacoes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.futurebankgrupo1.HomeActivity;
import com.example.futurebankgrupo1.R;
import com.example.futurebankgrupo1.databinding.ActivityConsultarChavePixBinding;
import com.example.futurebankgrupo1.recycler.AdapterChavePix;
import com.example.futurebankgrupo1.recycler.RecyclerChavePix;

import java.util.ArrayList;
import java.util.List;

public class ConsultarChavePixActivity extends AppCompatActivity {

    private ActivityConsultarChavePixBinding binding;
    private RecyclerView recyclerView;
    private List<RecyclerChavePix> listaChavePix = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConsultarChavePixBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.icBack.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        });

        //conversão recycler
        recyclerView = findViewById(R.id.recyclerView);

        this.criarListaChavePix();


        SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);

        //configuração adapter
        AdapterChavePix AdapterChavePix = new AdapterChavePix(listaChavePix, preferences);

        //configuração recycler
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(AdapterChavePix);
    }

    public void criarListaChavePix() {

        SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);

        boolean cbCpf = preferences.getBoolean("chaveCbCpf", false);
        boolean cbCelular = preferences.getBoolean("chaveCbCelular", false);
        boolean cbEmail = preferences.getBoolean("chaveCbEmail", false);
        boolean cbChaveAleatoria = preferences.getBoolean("chaveCbChaveAleatoria", false);

        String chaveCpf = preferences.getString("chaveCpf", "");
        String chaveCelular = preferences.getString("chaveCelular", "");
        String chaveEmail = preferences.getString("chaveEmail", "");
        String chaveAleatoria = preferences.getString("chaveAleatoria", "");

        if(cbCpf){
            RecyclerChavePix recyclerChavePix = new RecyclerChavePix("Tipo de chave: CPF", "Chave: " + chaveCpf);
            listaChavePix.add(recyclerChavePix);
        }

        if(cbCelular){
            RecyclerChavePix recyclerChavePix = new RecyclerChavePix("Tipo de chave: Celular", "Chave: " + chaveCelular);
            listaChavePix.add(recyclerChavePix);
        }
        if(cbEmail){
            RecyclerChavePix recyclerChavePix = new RecyclerChavePix("Tipo de chave: Email", "Chave: " + chaveEmail);
            listaChavePix.add(recyclerChavePix);
        }
        if(cbChaveAleatoria){
            RecyclerChavePix recyclerChavePix = new RecyclerChavePix("Tipo de chave: Chave Aleatória", "Chave: " + chaveAleatoria);
            listaChavePix.add(recyclerChavePix);
        }
    }
}