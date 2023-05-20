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

public class OrarVineri extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton add_ora;
    DatabaseHelper db;
    ArrayList<String> Profesor, Disciplina, Sala, Ora;
    CustomAdapterOrar customAdapterOrar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orar_vineri, container, false);

        recyclerView = view.findViewById(R.id.reciclerViewOreVineri);
        add_ora = view.findViewById(R.id.addOraVineri);

        db = new DatabaseHelper(OrarVineri.super.getContext());
        Profesor = new ArrayList<>();
        Disciplina = new ArrayList<>();
        Sala = new ArrayList<>();
        Ora = new ArrayList<>();

        storeDataIntoArrays();

        customAdapterOrar = new CustomAdapterOrar(OrarVineri.super.getContext(), Profesor, Disciplina, Sala, Ora, "vineri");
        recyclerView.setLayoutManager(new LinearLayoutManager(OrarVineri.super.getContext()));
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
                bundle.putString("ziua din orar", "vineri");
                addOra.setArguments(bundle);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.constraintVineri, addOra);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return view;
    }

    private void storeDataIntoArrays() {
        Cursor cursor = db.readOre("vineri");
        if (cursor.getCount() == 0){
            Toast.makeText(OrarVineri.super.getContext(), "Nu exista ore :|", Toast.LENGTH_SHORT).show();
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