package com.example.futurebankgrupo1;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AccountAPI {

    //https://run.mocky.io/v3/          d6bf6761-2fe4-4b8b-a596-689829441605
    @GET("d6bf6761-2fe4-4b8b-a596-689829441605")
    Call<Account> getAccount();

    //Criar, excluir e retornar dados da conta
    @GET("/account/{accountId}")
    Call<Account> retornarInfoConta(@Path("accountId") int id);

    @DELETE("/account/{accountId}")
    Call<Account> excluirConta(@Path("accountId") int id);

    @POST("/account")
    Call<Account> criarConta();


    //Configurações de notificações
    @GET("/account/notification/{accountId}")
    Call<Account> retornarNotificacoesHabilitadas(@Path("accountId") int id);

    @PUT("/account/notification/{accountId}")
    Call<Account> editarNotificacoes(@Path("accountId") int id);


    //Tipos de contas ************************************** Verificar se precisa de <List<Account>>
    @GET("/account/byUser/{userId}")
    Call<Account> retornarTodasContas(@Path("userId") int User_id);


    //Chaves Pix
    @GET("/account/pix/{accountId}")
    Call<Account> retornarChavesPix(@Path("accountId") int id);


    @POST("/account/pix/{accountId}")
    Call<Account> criarChavePix(@Path("accountId") int id);

}
