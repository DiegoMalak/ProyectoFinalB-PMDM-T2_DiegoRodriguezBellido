package com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.Fragments.InicioFragment;
import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.Fragments.ListadoFragment;
import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.dialogFragment.FiltroFragment;
import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.dialogFragment.OnFiltroListener;
import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.retrofitData.Address;
import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.retrofitData.Area;
import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.retrofitData.District;
import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.retrofitData.Location;
import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.retrofitData.Museo;
import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.retrofitData.Organization;
import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.rvUtil.MuseoAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements OnFiltroListener {

    public static final String DISTRICT = "DISTRICT";

    // Declaramos los elementos de la vista.
    Button btnFiltro; // Botón para mostrar el diálogo y seleccionar el filtro.
    Button btnConsul; // Botón para consultar la API y que nos devuelva los museos del distrito seleccionado.
    TextView tvLocalidadSelec; // TextView de titulo para mostrar la localidad seleccionada.

    // Declaramos las variables.
    String distrito; // Variable para guardar la localidad seleccionada en el diálogo.
    ArrayList<Museo> listaMuseos; // Lista para guardar los museos que nos devuelve la API.

    RecyclerView  rv;

    MuseoAdapter museoAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //conectarApi();

        //ArrayList<Museo>  museosLocalidad = listaMuseos("Centro");
       // museoAdapter = new MuseoAdapter(museosLocalidad);
       // rv.setAdapter(museoAdapter);

        // Inicializamos los elementos de la vista.
        btnFiltro = findViewById(R.id.btnSeleccionarFiltro); // Botón para mostrar el diálogo y seleccionar el filtro.
        btnConsul = findViewById(R.id.btnConsultarListado); // Botón para consultar la API y que nos devuelva los museos del distrito seleccionado.
        tvLocalidadSelec = findViewById(R.id.tvFiltroSeleccionado); // TextView de titulo para mostrar la localidad seleccionada.
        rv = findViewById(R.id.rvListadoMuseos);
        // Añadimos el listener al botón para darle funcionalidad.
        btnFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialog();
            }
        });
        btnConsul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.flContenedor, ListadoFragment.newInstance(distrito));
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        //replaceFragment(InicioFragment.newInstance());
    }

    // Este es el fragmento que va a gestionar el cambio de fragmentos.
    /*private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContenedor, fragment)
                .addToBackStack(null)
                .commit();
    }*/

    /*private void fragmentManager() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.flContenedor, new InicioFragment());
        ft.addToBackStack(null);
        ft.commit();
    }*/

    // Metodo para crear el menú.
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflamos el menú para que se muestre en la barra de opciones.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Añadimos @SuppressLint("NonConstantResourceId") para que no nos de error al usar el switch.
    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuListado:
                //replaceFragment(ListadoFragment.newInstance(distrito));
                break;
            case R.id.menuMapa:
                //replaceFragment(MapaFragment.newInstance(tam));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Método para mostrar el diálogo de selección de filtro.
    public void mostrarDialog(){
        // Creamos el diálogo.
        FiltroFragment df = new FiltroFragment();
        // Si se pulsa atras se cierra el diálogo.
        df.setCancelable(false);
        // Mostramos el diálogo con el método show.
        df.show(getSupportFragmentManager(), "FiltroFragment");
    }

    // Método para coger la localidad seleccionada en el diálogo y mostrarla en el TextView titulo.
    @Override
    public void onFiltro(String distrito) {
        // Traza de control con un sout, que nos muestra la localidad seleccionada.
        System.out.println("Distrito: " + distrito);
        // Asignamos la localidad seleccionada a la variable localidad.
        this.distrito = distrito;
        // Mostramos la localidad seleccionada en el TextView.
        tvLocalidadSelec.setText(distrito);
    }

}