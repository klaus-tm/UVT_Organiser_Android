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

public class ProfesoriFragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton add_profi;
    DatabaseHelper db;
    ArrayList<String>numeProf, mailProf, telefonProf;
    CustomAdapterProfesori customAdapterProfesori;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profesori, container, false);

        recyclerView = view.findViewById(R.id.reciclerViewProfesori);
        add_profi = view.findViewById(R.id.addProfesor);
        add_profi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_menuProfesori_to_addProfi);
            }
        });

        db = new DatabaseHelper(ProfesoriFragment.super.getContext());
        numeProf = new ArrayList<>();
        mailProf = new ArrayList<>();
        telefonProf = new ArrayList<>();

        storeDataIntoArrays();

        customAdapterProfesori = new CustomAdapterProfesori(ProfesoriFragment.super.getContext(), numeProf, mailProf, telefonProf);
        recyclerView.setAdapter(customAdapterProfesori);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProfesoriFragment.super.getContext()));

        return view;
    }

    void storeDataIntoArrays(){
        Cursor cursor = db.readProfi();
        if(cursor.getCount() == 0){
            Toast.makeText(ProfesoriFragment.super.getContext(), "Nu exista profesori :(", Toast.LENGTH_SHORT).show();
        } else {
          while(cursor.moveToNext()){
              numeProf.add(cursor.getString(0));
              mailProf.add(cursor.getString(1));
              telefonProf.add(cursor.getString(2));
          }
        }
    }
}