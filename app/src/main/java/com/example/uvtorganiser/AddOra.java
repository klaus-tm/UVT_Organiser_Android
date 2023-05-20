package com.example.uvtorganiser;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;

public class AddOra extends Fragment {
    String zi;
    AutoCompleteTextView autoCompleteTextViewProfesor, autoCompleteTextViewDisciplina, autoCompleteTextViewSala, autoCompleteTextViewOra;
    ArrayList<String> profesori, discipline, sali, ore = new ArrayList<String>(){{
        add("8:00-9:30"); add("9:40-11:10"); add("11:20-12:50"); add("13:00-14:30"); add("14:40-16:10"); add("16:20-17:50"); add("18:00-19:30"); add("19:40-21:10");
    }};
    ArrayAdapter<String> arrayAdapterProfesor, arrayAdapterDisciplina, arrayAdapterSala, arrayAdapterOra;
    Button addOra, back;
    DatabaseHelper db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_ora, container, false);
        Bundle arguments = getArguments();
        if (arguments!=null)
            zi = arguments.getString("ziua din orar");
        db = new DatabaseHelper(AddOra.super.getContext());
        profesori = new ArrayList<>();
        discipline = new ArrayList<>();
        sali = new ArrayList<>();

        autoCompleteTextViewProfesor = view.findViewById(R.id.auto_complete_Profesori);
        autoCompleteTextViewDisciplina = view.findViewById(R.id.auto_complete_Discipline);
        autoCompleteTextViewOra = view.findViewById(R.id.auto_complete_Ora);
        autoCompleteTextViewSala = view.findViewById(R.id.auto_complete_Sala);

        storeDataIntoArraysAdd();

        arrayAdapterProfesor = new ArrayAdapter<String>(AddOra.super.getContext(), R.layout.list_item, profesori);
        arrayAdapterDisciplina = new ArrayAdapter<String>(AddOra.super.getContext(), R.layout.list_item, discipline);
        arrayAdapterOra = new ArrayAdapter<String>(AddOra.super.getContext(), R.layout.list_item, ore);
        autoCompleteTextViewProfesor.setAdapter(arrayAdapterProfesor);
        autoCompleteTextViewDisciplina.setAdapter(arrayAdapterDisciplina);
        autoCompleteTextViewOra.setAdapter(arrayAdapterOra);
        autoCompleteTextViewDisciplina.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                sali = new ArrayList<>();
                DatabaseHelper databaseHelper = new DatabaseHelper(AddOra.super.getContext());
                Cursor cursor = databaseHelper.readDiscipline();
                while (cursor.moveToNext()) {
                    if (item.equals(cursor.getString(0))){
                        if (!cursor.getString(1).isEmpty())
                            sali.add(cursor.getString(1));
                        sali.add(cursor.getString(2));
                    }
                }
                arrayAdapterSala = new ArrayAdapter<String>(AddOra.super.getContext(), R.layout.list_item, sali);
                autoCompleteTextViewSala.setAdapter(arrayAdapterSala);
            }
        });
        back = view.findViewById(R.id.back);
        addOra = view.findViewById(R.id.addOrar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (zi.equals("luni"))
                    Navigation.findNavController(view).navigate(R.id.action_orarLuni_self);
                else if (zi.equals("marti"))
                    Navigation.findNavController(view).navigate(R.id.action_orarMarti_self);
                else if (zi.equals("miercuri"))
                    Navigation.findNavController(view).navigate(R.id.action_orarMiercuri_self);
                else if (zi.equals("joi"))
                    Navigation.findNavController(view).navigate(R.id.action_orarJoi_self);
//                else
//                    Navigation.findNavController(view).navigate(R.id.action_orarVineri_self);
            }
        });

        addOra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(AddOra.super.getContext());
                databaseHelper.addOra(autoCompleteTextViewProfesor.getText().toString().trim(), autoCompleteTextViewDisciplina.getText().toString().trim(), autoCompleteTextViewSala.getText().toString(), zi, autoCompleteTextViewOra.getText().toString());
                if (zi.equals("luni"))
                    Navigation.findNavController(view).navigate(R.id.action_orarLuni_self);
                else if (zi.equals("marti"))
                    Navigation.findNavController(view).navigate(R.id.action_orarMarti_self);
                else if (zi.equals("miercuri"))
                    Navigation.findNavController(view).navigate(R.id.action_orarMiercuri_self);
                else if (zi.equals("joi"))
                    Navigation.findNavController(view).navigate(R.id.action_orarJoi_self);
                else
                    Navigation.findNavController(view).navigate(R.id.action_orarVineri_self);
            }
        });
        return view;
    }
    private void storeDataIntoArraysAdd() {
        Cursor cursor = db.readDiscipline();
        if(cursor.getCount() == 0){
            Toast.makeText(AddOra.super.getContext(), "Nu exista discipline! Introdu discipline daca vrei sa adaugi ore :(", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                discipline.add(cursor.getString(0));
            }
        }
        cursor = db.readProfi();
        if(cursor.getCount() == 0){
            Toast.makeText(AddOra.super.getContext(), "Nu exista profesori! Introdu discipline daca vrei sa adaugi ore :(", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                profesori.add(cursor.getString(0));
            }
        }
    }
}