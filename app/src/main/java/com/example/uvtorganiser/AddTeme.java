package com.example.uvtorganiser;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddTeme extends Fragment {
    ArrayList<String> numeDiscipline;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;
    Button addTema;
    EditText detaliiTema, termen;
    DatabaseHelper db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_teme, container, false);
        autoCompleteTextView = view.findViewById(R.id.auto_complete_Discipline);
        addTema = view.findViewById(R.id.addTema);
        detaliiTema = view.findViewById(R.id.DetaliiTema);
        termen = view.findViewById(R.id.termenTema);
        db = new DatabaseHelper(AddTeme.super.getContext());
        numeDiscipline = new ArrayList<>();
        storeDataIntoArrays();

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        termen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTeme.super.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month += 1;
                        String date = day + "/" + month + "/" + year;
                        termen.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        arrayAdapter = new ArrayAdapter<String>(AddTeme.super.getContext(), R.layout.list_item, numeDiscipline);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }
        });
        addTema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(AddTeme.super.getContext());
                db.addTema(autoCompleteTextView.getText().toString().trim(), detaliiTema.getText().toString().trim(), termen.getText().toString().trim());
                Navigation.findNavController(view).navigate(R.id.action_addTeme_to_menuTeme);
            }
        });
        return view;
    }
    void storeDataIntoArrays(){
        Cursor cursor = db.readDiscipline();
        if(cursor.getCount() == 0){
            Toast.makeText(AddTeme.super.getContext(), "Nu exista discipline! Introdu discipline daca vrei sa adaugi tema :(", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                numeDiscipline.add(cursor.getString(0));
            }
        }
    }
}