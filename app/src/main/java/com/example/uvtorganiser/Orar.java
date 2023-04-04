package com.example.uvtorganiser;


/**
 * class for table orar
 */
public class Orar {
    private String numeDisciplina, numeProfesor, sala, zi;
    private Integer ora;

    public Orar(String numeProfesor, String numeDisciplina, String sala, String zi, Integer ora){
        this.numeDisciplina = numeDisciplina;
        this.numeProfesor = numeProfesor;
        this.sala = sala;
        this.zi = zi;
        this.ora = ora;
    }

    public String getNumeDisciplina() {
        return numeDisciplina;
    }

    public String getNumeProfesor() {
        return numeProfesor;
    }

    public String getSala() {
        return sala;
    }

    public String getZi() {
        return zi;
    }

    public Integer getOra() {
        return ora;
    }

    public String getOraTabel(){
        Integer minute = this.ora % 100, hour = this.ora / 100;
        String interval = hour.toString() + ":" + minute.toString();
        if(minute == 0)
            interval += "0";
        return interval;
    }

    public void setNumeDisciplina(String numeDisciplina) {
        this.numeDisciplina = numeDisciplina;
    }

    public void setNumeProfesor(String numeProfesor) {
        this.numeProfesor = numeProfesor;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public void setZi(String zi) {
        this.zi = zi;
    }

    public void setOra(Integer ora) {
        this.ora = ora;
    }
}