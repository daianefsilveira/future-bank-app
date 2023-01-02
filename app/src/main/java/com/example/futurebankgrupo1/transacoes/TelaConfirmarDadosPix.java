package com.example.futurebankgrupo1.transacoes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.futurebankgrupo1.databinding.ActivityTelaConfirmarDadosPixBinding;

import java.util.Calendar;
import java.util.concurrent.Executor;

public class TelaConfirmarDadosPix extends AppCompatActivity {

    //----------------------------------------------------- DATA CALENDARIO ----------------------------------------------------------------------------------
    DatePickerDialog.OnDateSetListener onDateSetListener;
    private ActivityTelaConfirmarDadosPixBinding binding;

    final java.util.Calendar calendar = java.util.Calendar.getInstance();
    int year = calendar.get(java.util.Calendar.YEAR);
    int month = calendar.get(java.util.Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    //----------------------------------------------------- DATA CALENDARIO ----------------------------------------------------------------------------------

    //---------------------------------------------------------- BINDING ----------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaConfirmarDadosPixBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.icBack.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PixTransferirActivity.class);
            startActivity(intent);
        });


        String valorPix;
        String nomeRecebedor;
        SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);
        valorPix = preferences.getString("chaveValorPix", "");
        binding.tvValor.setText("R$" + valorPix);
        nomeRecebedor = preferences.getString("chaveNomeRecebedor", "");
        binding.tvNomeRecebedor.setText(nomeRecebedor);


        //data calendar
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        binding.tvReagendar.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
              DatePickerDialog datePickerDialog = new DatePickerDialog(
                        TelaConfirmarDadosPix.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
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
                binding.tvAgora.setText(date);
                SharedPreferences preferences3 = getSharedPreferences("chaveGeral", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences3.edit();
                editor.putString("chaveDate", date);
                editor.commit();
            }
        };

        binding.btnConfirmarTransferencia.setOnClickListener(v -> {
            //int month2 = month+1;
            //String date = day+"/"+ (month + 1) +"/"+year;
            SharedPreferences preferences1 = getSharedPreferences("chaveGeral", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences1.edit();
            //editor.putString("chaveDate", date);
            editor.putString("chaveMensagemPix", binding.edtMensagem.getText().toString());
            editor.commit();

            startActivity(new Intent(getApplicationContext(), SenhaPixActivity.class));
        });

        //----------------------------------------------------- BIOMETRIA ----------------------------------------------------------------------------------

    }
}