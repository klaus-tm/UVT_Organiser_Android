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

public class NoteFragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton add_note;
    DatabaseHelper db;
    ArrayList<String>numeDisciplina, detaliiNota, peste5;
    ArrayList<Float>nota;
    CustomAdapterNote customAdapterNote;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        recyclerView = view.findViewById(R.id.reciclerViewNote);
        add_note = view.findViewById(R.id.addNota);

        add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_menuNote_to_addNote);
            }
        });

        db = new DatabaseHelper(NoteFragment.super.getContext());
        numeDisciplina = new ArrayList<>();
        detaliiNota = new ArrayList<>();
        nota = new ArrayList<>();
        peste5 = new ArrayList<>();

        storeDataIntoArrays();
        customAdapterNote = new CustomAdapterNote(NoteFragment.super.getContext(), numeDisciplina, detaliiNota, nota, peste5);
        recyclerView.setAdapter(customAdapterNote);
        recyclerView.setLayoutManager(new LinearLayoutManager(NoteFragment.super.getContext()));
        return view;
    }

    void storeDataIntoArrays(){
        Cursor cursor = db.readNote();
        if(cursor.getCount() == 0){
            Toast.makeText(NoteFragment.super.getContext(), "Nu exista note :(", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                numeDisciplina.add(cursor.getString(0));
                detaliiNota.add(cursor.getString(1));
                nota.add(cursor.getFloat(2));
                if (cursor.getInt(3) == 0)
                    peste5.add("Nu");
                else peste5.add("Da");
            }
        }
    }
}