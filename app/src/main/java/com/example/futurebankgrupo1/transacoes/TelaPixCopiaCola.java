package com.example.futurebankgrupo1.transacoes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.futurebankgrupo1.HomeActivity;
import com.example.futurebankgrupo1.MyViewModel;
import com.example.futurebankgrupo1.databinding.ActivityTelaPixCopiaColaBinding;
import com.example.futurebankgrupo1.usuario.UserFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.Locale;

public class TelaPixCopiaCola extends AppCompatActivity {

    private ActivityTelaPixCopiaColaBinding binding;
    private MyViewModel viewModel;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaPixCopiaColaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.icClearCopiaCola.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        });

        binding.btnProsseguir.setOnClickListener(v -> {

            SharedPreferences preferences1 = getSharedPreferences("chaveGeral", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences1.edit();
            editor.putString("chaveValorPixCopiaCola", binding.edtValorPix.getText().toString());
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), TelaConfirmarDadosPixCopiaCola.class);
            startActivity(intent);



        });
    }
}