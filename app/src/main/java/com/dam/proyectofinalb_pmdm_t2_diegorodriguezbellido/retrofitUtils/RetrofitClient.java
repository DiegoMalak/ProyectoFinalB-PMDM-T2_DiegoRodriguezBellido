package com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.retrofitUtils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    // Esto es un singleton que hace que devuelva el mismo cliente de Retrofit durante
    // toda la ejecución de la aplicación.
    public static Retrofit getClient(String baseUrl) {
        // Si no existe el cliente de Retrofit, lo creamos.
        if (retrofit == null) {
            // Creamos el cliente de Retrofit.
            retrofit = new Retrofit.Builder() // El Builder es el constructor de Retrofit.
                                    .baseUrl(baseUrl) // Indicamos la URL base de la API que se usará para las llamadas.
                                    .addConverterFactory(GsonConverterFactory.create()) // Indicamos el conversor de JSON a Java.
                                    .build(); // Creamos el cliente de Retrofit.
        }
        return retrofit;
    }
}
