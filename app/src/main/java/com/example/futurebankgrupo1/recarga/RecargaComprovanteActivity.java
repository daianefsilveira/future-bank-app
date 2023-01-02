package com.example.futurebankgrupo1.recarga;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.futurebankgrupo1.HomeActivity;
import com.example.futurebankgrupo1.MyViewModel;
import com.example.futurebankgrupo1.databinding.ActivityRecargaComprovanteBinding;
import com.example.futurebankgrupo1.recycler.Compra;
import com.example.futurebankgrupo1.recycler.RecyclerCorrente;
import com.example.futurebankgrupo1.usuario.UserFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class RecargaComprovanteActivity extends AppCompatActivity {

    private ActivityRecargaComprovanteBinding binding;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    Date data = new Date();
    String dataFormatada = formataData.format(data);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecargaComprovanteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.icClear.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        });


        String telefone;
        String operadora;
        String valorRecarga;
        String tipoPagamento;

        SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);

        telefone = preferences.getString("chaveTelefone", "");
        binding.tvGetTelRecebedor.setText(telefone);

        operadora = preferences.getString("chaveOperadora", "");
        binding.tvGetOperRecebedora.setText(operadora);

        valorRecarga = preferences.getString("chaveValorRecarga", "");
        String valor = valorRecarga.replace(".", ",");
        binding.tvGetValorRecarga.setText("R$" + valor);

        tipoPagamento = preferences.getString("chaveTipoPagamento", "");
        binding.tvGetConta1.setText(tipoPagamento);

        if (tipoPagamento.equals("Débito")){
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            String onlineUserId = mAuth.getCurrentUser().getUid();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("extratos").child(onlineUserId);
            String id = ref.push().getKey();
            DateFormat dateFormat = DateFormat.getDateInstance();
            Calendar cal = Calendar.getInstance();
            String date = dateFormat.format(cal.getTime());

            RecyclerCorrente recyclerCorrente = new RecyclerCorrente("Recarga de celular", ("R$ " + valor), date);
            assert id != null;
            ref.child(id).setValue(recyclerCorrente);

        } else if (tipoPagamento.equals("Crédito")){
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            String onlineUserId = mAuth.getCurrentUser().getUid();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("fatura").child(onlineUserId);
            String id = ref.push().getKey();
            DateFormat dateFormat = DateFormat.getDateInstance();
            Calendar cal = Calendar.getInstance();
            String date = dateFormat.format(cal.getTime());


            Compra compra = new Compra("Recarga de celular", ("R$ " + valor), date);
            assert id != null;
            ref.child(id).setValue(compra);
        }





//        //puxa dados tela p/ comprovante
        binding.tvGetTelRecebedor.getText().toString();
        binding.tvGetOperRecebedora.getText().toString();
        binding.tvGetValorRecarga.getText().toString();
        binding.tvGetConta1.getText().toString(); //tipo de pagamento
        binding.tvGetPagador.getText().toString();

        //Data e hora do pagamento
        binding.tvGetDataHora.setText("Data " + dataFormatada);

        //Gerar ID da transação
        gerarIdTransacao();

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserFirebase userProfile = snapshot.getValue(UserFirebase.class);

                if (userProfile != null) {
                    String nome = userProfile.getNome();

                    binding.tvGetPagador.setText(nome);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RecargaComprovanteActivity.this, "Ocorreu algum erro com o nome do pagador!", Toast.LENGTH_SHORT).show();
            }
        });

        //----------------------------------------------------- PrintScreen ----------------------------------------------------------------------------------

        //verifyStoragePermission(this);

        /*binding.btnGerarPrint.setOnClickListener(v -> {
            binding.btnGerarPdf.setVisibility(View.GONE);
            binding.btnGerarPrint.setVisibility(View.GONE);
            binding.icClear.setVisibility(View.GONE);

            takeScreenShot(getWindow().getDecorView().getRootView(), "FutureBANK_recarga_");

            //Abrir imagem na galeria
            //startActivity(new Intent(Intent.ACTION_VIEW, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI));

            new Handler().postDelayed(() -> {
                binding.btnGerarPdf.setVisibility(View.VISIBLE);
                binding.btnGerarPrint.setVisibility(View.VISIBLE);
                binding.icClear.setVisibility(View.VISIBLE);
            }, 1000);

        });*/

        //----------------------------------------------------- PrintScreen ----------------------------------------------------------------------------------


        //----------------------------------------------------- PDF----------------------------------------------------------------------------------

        binding.btnGerarPdf.setOnClickListener(v -> {
            gerarPDF();
        });


    }//Fim onCreate



    private void gerarPDF() {

        String getIdNome = binding.tvGetId.getText().toString();

        PdfDocument documentoPDF = new PdfDocument();

        PdfDocument.PageInfo detalhesDaPagina = new PdfDocument.PageInfo.Builder(350, 600, 1).create();

        PdfDocument.Page novaPagina = documentoPDF.startPage(detalhesDaPagina);

        Canvas canvas = novaPagina.getCanvas();

        Paint corDoTexto = new Paint();
        corDoTexto.setColor(Color.BLACK);
        Paint corCinzaTexto = new Paint();
        corCinzaTexto.setColor(Color.GRAY);
        Paint corRosaTexto = new Paint();
        corRosaTexto.setColor(Color.MAGENTA);

        //Topo
        canvas.drawText(binding.tvFuture.getText().toString(),20, 50, corDoTexto);


        //Lado esquerdo
        canvas.drawText(binding.tvRecargaRealizada.getText().toString(), 20, 80, corDoTexto);
        canvas.drawText(binding.tvGetDataHora.getText().toString(), 20, 96, corCinzaTexto);

        //separador
        canvas.drawText("______________________________________________________________",20, 134, corCinzaTexto);

        canvas.drawText(binding.tvTelRecebedor.getText().toString(), 20, 166, corCinzaTexto);
        canvas.drawText(binding.tvOperRecebora.getText().toString(), 20, 182, corCinzaTexto);
        canvas.drawText(binding.tvValorRecarga.getText().toString(), 20, 198, corCinzaTexto);
        canvas.drawText(binding.tvTipoPagamento.getText().toString(), 20, 214, corCinzaTexto);
        canvas.drawText(binding.tvPagador.getText().toString(), 20, 278, corCinzaTexto);
        canvas.drawText(binding.tvInstituicao.getText().toString(), 20, 294, corCinzaTexto);
        canvas.drawText(binding.tvAgencia.getText().toString(), 20, 310, corCinzaTexto);
        canvas.drawText(binding.tvContaCorrente.getText().toString(), 20, 326, corCinzaTexto);

        //separador
        canvas.drawText("______________________________________________________________",20, 242, corCinzaTexto);

        //Lado Direito
        canvas.drawText(binding.tvGetTelRecebedor.getText().toString(), 160, 166, corDoTexto);
        canvas.drawText(binding.tvGetOperRecebedora.getText().toString(), 160, 182, corDoTexto);
        canvas.drawText(binding.tvGetValorRecarga.getText().toString(), 160, 198, corDoTexto);
        canvas.drawText(binding.tvGetConta1.getText().toString(), 160, 214, corDoTexto);
        canvas.drawText(binding.tvGetPagador.getText().toString(), 160, 278, corDoTexto);
        canvas.drawText(binding.tvGetInstituicao.getText().toString(), 160, 294, corDoTexto);
        canvas.drawText(binding.tvGetAgencia.getText().toString(), 160, 310, corDoTexto);
        canvas.drawText(binding.tvGetConta.getText().toString(), 160, 326, corDoTexto);

        //Bottom
        canvas.drawText(binding.tvIdTransacao.getText().toString(), 20, 550, corCinzaTexto);
        canvas.drawText(binding.tvGetId.getText().toString(), 160, 550, corDoTexto);



        documentoPDF.finishPage(novaPagina);

        String targetPdf = "/storage/emulated/0/Download/recarga_" + getIdNome +  ".pdf";
        File filePath = new File(targetPdf);

        try {
            documentoPDF.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "PDF salvo na pasta de download do seu dispositivo", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao gerar PDF" + e, Toast.LENGTH_LONG).show();
        }

        final Uri arquivo = Uri.parse(targetPdf);
        final Intent _intent = new Intent();
        _intent.setAction(Intent.ACTION_SEND);
        _intent.putExtra(Intent.EXTRA_STREAM, arquivo);
        _intent.putExtra(Intent.EXTRA_TEXT, "");
        _intent.putExtra(Intent.EXTRA_TITLE, "Compartilhar comprovate.");

        _intent.setType("application/pdf");

        startActivity(Intent.createChooser(_intent, "Compartilhar"));


    }

    public String gerarIdTransacao() {
        Random rand = new Random();
        String letrasID = "";
        String numerosID = "";

        SimpleDateFormat formatDate = new SimpleDateFormat("ddMMyyyy-hhmmss");
        Date data = new Date();
        String dateFormat = formatDate.format(data);

        for (int i = 0; i < 3; i++) {
            char randomizedCharacter = (char) (rand.nextInt(26) + 'A');
            letrasID += randomizedCharacter;
        }

        for (int i = 0; i < 3; i++) {
            int value = rand.nextInt(9);
            numerosID += value;
        }
        binding.tvGetId.setText(letrasID + "-" + numerosID + "-" + dateFormat);
        return letrasID + "_" + numerosID;
    }

    //----------------------------------------------------- PDF ----------------------------------------------------------------------------------

    //----------------------------------------------------- PrintScreen ----------------------------------------------------------------------------------

    /*protected static File takeScreenShot(View view, String fileName) {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd_MM_yyyy");
        Date data = new Date();
        String dateFormat = formatDate.format(data);

        try{

            String dirPath = Environment.getExternalStorageDirectory().toString() + "/comprovanteRecarga";
            File fileDir = new File(dirPath);
            if(!fileDir.exists()) {
                boolean mkdir = fileDir.mkdir();
            }

            String path = dirPath + "/" + fileName + dateFormat + ".jpeg";
            view .setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File imageFile = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return imageFile;



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSION_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermission(Activity activity) {

        int permission = ActivityCompat.checkSelfPermission(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSION_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }*/


    //----------------------------------------------------- PrintScreen ----------------------------------------------------------------------------------


}