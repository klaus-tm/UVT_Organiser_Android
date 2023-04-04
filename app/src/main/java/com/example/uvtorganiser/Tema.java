package com.example.uvtorganiser;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * class for table tema
 */
public class Tema {
    private String numeDisciplina;
    private String detaliiTema;
    //private String termen;
    private Boolean terminata;
    private Date termen;

    public Tema(String numeDisciplina, String detaliiTema, Date termen, Boolean terminata){
        this.numeDisciplina = numeDisciplina;
        this.detaliiTema = detaliiTema;
        this.termen = termen;
        this.terminata = terminata;
    }

    public String getNumeDisciplina() {
        return numeDisciplina;
    }

    public String getDetaliiTema() {
        return detaliiTema;
    }

    public Date getTermen() {
        return termen;
    }

    public boolean isTerminata() {
        return terminata;
    }

    public String getTermenTabel(){
        String due = "";
        LocalDate temp = termen.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if(temp.getDayOfWeek().equals(DayOfWeek.MONDAY))
            due = "Luni";
        else if(temp.getDayOfWeek().equals(DayOfWeek.TUESDAY))
            due = "Marti";
        else if(temp.getDayOfWeek().equals(DayOfWeek.WEDNESDAY))
            due = "Miercuri";
        else if(temp.getDayOfWeek().equals(DayOfWeek.THURSDAY))
            due = "Joi";
        else if(temp.getDayOfWeek().equals(DayOfWeek.FRIDAY))
            due = "Vineri";

        return due;
    }

    public void setNumeDisciplina(String numeDisciplina) {
        this.numeDisciplina = numeDisciplina;
    }

    public void setDetaliiTema(String detaliiTema) {
        this.detaliiTema = detaliiTema;
    }

    public void setTermen(Date termen) {
        this.termen = termen;
    }

    public void setTerminata(Boolean terminata) {
        this.terminata = terminata;
    }
}