package com.example.uvtorganiser;

/**
 * class for table disciplina
 */
public class Disciplina {
    private String nume, salaCurs, salaSeminar;

    public Disciplina(String nume, String salaCurs, String salaSeminar){
        this.nume = nume;
        this.salaCurs = salaCurs;
        this.salaSeminar = salaSeminar;
    }

    public String getNume() {
        return nume;
    }

    public String getSalaCurs() {
        return salaCurs;
    }

    public String getSalaSeminar() {
        return salaSeminar;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setSalaCurs(String salaCurs) {
        this.salaCurs = salaCurs;
    }

    public void setSalaSeminar(String salaSeminar) {
        this.salaSeminar = salaSeminar;
    }
}