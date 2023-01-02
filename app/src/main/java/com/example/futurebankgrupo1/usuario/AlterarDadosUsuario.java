package com.example.futurebankgrupo1.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.futurebankgrupo1.databinding.ActivityAlterarDadosUsuarioBinding;
import com.example.futurebankgrupo1.usuario.DadosUsuario;
import com.example.futurebankgrupo1.usuario.UserFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AlterarDadosUsuario extends AppCompatActivity {
    ActivityAlterarDadosUsuarioBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlterarDadosUsuarioBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.icBack.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), DadosUsuario.class);
            startActivity(intent); });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        String userID = user.getUid();

        //mostar dados já preenchidos nos edTexts
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


                    binding.edtSetNomeUsuario.setText(nome);
                    binding.edtSetCpf.setText(cpf);
                    binding.edtSetDataNascimento.setText(idade);
                    binding.edtSetEmail.setText(email);
                    binding.edtSetTelefone.setText(telefone);
                    binding.edtSetLogradouro.setText(logradouro);
                    binding.edtSetNumeroCasa.setText(numero);
                    binding.edtSetBairro.setText(bairro);
                    binding.edtSetCidade.setText(cidade);
                    binding.edtSetEstado.setText(estado);
                    binding.edtSetPais.setText(pais);
                    binding.edtSetCep.setText(cep);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AlterarDadosUsuario.this, "Ocorreu algum erro!", Toast.LENGTH_SHORT).show();

            }
        });

        //alterar dados
        binding.btnSalvarAlteracoes.setOnClickListener(view1 -> {
            reference.child(userID).child("nome").setValue(binding.edtSetNomeUsuario.getText().toString());
            reference.child(userID).child("cpf").setValue(binding.edtSetCpf.getText().toString());
            reference.child(userID).child("idade").setValue(binding.edtSetDataNascimento.getText().toString());
            reference.child(userID).child("email").setValue(binding.edtSetEmail.getText().toString());
            reference.child(userID).child("telefone").setValue(binding.edtSetTelefone.getText().toString());
            reference.child(userID).child("logradouro").setValue(binding.edtSetLogradouro.getText().toString());
            reference.child(userID).child("numero").setValue(binding.edtSetNumeroCasa.getText().toString());
            reference.child(userID).child("bairro").setValue(binding.edtSetBairro.getText().toString());
            reference.child(userID).child("cidade").setValue(binding.edtSetCidade.getText().toString());
            reference.child(userID).child("estado").setValue(binding.edtSetEstado.getText().toString());
            reference.child(userID).child("pais").setValue(binding.edtSetPais.getText().toString());
            reference.child(userID).child("cep").setValue(binding.edtSetCep.getText().toString());



            Intent intent = new Intent(getApplicationContext(), DadosUsuario.class);
            startActivity(intent);


        });






//        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                .setDisplayName("Jane Q. User")
//                //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
//                .build();
//
//        user.updateProfile(profileUpdates);
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "User profile updated.");
//                        }
//                    }
//                });



    }
}