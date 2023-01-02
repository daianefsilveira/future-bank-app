package com.example.futurebankgrupo1;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TransactionAPI {

    //Pix
    @GET("/transaction/pix/{transactionId}")
    Call<Transaction> retornarDadosTransacao(@Path("transactionId") int id);

    @POST("/transaction/pix")
    Call<Transaction> criarTrasacaoPix();

    //Transferencia contas
    @POST("/transaction/transfer")
    Call<Transaction> criarTransacaoTransferencia();

    //Pix Copia e Cola
    @GET("/transaction/pix/copyAndPast/{transactionId}")
    Call<Transaction> retornarDadosCobrancaPix(@Path("transactionId") int id);

    @POST("/transaction/pix/copyAndPast")
    Call<Transaction> criarCobrancaPix();
}
