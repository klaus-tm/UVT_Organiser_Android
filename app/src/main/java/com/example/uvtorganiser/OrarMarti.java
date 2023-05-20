package com.example.uvtorganiser;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class OrarMarti extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton add_ora;
    DatabaseHelper db;
    ArrayList<String> Profesor, Disciplina, Sala, Ora;
    CustomAdapterOrar customAdapterOrar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orar_marti, container, false);

        recyclerView = view.findViewById(R.id.reciclerViewOreMarti);
        add_ora = view.findViewById(R.id.addOraMarti);

        db = new DatabaseHelper(OrarMarti.super.getContext());
        Profesor = new ArrayList<>();
        Disciplina = new ArrayList<>();
        Sala = new ArrayList<>();
        Ora = new ArrayList<>();

        storeDataIntoArrays();

        customAdapterOrar = new CustomAdapterOrar(OrarMarti.super.getContext(), Profesor, Disciplina, Sala, Ora, "marti");
        recyclerView.setLayoutManager(new LinearLayoutManager(OrarMarti.super.getContext()));
        recyclerView.setAdapter(customAdapterOrar);

        add_ora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_ora.setVisibility(View.GONE);
                Profesor.clear();
                Disciplina.clear();
                Sala.clear();
                Ora.clear();
                customAdapterOrar.notifyDataSetChanged();

                AddOra addOra = new AddOra();
                Bundle bundle = new Bundle();
                bundle.putString("ziua din orar", "marti");
                addOra.setArguments(bundle);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.constraintMarti, addOra);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return view;
    }

    private void storeDataIntoArrays() {
        Cursor cursor = db.readOre("marti");
        if (cursor.getCount() == 0){
            Toast.makeText(OrarMarti.super.getContext(), "Nu exista ore :|", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                Profesor.add(cursor.getString(0));
                Disciplina.add(cursor.getString(1));
                Sala.add(cursor.getString(2));
                Ora.add(cursor.getString(4));
            }
        }
    }
}