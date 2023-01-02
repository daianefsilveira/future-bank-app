package com.example.futurebankgrupo1.transacoes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.futurebankgrupo1.HomeActivity;
import com.example.futurebankgrupo1.MyViewModel;
import com.example.futurebankgrupo1.databinding.ActivityPixComprovanteCopiaColaBinding;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class PixComprovanteCopiaCola extends AppCompatActivity {

    private ActivityPixComprovanteCopiaColaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPixComprovanteCopiaColaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.icClear.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        String userID = user.getUid();
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserFirebase userProfile = snapshot.getValue(UserFirebase.class);

                if (userProfile != null){
                    String nome = userProfile.getNome();
                    String cpf = userProfile.getCpf();

                    binding.tvPagador.setText(nome);
                    binding.tvNumCpfPagador.setText(cpf);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PixComprovanteCopiaCola.this, "Error Firebase", Toast.LENGTH_SHORT).show();
            }
        });

        String valorPix;
        String date;
        String mensagemPixCopiaCola;
        SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);
        valorPix = preferences.getString("chaveValorPixCopiaCola", "");
        date = preferences.getString("chaveDate", "");
        mensagemPixCopiaCola = preferences.getString("chaveMensagemPixCopiaCola", "");
        binding.tvGetValorTransferido.setText("R$" + valorPix);
        binding.tvDataHora.setText(date);
        binding.tvTipoPgto.setText(mensagemPixCopiaCola);

        addToExtrato(valorPix);


        //Gerar ID transação
        gerarIdTransacao();

        //----------------------------------------------------- PDF----------------------------------------------------------------------------------

        binding.btnComprovantePixCopiaCola.setOnClickListener(v -> {
            gerarPDF();
        });


    }

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

        //Top
        canvas.drawText(binding.tvFuture.getText().toString(),20, 50, corDoTexto);


        //Lado esquerdo
        canvas.drawText(binding.tvCopiaColaPix.getText().toString().trim(), 20, 80, corDoTexto);
        canvas.drawText(binding.tvData.getText().toString().trim(), 20, 96, corCinzaTexto);
        canvas.drawText(binding.tvDataHora.getText().toString(), 50, 96, corCinzaTexto);

        //separador
        canvas.drawText("______________________________________________________________",20, 134, corCinzaTexto);

        canvas.drawText(binding.tvParaRecebedor.getText().toString(), 20, 166, corCinzaTexto);
        canvas.drawText(binding.tvCpfRecebedor.getText().toString(), 20, 182, corCinzaTexto);
        canvas.drawText(binding.tvInstituicaoRecebedor.getText().toString(), 20, 198, corCinzaTexto);
        canvas.drawText(binding.tvAgRecebedor.getText().toString(), 20, 214, corCinzaTexto);
        canvas.drawText(binding.tvCcRecebedor.getText().toString(), 20, 230, corCinzaTexto);
        canvas.drawText(binding.tvTipoDeChavePixRecebedor.getText().toString(), 20, 246, corCinzaTexto);
        canvas.drawText(binding.tvValorTransferencia.getText().toString(), 20, 262, corCinzaTexto);

        canvas.drawText(binding.tvDePagador.getText().toString(), 20, 318, corCinzaTexto);
        canvas.drawText(binding.tvCpfPagador.getText().toString(), 20, 334, corCinzaTexto);
        canvas.drawText(binding.tvInstituicaoPagador.getText().toString(), 20, 350, corCinzaTexto);
        canvas.drawText(binding.tvAgPagador.getText().toString(), 20, 366, corCinzaTexto);
        canvas.drawText(binding.tvCcPagador.getText().toString(), 20, 382, corCinzaTexto);
        canvas.drawText(binding.tvMsg.getText().toString(), 20, 430, corCinzaTexto);
        canvas.drawText(binding.tvTipoPgto.getText().toString(), 20, 446, corDoTexto);

        //separador
        canvas.drawText("______________________________________________________________",20, 290, corCinzaTexto);

        //Lado Direito
        canvas.drawText(binding.tvRecebedor.getText().toString(), 160, 166, corDoTexto);
        canvas.drawText(binding.tvNumCpfRecebedor.getText().toString(), 160, 182, corDoTexto);
        canvas.drawText(binding.tvNomeInstituicaoRecebedor.getText().toString(), 160, 198, corDoTexto);
        canvas.drawText(binding.tvNumAgRecebedor.getText().toString(), 160, 214, corDoTexto);
        canvas.drawText(binding.tvNumCcRecebedor.getText().toString(), 160, 230, corDoTexto);
        canvas.drawText(binding.tvChavePixRecebedor.getText().toString(), 160, 246, corDoTexto);
        canvas.drawText(binding.tvGetValorTransferido.getText().toString(), 160, 262, corDoTexto);

        canvas.drawText(binding.tvPagador.getText().toString(), 160, 318, corDoTexto);
        canvas.drawText(binding.tvNumCpfPagador.getText().toString(), 160, 334, corDoTexto);
        canvas.drawText(binding.tvNomeInstituicaoPagador.getText().toString(), 160, 350, corDoTexto);
        canvas.drawText(binding.tvNumAgPagador.getText().toString(), 160, 366, corDoTexto);
        canvas.drawText(binding.tvNumCcPagador.getText().toString(), 160, 382, corDoTexto);



        //Bottom
        canvas.drawText(binding.tvIdTransacao.getText().toString(), 20, 550, corCinzaTexto);
        canvas.drawText(binding.tvGetId.getText().toString(), 160, 550, corDoTexto);

        documentoPDF.finishPage(novaPagina);

        String targetPdf = "/storage/emulated/0/Download/FuturaBANK_CopiaCola_" + getIdNome + ".pdf";
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

    private void addToExtrato(String valor) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String onlineUserId = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("extratos").child(onlineUserId);
        String id = ref.push().getKey();
        DateFormat dateFormat = DateFormat.getDateInstance();
        Calendar cal = Calendar.getInstance();
        String data = dateFormat.format(cal.getTime());

        RecyclerCorrente recyclerCorrente = new RecyclerCorrente("Pix para João da Silva", "R$ " + valor, data);
        assert id != null;
        ref.child(id).setValue(recyclerCorrente);
    }
}