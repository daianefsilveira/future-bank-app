package com.example.futurebankgrupo1.contas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.futurebankgrupo1.HomeActivity;
import com.example.futurebankgrupo1.databinding.ActivityTiposContasBinding;
import com.example.futurebankgrupo1.usuario.UserFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.Locale;

public class TiposContasActivity extends AppCompatActivity {

    private ActivityTiposContasBinding binding;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    Locale localeBR = new Locale( "pt", "BR" );
    NumberFormat dinheiroBR = NumberFormat.getCurrencyInstance(localeBR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTiposContasBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserFirebase userProfile = snapshot.getValue(UserFirebase.class);

                if (userProfile != null){
                    float saldo = userProfile.getSaldo();
                    float saldoPoupanca = userProfile.getSaldoPoupanca();

                    //binding.tvGetSaldoContas.setText(String.valueOf("R$" + saldo));
                    binding.tvGetSaldoContas.setText(dinheiroBR.format(saldo));
                    //binding.tvGetValorGuardadoContas.setText(String.valueOf("R$" + saldoPoupanca));
                    binding.tvGetValorGuardadoContas.setText(dinheiroBR.format(saldoPoupanca));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TiposContasActivity.this, "OTcorreu algum erro ao exibir saldo!", Toast.LENGTH_SHORT).show();
            }
        });



        binding.icClear.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        });

        binding.ivArrowForward1.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ContaCorrenteActivity.class);
            startActivity(intent);
        });

        binding.tvContaCorrente.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ContaCorrenteActivity.class);
            startActivity(intent);
        });

        binding.ivPixBlack.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ContaCorrenteActivity.class);
            startActivity(intent);
        });

        binding.ivArrowForward2.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ContaPoupanca.class);
            startActivity(intent);
        });

        binding.tvContaPoupanca.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ContaPoupanca.class);
            startActivity(intent);
        });

        binding.ivContaPoupanca.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ContaPoupanca.class);
            startActivity(intent);
        });

    }
}