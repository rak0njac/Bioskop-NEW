package com.bioskop.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Multiplex implements Serializable {
    public Multiplex() {
    }

    private int idMultiplex;
    private String naziv;

    public int getIdMultiplex() {
        return idMultiplex;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}