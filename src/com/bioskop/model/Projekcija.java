package com.bioskop.model;

import com.bioskop.helpers.DateHelper;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Projekcija implements Serializable {
    public Projekcija() {
    }

    private int idProjekcija;
    private Film film;
    private Proj_Sala sala;
    private DateHelper vremePocetka;
    private boolean zavrseno;
    private boolean premijera;
    private ArrayList<Karta> karte;

    public int getIdProjekcija() {
        return idProjekcija;
    }

    public void setIdProjekcija(int idProjekcija) {
        this.idProjekcija = idProjekcija;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Proj_Sala getSala() {
        return sala;
    }

    public void setSala(Proj_Sala sala) {
        this.sala = sala;
    }

    public DateHelper getVremePocetka() {
        return vremePocetka;
    }

    public void setVremePocetka(DateHelper vremePocetka) {
        this.vremePocetka = vremePocetka;
    }

    public boolean isZavrseno() {
        return zavrseno;
    }

    public void setZavrseno(boolean zavrseno) {
        this.zavrseno = zavrseno;
    }

    public boolean isPremijera() {
        return premijera;
    }

    public void setPremijera(boolean premijera) {
        this.premijera = premijera;
    }

    public ArrayList<Karta> getKarte() {
        return karte;
    }

    public void setKarte(ArrayList<Karta> karte) {
        this.karte = karte;
    }
}