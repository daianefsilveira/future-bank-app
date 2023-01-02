package com.example.futurebankgrupo1.contas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.futurebankgrupo1.HomeActivity;
import com.example.futurebankgrupo1.R;
import com.example.futurebankgrupo1.recycler.AdapterCorrente;
import com.example.futurebankgrupo1.recycler.RecyclerCorrente;
import com.example.futurebankgrupo1.usuario.UserFirebase;
import com.example.futurebankgrupo1.databinding.ActivityContaPoupancaBinding;
import com.example.futurebankgrupo1.recycler.AdapterPoupanca;
import com.example.futurebankgrupo1.recycler.RecyclerPoupanca;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class    ContaPoupanca extends AppCompatActivity {

    private ActivityContaPoupancaBinding binding;
    private FirebaseUser user;
    private String userID;

    Locale localeBR = new Locale( "pt", "BR" );
    NumberFormat dinheiroBR = NumberFormat.getCurrencyInstance(localeBR);

    private RecyclerView recyclerView;
    private List<RecyclerCorrente> listaCorrente = new ArrayList<>();
    private AdapterCorrente adapterCorrente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContaPoupancaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserFirebase userProfile = snapshot.getValue(UserFirebase.class);

                if (userProfile != null){
                    float saldo = userProfile.getSaldo();
                    float saldoPoupanca = userProfile.getSaldoPoupanca();

                    binding.tvGetValorContaCorrente.setText(dinheiroBR.format(saldo));
                    binding.tvValorGuardado.setText(dinheiroBR.format(saldoPoupanca));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ContaPoupanca.this, "Ocorreu algum erro ao exibir saldo!", Toast.LENGTH_SHORT).show();
            }
        });

        //conversão variavel recycler view
        recyclerView = findViewById(R.id.recyclerView);

        //configuração adapter
        adapterCorrente = new AdapterCorrente(ContaPoupanca.this, listaCorrente);

        //configuração recyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterCorrente);

        readItems();

        binding.icClearCp.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        });

        binding.ivArrowForward1.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ContaCorrenteActivity.class);
            startActivity(intent);
        });

        binding.ivContaCorrente.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ContaCorrenteActivity.class);
            startActivity(intent);
        });

        binding.tvExtratoContaCorrente.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ContaCorrenteActivity.class);
            startActivity(intent);
        });

        //forma mais resumida para mudar tela
        binding.tvAplicar.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), AplicarCP.class));
        } );

        binding.ivArrowAplicar.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), AplicarCP.class));
        } );

        binding.ivAplicar1.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), AplicarCP.class));
        } );

        binding.tvAplicarTitle.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), AplicarCP.class));
        } );

        binding.tvResgatarCp.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ResgatarCC.class));
        } );

        binding.ivArrowResgatarCp.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ResgatarCC.class));
        } );

        binding.tvResgatarTitleCp.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ResgatarCC.class));
        } );

        binding.ivResgatarCp.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ResgatarCC.class));
        } );

    }

    private void readItems(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("extratos").child(userID);
        Query query = reference.orderByPriority();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    RecyclerCorrente recyclerCorrente = dataSnapshot.getValue(RecyclerCorrente.class);
                    listaCorrente.add(recyclerCorrente);
                }
                Collections.reverse(listaCorrente);
                adapterCorrente.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}