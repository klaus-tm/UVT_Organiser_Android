package com.example.uvtorganiser;

/**
 * class for table profesor
 */
public class Profesor {
    private String nume, mail, telefon;

    public Profesor(){}
    public Profesor(String nume, String mail, String telefon){
        this.nume = nume;
        this.mail = mail;
        this.telefon = telefon;
    }

    public String getNume() {
        return nume;
    }

    public String getMail() {
        return mail;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}