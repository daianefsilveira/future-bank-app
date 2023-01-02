package com.example.futurebankgrupo1.fatura;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.futurebankgrupo1.recycler.AdapterCorrente;
import com.example.futurebankgrupo1.recycler.RecyclerCorrente;
import com.example.futurebankgrupo1.usuario.UserFirebase;
import com.example.futurebankgrupo1.recycler.Compra;
import com.example.futurebankgrupo1.HomeActivity;
import com.example.futurebankgrupo1.MyViewModel;
import com.example.futurebankgrupo1.R;
import com.example.futurebankgrupo1.recycler.AdapterCompra;
import com.example.futurebankgrupo1.databinding.ActivityFaturaCartaoBinding;
import com.example.futurebankgrupo1.fatura.pagarfatura.PagarFatura;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class FaturaCartao extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Compra> listaCompras = new ArrayList<>();
    private AdapterCompra adapterCompra;

    String userID;


    private ActivityFaturaCartaoBinding binding;

    Locale localeBR = new Locale( "pt", "BR" );
    NumberFormat dinheiroBR = NumberFormat.getCurrencyInstance(localeBR);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFaturaCartaoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //conversão variável recyclerView
        recyclerView = findViewById(R.id.recyclerView);



        //configuração do adapter
        adapterCompra = new AdapterCompra(FaturaCartao.this, listaCompras);

        //configuração recyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterCompra);

        readItems();
        //this.criarCompra();

        binding.icClear.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        });

        binding.tvPagarFaturaAtual.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PagarFatura.class);
            startActivity(intent);
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserFirebase userProfile = snapshot.getValue(UserFirebase.class);

                if (userProfile != null){
                    float valorFatura = userProfile.getValorFatura();
                    float limiteCartao = userProfile.getLimiteCartao();

                    binding.tvValorAtual.setText(dinheiroBR.format(valorFatura));
                    binding.tvGetLimite.setText(dinheiroBR.format(limiteCartao));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FaturaCartao.this, "Ocorreu algum erro ao exibir saldo!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void readItems(){

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String onlineUserId = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("fatura").child(onlineUserId);
        Query query = ref.orderByPriority();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Compra compra = dataSnapshot.getValue(Compra.class);
                    listaCompras.add(compra);
                }
                Collections.reverse(listaCompras);
                adapterCompra.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*public void criarCompra(){

        Compra compra = new Compra("Compra em Petz", "03/06/2022", "R$199,90", R.drawable.ic_pet2);
        this.listaCompras.add(compra);

        compra = new Compra("Compra em Americanas.com", "01/06/2022", "R$89,00", R.drawable.ic_internet);
        this.listaCompras.add(compra);

        compra = new Compra("Compra em Amazon", "29/05/2022", "R$49,90", R.drawable.ic_internet);
        this.listaCompras.add(compra);

        compra = new Compra("Compra em BurgerKing", "25/05/2022", "R$79,80", R.drawable.ic_restaurante);
        this.listaCompras.add(compra);

        compra = new Compra("Recarga de Celular", "23/05/2022", "R$20,00", R.drawable.ic_smartphone);
        this.listaCompras.add(compra);

        compra = new Compra("Compra em MC Donalds", "20/05/2022", "R$234,83", R.drawable.ic_restaurante);
        this.listaCompras.add(compra);

        compra = new Compra("Compra em Carrefour", "15/05/2022", "R$92,34", R.drawable.ic_mercado);
        this.listaCompras.add(compra);

        compra = new Compra("Compra em Assaí", "10/05/2022", "R$132,81", R.drawable.ic_mercado);
        this.listaCompras.add(compra);

        compra = new Compra("Compra em Shopee", "04/05/2022", "R$159,55", R.drawable.ic_internet);
        this.listaCompras.add(compra);

        compra = new Compra("Compra em Farmácia Nissei", "03/05/2022", "R$39,90", R.drawable.ic_farmacia);
        this.listaCompras.add(compra);

    }*/

}