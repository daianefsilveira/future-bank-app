package com.example.futurebankgrupo1.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.futurebankgrupo1.HomeActivity;
import com.example.futurebankgrupo1.databinding.ActivityLoginBinding;
import com.example.futurebankgrupo1.splash.SplashActivity;
import com.example.futurebankgrupo1.splash.SubSplashActivity2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //

        //esconder ícone da biometria
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            binding.ivBiometria.setVisibility(view.VISIBLE);
        } else {
            binding.ivBiometria.setVisibility(view.GONE);
        }

        //Biometria

        Executor executor = ContextCompat.getMainExecutor(this);

        BiometricPrompt biometricPrompt = new BiometricPrompt(LoginActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(LoginActivity.this, "Digital com erro ou não cadastrada em seu dispositivo! Tente outra digital.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Autenticação realizada com sucesso!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                /*Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);*/
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(LoginActivity.this, "Este dispositivo não suporta autenticação por biometria.", Toast.LENGTH_SHORT).show();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login")
                .setDescription("Use sua digital para autenticar o login no App.")
                .setNegativeButtonText("Cancelar")
                .build();

        binding.ivBiometria.setOnClickListener(v -> {
            biometricPrompt.authenticate(promptInfo);
        });

        //Biometria





        mAuth = FirebaseAuth.getInstance();

        binding.tvCriarConta.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
            startActivity(intent);
        });

        binding.tvEsqueceuSenha.setOnClickListener(view12 -> {
            Intent intent = new Intent(getApplicationContext(), ResetarSenha.class);
            startActivity(intent);
        });

        binding.btnAcessar.setOnClickListener(view1 -> {
            userLogin();
        });

    }

    private void userLogin() {
        String email = binding.edtEmailLogin.getText().toString().trim();
        String senha = binding.edtSenhaLogin.getText().toString().trim();

        if(email.isEmpty()){
            binding.edtEmailLogin.setError("Insira o seu email!");
            binding.edtEmailLogin.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.edtEmailLogin.setError("Insira seu email válido!");
            binding.edtEmailLogin.requestFocus();
            return;
        }

        if (senha.isEmpty()){
            binding.edtSenhaLogin.setError("Insira seu senha!");
            binding.edtSenhaLogin.requestFocus();
            return;
        }

        if (senha.length() < 6 ){
            binding.edtSenhaLogin.setError("A senha deve ter no mínimo 6 caracteres!");
            binding.edtSenhaLogin.requestFocus();
            return;
        }

        binding.progressBarLogin.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()){
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        binding.edtEmailLogin.setText("");
                        binding.edtSenhaLogin.setText("");
                        binding.progressBarLogin.setVisibility(View.GONE);

                    }else {
                        user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this, "Vá ao seu email e clique no link de verificação!", Toast.LENGTH_LONG).show();
                        binding.progressBarLogin.setVisibility(View.GONE);

                    }



                }else {
                    Toast.makeText(LoginActivity.this, "Falha no login. Revise suas credenciais", Toast.LENGTH_SHORT).show();
                    binding.progressBarLogin.setVisibility(View.GONE);

                }
            }
        });

    }
}