package com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.retrofitUtils;

import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.retrofitData.MuseoRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIRestService {

    public static final String API_KEY = "201132-0-museos.json";
    // Ponemos la URL base de la API, que es desde donde se obtendrán los datos.
    public static final String BASE_URL = "https://datos.madrid.es/egob/catalogo/";

    // Y en el GET le indicamos la 'clave' de la API que queremos usar.
    @GET("201132-0-museos.json")
    Call<MuseoRes> getMuseoRes();

    @GET("201132-0-museos.json")
    Call<MuseoRes> getMuseoDistrict(
            @Query("district_name") String distrito
    );



}
