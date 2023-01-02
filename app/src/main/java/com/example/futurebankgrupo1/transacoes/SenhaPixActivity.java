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

import com.example.futurebankgrupo1.R;
import com.example.futurebankgrupo1.databinding.ActivitySenhaPixBinding;
import com.example.futurebankgrupo1.recarga.RecargaCelularActivity;
import com.example.futurebankgrupo1.recarga.RecargaComprovanteActivity;
import com.example.futurebankgrupo1.recarga.SenhaRecargaActivity;
import com.example.futurebankgrupo1.recycler.RecyclerCorrente;
import com.example.futurebankgrupo1.usuario.UserFirebase;
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

public class SenhaPixActivity extends AppCompatActivity {

    private ActivitySenhaPixBinding binding;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private final String flaviodev = "flaviofonsecadev@gmail.com";
    private final String daiane = "daiane.silveira@foursys.com.br";
    private final String rafael = "rafael.silverio@foursys.com.br";
    private final String kaua = "kaua.alcaya@foursys.com.br";
    private final String flavio = "flavio.fonseca@foursys.com.br";
    private final String rafaelOutlook = "rafael.silverio.s@outlook.com";
    private final String flavioDevId = "v6PqMgULNofhHMwIopEh8tSCKdn1";
    private final String daianeId = "BToaOYweJRYyAZi3Okv2upoHfyd2";
    private final String rafaelId = "rcc2qZeAzqbAnsNf5TvzvK0W2qF2";
    private final String kauaId = "sFvyg4LiTzb2BI9e4P1X7Myowrf2";
    private final String flavioId = "EP0rIiIDS5Tma0cAZRK74OwE6HJ3";
    private final String rafaelOutlookId = "ZDKJqSYJMRaVPTtIkhiQhngtbJM2";

    Locale localeBR = new Locale( "pt", "BR" );
    NumberFormat dinheiroBR = NumberFormat.getCurrencyInstance(localeBR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySenhaPixBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        String valorPix;
        String chavePix;
        SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);
        chavePix = preferences.getString("chaveChavePix", "");
        valorPix = preferences.getString("chaveValorPix", "");
        String textoMask = valorPix;
        String textoNovo = textoMask.replace(",", ".");
        float valor = Float.parseFloat(textoNovo);


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserFirebase userProfile = snapshot.getValue(UserFirebase.class);

                if (userProfile != null) {


                    //_______________________________________Biometria______________________________________
                    Executor executor = ContextCompat.getMainExecutor(getApplicationContext());

                    BiometricPrompt biometricPrompt = new BiometricPrompt(SenhaPixActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                            super.onAuthenticationError(errorCode, errString);
                            //Toast.makeText(getApplicationContext(), "Digital com erro ou não cadastrada em seu dispositivo! Tente outra digital.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                            super.onAuthenticationSucceeded(result);

                            String nomeRemetente = userProfile.getNome();
                            float saldo = userProfile.getSaldo();
                            if (saldo >= valor) {
                                reference.child(userID).child("saldo").setValue(saldo - valor);
                                startActivity(new Intent(getApplicationContext(), PixComprovanteActivity.class));
                            }

                            if (chavePix.equals(flaviodev)) {
                                transferirPix(flavioDevId, valor);
                                addToExtrato(flavioDevId, nomeRemetente, valorPix);
                            } else if (chavePix.equals(daiane)) {
                                transferirPix(daianeId, valor);
                                addToExtrato(daianeId, nomeRemetente, valorPix);
                            } else if (chavePix.equals(rafael)) {
                                transferirPix(rafaelId, valor);
                                addToExtrato(rafaelId, nomeRemetente, valorPix);
                            } else if (chavePix.equals(kaua)) {
                                transferirPix(kauaId, valor);
                                addToExtrato(kauaId, nomeRemetente, valorPix);
                            } else if (chavePix.equals(flavio)) {
                                transferirPix(flavioId, valor);
                                addToExtrato(flavioId, nomeRemetente, valorPix);
                            } else if (chavePix.equals(rafaelOutlook)) {
                                transferirPix(rafaelOutlookId, valor);
                                addToExtrato(rafaelOutlookId, nomeRemetente, valorPix);
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
                            String nomeRemetente = userProfile.getNome();
                            float saldo = userProfile.getSaldo();
                            if (saldo >= valor) {
                                reference.child(userID).child("saldo").setValue(saldo - valor);
                                startActivity(new Intent(getApplicationContext(), PixComprovanteActivity.class));
                            }

                            if (chavePix.equals(flaviodev)) {
                                transferirPix(flavioDevId, valor);
                                addToExtrato(flavioDevId, nomeRemetente, valorPix);
                                String nomeRecebedor = "Flavio Fonseca";
                                SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("chaveNomeRecebedor", nomeRecebedor);
                                editor.commit();
                            } else if (chavePix.equals(daiane)) {
                                transferirPix(daianeId, valor);
                                addToExtrato(daianeId, nomeRemetente, valorPix);
                                String nomeRecebedor = "Daiane Silveira";
                                SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("chaveNomeRecebedor", nomeRecebedor);
                                editor.commit();
                            } else if (chavePix.equals(rafael)) {
                                transferirPix(rafaelId, valor);
                                addToExtrato(rafaelId, nomeRemetente, valorPix);
                                String nomeRecebedor = "Rafael Luiz Silverio dos Santos";
                                SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("chaveNomeRecebedor", nomeRecebedor);
                                editor.commit();
                            } else if (chavePix.equals(kaua)) {
                                transferirPix(kauaId, valor);
                                addToExtrato(kauaId, nomeRemetente, valorPix);
                                String nomeRecebedor = "kauã alcaya";
                                SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("chaveNomeRecebedor", nomeRecebedor);
                                editor.commit();
                            } else if (chavePix.equals(flavio)) {
                                transferirPix(flavioId, valor);
                                addToExtrato(flavioId, nomeRemetente, valorPix);
                                String nomeRecebedor = "Flávio J. Fonsêca Filho";
                                SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("chaveNomeRecebedor", nomeRecebedor);
                                editor.commit();
                            } else if (chavePix.equals(rafaelOutlook)) {
                                transferirPix(rafaelOutlookId, valor);
                                addToExtrato(rafaelOutlookId, nomeRemetente, valorPix);
                                String nomeRecebedor = "Rafael Luiz Silverio";
                                SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("chaveNomeRecebedor", nomeRecebedor);
                                editor.commit();
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

            }
        });


    }

    public void transferirPix(String id, float valor) {
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserFirebase userProfile = snapshot.getValue(UserFirebase.class);
                assert userProfile != null;
                float saldo = userProfile.getSaldo();
                reference.child(id).child("saldo").setValue(saldo + valor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Ocorreu algum erro!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addToExtrato(String idRecebedor, String nomeRemetente, String valor){
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        String onlineUserId = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("extratos").child(idRecebedor);
        String id = ref.push().getKey();
        DateFormat dateFormat = DateFormat.getDateInstance();
        Calendar cal = Calendar.getInstance();
        String data = dateFormat.format(cal.getTime());

        RecyclerCorrente recyclerCorrente = new RecyclerCorrente("Pix recebido de " + nomeRemetente, "R$ " + valor, data);
        assert id != null;
        ref.child(id).setValue(recyclerCorrente);
    }
}