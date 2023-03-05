package com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.rvUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.R;
import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.retrofitData.Museo;

import java.util.ArrayList;
import java.util.List;

public class MuseoAdapter extends RecyclerView.Adapter<MuseoAdapter.MuseoVH> implements View.OnClickListener {

    // Ponemos el ArrayList de Museos que vamos a mostrar en el RecyclerView.
    private List<Museo> datosMuseos;

    // Creamos una variable para guardar el listener.
    private View.OnClickListener listener;

    // Creamos un constructor para inicializar el ArrayList.
    public MuseoAdapter(List<Museo> datosMuseos, View.OnClickListener listener) {
        this.datosMuseos = datosMuseos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MuseoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Creamos una vista a partir del layout mostrar_museo_layout.
        // Para ello, necesitamos un LayoutInflater, que es el que se encarga de crear
        // las vistas a partir de los layouts.
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mostrar_museo_layout, parent, false);
        // Asignamos el listener a la vista.
        v.setOnClickListener(this);
        // Creamos un objeto MuseoVH y le pasamos la vista.
        MuseoVH vh = new MuseoVH(v);
        // Devolvemos el objeto MuseoVH.
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MuseoVH holder, int position) {
        // Obtenemos el elemento de la lista de museos.
        holder.bindMuseo(datosMuseos.get(position));
    }

    @Override
    public int getItemCount() {
        // Devolvemos el tamaño de la lista de museos.
        System.out.println("Tamaño de la lista: " + datosMuseos);
        return datosMuseos.size();
    }

    @Override
    public void onClick(View v) {
        // Si el listener no es nulo, ejecutamos el método onClick().
        if (listener != null) {
            listener.onClick(v);
        }
    }

    // Creamos una clase interna para crear el ViewHolder.
    public static class MuseoVH extends RecyclerView.ViewHolder {

        // Declaramos los elementos que vamos a mostrar en el RecyclerView.
        private TextView tvNomMusFilt;

        // Creamos un constructor para inicializar los elementos.
        public MuseoVH(@NonNull View itemView) {
            super(itemView);
            // Inicializamos los elementos que vamos a mostrar en el RecyclerView.
            tvNomMusFilt = itemView.findViewById(R.id.tvNombreMuseoFiltrado);
        }

        // Creamos un método que nos permita enlazar los datos con los atributos de la clase.
        public void bindMuseo(Museo m) {
            // Volcamos los datos en los elementos, en este caso, en el TextView.
            // Sacamos el título del museo porque es el que nos muestra el nombre del museo.
            tvNomMusFilt.setText(m.getTitle());
        }

    }
}
