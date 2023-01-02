package com.example.futurebankgrupo1.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.futurebankgrupo1.User;
import com.example.futurebankgrupo1.UserApi;
import com.example.futurebankgrupo1.configuracoes.TelaConfiguracoesActivity;
import com.example.futurebankgrupo1.databinding.ActivityDadosUsuarioBinding;
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

public class DadosUsuario extends AppCompatActivity {
    ActivityDadosUsuarioBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDadosUsuarioBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.icBack.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TelaConfiguracoesActivity.class);
            startActivity(intent);
        });

        binding.tvAlterarDados.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AlterarDadosUsuario.class);
            startActivity(intent);
        });

        binding.ivArrowForward.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AlterarDadosUsuario.class);
            startActivity(intent);
        });


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        String userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserFirebase userProfile = snapshot.getValue(UserFirebase.class);

                if (userProfile != null){
                    String nome = userProfile.getNome();
                    String cpf = userProfile.getCpf();
                    String idade = userProfile.getIdade();
                    String email = userProfile.getEmail();
                    String telefone = userProfile.getTelefone();
                    String logradouro = userProfile.getLogradouro();
                    String numero = userProfile.getNumero();
                    String bairro = userProfile.getBairro();
                    String cidade = userProfile.getCidade();
                    String estado = userProfile.getEstado();
                    String pais = userProfile.getPais();
                    String cep = userProfile.getCep();


                    binding.tvGetNomeUsuario.setText(nome);
                    binding.tvGetCpf.setText(cpf);
                    binding.tvGetDataNascimento.setText(idade);
                    binding.tvGetConta1.setText(email);
                    binding.tvGetTelefone.setText(telefone);
                    binding.tvGetLogradouro.setText(logradouro);
                    binding.tvGetNumero.setText(numero);
                    binding.tvGetBairro.setText(bairro);
                    binding.tvGetCidade.setText(cidade);
                    binding.tvGetEstado.setText(estado);
                    binding.tvGetPais.setText(pais);
                    binding.tvGetCep.setText(cep);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DadosUsuario.this, "Ocorreu algum erro!", Toast.LENGTH_SHORT).show();

            }
        });


      /*  Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://run.mocky.io/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserApi userApi = retrofit.create(UserApi.class);
        Call<User> call = userApi.infoUserMocky();


        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() !=200){
                    Toast.makeText(DadosUsuario.this, "Verificar a conexão", Toast.LENGTH_SHORT).show();
                    return;
                }


                User user = response.body();
                assert user != null;
                binding.tvGetNomeUsuario.setText(user.getName());
                binding.tvGetCpf.setText(user.getCpf());
                binding.tvGetDataNascimento.setText(user.getBirthDate());
                binding.tvGetConta1.setText(user.getEmail());
                binding.tvGetTelefone.setText(user.getPhone());

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

       */


    }
}