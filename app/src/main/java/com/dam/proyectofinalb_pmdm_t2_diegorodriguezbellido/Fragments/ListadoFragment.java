package com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.DetalleMuseoActivity;
import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.MainActivity;
import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.R;
import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.retrofitData.Museo;
import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.retrofitData.MuseoRes;
import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.retrofitUtils.APIRestService;
import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.retrofitUtils.RetrofitClient;
import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.rvUtil.MuseoAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListadoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListadoFragment extends Fragment implements View.OnClickListener {

    // Creamos la variable que vamos a traer del mainActivity.
    private String distrito;
    // Declaramos el RecyclerView para poder meterle los datos.
    RecyclerView rvMuseos;
    // Declaramos el adapter para poder gestionar el RecyclerView.
    MuseoAdapter adapter;
    // Creamos el arrayList de museos que vamos a mostrar en el RecyclerView.
    //List<Museo> museos;
    MuseoRes museoRes;
    // Declaramos el LinearLayoutManager para poder gestionar el RecyclerView.
    LinearLayoutManager llm;

    // Método para consultar todos los datos de la API.
    public ListadoFragment() {
        // Requiere un constructor vacío para instanciar el fragmento.
    }

    // Método para crear una nueva instancia del fragmento.
    public static ListadoFragment newInstance(String distrito) {
        // Creamos una nueva instancia del fragmento.
        ListadoFragment fragment = new ListadoFragment();
        // Creamos un Bundle para poder pasarle el parámetro al fragmento.
        Bundle args = new Bundle();
        // Añadimos el parámetro al Bundle. Le ponemos la palabra clave DISTRICT.
        args.putString(MainActivity.DISTRICT, distrito);
        // Asignamos el Bundle al fragmento.
        fragment.setArguments(args);
        // Devolvemos el fragmento.
        return fragment;
    }

    // Método para crear la vista del fragmento.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflamos la vista del fragmento, para que se muestre en el MainActivity.
        View view = inflater.inflate(R.layout.fragment_listado, container, false);
        // Asignamos el RecyclerView a la variable.
        rvMuseos = view.findViewById(R.id.rvListadoMuseos);
        return view;
    }

    // Método para crear la actividad del fragmento.
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Comprobamos si el fragmento tiene argumentos.
        if (getArguments() != null) {
            //Asiganamos valor a la variable flitro con lo que recibimos del main
            //gracias al ala palabra calve FILTRO
            distrito = getArguments().getString(MainActivity.DISTRICT);
        }
        // Llamamos al método para consultar los datos de la API.
        if (distrito.equals("")) {
            // Si el filtro es vacío, entonces llamamos al método para consultar todos los datos.
            consultarDatosApiTodos();
        } else {
            // Si el filtro no es vacío, entonces llamamos al método para consultar los datos filtrados.
            consultarDatosAPI(distrito);
            consultarDatosAPI(distrito);
        }
    }

    // Método para consultar los datos de la API y mostrarlos en el RecyclerView.
    private void consultarDatosAPI(String distrito) {
        // Creamos el objeto retrofit para poder hacer la llamada a la API.
        Retrofit retrofit = RetrofitClient.getClient(APIRestService.BASE_URL);
        // Creamos el objeto de la interfaz APIRestService para poder hacer la llamada a la API.
        APIRestService apiService = retrofit.create(APIRestService.class);
        // Vamos a inicia el call en null para comprobar si tiene el distrito. (Lo hacemos por recomendación).
        Call<MuseoRes> call = apiService.filtroMuseos(this.distrito);

        // Llamamos al método de la API, con el enqueue para que se ejecute en segundo plano.
        System.out.println("=============> INICIAMOS LA CALL <=============");
        call.enqueue(new Callback<MuseoRes>() {
            // Si responde la API lo que hacemos es recoger los datos y mostrarlos en el RecyclerView.
            @Override
            public void onResponse(Call<MuseoRes> call, Response<MuseoRes> response) {
                // Si la respuesta es correcta, entonces...
                if (response.isSuccessful()) {
                    // Creamos un objeto de la clase MuseoRes para poder acceder a los datos.
                    // Con el response.body() obtenemos el cuerpo de la respuesta.
                    System.out.println("=============> RESPUESTA CORRECTA <=============");
                    museoRes = response.body();
                    // Comprobamos que el objeto no sea nulo para evitar errores.
                    // Si fuera nulo, el programa se cerraría.
                    assert museoRes != null;
                    // Llamamos al método para cargar los datos en el RecyclerView.
                    cargarDatosRv(museoRes.getMuseo());
                } else {
                    // Si no es correcta la respuesta, mostramos un mensaje de error.
                    Snackbar.make(requireView(), "No es succesful", Snackbar.LENGTH_LONG).show();
                }
            }

            // Método para cargar los datos en el RecyclerView y mostrarlos.
            private void cargarDatosRv(List<Museo> museoInfo) {
                // El llm es necesario para poder mostrar los datos en el RecyclerView.
                llm = new LinearLayoutManager(getActivity());
                // El adapter es necesario para poder mostrar los datos en el RecyclerView.
                // me exige castear el datosMuseos a ArrayList<Museo>.
                adapter = new MuseoAdapter((ArrayList<Museo>) museoInfo);
                adapter.setOnclickListener(ListadoFragment.this);
                // Con el setHasFixedSize(true) le decimos que el tamaño del RecyclerView no va a cambiar.
                rvMuseos.setHasFixedSize(true);
                // Con el setLayoutManager(llm) le decimos que el RecyclerView va a tener un layout lineal.
                rvMuseos.setLayoutManager(llm);
                // Con el setAdapter(adapter) le decimos que el RecyclerView va a tener un adapter.
                rvMuseos.setAdapter(adapter);
                System.out.println("===================================> RV cargado");
            }
            // Si no responde la API lo que hacemos es mostrar un mensaje de error.
            @Override
            public void onFailure(Call<MuseoRes> call, @NonNull Throwable t) {
                // Mostramos un mensaje de error. (Lo hacemos con el requireView() para que no de error ya que me lo recomienda el AndroidStudio).
                Snackbar.make(requireView(), R.string.call_onFailure_msg_error, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    // Método para consultar los datos de la API y mostrarlos en el RecyclerView,
    // cuando el filtro es vacío (todos los datos).
    private void consultarDatosApiTodos() {
        // Creamos el objeto retrofit para poder hacer la llamada a la API.
        Retrofit r = RetrofitClient.getClient(APIRestService.BASE_URL);
        // Creamos el objeto de la interfaz APIRestService para poder hacer la llamada a la API.
        APIRestService ars = r.create(APIRestService.class);
        // Vamos a inicia el call en null para comprobar si tiene el distrito. (Lo hacemos por recomendación).
        Call<MuseoRes> call = ars.obtenerMuseo();
        call.enqueue(new Callback<MuseoRes>() {
            @Override
            public void onResponse(@NonNull Call<MuseoRes> call, @NonNull Response<MuseoRes> response) {
                // Comprobamos que la respuesta sea correcta, si lo es lo que hacemos es recoger
                // los datos y mostrarlos en el RecyclerView.
                if (response.isSuccessful()) {
                    // Creamos un objeto de la clase MuseoRes para poder acceder a los datos.
                    museoRes = response.body();
                    // Comprobamos que el objeto no sea nulo para evitar errores.
                    // Si fuera nulo, el programa se cerraría.
                    assert museoRes != null;
                    // Llamamos al método para cargar los datos en el RecyclerView y le pasamos los datos.
                    cargarRV(museoRes.getMuseo());
                } else {
                    // Si no es correcta la respuesta, mostramos un mensaje de error.
                    Snackbar.make(requireView(), R.string.error_consultar_todos_datos, Snackbar.LENGTH_LONG).show();
                }
            }

            // Si no responde la API lo que hacemos es mostrar un mensaje de error.
            @Override
            public void onFailure(Call<MuseoRes> call, Throwable t) {
                // Mostramos un mensaje de error. (Lo hacemos con el requireView() para que no de error
                // ya que me lo recomienda el AndroidStudio).
                Snackbar.make(requireView(), R.string.fallo_onFailure_call, Snackbar.LENGTH_LONG).show();
            }

            // Método para cargar los datos en el RecyclerView y mostrarlos.
            private void cargarRV(List<Museo> museos) {
                // El llm es necesario para poder mostrar los datos en el RecyclerView.
                llm = new LinearLayoutManager(getActivity());
                // El adapter es necesario para poder mostrar los datos en el RecyclerView.
                adapter = new MuseoAdapter((ArrayList<Museo>) museos); // me exige castear el datosMuseos a ArrayList<Museo>.
                // Le ponemos el setOnclickListener para que cuando cliquemos en un item del RecyclerView
                // nos lleve a la actividad de detalles.
                adapter.setOnclickListener(ListadoFragment.this);
                // Con el setHasFixedSize(true) le decimos que el tamaño del RecyclerView no va a cambiar.
                rvMuseos.setHasFixedSize(true);
                // Con el setLayoutManager(llm) le decimos que el RecyclerView va a tener un layout lineal.
                rvMuseos.setLayoutManager(llm);
                // Con el setAdapter(adapter) le decimos que el RecyclerView va a tener un adapter.
                rvMuseos.setAdapter(adapter);
            }
        });
    }

    // Método para cuando cliquemos en un item del RecyclerView.
    @Override
    public void onClick(View v) {
        // Guardamos la posicion del item que cliquemos.
        int pos = rvMuseos.getChildAdapterPosition(v); // Le pasamos la vista para que nos devuelva la posicion.
        // Guardamos el id del item que cliquemos para pasarlo a la actividad de detalles.
        String id = museoRes.getMuseo().get(pos).getId(); // Le pasamos la posicion para que nos devuelva el id.
        // Separamos el id mediante el split para quedarnos solo con el id.
        String[] partes = id.split("/"); // Hay que pasarle un separador.
        // Guardamos el id en una variable y le indicamos que es la posicion 7 del array
        // la que queremos guardar.
        String idurl = partes[7];
        // Creamos un intent para pasar de la actividad de listado a la actividad de detalles.
        Intent i = new Intent(getActivity(), DetalleMuseoActivity.class);
        // Le pasamos el id a la actividad de detalles para que muestre los datos del museo
        // que corresponda con el id.
        i.putExtra("ID", idurl);
        // Iniciamos la actividad de detalles.
        startActivity(i);
    }
}