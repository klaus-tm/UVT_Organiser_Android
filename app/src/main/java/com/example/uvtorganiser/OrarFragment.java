package com.example.uvtorganiser;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class OrarFragment extends Fragment {
    Button luni, marti, miercuri, joi, vineri, delete;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orar, container, false);

        luni = view.findViewById(R.id.OrarLuni);
        marti = view.findViewById(R.id.OrarMarti);
        miercuri = view.findViewById(R.id.OrarMiercuri);
        joi = view.findViewById(R.id.OrarJoi);
        vineri = view.findViewById(R.id.OrarVineri);
        delete = view.findViewById(R.id.deleteOrar);

        luni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_menuOrar_to_orarLuni);
            }
        });
        marti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_menuOrar_to_orarMarti);
            }
        });
        miercuri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_menuOrar_to_orarMiercuri);
            }
        });
        joi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_menuOrar_to_orarJoi);
            }
        });
        vineri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_menuOrar_to_orarVineri);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(OrarFragment.super.getContext());
                databaseHelper.deleteOrar();
            }
        });
        return view;
    }
}