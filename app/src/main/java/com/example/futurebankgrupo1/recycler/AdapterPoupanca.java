package com.example.futurebankgrupo1.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.futurebankgrupo1.R;

import java.util.List;

public class AdapterPoupanca extends RecyclerView.Adapter<AdapterPoupanca.MyViewHolder> {

    private List<RecyclerPoupanca> listaRecyclerPoupancas;
    private Context mContext;


    public AdapterPoupanca(Context mContext, List<RecyclerPoupanca> lista) {
        this.mContext = mContext;
        this.listaRecyclerPoupancas = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(mContext).inflate(R.layout.adapter_lista_poupanca, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemLista);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        RecyclerPoupanca recyclerPoupanca = listaRecyclerPoupancas.get(position);

        holder.guardado.setText(recyclerPoupanca.getTransacaoCreditoDebito());
        holder.data.setText(recyclerPoupanca.getData());
        holder.valor.setText(recyclerPoupanca.getValor());
        switch (recyclerPoupanca.getTransacaoCreditoDebito()){
            case "Transferência recebida":
            case "Aplicação na poupança":
                holder.imagem.setImageResource(R.drawable.ic_money_verde);
                break;
            case "Transferência enviada":
            default:
                holder.imagem.setImageResource(R.drawable.ic__money_vermelho);
        }

    }

    @Override
    public int getItemCount() {
        return listaRecyclerPoupancas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView guardado;
        TextView valor;
        TextView data;
        ImageView imagem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            guardado = itemView.findViewById(R.id.tv_guardado_resgatado);
            valor = itemView.findViewById(R.id.tv_valor);
            data = itemView.findViewById(R.id.tv_data);
            imagem = itemView.findViewById(R.id.iv_imagem);
        }
    }
}
