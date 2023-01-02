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

public class AdapterCompra extends RecyclerView.Adapter<AdapterCompra.MyViewHolder> {

    private Context mContext;
    private List<Compra> listaCompras;

    public AdapterCompra(Context mContext, List<Compra> lista){
        this.mContext = mContext;
        this.listaCompras = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(mContext).inflate(R.layout.adapter_lista_compra, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemLista);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holderFatura, int position) {

        final Compra compra = listaCompras.get(position);
        holderFatura.creditoDebito.setText(compra.getTransacaoCreditoDebito());
        holderFatura.valor.setText(compra.getValor());
        holderFatura.data.setText(compra.getData());

        switch (compra.getTransacaoCreditoDebito()){
            case "Recarga de celular":
                holderFatura.imagem.setImageResource(R.drawable.ic_smartphone);
                break;
            case "Pagamento fatura do cartão":
                holderFatura.imagem.setImageResource(R.drawable.icon_config_card);
                break;
            default:
                holderFatura.imagem.setImageResource(R.drawable.ic__money_vermelho);
        }

    }

    @Override
    public int getItemCount() {
        return listaCompras.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView creditoDebito;
        TextView valor;
        TextView data;
        ImageView imagem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            creditoDebito = itemView.findViewById(R.id.tv_compra);
            valor = itemView.findViewById(R.id.tv_valor);
            data = itemView.findViewById(R.id.tv_data);
            imagem = itemView.findViewById(R.id.iv_imagem);

        }
    }
}
