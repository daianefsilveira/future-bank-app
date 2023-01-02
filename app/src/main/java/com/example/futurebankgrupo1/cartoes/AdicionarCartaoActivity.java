package com.example.futurebankgrupo1.cartoes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.futurebankgrupo1.cartoes.MeusCartoesActivity;
import com.example.futurebankgrupo1.databinding.ActivityAdicionarCartaoBinding;
import com.example.futurebankgrupo1.transacoes.PixTransferirActivity;
import com.example.futurebankgrupo1.usuario.UserFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class AdicionarCartaoActivity extends AppCompatActivity {

    private ActivityAdicionarCartaoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdicionarCartaoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.icBack.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MeusCartoesActivity.class);
            startActivity(intent);
        });

        binding.ivCardRosa.setOnClickListener(v -> {
            gerarNumeroCartao();
            gerarDataValidade();
            gerarCvv();
            gerarNome();
        });


    }

    public void gerarNumeroCartao() {
        Random rand = new Random();
        String numeroCartao = "";

        for (int i = 0; i < 4; i++) {
            int value = rand.nextInt(9999);
            numeroCartao += String.valueOf(value) + " ";
        }
        binding.edtNumeroCartao.setText(numeroCartao);
    }

    public void gerarDataValidade() {
        Random rand = new Random();
        String dataValidade = "";

        int mes = rand.nextInt(11) + 1;
        int ano = rand.nextInt(10) + 2022;
        dataValidade = String.valueOf(mes) + "/" + String.valueOf(ano);
        binding.edtValidade.setText(dataValidade);
    }

    public void gerarCvv() {
        Random rand = new Random();
        String cvv = "";

        int codigo = rand.nextInt(100) + 100;
        cvv = String.valueOf(codigo);
        binding.edtCvv.setText(cvv);
    }

    public void gerarNome() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        String userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserFirebase userProfile = snapshot.getValue(UserFirebase.class);

                if (userProfile != null){
                    String nome = userProfile.getNome();
                    binding.edtNomeTitular.setText(nome);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdicionarCartaoActivity.this, "Ocorreu algum erro!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}