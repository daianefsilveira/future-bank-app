package com.example.futurebankgrupo1.cartoes;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.futurebankgrupo1.databinding.ActivityCartaoComumBinding;
import com.example.futurebankgrupo1.databinding.ActivityCartaoSupremeBinding;

public class CartaoSupremeActivity extends AppCompatActivity {

    private ActivityCartaoSupremeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartaoSupremeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}