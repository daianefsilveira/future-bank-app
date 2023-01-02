package com.example.futurebankgrupo1.fatura.pagarfatura;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.futurebankgrupo1.HomeActivity;
import com.example.futurebankgrupo1.MyViewModel;
import com.example.futurebankgrupo1.usuario.UserFirebase;
import com.example.futurebankgrupo1.databinding.ActivityPagarFaturaBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.Locale;

public class PagarFatura extends AppCompatActivity {

    private ActivityPagarFaturaBinding binding;
    MyViewModel viewModel;

    Locale localeBR = new Locale( "pt", "BR" );
    NumberFormat dinheiroBR = NumberFormat.getCurrencyInstance(localeBR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPagarFaturaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.icClear.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        });

        binding.ivArrowForward.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PagarFaturaConfirmarValor.class);
            startActivity(intent);
        });

        binding.tvPagarSaldo.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PagarFaturaConfirmarValor.class);
            startActivity(intent);
        });

        //viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        //binding.tvGetValorFatura.setText(String.valueOf(viewModel.exibirValorFatura()));


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        String userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserFirebase userProfile = snapshot.getValue(UserFirebase.class);

                if (userProfile != null){
                    float valorFatura = userProfile.getValorFatura();

                    binding.tvGetValorFatura.setText(dinheiroBR.format(valorFatura));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PagarFatura.this, "Ocorreu algum erro ao exibir saldo!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}