package com.example.futurebankgrupo1.transacoes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.example.futurebankgrupo1.databinding.ActivityTelaConfirmarDadosTransferirContaBinding;

import java.util.Calendar;

public class TelaConfirmarDadosTransferirConta extends AppCompatActivity {

    //datepicker
    DatePickerDialog.OnDateSetListener onDateSetListener;

    private ActivityTelaConfirmarDadosTransferirContaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaConfirmarDadosTransferirContaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.icBack.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TelaTransferirConta.class);
            startActivity(intent);
        });


        String valorTed;
        String banco;
        String tipoConta;
        String agencia;
        String numeroConta;
        String cpfRecebedor;
        SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);
        valorTed = preferences.getString("chaveValorTED", "");
        binding.tvValor.setText("R$" + valorTed);
        banco = preferences.getString("chaveBanco", "");
        binding.tvGetInstituicao.setText(banco);
        tipoConta = preferences.getString("chaveTipoConta", "");
        binding.tvGetTipoConta.setText(tipoConta);
        agencia = preferences.getString("chaveAgencia", "");
        binding.tvGetAgencia.setText(agencia);
        numeroConta = preferences.getString("chaveNumeroConta","");
        binding.tvGetConta.setText(numeroConta);
        cpfRecebedor = preferences.getString("chaveCpfRecebedor", "");
        binding.tvGetCpf.setText(cpfRecebedor);



        //data calendar
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        binding.tvReagendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        TelaConfirmarDadosTransferirConta.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
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
            startActivity(new Intent(getApplicationContext(), SenhaTransferircontaActivity.class));
        });


    }
}