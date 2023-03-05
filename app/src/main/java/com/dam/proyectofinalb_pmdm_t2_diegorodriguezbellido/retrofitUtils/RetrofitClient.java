package com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.retrofitUtils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    // Esta clase es la que nos permite crear el objeto retrofit que nos permitira
    // realizar las peticiones a la API.
    private static Retrofit retrofit= null; // Creamos el objeto retrofit como nulo.

    // Este metodo nos permite crear el objeto retrofit.
    public static Retrofit getClient(String baseUrl) {
        // Si el objeto retrofit es nulo, lo creamos.
        if (retrofit==null) {
            retrofit = new Retrofit.Builder() // Creamos el objeto retrofit.
                    .baseUrl(baseUrl) // Le pasamos la url base.
                    .addConverterFactory(GsonConverterFactory.create()) // Le pasamos el convertidor de Gson.
                    .build(); // Creamos el objeto retrofit.
        }
        // Devolvemos el objeto retrofit.
        return retrofit;
    }
}
