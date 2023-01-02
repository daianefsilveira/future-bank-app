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

import com.example.futurebankgrupo1.databinding.ActivityTelaConfirmarDadosPixCopiaColaBinding;

import java.util.Calendar;

public class TelaConfirmarDadosPixCopiaCola extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener onDateSetListener;
    private ActivityTelaConfirmarDadosPixCopiaColaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaConfirmarDadosPixCopiaColaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.icBack.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TelaPixCopiaCola.class);
            startActivity(intent);
        });

        //recebe valor pix copia e cola
        String valorPix;
        SharedPreferences preferences1 = getSharedPreferences("chaveGeral", MODE_PRIVATE);
        valorPix = preferences1.getString("chaveValorPixCopiaCola", "");
        binding.tvValor.setText("R$" + valorPix);

        //data calendar
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        binding.tvReagendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        TelaConfirmarDadosPixCopiaCola.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
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

            SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("chaveMensagemPixCopiaCola", binding.edtMensagem.getText().toString());
            editor.commit();

            startActivity(new Intent(getApplicationContext(), SenhaPixCopiaColaActivity.class));
        });
    }
}