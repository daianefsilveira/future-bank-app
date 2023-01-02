package com.example.futurebankgrupo1.fatura.pagarfatura;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.futurebankgrupo1.MyViewModel;
import com.example.futurebankgrupo1.R;
import com.example.futurebankgrupo1.recycler.RecyclerCorrente;
import com.example.futurebankgrupo1.transacoes.PixComprovanteCopiaCola;
import com.example.futurebankgrupo1.transacoes.TelaConfirmarDadosPixCopiaCola;
import com.example.futurebankgrupo1.usuario.UserFirebase;
import com.example.futurebankgrupo1.databinding.ActivityPagarFaturaConfirmarValorBinding;
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
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executor;

public class PagarFaturaConfirmarValor extends AppCompatActivity {

    //data calendar

    DatePickerDialog.OnDateSetListener onDateSetListener;

    private ActivityPagarFaturaConfirmarValorBinding binding;
    private MyViewModel viewModel;
    Locale localeBR = new Locale( "pt", "BR" );
    NumberFormat dinheiroBR = NumberFormat.getCurrencyInstance(localeBR);

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPagarFaturaConfirmarValorBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();



        /*//data calendar
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        binding.tvReagendarFatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        PagarFaturaConfirmarValor.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener,year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        onDateSetListener =  new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            month = month+1;
            String date = day+"/"+month+"/"+year;
            binding.textData.setText(date);
            }
        };*/


//        //calendario do dia atual
//        final Calendar calendar1 = Calendar.getInstance();
//        int year1 = calendar1.get(Calendar.YEAR);
//        int month1 = calendar1.get(Calendar.MONTH);
//        int day1 = calendar1.get(Calendar.DAY_OF_MONTH);
//
//        TextData = findViewById(R.id.text_data);
//        TextData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(
//                        PagarFaturaConfirmarValor.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
//                        onDateSetListener,year1, month1, day1);
//                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                datePickerDialog.show();
//            }
//        });
//        onDateSetListener =  new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                month = month+1;
//                String date = day+"/"+month+"/"+year;
//                TextData.setText(date);
//            }
//        };


//        //calendario reagendamento
//        final Calendar calendar1 = Calendar.getInstance();
//        int year1 = calendar1.get(Calendar.YEAR);
//        int month1 = calendar1.get(Calendar.MONTH);
//        int day1 = calendar1.get(Calendar.DAY_OF_MONTH);
//
//        TextData = findViewById(R.id.text_data);
//        TextData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(
//                        PagarFaturaConfirmarValor.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
//                        onDateSetListener,year1, month1, day1);
//                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                datePickerDialog.show();
//            }
//        });
//        onDateSetListener =  new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                month = month+1;
//                String date = day+"/"+month+"/"+year;
//                TextData.setText(date);
//            }
//        };






        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserFirebase userProfile = snapshot.getValue(UserFirebase.class);

                if (userProfile != null){
                    //String nome = userProfile.getNome();
                    float saldo = userProfile.getSaldo();

                    binding.tvGetSaldoConta.setText(dinheiroBR.format(saldo));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PagarFaturaConfirmarValor.this, "Ocorreu algum erro!", Toast.LENGTH_SHORT).show();
            }
        });



        binding.icBack.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PagarFatura.class);
            startActivity(intent);
        });

       // binding.tvReagendarFatura.setOnClickListener(v -> {
           // Intent intent = new Intent(getApplicationContext(), ActivityReagendarPagamentosBinding.class);
           // startActivity(intent);
        //});

        /*binding.btnPagarFatura.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ComprovanteFatura.class);
            startActivity(intent);
        });*/

        //viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        //binding.tvGetValorFatura.setText(String.valueOf(viewModel.exibirValorFatura()));
        //binding.tvGetValorFatura.setText(dinheiroBR.format(viewModel.exibirValorFaturaFirebase()));

        //caso o novo calendario de erro, reativar este
        //SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);
        //day = preferences.getInt("chaveDay",0);
        //month = preferences.getInt("chaveMonth",0);
        //year= preferences.getInt("chaveYear", 0);
        //binding.tvAgora.setText(day + "/" +month+"/"+year);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        String userID = user.getUid();
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserFirebase userProfile = snapshot.getValue(UserFirebase.class);
                if (userProfile != null){
                    float saldo = userProfile.getSaldo();
                    float valorFatura = userProfile.getValorFatura();
                    float limite = userProfile.getLimiteCartao();
                    binding.tvGetValorFatura.setText(dinheiroBR.format(valorFatura));
                    binding.tvGetSaldoConta.setText(dinheiroBR.format(saldo));
                    //binding.tvGetValorFatura.setText(String.valueOf("R$" + valorFatura));
                    //binding.tvGetSaldoConta.setText(String.valueOf("R$" + saldo));


                    //Biometria

                    Executor executor = ContextCompat.getMainExecutor(getApplicationContext());

                    BiometricPrompt biometricPrompt = new BiometricPrompt(PagarFaturaConfirmarValor.this, executor, new BiometricPrompt.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                            super.onAuthenticationError(errorCode, errString);
                            Toast.makeText(PagarFaturaConfirmarValor.this, "Digital com erro ou não cadastrada em seu dispositivo! Tente outra digital.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                            super.onAuthenticationSucceeded(result);
                            Toast.makeText(getApplicationContext(), "Pagamento realizado com sucesso!", Toast.LENGTH_SHORT).show();
                            if (saldo >= valorFatura){
                                //viewModel.setarSaldo(saldo - valorFatura);
                                //viewModel.setarLimiteCartaoFirebase(valorFatura + limite);
                                //userProfile.setSaldo(saldo - valorFatura);
                                //userProfile.setLimiteCartao(valorFatura + limite);

                                Bundle enviarDados = new Bundle();
                                enviarDados.putFloat("valorFatura", valorFatura);

                                reference.child(userID).child("valorFatura").setValue(0);
                                reference.child(userID).child("limiteCartao").setValue(valorFatura + limite);
                                reference.child(userID).child("saldo").setValue(saldo - valorFatura);
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("extratos").child(userID);
                                String idFatura = ref.push().getKey();
                                DateFormat dateFormat = DateFormat.getDateInstance();
                                Calendar cal = Calendar.getInstance();
                                String date = dateFormat.format(cal.getTime());
                                //String valorRecycler = String.valueOf(valorFatura).replace(".", ",");
                                String valorRecycler = dinheiroBR.format(valorFatura);

                                RecyclerCorrente recyclerFatura = new RecyclerCorrente("Pagamento fatura do cartão", valorRecycler, date);
                                assert idFatura != null;
                                ref.child(idFatura).setValue(recyclerFatura);

                                Intent intent = new Intent(getApplicationContext(), ComprovanteFatura.class);
                                intent.putExtras(enviarDados);
                                startActivity(intent);
                            }else {
                                Toast.makeText(PagarFaturaConfirmarValor.this, "Saldo indisponível.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onAuthenticationFailed() {
                            super.onAuthenticationFailed();
                            Toast.makeText(PagarFaturaConfirmarValor.this, "Este dispositivo não suporta autenticação por biometria.", Toast.LENGTH_SHORT).show();
                        }
                    });

                    BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                            .setTitle("Confirmar Transação")
                            .setDescription("Use sua digital para confirmar esta transação.")
                            .setNegativeButtonText("Cancelar")
                            .build();

                    /*binding.btnPagarFatura.setOnClickListener(v -> {
                        biometricPrompt.authenticate(promptInfo);
                    });*/


                    binding.btnPagarFatura.setOnClickListener(v -> {
                        biometricPrompt.authenticate(promptInfo);

                    });

//                    binding.btnPagarFatura.setOnClickListener(v -> {
//
//                        if (saldo >= valorFatura){
//                            //viewModel.setarSaldo(saldo - valorFatura);
//                            //viewModel.setarLimiteCartaoFirebase(valorFatura + limite);
//                            userProfile.setSaldo(saldo - valorFatura);
//                            userProfile.setLimiteCartao(valorFatura + limite);
//                            Intent intent = new Intent(getApplicationContext(), ComprovanteFatura.class);
//                            startActivity(intent);
//                        }else {
//                            Toast.makeText(PagarFaturaConfirmarValor.this, "Saldo indisponível.", Toast.LENGTH_SHORT).show();
//                        }
//                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PagarFaturaConfirmarValor.this, "Ocorreu algum erro!", Toast.LENGTH_SHORT).show();
            }
        });

//        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
//
//        binding.btnPagarFatura.setOnClickListener(v -> {
//            float saldoCc = viewModel.exibirSaldoContaCorrente();
//            float fatura = viewModel.exibirValorFaturaFirebase();
//            float limite = viewModel.exibirLimiteCartaoFirebase();
//
//            if (saldoCc >= fatura){
//                reference.child(userID).child("valorFatura").setValue(0);
//                reference.child(userID).child("limiteCartao").setValue(fatura + limite);
//                reference.child(userID).child("saldo").setValue(saldoCc - fatura);
//
//                //viewModel.setarLimiteCartaoFirebase(fatura + limite);
//                //viewModel.setarSaldo(saldoCc - fatura);
//                //viewModel.setarValorFaturaFirebase(fatura - fatura);
//                Intent intent = new Intent(getApplicationContext(), ComprovanteFatura.class);
//                startActivity(intent);
//            }else {
//                Toast.makeText(PagarFaturaConfirmarValor.this, "Saldo indisponível.", Toast.LENGTH_SHORT).show();
//            }
//
//        });
    }
}