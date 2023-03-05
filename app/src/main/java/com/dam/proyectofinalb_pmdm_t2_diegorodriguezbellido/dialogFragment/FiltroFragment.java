package com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.dialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.R;

public class FiltroFragment extends DialogFragment {
    // Declaramos la clase OnFiltroListener para poder comunicar el dialogo.
    OnFiltroListener listener;

    // Declaramos los elementos de la vista.
    Spinner spnFiltroLoc;

    // Método que se ejecuta cuando se crea el dialogo.
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Usamos el inflater para crear la vista.
        View v = getActivity().getLayoutInflater().inflate(R.layout.filtro_dialog, null);

        spnFiltroLoc = v.findViewById(R.id.spFiltroLocalidad);

        // Construimos el diálogo que retorna el método.
        // El AlertDialog que se importa es el androidx.appcompat.app.AlertDialog. (el segundo que sale)
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);

        // En el botón de aceptar, se comunicarán los datos introducidos con el Activity Main.
        // En el botón de cancelar, cerramos el diálogo.
        builder.setTitle(R.string.titulo_filtro_dialog)
                .setPositiveButton(R.string.btn_aceptar, new DialogInterface.OnClickListener() {
                    // Este es el método que se ejecuta cuando se pulsa el botón de aceptar.
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Cogemos los datos introducidos.
                        String localidad = spnFiltroLoc.getSelectedItem().toString();
                        // Le pasamos los datos al Activity Main con el método onFiltro de la interfaz FiltroListener.
                        listener.onFiltro(localidad);
                    }
                })
                .setNegativeButton(R.string.btn_cancelar, new DialogInterface.OnClickListener() {
                    // Este es el método que se ejecuta cuando se pulsa el botón de cancelar.
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Cerramos el diálogo.
                        dialogInterface.dismiss();
                    }
                });

        // Creamos el AlertDialog, que es el que se va a mostrar.
        AlertDialog ad = builder.create();
        ad.setCanceledOnTouchOutside(false);
        ad.setCancelable(false);
        // Retornamos el AlertDialog.
        return ad;
    }

    // Método que se ejecuta cuando se crea el dialogo.
    public void onAttach(@NonNull android.app.Activity activity) {
        // No olvidar llamar al metodo de la superclase.
        super.onAttach(activity);
        // Comprobamos que el contexto implementa la interfaz.
        // Si no implementa la interfaz, lanzamos una excepción.
        // Si implementa la interfaz, la asignamos a la variable listener.
        listener = (OnFiltroListener) activity;
    }

    // El método onDetach se ejecuta cuando el fragmento se desata de su actividad.
    // En este método, ponemos la variable listener a null para que no se quede con una referencia
    // al contexto.
    public void onDetach() {
        // Si la variable listener no es null, la ponemos a null.
        if (listener != null) {
            listener = null;
        }
        // No olvidar llamar al método de la superclase.
        super.onDetach();
    }
}
