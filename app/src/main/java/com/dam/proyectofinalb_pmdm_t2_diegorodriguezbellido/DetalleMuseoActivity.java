package com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.retrofitData.MuseoRes;
import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.retrofitUtils.APIRestService;
import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.retrofitUtils.RetrofitClient;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetalleMuseoActivity extends AppCompatActivity {

    TextView  tvDetallesTituloMuseo;
    TextView tvDetallesDistritoMuseoVacio;
    TextView tvDetallesAreaMuseoVacio;
    TextView tvDetallesDireccionMuseoVacio;
    TextView tvDetallesDescripcionMuseoVacio;

    MuseoRes museoRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_museo);

        // Inicializamos las variables.
        tvDetallesTituloMuseo = findViewById(R.id.tvDetallesTituloMuseo);
        tvDetallesDistritoMuseoVacio = findViewById(R.id.tvDetallesDistritoMuseoVacio);
        tvDetallesAreaMuseoVacio = findViewById(R.id.tvDetallesAreaMuseoVacio);
        tvDetallesDireccionMuseoVacio = findViewById(R.id.tvDetallesDireccionMuseoVacio);
        tvDetallesDescripcionMuseoVacio = findViewById(R.id.tvDetallesDescripcionMuseoVacio);

        // Recogemos el objeto Museo que nos pasan desde la actividad anterior.
        String ID = getIntent().getStringExtra("ID");

        // Llamamos al método que nos va a traer los datos de la API.
        consultarDatosApiDetallado(ID);
    }

    private void consultarDatosApiDetallado(String id) {
        // Creamos el objeto Retrofit para poder hacer la llamada a la API.
        Retrofit r = RetrofitClient.getClient(APIRestService.BASE_URL);
        // Creamos el objeto APIRestService para poder hacer la llamada a la API.
        APIRestService ars = r.create(APIRestService.class);
        // Creamos el objeto Call para poder hacer la llamada a la API.
        Call<MuseoRes> call = ars.detealles(id);
        // Hacemos la llamada a la API.
        call.enqueue(new Callback<MuseoRes>() {
            @Override
            public void onResponse(@NonNull Call<MuseoRes> call, @NonNull Response<MuseoRes> response) {
                // Comprobamos que la respuesta es correcta y si es así, recogemos los datos.
                if (response.isSuccessful()) {
                    // Recogemos los datos del objeto MuseoRes que nos devuelve la API.
                    museoRes = response.body();
                    // Comprobamos que el objeto no sea nulo y si es así, recogemos los datos.
                    // En caso de que sea nulo, mostramos un mensaje de error y salimos de la aplicación.
                    assert museoRes != null;

                    // Recogemos los datos del objeto Museo que nos devuelve la API.
                    // Para el distrito, recogemos el último valor del array que nos devuelve la API.
                    String distritoMuseo = museoRes.getMuseo().get(0).getAddress().getDistrict().getId(); // Recogemos el distrito.
                    String[] distritoMuseoArray = distritoMuseo.split("/"); // Separamos el distrito en un array.
                    String distritoMuseoFinal = distritoMuseoArray[distritoMuseoArray.length - 1]; // Recogemos el último valor del array.
                    // Con estas tres líneas de código, recogemos el último valor del array que nos devuelve la API para el distrito.

                    // Para el área, recogemos el último y el penúltimo valor del array que nos devuelve la API.
                    String areaMuseo = museoRes.getMuseo().get(0).getAddress().getArea().getId(); // Recogemos el área.
                    String[] areaMuseoArray = areaMuseo.split("/"); // Separamos el área en un array.
                    String areaMuseoFinal = areaMuseoArray[areaMuseoArray.length - 1]; // Recogemos el último valor del array.
                    String areaMuseoFinal1 = areaMuseoArray[areaMuseoArray.length - 2]; // Recogemos el penúltimo valor del array.
                    String areaFinal = areaMuseoFinal1 + " - " + areaMuseoFinal; // Concatenamos los dos valores.
                    // Con estas cinco líneas de código, recogemos el último y el penúltimo valor del array que nos devuelve la API para el área.
                    // Y por último, concatenamos los dos valores para mostrarlos en el TextView.

                    // Traza para comprobar que los datos se recogen correctamente.
                    System.out.println("MUSEO: " + museoRes.getMuseo().get(0).getTitle());
                    // Le asignamos los valores a los TextView.
                    tvDetallesTituloMuseo.setText(museoRes.getMuseo().get(0).getTitle()); // Asignamos el título del museo.
                    tvDetallesDistritoMuseoVacio.setText(distritoMuseoFinal); // Asignamos el distrito del museo.
                    tvDetallesAreaMuseoVacio.setText(areaFinal); // Asignamos el área del museo.
                    tvDetallesDireccionMuseoVacio.setText(museoRes.getMuseo().get(0).getAddress().getStreetAddress()); // Asignamos la dirección del museo.
                    tvDetallesDescripcionMuseoVacio.setText(museoRes.getMuseo().get(0).getOrganization().getOrganizationDesc()); // Asignamos la descripción del museo.
                } else {
                    // En caso de que la respuesta no sea correcta, mostramos un mensaje de error.
                    Snackbar.make(findViewById(android.R.id.content), R.string.error_consultar_datos_api, Snackbar.LENGTH_LONG).show();
                }
            }

            // En caso de que la llamada a la API falle, mostramos un mensaje de error.
            @Override
            public void onFailure(Call<MuseoRes> call, Throwable t){
                // Le mostramos un mensaje de error al usuario con un Snackbar.
                Snackbar.make(findViewById(android.R.id.content), R.string.llamada_api_falla, Snackbar.LENGTH_LONG).show();
            }
        });
    }

}