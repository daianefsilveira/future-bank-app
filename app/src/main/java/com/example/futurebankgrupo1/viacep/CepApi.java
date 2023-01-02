package com.example.futurebankgrupo1.viacep;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CepApi {

    @GET("{cep}/json/")
    Call<ViaCEP> consultarCEP(@Path("cep") String cep);
}
