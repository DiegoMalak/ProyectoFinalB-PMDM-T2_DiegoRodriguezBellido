package com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.R;

// Este es un fragmento inicio para gestionar los otros dos fragments.
public class InicioFragment extends Fragment {

    public InicioFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new InicioFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }
}