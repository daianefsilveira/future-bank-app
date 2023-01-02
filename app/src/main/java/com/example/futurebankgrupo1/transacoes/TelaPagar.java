package com.example.futurebankgrupo1.transacoes;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.Toast;

import com.example.futurebankgrupo1.HomeActivity;
import com.example.futurebankgrupo1.R;
import com.example.futurebankgrupo1.databinding.ActivityTelaPagarBinding;
import com.example.futurebankgrupo1.fatura.pagarfatura.PagarFatura;
import com.example.futurebankgrupo1.recycler.RecyclerCorrente;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.android.Intents;
import com.google.zxing.qrcode.encoder.QRCode;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class TelaPagar extends AppCompatActivity {


    private ActivityTelaPagarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaPagarBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //SCANNER
        binding.tvSetaPagarBoleto.setOnClickListener(view12 -> {
            scanCode();
        });


        binding.ivArrowForward1.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TelaPagarComPix.class);
            startActivity(intent);
        });

        binding.ivArrowForward2.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PagarFatura.class);
            startActivity(intent);
        });

        binding.icClear.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        });

        /*binding.tvSetaQr.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CaptureQr.class);
            startActivity(intent);
        });*/
    }

    /*private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureQr.class);
        barLauncher.launch(options);
    }
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
       if(result.getContents() !=null){
           AlertDialog.Builder builder = new AlertDialog.Builder(TelaPagar.this);
           builder.setTitle("Result");
           builder.setMessage(result.getContents());
           builder.setPositiveButton("okay", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i)
               {
                dialogInterface.dismiss();
               }
           }).show();
       }
    });*/

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Intent originalIntent = result.getOriginalIntent();
                    if (originalIntent == null) {
                        Toast.makeText(TelaPagar.this, "Cancelado", Toast.LENGTH_LONG).show();
                    } else if(originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION_DIALOG_MESSAGE)) {
                        Toast.makeText(TelaPagar.this, "Cancelado - Habilite a permissão da camera", Toast.LENGTH_LONG).show();
                    } else if(originalIntent.hasExtra(Intents.Scan.TIMEOUT)) {
                        Toast.makeText(TelaPagar.this, "Tempo excedido - Tente novamente", Toast.LENGTH_LONG).show();
                    }
                } else {
                    String barcoderesult = result.getContents();
                    binding.tvPagar.setText(barcoderesult);


                    Toast.makeText(TelaPagar.this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                }
            });

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setBeepEnabled(true);
        options.setOrientationLocked(false);
        options.setTimeout(30000);
        barcodeLauncher.launch(options);
    }

   /*//gerar qrcode
    try {
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = barcodeEncoder.encodeBitmap("content", BarcodeFormat.QR_CODE, 400, 400);
        ImageView imageViewQrCode = (ImageView) findViewById(R.id.qrCode);
        imageViewQrCode.setImageBitmap(bitmap);
    } catch(Exception e) {

    }*/

}
















































































































































































































































































