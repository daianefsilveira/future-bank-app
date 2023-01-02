package com.example.futurebankgrupo1.transacoes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.futurebankgrupo1.databinding.ActivitySenhaPixCopiaColaBinding;
import com.example.futurebankgrupo1.usuario.UserFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

public class SenhaPixCopiaColaActivity extends AppCompatActivity {

    private ActivitySenhaPixCopiaColaBinding binding;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySenhaPixCopiaColaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        String valorPixCopiaCola;
        SharedPreferences preferences1 = getSharedPreferences("chaveGeral", MODE_PRIVATE);
        valorPixCopiaCola = preferences1.getString("chaveValorPixCopiaCola", "");

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();


        String textoMask = valorPixCopiaCola;
        String textoNovo = textoMask.replace(",", ".");
        float valor = Float.parseFloat(textoNovo);


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserFirebase userProfile = snapshot.getValue(UserFirebase.class);
                if (userProfile != null) {

                    //_______________________________________Biometria______________________________________
                    Executor executor = ContextCompat.getMainExecutor(getApplicationContext());

                    BiometricPrompt biometricPrompt = new BiometricPrompt(SenhaPixCopiaColaActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                            super.onAuthenticationError(errorCode, errString);
                            //Toast.makeText(getApplicationContext(), "Digital com erro ou não cadastrada em seu dispositivo! Tente outra digital.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                            super.onAuthenticationSucceeded(result);

                            float saldo = userProfile.getSaldo();
                            if (saldo >= valor) {
                                reference.child(userID).child("saldo").setValue(saldo - valor);
                                startActivity(new Intent(getApplicationContext(), PixComprovanteCopiaCola.class));
                                Toast.makeText(getApplicationContext(), "Transação realizada com sucesso!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onAuthenticationFailed() {
                            super.onAuthenticationFailed();
                            Toast.makeText(getApplicationContext(), "Digital com erro ou não cadastrada em seu dispositivo!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                            .setTitle("Confirmar Recarga")
                            .setDescription("Use sua digital para confirmar esta transação.")
                            .setNegativeButtonText("Digite a Senha")
                            .build();

                    binding.ivBiometria.setOnClickListener(view1 -> {
                        //Prompt biometria
                        biometricPrompt.authenticate(promptInfo);
                    });

                    //biometricPrompt.authenticate(promptInfo);
                    //_______________________________________Biometria______________________________________


                    //_______________________________________ Senha ______________________________________
                    binding.btnConfirmarTransacao.setOnClickListener(v -> {

                        String senhaFireBase = userProfile.getSenha();
                        String senhaDigitada = binding.edtSenhaConfirmacao.getText().toString().trim();

                        if (senhaDigitada.equals(senhaFireBase)) {

                            float saldo = userProfile.getSaldo();
                            if (saldo >= valor) {
                                reference.child(userID).child("saldo").setValue(saldo - valor);
                                startActivity(new Intent(getApplicationContext(), PixComprovanteCopiaCola.class));
                                Toast.makeText(getApplicationContext(), "Transação realizada com sucesso!", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Senha incorreta!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //_______________________________________ Senha ______________________________________
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Erro. Tente novamente!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}