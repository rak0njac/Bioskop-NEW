package com.bioskop.model;

import java.io.Serializable;
import java.time.LocalTime;

public class Sediste implements Serializable {
    public Sediste() {
    }

    private int idSediste;
    private int broj;
    private Proj_Sala sala;
    private String tip;

    public int getIdSediste() {
        return idSediste;
    }

    public int getBroj() {
        return broj;
    }

    public void setBroj(int broj) {
        this.broj = broj;
    }

    public Proj_Sala getSala() {
        return sala;
    }

    public void setSala(Proj_Sala sala) {
        this.sala = sala;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}