package com.example.futurebankgrupo1.recarga;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.futurebankgrupo1.databinding.ActivitySenhaRecargaBinding;
import com.example.futurebankgrupo1.usuario.UserFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

public class SenhaRecargaActivity extends AppCompatActivity {

    private ActivitySenhaRecargaBinding binding;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySenhaRecargaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        String valor;
        String pagamentoSelect;
        SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);
        valor = preferences.getString("chaveValorRecarga", "");
        pagamentoSelect = preferences.getString("chaveTipoPagamento", "");
        float valorSelect = Float.parseFloat(valor);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserFirebase userProfile = snapshot.getValue(UserFirebase.class);

                if (userProfile != null) {
                    float valorFatura = userProfile.getValorFatura();
                    float limite = userProfile.getLimiteCartao();
                    float saldo = userProfile.getSaldo();

                    //_______________________________________Biometria______________________________________
                    Executor executor = ContextCompat.getMainExecutor(getApplicationContext());

                    BiometricPrompt biometricPrompt = new BiometricPrompt(SenhaRecargaActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                            super.onAuthenticationError(errorCode, errString);
                            //Toast.makeText(getApplicationContext(), "Digital com erro ou não cadastrada em seu dispositivo! Tente outra digital.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                            super.onAuthenticationSucceeded(result);

                            if ((pagamentoSelect.equals("Débito")) && saldo >= valorSelect) {
                                reference.child(userID).child("saldo").setValue(saldo - valorSelect);
                                Toast.makeText(getApplicationContext(), "Recarga realizada com Sucesso!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), RecargaComprovanteActivity.class);
                                startActivity(intent);
                            }else if (pagamentoSelect.equals("Débito")) {
                                Toast.makeText(SenhaRecargaActivity.this, "Saldo insuficiente", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), RecargaCelularActivity.class));
                            }


                            else if ((pagamentoSelect.equals("Crédito")) && limite >= valorSelect) {
                                reference.child(userID).child("valorFatura").setValue(valorFatura + valorSelect);
                                reference.child(userID).child("limiteCartao").setValue(limite - valorSelect);
                                Toast.makeText(getApplicationContext(), "Recarga realizada com Sucesso!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), RecargaComprovanteActivity.class);
                                startActivity(intent);
                            }else if (pagamentoSelect.equals("Crédito")) {
                                Toast.makeText(SenhaRecargaActivity.this, "Limite insuficiente", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), RecargaCelularActivity.class));
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
                    //_______________________________________ Biometria ______________________________________



                    //_______________________________________ Senha ______________________________________
                    binding.btnConfirmarTransacao.setOnClickListener(v -> {

                        String senhaFireBase = userProfile.getSenha();
                        String senhaDigitada = binding.edtSenhaConfirmacao.getText().toString().trim();

                        if (senhaDigitada.equals(senhaFireBase)) {

                            if ((pagamentoSelect.equals("Débito")) && saldo >= valorSelect) {
                                reference.child(userID).child("saldo").setValue(saldo - valorSelect);
                                Toast.makeText(getApplicationContext(), "Recarga realizada com Sucesso!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), RecargaComprovanteActivity.class);
                                startActivity(intent);
                            }else if (pagamentoSelect.equals("Débito")) {
                                Toast.makeText(SenhaRecargaActivity.this, "Saldo insuficiente", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), RecargaCelularActivity.class));
                            }


                            else if ((pagamentoSelect.equals("Crédito")) && limite >= valorSelect) {
                                reference.child(userID).child("valorFatura").setValue(valorFatura + valorSelect);
                                reference.child(userID).child("limiteCartao").setValue(limite - valorSelect);
                                Toast.makeText(getApplicationContext(), "Recarga realizada com Sucesso!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), RecargaComprovanteActivity.class);
                                startActivity(intent);
                            }else if (pagamentoSelect.equals("Crédito")) {
                                Toast.makeText(SenhaRecargaActivity.this, "Limite insuficiente", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), RecargaCelularActivity.class));
                            }

                        }else {
                            Toast.makeText(getApplicationContext(), "Senha incorreta!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //_______________________________________ Senha ______________________________________

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Ocorreu algum erro! Tente novamente.", Toast.LENGTH_SHORT).show();
            }
        });


    }
}