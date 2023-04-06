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
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddNote extends Fragment {
    ArrayList<String> numeDiscipline;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;
    Button addNota;
    EditText detaliiNota, Nota;
    DatabaseHelper db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);
        autoCompleteTextView = view.findViewById(R.id.auto_complete_Discipline);
        addNota = view.findViewById(R.id.addNota);
        detaliiNota = view.findViewById(R.id.DetaliiNota);
        Nota = view.findViewById(R.id.Nota);
        db = new DatabaseHelper(AddNote.super.getContext());
        numeDiscipline = new ArrayList<>();
        storeDataIntoArrays();

        arrayAdapter = new ArrayAdapter<String>(AddNote.super.getContext(), R.layout.list_item, numeDiscipline);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }
        });
        addNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(AddNote.super.getContext());
                db.addNota(autoCompleteTextView.getText().toString().trim(), detaliiNota.getText().toString().trim(), Float.valueOf(Nota.getText().toString()));
                Navigation.findNavController(view).navigate(R.id.action_addNote_to_menuNote);
            }
        });
        return view;
    }
    void storeDataIntoArrays(){
        Cursor cursor = db.readDiscipline();
        if(cursor.getCount() == 0){
            Toast.makeText(AddNote.super.getContext(), "Nu exista discipline! Introdu discipline daca vrei sa adaugi nota :(", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                numeDiscipline.add(cursor.getString(0));
            }
        }
    }
}