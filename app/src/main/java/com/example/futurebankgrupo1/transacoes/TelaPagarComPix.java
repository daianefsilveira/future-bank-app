package com.example.futurebankgrupo1.transacoes;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.futurebankgrupo1.HomeActivity;
import com.example.futurebankgrupo1.databinding.ActivityTelaPagarComPixBinding;
import com.example.futurebankgrupo1.recycler.RecyclerCorrente;
import com.example.futurebankgrupo1.usuario.UserFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.client.android.Intents;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.text.DateFormat;
import java.util.Calendar;

public class TelaPagarComPix extends AppCompatActivity {

    private ActivityTelaPagarComPixBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaPagarComPixBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.ivArrowForwardQrcode.setOnClickListener(view1 -> {
            scanCode();
        });

        binding.icClearPagarComPix.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        });

        binding.ivArrowForward1.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PixTransferirActivity.class);
            startActivity(intent);
        });

        binding.ivArrowForward2.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TelaPixCopiaCola.class);
            startActivity(intent);
        });
    }

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Intent originalIntent = result.getOriginalIntent();
                    if (originalIntent == null) {
                        Toast.makeText(TelaPagarComPix.this, "Cancelado", Toast.LENGTH_LONG).show();
                    } else if(originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION_DIALOG_MESSAGE)) {
                        Toast.makeText(TelaPagarComPix.this, "Cancelado - Habilite a permissão da camera", Toast.LENGTH_LONG).show();
                    } else if(originalIntent.hasExtra(Intents.Scan.TIMEOUT)) {
                        Toast.makeText(TelaPagarComPix.this, "Tempo excedido - Tente novamente", Toast.LENGTH_LONG).show();
                    }
                } else {
                    String valor = result.getContents();
                    addToExtrato(valor);
                    String valorFormat = valor.replace(",", ".");
                    float valorf = Float.parseFloat(valorFormat);
                    pagarPixQrcode(valorf);
                    Toast.makeText(TelaPagarComPix.this, "Pagamento Realizado", Toast.LENGTH_LONG).show();
                }
            });

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setTimeout(30000);
        barcodeLauncher.launch(options);
    }

    public void pagarPixQrcode(Float valorf) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        String userID = user.getUid();
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserFirebase userProfile = snapshot.getValue(UserFirebase.class);
                assert userProfile != null;
                float saldo = userProfile.getSaldo();
                if (saldo > valorf){
                    reference.child(userID).child("saldo").setValue(saldo - valorf);
                } else {
                    Toast.makeText(TelaPagarComPix.this, "Saldo insuficiente", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Ocorreu algum erro!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addToExtrato(String valor){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String onlineUserId = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("extratos").child(onlineUserId);
        String id = ref.push().getKey();
        DateFormat dateFormat = DateFormat.getDateInstance();
        Calendar cal = Calendar.getInstance();
        String data = dateFormat.format(cal.getTime());
        //String valorFormat = valor.replace(".", ",");

        RecyclerCorrente recyclerCorrente = new RecyclerCorrente("Pagamento por pix - QR Code ", "R$ " + valor, data);
        assert id != null;
        ref.child(id).setValue(recyclerCorrente);
    }

}