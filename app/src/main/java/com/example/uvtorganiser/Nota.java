package com.example.uvtorganiser;


/**
 * class for table nota
 */
public class Nota {
    private String numeDisciplina;
    private String detaliiNota;
    private Double nota;
    private Boolean peste5;
    public Nota(String numeDisciplina, String detaliiNota, Double nota){
        this.numeDisciplina = numeDisciplina;
        this.detaliiNota = detaliiNota;
        this.nota = nota;
        if(nota < 5)
            this.peste5 = false;
        else this.peste5 = true;
    }

    public String getNumeDisciplina() {
        return numeDisciplina;
    }

    public String getDetaliiNota() {
        return detaliiNota;
    }

    public Double getNota() {
        return nota;
    }

    public boolean isPeste5() {
        return peste5;
    }

    public void setNumeDisciplina(String numeDisciplina) {
        this.numeDisciplina = numeDisciplina;
    }

    public void setDetaliiNota(String detaliiNota) {
        this.detaliiNota = detaliiNota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public void setPeste5(Boolean peste5) {
        this.peste5 = peste5;
    }
}