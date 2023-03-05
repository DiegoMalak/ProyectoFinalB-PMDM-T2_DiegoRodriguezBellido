package com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.Fragments.ListadoFragment;
import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.dialogFragment.FiltroFragment;
import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.dialogFragment.OnFiltroListener;
import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.retrofitData.Museo;
import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.rvUtil.MuseoAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnFiltroListener {

    public static final String DISTRICT = "DISTRICT"; // Constante para pasar el distrito seleccionado en el diálogo.

    // Declaramos los elementos de la vista.
    Button btnFiltro; // Botón para mostrar el diálogo y seleccionar el filtro.
    Button btnConsul; // Botón para consultar la API y que nos devuelva los museos del distrito seleccionado.
    TextView tvLocalidadSelec; // TextView de titulo para mostrar la localidad seleccionada.

    // Declaramos las variables.
    String distrito; // Variable para guardar la localidad seleccionada en el diálogo.
    ArrayList<Museo> listaMuseos; // Lista para guardar los museos que nos devuelve la API.

    // Declaramos el RecyclerView.
    RecyclerView  rv;

    // Declaramos el adapter.
    MuseoAdapter museoAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializamos los elementos de la vista.
        btnFiltro = findViewById(R.id.btnSeleccionarFiltro); // Botón para mostrar el diálogo y seleccionar el filtro.
        btnConsul = findViewById(R.id.btnConsultarListado); // Botón para consultar la API y que nos devuelva los museos del distrito seleccionado.
        tvLocalidadSelec = findViewById(R.id.tvFiltroSeleccionado); // TextView de titulo para mostrar la localidad seleccionada.
        //rv = findViewById(R.id.rvListadoMuseos);
        // Añadimos el listener al botón para darle funcionalidad.
        // setOnClickListener para el botón de seleccionar filtro y darle la funcionalidad de mostrar el diálogo.
        btnFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llamamos al método para mostrar el diálogo y seleccionar el filtro.
                mostrarDialog();
            }
        });
        // setOnClickListener para el botón de consultar y darle la funcionalidad de mostrar el listado de museos.
        btnConsul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llamamos al método para mostrar el listado de museos en el fragment.
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.flContenedor, ListadoFragment.newInstance(tvLocalidadSelec.getText().toString())); // Pasamos el distrito seleccionado.
                // Lo cogemos del TextView tvLocalidadSelec ya que lo hemos configurado para que se muestre la localidad seleccionada.
                ft.addToBackStack(null); // Añadimos el fragment a la pila de fragmentos.
                ft.commit(); // Comit para que se ejecute la transacción.
            }
        });
    }

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
        // Creamos un switch para que dependiendo de la opción que se seleccione se ejecute un fragment.
        switch (item.getItemId()) {
            // Si se selecciona la opción de listado se ejecuta el fragment de listado.
            case R.id.menuListado:
                //replaceFragment(ListadoFragment.newInstance(distrito));
                break;
            // Si se selecciona la opción de mapa se ejecuta el fragment de mapa.
            case R.id.menuMapa:
                Intent i = new Intent(this, MapaActivity.class);
                startActivity(i);
                break;
            default:
                break;
        }
        // Devolvemos el resultado.
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