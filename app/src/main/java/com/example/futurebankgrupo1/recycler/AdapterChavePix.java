package com.example.futurebankgrupo1.recycler;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.futurebankgrupo1.R;

import java.util.List;

public class AdapterChavePix extends RecyclerView.Adapter<AdapterChavePix.MyViewHolder> {

    private final List<RecyclerChavePix> listaChavePix;
    private final SharedPreferences sharedPreferences;

    public AdapterChavePix(List<RecyclerChavePix> lista, SharedPreferences preferences) {
        this.listaChavePix = lista;
        this.sharedPreferences = preferences;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lista_chave_pix, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        RecyclerChavePix recyclerChavePix = listaChavePix.get(position);

        holder.tipoChave.setText(recyclerChavePix.getTipoChave());
        holder.chavePix.setText(recyclerChavePix.getChavePix());

        holder.ivDelete.setOnClickListener(v -> {
            switch (recyclerChavePix.getTipoChave()) {
                case "Tipo de chave: CPF" : removerDoShared("chaveCpf", "chaveCbCpf", holder);
                    break;
                case "Tipo de chave: Celular" :  removerDoShared("chaveCelular", "chaveCbCelular", holder);
                    break;
                case "Tipo de chave: Email" : removerDoShared("chaveEmail", "chaveCbEmail", holder);
                    break;
                case "Tipo de chave: Chave Aleatória" : removerDoShared("", "chaveCbChaveAleatoria", holder);
                    break;
                default:
            }
        });
    }

    private void removerDoShared(String tipoDeChave, String chaveCheckBox, MyViewHolder holder) {
        sharedPreferences.edit().remove(tipoDeChave).apply();
        sharedPreferences.edit().remove(chaveCheckBox).apply();
        listaChavePix.remove(holder.getAdapterPosition());
        notifyItemRemoved(holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return listaChavePix.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tipoChave;
        TextView chavePix;
        ImageView ivDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tipoChave = itemView.findViewById(R.id.tv_tipo_de_chave_pix);
            chavePix = itemView.findViewById(R.id.tv_chave_pix);
            ivDelete = itemView.findViewById(R.id.iv_delete);
        }
    }
}
