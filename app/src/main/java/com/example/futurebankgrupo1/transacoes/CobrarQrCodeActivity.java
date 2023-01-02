package com.example.futurebankgrupo1.transacoes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

import com.example.futurebankgrupo1.HomeActivity;
import com.example.futurebankgrupo1.databinding.ActivityCobrarQrCodeBinding;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

//import com.example.futurebankgrupo1.pagarcompix.TelaConfirmarDados;






public class CobrarQrCodeActivity extends AppCompatActivity {

    private ActivityCobrarQrCodeBinding binding;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCobrarQrCodeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.icClear.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        });

        String valorCobradoPix;
        SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);
        valorCobradoPix = preferences.getString("chaveCobrarPix", "");


        //gerar qrcode
    try {
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = barcodeEncoder.encodeBitmap(valorCobradoPix, BarcodeFormat.QR_CODE, 400, 400);
        binding.ivQrcode2.setImageBitmap(bitmap);
    } catch(Exception e) {

    }

    }
}