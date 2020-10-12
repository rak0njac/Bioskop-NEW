package com.bioskop.model;

import java.io.Serializable;

public class Proj_Sala implements Serializable {
    public Proj_Sala() {
    }

    private int idSala;
    private int broj;
    private Multiplex multiplex;

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public int getBroj() {
        return broj;
    }

    public void setBroj(int broj) {
        this.broj = broj;
    }

    public Multiplex getMultiplex() {
        return multiplex;
    }

    public void setMultiplex(Multiplex multiplex) {
        this.multiplex = multiplex;
    }
}