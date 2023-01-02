package com.example.futurebankgrupo1.configuracoes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.futurebankgrupo1.Account;
import com.example.futurebankgrupo1.AccountAPI;
import com.example.futurebankgrupo1.HomeActivity;
import com.example.futurebankgrupo1.User;
import com.example.futurebankgrupo1.cartoes.MeusCartoesActivity;
import com.example.futurebankgrupo1.databinding.ActivityTelaConfiguracoesBinding;
import com.example.futurebankgrupo1.splash.SplashActivity;
import com.example.futurebankgrupo1.usuario.DadosUsuario;
import com.example.futurebankgrupo1.usuario.ResetarSenha;
import com.example.futurebankgrupo1.usuario.Security;
import com.example.futurebankgrupo1.usuario.UserFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TelaConfiguracoesActivity extends AppCompatActivity {

    private ActivityTelaConfiguracoesBinding binding;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaConfiguracoesBinding.inflate(getLayoutInflater());
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
                    String nome = userProfile.getNome();

                    binding.tvNome.setText(nome);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TelaConfiguracoesActivity.this, "Ocorreu algum erro!", Toast.LENGTH_SHORT).show();
            }
        });


        //botão configurações cartão
        binding.icConfigCard.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MeusCartoesActivity.class);
            startActivity(intent);
        });

        binding.tvConfigCard.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MeusCartoesActivity.class);
            startActivity(intent);
        });

        binding.ivArrowForward3.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MeusCartoesActivity.class);
            startActivity(intent);
        });


        //botão configurações app
        binding.icConfigApp.setOnClickListener(view12 -> {
            Intent intent = new Intent(getApplicationContext(), ConfiguracoesApp.class);
            startActivity(intent);
        });

        binding.tvConfigApp.setOnClickListener(view12 -> {
            Intent intent = new Intent(getApplicationContext(), ConfiguracoesApp.class);
            startActivity(intent);
        });

        binding.ivArrowConfigApp.setOnClickListener(view12 -> {
            Intent intent = new Intent(getApplicationContext(), ConfiguracoesApp.class);
            startActivity(intent);
        });

        //botão segurança
        binding.icConfigSecurity.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Security.class);
            startActivity(intent);
        });

        binding.tvConfigSecurity.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Security.class);
            startActivity(intent);
        });
        //arrow perfil usuario
        binding.ivArrowForward2.setOnClickListener(view123 -> {
            Intent intent = new Intent(getApplicationContext(), DadosUsuario.class);
            startActivity(intent);
        });

        //arrow segurança
        binding.ivArrowForward4.setOnClickListener(view123 -> {
            Intent intent = new Intent(getApplicationContext(), ResetarSenha.class);
            startActivity(intent);
        });


        binding.ivArrowForward4.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Security.class);
            startActivity(intent);
        });

        // X icone sair
        binding.icClear.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        });


        //botão deslogar
        binding.icConfigSair.setOnClickListener(view1 -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
            startActivity(intent);
        });

        binding.tvConfigSair.setOnClickListener(view1 -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
            startActivity(intent);
        });

        //Retrofit

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://run.mocky.io/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AccountAPI accountAPI = retrofit.create(AccountAPI.class);
        Call<Account> call = accountAPI.getAccount();

        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.code() !=200) {
                    Toast.makeText(getApplicationContext(), "Servidor offline", Toast.LENGTH_SHORT).show();
                    return;
                }
                String json = "";
                json = "Agência: " + response.body().getAgency() + " - " + response.body().getAccountType() + ": " + response.body().getNumber();
                binding.tvAgencia.append(json);

                String json2 = "";
                json2 = "Banco: " + response.body().getBankNumber() + " - FutureBANK";
                binding.tvBanco.append(json2);
//                binding.tvBanco.setText(buscarDadosConta.getBankNumber());
//                binding.tv.setText(buscarDadosConta.getAgency());
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro ao carregar dados da Conta", Toast.LENGTH_SHORT).show();
            }
        });

        //Retrofit

    }

}
