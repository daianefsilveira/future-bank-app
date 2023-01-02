package com.example.futurebankgrupo1.contas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.futurebankgrupo1.HomeActivity;
import com.example.futurebankgrupo1.usuario.UserFirebase;
import com.example.futurebankgrupo1.databinding.ActivityAplicarComprovanteBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.Locale;

public class AplicarComprovante extends AppCompatActivity {
    ActivityAplicarComprovanteBinding binding;

    Locale localeBR = new Locale( "pt", "BR" );
    NumberFormat dinheiroBR = NumberFormat.getCurrencyInstance(localeBR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAplicarComprovanteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.icClearAplicarComprovante.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        });


        String valorAplicar;
        SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);
        valorAplicar = preferences.getString("chaveValorAplicar", "");
        binding.tvValorAplicarComprovante.setText("R$" + valorAplicar);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        String userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserFirebase userProfile = snapshot.getValue(UserFirebase.class);

                if (userProfile != null){
                    float saldo = userProfile.getSaldo();
                    float saldoPoupanca = userProfile.getSaldoPoupanca();

                    //binding.tvSaldoDisponivelCcAplicarComprovante.setText(String.valueOf("R$" + saldo));
                    binding.tvSaldoDisponivelCcAplicarComprovante.setText(dinheiroBR.format(saldo));
                    //binding.tvSaldoDisponivelAplicarComprovante.setText(String.valueOf("R$" + saldoPoupanca));
                    binding.tvSaldoDisponivelAplicarComprovante.setText(dinheiroBR.format(saldoPoupanca));

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AplicarComprovante.this, "Ocorreu algum erro ao exibir saldo!", Toast.LENGTH_SHORT).show();
            }
        });



    }
}