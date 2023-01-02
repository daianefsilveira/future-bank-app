package com.example.futurebankgrupo1.recarga;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.futurebankgrupo1.HomeActivity;
import com.example.futurebankgrupo1.databinding.ActivityRecargaCelularBinding;
import com.example.futurebankgrupo1.usuario.UserFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RecargaCelularActivity extends AppCompatActivity {

    private ActivityRecargaCelularBinding binding;
    //MyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecargaCelularBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        String userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserFirebase userProfile = snapshot.getValue(UserFirebase.class);

                if (userProfile != null) {
                    String telefoneFirebase = userProfile.getTelefone();

                    binding.edtTelefone.setText(telefoneFirebase);

                    binding.btnRecarregar.setOnClickListener(view1 -> {

                        //spinner
                        float valorSelect = Float.parseFloat(binding.spValor.getSelectedItem().toString());
                        String pagamentoSelect = binding.spPagamento.getSelectedItem().toString();
                        String operadoraSelect = binding.spOperadora.getSelectedItem().toString();
                        String telefone = binding.edtTelefone.getText().toString();


                        if (!pagamentoSelect.isEmpty() && !operadoraSelect.isEmpty() && !telefone.isEmpty() && Patterns.PHONE.matcher(telefone).matches()) {
                            startActivity(new Intent(getApplicationContext(), SenhaRecargaActivity.class));
                        } else {
                            if (telefone.isEmpty()) {
                                binding.edtTelefone.setError("Preencha o campo");
                            }
                            if (!Patterns.PHONE.matcher(telefone).matches()) {
                                binding.edtTelefone.setError("Preencha o campo");
                            }
                            if (valorSelect <= 0) {
                                ((TextView) binding.spValor.getSelectedView()).setError("Selecione um dos campos");
                            }
                            if (pagamentoSelect.isEmpty()) {
                                ((TextView) binding.spOperadora.getSelectedView()).setError("Selecione um dos campos");
                            }
                            if (operadoraSelect.isEmpty()) {
                                ((TextView) binding.spPagamento.getSelectedView()).setError("Selecione um dos campos");
                            }
                        }

                        SharedPreferences preferences = getSharedPreferences("chaveGeral", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("chaveTelefone", binding.edtTelefone.getText().toString());
                        editor.putString("chaveOperadora", binding.spOperadora.getSelectedItem().toString());
                        editor.putString("chaveValorRecarga", binding.spValor.getSelectedItem().toString());
                        editor.putString("chaveTipoPagamento", binding.spPagamento.getSelectedItem().toString());
                        editor.commit();




                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RecargaCelularActivity.this, "Ocorreu um erro ao exibir o numero do celular!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.icClear.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        });

    }
}