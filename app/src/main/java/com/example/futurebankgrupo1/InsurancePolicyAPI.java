package com.example.futurebankgrupo1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InsurancePolicyAPI {

    //Cria uma nova apólice para o cartão especificado
    @POST("/policy/{cardId}")
    Call<InsurancePolicy> criarNovaApolice(@Path("cardId") int id);

    //Retorna todas as apolices de seguro de um cartao
    @GET("/policy/{cardId}")
    Call<List<InsurancePolicy>> retornarTodasApolices(@Path("cardId") int id);

    //Retorna uma apolice de seguro pelo id
    @GET("/policy/{policyId}")
    Call<InsurancePolicy> retornarUmaApolicePeloId(@Path("policyId") int id);

}
