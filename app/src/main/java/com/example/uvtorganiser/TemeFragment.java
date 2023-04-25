package com.example.uvtorganiser;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TemeFragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton add_Teme;
    DatabaseHelper db;
    ArrayList<String>numeDisciplina, detaliiTema, terminata, termen;
    CustomAdapterTeme customAdapterTeme;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teme, container, false);

        recyclerView = view.findViewById(R.id.reciclerViewTeme);
        add_Teme = view.findViewById(R.id.addTema);

        add_Teme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_menuTeme_to_addTeme);
            }
        });

        db = new DatabaseHelper(TemeFragment.super.getContext());
        numeDisciplina = new ArrayList<>();
        detaliiTema = new ArrayList<>();
        termen = new ArrayList<>();
        terminata = new ArrayList<>();

        storeDataIntoArrays();
        customAdapterTeme = new CustomAdapterTeme(TemeFragment.super.getContext(), numeDisciplina, detaliiTema, termen, terminata);
        recyclerView.setAdapter(customAdapterTeme);
        recyclerView.setLayoutManager(new LinearLayoutManager(TemeFragment.super.getContext()));
        return view;
    }

    void storeDataIntoArrays(){
        Cursor cursor = db.readTeme();
        if(cursor.getCount() == 0){
            Toast.makeText(TemeFragment.super.getContext(), "Nu ai teme de facut. YAY :)", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()){
                numeDisciplina.add(cursor.getString(0));
                detaliiTema.add(cursor.getString(1));
                termen.add(cursor.getString(2));
                if (cursor.getInt(3) == 0)
                    terminata.add("Nu");
                else terminata.add("Da");
            }
        }

        Handler handlerMain = new Handler();
        handlerMain.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (terminata.contains("Da")){
                    Toast.makeText(TemeFragment.super.getContext(), "Se pare ca ai teme terminate. Asteapta cateva momente ca sa le sterg!", Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            db.deleteTema();
                            Navigation.findNavController(TemeFragment.super.getView()).navigate(R.id.action_menuTeme_self);
                        }
                    }, 2000);
                }

            }
        }, 5000);

    }
}