package com.example.uvtorganiser;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment {
    TextView welcomeText, noSchedule, noHomework, noGrade;
    RecyclerView orarHome, temeHome, noteHome;

    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> arrayAdapter;
    ArrayList <String> profesor, disciplinaOrar, ora, sala, disciplinaTema, detaliiTema, terminata, termen, disciplinaNote, detaliiNota, peste5, numeDiscipline;
    ArrayList <Float> nota;
    DatabaseHelper db;
    CustomAdapterOrar customAdapterOrar;
    CustomAdapterTeme customAdapterTeme;

    CustomAdapterNote customAdapterNote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        db = new DatabaseHelper(HomeFragment.super.getContext());

        welcomeText = view.findViewById(R.id.welcomeText);
        welcomeText.setText(getGreeting());
        noSchedule = view.findViewById(R.id.noSchedule);
        noHomework = view.findViewById(R.id.noHomework);
        noGrade = view.findViewById(R.id.noGrades);

        autoCompleteTextView = view.findViewById(R.id.auto_complete_Discipline);

        orarHome = view.findViewById(R.id.orarHome);
        temeHome = view.findViewById(R.id.temeHome);
        noteHome = view.findViewById(R.id.noteHome);

        profesor = new ArrayList<>();
        disciplinaOrar = new ArrayList<>();
        ora = new ArrayList<>();
        sala = new ArrayList<>();
        disciplinaTema = new ArrayList<>();
        detaliiTema = new ArrayList<>();
        terminata = new ArrayList<>();
        termen = new ArrayList<>();
        disciplinaNote = new ArrayList<>();
        detaliiNota = new ArrayList<>();
        peste5 = new ArrayList<>();
        numeDiscipline = new ArrayList<>();
        nota = new ArrayList<>();

        if (getZi().equals("")){
            noSchedule.setVisibility(View.VISIBLE);
            orarHome.setVisibility(View.GONE);
        }
        else {
            storeDataFromTimetable();
            if (disciplinaOrar.size() == 0)
                orarHome.setVisibility(View.GONE);
            else {
                customAdapterOrar = new CustomAdapterOrar(HomeFragment.super.getContext(), profesor, disciplinaOrar, sala, ora, getZi());
                orarHome.setLayoutManager(new LinearLayoutManager(HomeFragment.super.getContext()));
                orarHome.setAdapter(customAdapterOrar);
            }
        }

        storeDataFromHomework();

        if (disciplinaTema.size() == 0){
            noHomework.setText("Nu ai teme perioada asta. Revino luni!");
            noHomework.setVisibility(View.VISIBLE);
            temeHome.setVisibility(View.GONE);
        }
        else {
            customAdapterTeme = new CustomAdapterTeme(HomeFragment.super.getContext(), disciplinaTema, detaliiTema, termen, terminata);
            temeHome.setAdapter(customAdapterTeme);
            temeHome.setLayoutManager(new LinearLayoutManager(HomeFragment.super.getContext()));
        }

        getDiscipline();

        arrayAdapter = new ArrayAdapter<String>(HomeFragment.super.getContext(), R.layout.list_item, numeDiscipline);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                noGrade.setVisibility(View.GONE);
                noteHome.setVisibility(View.VISIBLE);
                disciplinaNote.clear();
                detaliiNota.clear();
                peste5.clear();
                nota.clear();
                storeNote(item);
                customAdapterNote = new CustomAdapterNote(HomeFragment.super.getContext(), disciplinaNote, detaliiNota, nota, peste5);
                noteHome.setAdapter(customAdapterNote);
                noteHome.setLayoutManager(new LinearLayoutManager(HomeFragment.super.getContext()));

            }
        });
        return view;
    }

    private void storeNote(String disciplina) {
        Cursor cursor = db.readNoteSpecial(disciplina);
        if (cursor.getCount() == 0){
            noGrade.setText("Disciplina nu are note inca!");
            noGrade.setVisibility(View.VISIBLE);
            noteHome.setVisibility(View.GONE);
        }
        else{
            while (cursor.moveToNext()) {
                disciplinaNote.add(cursor.getString(0));
                detaliiNota.add(cursor.getString(1));
                nota.add(cursor.getFloat(2));
                if (cursor.getInt(3) == 0)
                    peste5.add("Nu");
                else peste5.add("Da");
            }
        }
    }

    @NonNull
    private String getZi(){
        if(LocalDate.now().getDayOfWeek().equals(DayOfWeek.MONDAY))
            return "luni";
        else if(LocalDate.now().getDayOfWeek().equals(DayOfWeek.TUESDAY))
            return "marti";
        else if(LocalDate.now().getDayOfWeek().equals(DayOfWeek.WEDNESDAY))
            return "miercuri";
        else if(LocalDate.now().getDayOfWeek().equals(DayOfWeek.THURSDAY))
            return "joi";
        else if(LocalDate.now().getDayOfWeek().equals(DayOfWeek.FRIDAY))
            return "vineri";
        return "";
    }
    private String getGreeting() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 5 && hour < 12)
            return  "Buna dimineata tovaras!";
        else if (hour >= 12 && hour < 18)
            return "Ziua buna sa ai!";
        else
            return "Sa nu stai pana tarziu!";
    }

    private void storeDataFromHomework() {
        Cursor cursor = db.readTeme();
        if(cursor.getCount() == 0){
            noHomework.setVisibility(View.VISIBLE);
            temeHome.setVisibility(View.GONE);
        } else {
            while(cursor.moveToNext()){
                if (checkWeek(cursor.getString(2))){
                    disciplinaTema.add(cursor.getString(0));
                    detaliiTema.add(cursor.getString(1));
                    termen.add(cursor.getString(2));
                    if (cursor.getInt(3) == 0)
                        terminata.add("Nu");
                    else terminata.add("Da");
                }
            }
        }
    }

    private boolean checkWeek(String termen) {
        if(LocalDate.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == LocalDate.parse(termen, DateTimeFormatter.ofPattern("d/M/yyyy")).get(IsoFields.WEEK_OF_WEEK_BASED_YEAR))
            return true;
        else if (LocalDate.now().plusWeeks(1).get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == LocalDate.parse(termen, DateTimeFormatter.ofPattern("d/M/yyyy")).get(IsoFields.WEEK_OF_WEEK_BASED_YEAR))
            return true;

        else return false;
    }

    private void storeDataFromTimetable() {
        Cursor cursor = db.readOre(getZi());
        if (cursor.getCount() == 0){
            noSchedule.setText("Nu exista ore in orar :|");
            noSchedule.setVisibility(View.VISIBLE);
            orarHome.setVisibility(View.GONE);
        } else {
            while (cursor.moveToNext()){
                profesor.add(cursor.getString(0));
                disciplinaOrar.add(cursor.getString(1));
                sala.add(cursor.getString(2));
                ora.add(cursor.getString(4));
            }
        }
    }

    private void getDiscipline() {
        Cursor cursor = db.readDiscipline();
        if(cursor.getCount() == 0){
            noGrade.setText("Nu ai discipline introduse!");
            noGrade.setVisibility(View.VISIBLE);
            noteHome.setVisibility(View.GONE);
        } else {
            while (cursor.moveToNext()) {
                numeDiscipline.add(cursor.getString(0));
            }
        }
    }
}