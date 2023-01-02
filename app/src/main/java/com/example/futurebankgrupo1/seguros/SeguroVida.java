package com.example.futurebankgrupo1.seguros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.futurebankgrupo1.HomeActivity;
import com.example.futurebankgrupo1.contas.ContaCorrenteActivity;
import com.example.futurebankgrupo1.databinding.ActivityHomeBinding;
import com.example.futurebankgrupo1.databinding.ActivitySeguroVidaBinding;
import com.example.futurebankgrupo1.fatura.pagarfatura.ComprovanteFatura;
import com.example.futurebankgrupo1.fatura.pagarfatura.PagarFaturaConfirmarValor;
import com.example.futurebankgrupo1.recycler.RecyclerCorrente;
import com.example.futurebankgrupo1.transacoes.PixComprovanteActivity;
import com.example.futurebankgrupo1.transacoes.TelaConfirmarDadosPix;
import com.example.futurebankgrupo1.usuario.UserFirebase;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.concurrent.Executor;

public class SeguroVida extends AppCompatActivity {

    private ActivitySeguroVidaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeguroVidaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.icClear.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        });

        binding.cbIndividual.setOnClickListener(v -> {
            binding.cbPessoas.setChecked(false);
            binding.cbFamilia.setChecked(false);
        });

        binding.cbPessoas.setOnClickListener(v -> {
            binding.cbIndividual.setChecked(false);
            binding.cbFamilia.setChecked(false);
        });

        binding.cbFamilia.setOnClickListener(v-> {
            binding.cbIndividual.setChecked(false);
            binding.cbPessoas.setChecked(false);
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        String userID = user.getUid();


        //Biometria
        Executor executor = ContextCompat.getMainExecutor(this);

        BiometricPrompt biometricPrompt = new BiometricPrompt(SeguroVida.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(SeguroVida.this, "Digital com erro ou não cadastrada em seu dispositivo! Tente outra digital.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                //Lógica Contratação
                reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserFirebase userProfile = snapshot.getValue(UserFirebase.class);
                        if (userProfile != null){
                            float saldo = userProfile.getSaldo();

                            if (binding.cbIndividual.isChecked()){
                                if (saldo >= 37.90){
                                    reference.child(userID).child("saldo").setValue(saldo - 37.90);
                                    String transacao = "Seguro de Vida - Individual";
                                    String valor = "37,90";
                                    addToExtrato(transacao, valor);
                                    Toast.makeText(SeguroVida.this, "Plano individual contratado!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(SeguroVida.this, "Saldo insuficiente!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            else if (binding.cbPessoas.isChecked()){
                                if (saldo >= 43.90){
                                    reference.child(userID).child("saldo").setValue(saldo - 43.90);
                                    String transacao = "Seguro de Vida - Casal";
                                    String valor = "43,90";
                                    addToExtrato(transacao, valor);
                                    Toast.makeText(SeguroVida.this, "Plano para duas pessoas contratado!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(SeguroVida.this, "Saldo insuficiente!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            else if (binding.cbFamilia.isChecked()){
                                if (saldo >= 59.90){
                                    reference.child(userID).child("saldo").setValue(saldo - 59.90);
                                    String transacao = "Seguro de Vida - Família";
                                    String valor = "59,90";
                                    addToExtrato(transacao, valor);
                                    Toast.makeText(SeguroVida.this, "Plano família contratado!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(SeguroVida.this, "Saldo insuficiente!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else if(!binding.cbIndividual.isChecked() || !binding.cbPessoas.isChecked() || !binding.cbFamilia.isChecked()) {
                                Toast.makeText(SeguroVida.this, "Nenhum plano selecionado!", Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(SeguroVida.this, "Ocorreu algum erro, tente novamente!", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(SeguroVida.this, "Este dispositivo não suporta autenticação por biometria.", Toast.LENGTH_SHORT).show();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Confirmar Transação")
                .setDescription("Use sua digital para confirmar esta transação.")
                .setNegativeButtonText("Cancelar")
                .build();

        binding.btnContratarSeguroVida.setOnClickListener(v -> {
            biometricPrompt.authenticate(promptInfo);
        });




    }

    private void addToExtrato(String transacao, String valor) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String onlineUserId = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("extratos").child(onlineUserId);
        String id = ref.push().getKey();
        DateFormat dateFormat = DateFormat.getDateInstance();
        Calendar cal = Calendar.getInstance();
        String data = dateFormat.format(cal.getTime());

        RecyclerCorrente recyclerCorrente = new RecyclerCorrente(transacao, "R$ " + valor, data);
        assert id != null;
        ref.child(id).setValue(recyclerCorrente);
    }


}