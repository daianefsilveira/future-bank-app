package com.example.futurebankgrupo1.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.futurebankgrupo1.databinding.ActivityResetarSenhaBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetarSenha extends AppCompatActivity {
    ActivityResetarSenhaBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetarSenhaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();

        binding.btnAlterarSenha.setOnClickListener(view1 -> {
            alterarSenha();
        });


    }

    private void alterarSenha() {
        String email = binding.edtAlterarSenha.getText().toString().trim();

        if(email.isEmpty()){
            binding.edtAlterarSenha.setError("Insira o seu email!");
            binding.edtAlterarSenha.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.edtAlterarSenha.setError("Insira seu email cadastrado!");
            binding.edtAlterarSenha.requestFocus();
            return;
        }

        binding.progressBarResetarSenha.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    Toast.makeText(ResetarSenha.this, "Você receberá um email para alterar a senha!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(ResetarSenha.this, "Tente novamente, ocorreu algum erro!", Toast.LENGTH_SHORT).show();
                    binding.progressBarResetarSenha.setVisibility(View.GONE);
                }
            }
        });

    }
}