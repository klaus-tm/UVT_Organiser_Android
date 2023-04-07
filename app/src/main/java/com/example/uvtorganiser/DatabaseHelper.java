package com.example.uvtorganiser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "UVTOrganiser.db";
    public static final Integer DATABASE_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String profesor = "CREATE TABLE profesor (Nume TEXT NOT NULL PRIMARY KEY,Mail TEXT NOT NULL,Telefon TEXT NOT NULL)";
        String disciplina = "CREATE TABLE disciplina (Nume TEXT NOT NULL PRIMARY KEY, SalaCurs TEXT DEFAULT NULL, SalaSeminar TEXT NOT NULL)";
        String tema = "CREATE TABLE tema (Disciplina TEXT NOT NULL, DetaliiTema TEXT NOT NULL, Termen TEXT NOT NULL, Terminata INTEGER NOT NULL, CONSTRAINT Disciplina FOREIGN KEY(Disciplina) REFERENCES disciplina(Nume))";
        String nota = "CREATE TABLE nota (Disciplina TEXT NOT NULL, DetaliiNota TEXT NOT NULL, Nota INTEGER NOT NULL, Peste5 INTEGER NOT NULL, CONSTRAINT Disciplina FOREIGN KEY(Disciplina) REFERENCES disciplina(Nume))";
        String orar = "CREATE TABLE orar (Profesor TEXT NOT NULL, Disciplina TEXT NOT NULL, Sala TEXT NOT NULL, Zi TEXT NOT NULL, Ora INT NOT NULL, CONSTRAINT Disciplina FOREIGN KEY(Disciplina) REFERENCES disciplina(Nume), CONSTRAINT Profesor FOREIGN KEY(Profesor) REFERENCES profesor(Nume))";
        db.execSQL(profesor);
        db.execSQL(disciplina);
        db.execSQL(tema);
        db.execSQL(nota);
        db.execSQL(orar);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS profesor");
        db.execSQL("DROP TABLE IF EXISTS disciplina");
        db.execSQL("DROP TABLE IF EXISTS tema");
        db.execSQL("DROP TABLE IF EXISTS nota");
        db.execSQL("DROP TABLE IF EXISTS orar");
        onCreate(db);
    }

    //Operatii Profesori
    public void addProf(String nume, String mail, String telefon){
        if(nume.isEmpty() || mail.isEmpty())
            Toast.makeText(context, "Completeaza datele Profesorului!", Toast.LENGTH_SHORT).show();
        else{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Nume", nume);
            contentValues.put("Mail", mail);
            contentValues.put("Telefon", telefon);
            long result = db.insert("profesor", null, contentValues);
            if(result == -1)
                Toast.makeText(context, "Nu s-a putut insera Profesorul!", Toast.LENGTH_SHORT).show();
            else Toast.makeText(context, "Profesor adaugat cu success!", Toast.LENGTH_SHORT).show();
        }

    }
    public Cursor readProfi(){
        String querry = "SELECT * FROM profesor";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null)
            cursor = db.rawQuery(querry, null);
        return cursor;
    }

    public void deleteProf(String numeProf){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("profesor", "Nume=?", new String[]{numeProf});
        if(result == -1)
            Toast.makeText(context, "Profesorul nu a putut fi sters!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Profesor sters cu success!", Toast.LENGTH_SHORT).show();
    }

    //Operatii Discipline
    public void addDisciplina(String nume, String curs, String seminar){
        if(nume.isEmpty() || seminar.isEmpty())
            Toast.makeText(context, "Completeaza datele Disciplinei!", Toast.LENGTH_SHORT).show();
        else {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Nume", nume);
            contentValues.put("SalaCurs", curs);
            contentValues.put("SalaSeminar", seminar);
            long result = db.insert("disciplina", null, contentValues);
            if(result == -1)
                Toast.makeText(context, "Nu s-a putut insera Disciplina!", Toast.LENGTH_SHORT).show();
            else Toast.makeText(context, "Disciplina adaugata cu success!", Toast.LENGTH_SHORT).show();
        }
    }
    public Cursor readDiscipline(){
        String querry = "SELECT * FROM disciplina";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null)
            cursor = db.rawQuery(querry, null);
        return cursor;
    }

    public void deleteDisciplina(String numeDisciplina){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("disciplina", "Nume=?", new String[]{numeDisciplina});
        if(result == -1)
            Toast.makeText(context, "Disciplina nu a putut fi stearsa!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Disciplina stearsa cu success!", Toast.LENGTH_SHORT).show();
    }

    //Operatii Note
    public void addNota(String disciplina, String detalii, Float nota){
        if(disciplina.isEmpty() || nota.isNaN())
            Toast.makeText(context, "Completeaza datele Notei!", Toast.LENGTH_SHORT).show();
        else {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Disciplina", disciplina);
            contentValues.put("DetaliiNota", detalii);
            contentValues.put("Nota", nota);
            if(nota >= 5.0)
                contentValues.put("Peste5", 1);
            else contentValues.put("Peste5", 0);
            long result = db.insert("nota", null, contentValues);
            if(result == -1)
                Toast.makeText(context, "Nu s-a putut insera Nota!", Toast.LENGTH_SHORT).show();
            else Toast.makeText(context, "Nota adaugata cu success!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readNote(){
        String querry = "SELECT * FROM nota";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null)
            cursor = db.rawQuery(querry, null);
        return cursor;
    }

    public void deleteNota(String detaliiNota){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("nota", "DetaliiNota=?", new String[]{detaliiNota});
        if(result == -1)
            Toast.makeText(context, "Nota nu a putut fi stearsa!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Nota stearsa cu success!", Toast.LENGTH_SHORT).show();
    }

    //Operatii Teme
    public void addTema(String disciplina, String detalii, String termen){
        if(disciplina.isEmpty())
            Toast.makeText(context, "Completeaza datele Temei!", Toast.LENGTH_SHORT).show();
        else {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Disciplina", disciplina);
            contentValues.put("DetaliiTema", detalii);
            contentValues.put("Termen", termen);
            contentValues.put("Terminata", 0);
            long result = db.insert("tema", null, contentValues);
            if(result == -1)
                Toast.makeText(context, "Nu s-a putut insera Tema!", Toast.LENGTH_SHORT).show();
            else Toast.makeText(context, "Tema adaugata cu success!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readTeme(){
        String querry = "SELECT * FROM tema";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null)
            cursor = db.rawQuery(querry, null);
        return cursor;
    }

    void makeTerminata(String detalii){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Terminata", 1);
        long result = db.update("tema", contentValues, "DetaliiTema=?", new String[]{detalii});
        if (result == -1)
            Toast.makeText(context, "Nu s-a putut marca tema ca terminata!", Toast.LENGTH_SHORT).show();
        else Toast.makeText(context, "Tema s-a marcat ca terminata!", Toast.LENGTH_SHORT).show();
    }

    public void deleteTema(){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("tema", "Terminata=?", new String[]{"1"});
        if (result == -1)
            Toast.makeText(context, "Nu s-au putut sterge Temele!", Toast.LENGTH_SHORT).show();
        else Toast.makeText(context, "S-au sters toate temele terminate!", Toast.LENGTH_SHORT).show();
    }
}
