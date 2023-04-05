package com.example.uvtorganiser;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AddDiscipline extends Fragment {
    Button addDisciplina;
    EditText numeDisciplina, salaCurs, salaSeminar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_discipline, container, false);

        addDisciplina = view.findViewById(R.id.addDisciplina);
        numeDisciplina = view.findViewById(R.id.NumeDisciplina);
        salaCurs = view.findViewById(R.id.SalaCurs);
        salaSeminar = view.findViewById(R.id.SalaSeminar);
        addDisciplina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(AddDiscipline.super.getContext());
                db.addDisciplina(numeDisciplina.getText().toString().trim(), salaCurs.getText().toString().trim(), salaSeminar.getText().toString());
                Navigation.findNavController(view).navigate(R.id.action_addDiscipline_to_menuDiscipline);
            }
        });
        return view;
    }
}