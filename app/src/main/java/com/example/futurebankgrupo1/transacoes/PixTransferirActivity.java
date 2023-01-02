package com.example.futurebankgrupo1.transacoes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import com.example.futurebankgrupo1.HomeActivity;
import com.example.futurebankgrupo1.MyViewModel;
import com.example.futurebankgrupo1.R;
import com.example.futurebankgrupo1.recycler.RecyclerCorrente;
import com.example.futurebankgrupo1.usuario.UserFirebase;
import com.example.futurebankgrupo1.databinding.ActivityPixTransferirBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

public class PixTransferirActivity extends AppCompatActivity {

    private ActivityPixTransferirBinding binding;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private final String flaviodev = "flaviofonsecadev@gmail.com";
    private final String daiane = "daiane.silveira@foursys.com.br";
    private final String rafael = "rafael.silverio@foursys.com.br";
    private final String kaua = "kaua.alcaya@foursys.com.br";
    private final String flavio = "flavio.fonseca@foursys.com.br";
    private final String rafaelOutlook = "rafael.silverio.s@outlook.com";

    Locale localeBR = new Locale( "pt", "BR" );
    NumberFormat dinheiroBR = NumberFormat.getCurrencyInstance(localeBR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pix_transferir);
        binding = ActivityPixTransferirBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //botão voltar
        binding.icClear.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        });

        binding.tvPagar.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), TelaTransferirConta.class));
        });

        //texto transferir conta
        binding.tvPagar.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TelaTransferirConta.class);
            startActivity(intent);
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserFirebase userProfile = snapshot.getValue(UserFirebase.class);

                if (userProfile != null){
                    //String nome = userProfile.getNome();
                    float saldo = userProfile.getSaldo();

                    binding.tvSaldoDisponivelPix.setText(dinheiroBR.format(saldo));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PixTransferirActivity.this, "Ocorreu algum erro!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.button.setOnClickListener(v -> {

            SharedPreferences preferences1 = getSharedPreferences("chaveGeral", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = preferences1.edit();
            editor1.putString("chaveValorPix", binding.edtValorPix.getText().toString());
            editor1.putString("chaveChavePix", binding.edtChavePix.getText().toString().trim());
            editor1.commit();

            String textoMask = binding.edtValorPix.getText().toString();
            String textoNovo = textoMask.replace(",", ".");
            float valor = Float.parseFloat(textoNovo);
            String chavePix = binding.edtChavePix.getText().toString().trim();
            if (chavePix.isEmpty()){
                Toast.makeText(this, "Informe a chave Pix", Toast.LENGTH_SHORT).show();
            } else {
                reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserFirebase userProfile = snapshot.getValue(UserFirebase.class);
                        if (userProfile != null){
                            float saldo = userProfile.getSaldo();
                            if (saldo >= valor){
                                startActivity(new Intent(getApplicationContext(), TelaConfirmarDadosPix.class));
                            }else {
                                Toast.makeText(PixTransferirActivity.this, "Saldo insuficiente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(PixTransferirActivity.this, "Erro. Tente novamente!", Toast.LENGTH_SHORT).show();
                    }
                });

                if (chavePix.equals(flaviodev)){
                    String nomeRecebedor = "Flavio Fonseca Dev";
                    SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("chaveNomeRecebedor", nomeRecebedor);
                    editor.commit();
                } else if (chavePix.equals(daiane)){
                    String nomeRecebedor = "Daiane Silveira";
                    SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("chaveNomeRecebedor", nomeRecebedor);
                    editor.commit();
                } else if (chavePix.equals(rafael)){
                    String nomeRecebedor = "Rafael Luiz Silverio dos Santos";
                    SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("chaveNomeRecebedor", nomeRecebedor);
                    editor.commit();
                } else if (chavePix.equals(kaua)){
                    String nomeRecebedor = "kauã alcaya";
                    SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("chaveNomeRecebedor", nomeRecebedor);
                    editor.commit();
                } else if (chavePix.equals(flavio)){
                    String nomeRecebedor = "Flávio Fonsêca";
                    SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("chaveNomeRecebedor", nomeRecebedor);
                    editor.commit();
                }else if (chavePix.equals(rafaelOutlook)) {
                    String nomeRecebedor = "Rafael Silverio";
                    SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("chaveNomeRecebedor", nomeRecebedor);
                    editor.commit();
                }
            }

        });
    }



}