package com.example.uvtorganiser;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DisciplineFragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton add_discipline;
    DatabaseHelper db;
    ArrayList<String>numeDisciplina, salaCurs, salaSeminar;
    CustomAdapterDiscipline customAdapterDiscipline;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discipline, container, false);

        recyclerView = view.findViewById(R.id.reciclerViewDiscipline);
        add_discipline = view.findViewById(R.id.addDisciplina);

        add_discipline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_menuDiscipline_to_addDiscipline);
            }
        });

        db = new DatabaseHelper(DisciplineFragment.super.getContext());
        numeDisciplina = new ArrayList<>();
        salaCurs = new ArrayList<>();
        salaSeminar = new ArrayList<>();

        storeDataIntoArrays();

        customAdapterDiscipline = new CustomAdapterDiscipline(DisciplineFragment.super.getContext(), numeDisciplina, salaCurs, salaSeminar);
        recyclerView.setAdapter(customAdapterDiscipline);
        recyclerView.setLayoutManager(new LinearLayoutManager(DisciplineFragment.super.getContext()));
        return view;
    }

    void storeDataIntoArrays(){
        Cursor cursor = db.readDiscipline();
        if(cursor.getCount() == 0){
            Toast.makeText(DisciplineFragment.super.getContext(), "Nu exista discipline :(", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                numeDisciplina.add(cursor.getString(0));
                salaCurs.add(cursor.getString(1));
                salaSeminar.add(cursor.getString(2));
            }
        }
    }
}