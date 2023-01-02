package com.example.futurebankgrupo1.cartoes;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.futurebankgrupo1.databinding.ActivityCartaoPremiumBinding;

public class CartaoPremiumActivity extends AppCompatActivity {
    private ActivityCartaoPremiumBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartaoPremiumBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}
