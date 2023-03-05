package com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.retrofitUtils;

import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.retrofitData.MuseoRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIRestService {
    public static final String BASE_URL ="https://datos.madrid.es/egob/catalogo/";

    @GET("201132-0-museos.json")
    Call<MuseoRes>obtenerMuseo();

    @GET("201132-0-museos.json")
    Call<MuseoRes>filtroMuseos(
            @Query("distrito_nombre") String distrito
    );
    @GET("tipo/entidadesyorganismos/{museo}")
    Call<MuseoRes> detealles(
            @Path("museo")String museo
    );


}
