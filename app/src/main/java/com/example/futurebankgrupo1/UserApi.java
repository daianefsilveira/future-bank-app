package com.example.futurebankgrupo1;

import com.example.futurebankgrupo1.viacep.ViaCEP;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {

    //Autenticação do usuário - verifica se o cpf e senha estão corretos
    @POST("/auth")
    Call<User> autenticarUser(@Header("User") String cpf, String password);

    //Usuário
    @GET("/user/{userId}")
    Call<User> informacoesUser(@Path("userId") Integer id);

    @PUT("/user/{userId}")
    Call<User> atualizarUser(@Path("userId") Integer id);

    @DELETE("/user/{userId}")
    Call<User> excluirUser(@Path("userId") Integer id);

    /* exemplo do retrofit
    @GET("user")
    Call<User> getUser(@Header("Authorization") String authorization)
     */

    @POST("/user")
    Call<User> cadastrarUser(@Header("User") User user);

    @PUT("/user/password")
    Call<User> atualizarSenhaUser(@Header("User") String password);

    //https://run.mocky.io/v3/b4ccc373-c051-44bc-a44c-4d2cfe7ac12a
//    @GET("b4ccc373-c051-44bc-a44c-4d2cfe7ac12a")
//    Call<User> infoUserMocky();

    @GET("ebd7eec8-da80-454a-a113-3714bf1cdb40")
    Call<User> infoUserMocky();





}
