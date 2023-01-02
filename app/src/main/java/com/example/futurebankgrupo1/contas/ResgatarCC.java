package com.example.futurebankgrupo1.contas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.futurebankgrupo1.HomeActivity;
import com.example.futurebankgrupo1.MyViewModel;
import com.example.futurebankgrupo1.recycler.RecyclerCorrente;
import com.example.futurebankgrupo1.transacoes.PixComprovanteCopiaCola;
import com.example.futurebankgrupo1.transacoes.TelaConfirmarDadosPixCopiaCola;
import com.example.futurebankgrupo1.usuario.UserFirebase;
import com.example.futurebankgrupo1.databinding.ActivityResgatarCcBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import java.util.concurrent.Executor;

public class ResgatarCC extends AppCompatActivity {
    ActivityResgatarCcBinding binding;
    private FirebaseAuth mAuth;

    Locale localeBR = new Locale( "pt", "BR" );
    NumberFormat dinheiroBR = NumberFormat.getCurrencyInstance(localeBR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResgatarCcBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.icClearResgatar.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        });


        //Biometria
        Executor executor = ContextCompat.getMainExecutor(this);

        BiometricPrompt biometricPrompt = new BiometricPrompt(ResgatarCC.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(ResgatarCC.this, "Digital com erro ou não cadastrada em seu dispositivo! Tente outra digital.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);


                String textoMask = binding.edtValorResgatar.getText().toString();
                String textoNovo = textoMask.replace(",", ".");
                float valor = Float.parseFloat(textoNovo);
                String valorRecycler = "R$ " + textoMask;


                mAuth = FirebaseAuth.getInstance();
                String onlineUserId = mAuth.getCurrentUser().getUid();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("extratos").child(onlineUserId);
                String id = ref.push().getKey();
                DateFormat dateFormat = DateFormat.getDateInstance();
                Calendar cal = Calendar.getInstance();
                String data = dateFormat.format(cal.getTime());

                RecyclerCorrente recyclerCorrente = new RecyclerCorrente("Resgate da poupança", valorRecycler, data);
                assert id != null;
                ref.child(id).setValue(recyclerCorrente);

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                reference.child(onlineUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserFirebase userProfile = snapshot.getValue(UserFirebase.class);

                        if (userProfile != null){
                            float saldo = userProfile.getSaldo();
                            float saldoPoupanca = userProfile.getSaldoPoupanca();
                            binding.tvSaldoDisponivelCcResgatar.setText(dinheiroBR.format(saldo));
                            binding.tvSaldoDisponivelResgatar.setText(dinheiroBR.format(saldoPoupanca));

                            if (saldo >=valor){
                                reference.child(onlineUserId).child("saldo").setValue(saldo + valor);
                                reference.child(onlineUserId).child("saldoPoupanca").setValue(saldoPoupanca - valor);

                                SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("chaveValorResgatar", binding.edtValorResgatar.getText().toString());
                                editor.commit();
                                Toast.makeText(getApplicationContext(), "Resgate realizado com sucesso!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ResgatarComprovante.class);
                                startActivity(intent);
                            }


                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ResgatarCC.this, "Error Firebase", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(ResgatarCC.this, "Este dispositivo não suporta autenticação por biometria.", Toast.LENGTH_SHORT).show();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Confirmar Transação")
                .setDescription("Use sua digital para confirmar esta transação.")
                .setNegativeButtonText("Cancelar")
                .build();


        binding.buttonResgatar.setOnClickListener(v -> {
            biometricPrompt.authenticate(promptInfo);
        });

    }
}