package com.bioskop.model;

import java.io.Serializable;
import java.time.LocalTime;

public class Karta implements Serializable {
    public Karta() {
    }

    private int idKarta;
    private Projekcija projekcija;
    private Sediste sediste;
    private double cena;
    private String status;
    private Korisnik korisnik;

    public int getIdKarta() {
        return idKarta;
    }

    public void setIdKarta(int idKarta) {
        this.idKarta = idKarta;
    }

    public Projekcija getProjekcija() {
        return projekcija;
    }

    public void setProjekcija(Projekcija projekcija) {
        this.projekcija = projekcija;
    }

    public Sediste getSediste() {
        return sediste;
    }

    public void setSediste(Sediste sediste) {
        this.sediste = sediste;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }
}