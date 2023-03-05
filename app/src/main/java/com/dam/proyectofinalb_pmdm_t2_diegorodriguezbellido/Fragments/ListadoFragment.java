package com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    //List<Museo> datosMuseos = new ArrayList<>();
    MuseoRes museoRes;
    // Declaramos el LinearLayoutManager para poder gestionar el RecyclerView.
    LinearLayoutManager llm;

    public ListadoFragment() {
        // Requiere un constructor vacío para instanciar el fragmento.
    }

    public static ListadoFragment newInstance(String distrito) {
        ListadoFragment fragment = new ListadoFragment();
        Bundle args = new Bundle();
        args.putString(MainActivity.DISTRICT, distrito);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            distrito = getArguments().getString(MainActivity.DISTRICT);
        }
        consultarDatosAPI();
        /*if (distrito != null) {
            // Llamamos al método para consultar los datos de la API.

        } else {
            // Si no hay distrito, mostramos un mensaje de error.
            consultarDatosGeneralAPI();
        }*/

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflamos el layout para este fragmento y lo metemos en el View v.
        View v = inflater.inflate(R.layout.fragment_listado, container, false);
        // Inicializamos el RecyclerView.
        rvMuseos = v.findViewById(R.id.rvListadoMuseos);
        /*
        // Inicializamos el LinearLayoutManager.
        llm = new LinearLayoutManager(getContext());
        // Le decimos al RecyclerView que use el LinearLayoutManager.
        rvMuseos.setLayoutManager(llm);
        */
        return v;
    }

    @Override
    public void onClick(View v) {
       /* public  ArrayList<Museo> listaMuseos(String localidad){
            ArrayList<Museo> listaMuseosLocalidad = new ArrayList<>();

            for (int i = 0; i < listaMuseos.size(); i++){
                String url = listaMuseos.get(i).getAddress().getDistrict().getId();
                String[] parts = url.split("/");
                String loc =  parts[parts.length-1];

                if(loc.equalsIgnoreCase(localidad)){
                    listaMuseosLocalidad.add(listaMuseos.get(i));
                }
            }
            return listaMuseosLocalidad;
        }*/
    }

    // Método para consultar los datos de la API y mostrarlos en el RecyclerView.
    private void consultarDatosAPI() {
        Retrofit retrofit = RetrofitClient.getClient(APIRestService.BASE_URL);
        APIRestService apiService = retrofit.create(APIRestService.class);
        // Vamos a inicia el call en null para comprobar si tiene el distrito. (Lo hacemos por recomendación).
        Call<MuseoRes> call = apiService.getMuseoRes();

        // Llamamos al método de la API, con el enqueue para que se ejecute en segundo plano.
        call.enqueue(new Callback<MuseoRes>() {
            // Si responde la API lo que hacemos es recoger los datos y mostrarlos en el RecyclerView.
            @Override
            public void onResponse(Call<MuseoRes> call, Response<MuseoRes> response) {
                // Si la respuesta es correcta, entonces...
                if (response.isSuccessful()) {
                    // Creamos un objeto de la clase MuseoRes para poder acceder a los datos.
                    // Con el response.body() obtenemos el cuerpo de la respuesta.
                    museoRes = response.body();
                    assert museoRes != null;
                    // Llamamos al método para cargar los datos en el RecyclerView.
                    cargarDatosRv(museoRes.getMuseo());
                } else {
                    // Si no es correcta la respuesta, mostramos un mensaje de error.
                    Snackbar.make(requireView(), "No es succesful", Snackbar.LENGTH_LONG).show();
                }
            }
            // Si no responde la API lo que hacemos es mostrar un mensaje de error.
            @Override
            public void onFailure(Call<MuseoRes> call, @NonNull Throwable t) {
                // Mostramos un mensaje de error. (Lo hacemos con el requireView() para que no de error ya que me lo recomienda el AndroidStudio).
                Snackbar.make(requireView(), R.string.call_onFailure_msg_error, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    // Método para cargar los datos en el RecyclerView y mostrarlos.
    private void cargarDatosRv(List<Museo> museoInfo) {
        // El llm es necesario para poder mostrar los datos en el RecyclerView.
        llm = new LinearLayoutManager(getActivity());
        // El adapter es necesario para poder mostrar los datos en el RecyclerView.
        // me exige castear el datosMuseos a ArrayList<Museo>.
        adapter = new MuseoAdapter(museoInfo, this);
        // Con el setHasFixedSize(true) le decimos que el tamaño del RecyclerView no va a cambiar.
        rvMuseos.setHasFixedSize(true);
        // Con el setLayoutManager(llm) le decimos que el RecyclerView va a tener un layout lineal.
        rvMuseos.setLayoutManager(llm);
        // Con el setAdapter(adapter) le decimos que el RecyclerView va a tener un adapter.
        rvMuseos.setAdapter(adapter);
        System.out.println("===================================");
    }

}