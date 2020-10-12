package com.bioskop.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Projekcija implements Serializable {
    public Projekcija() {
    }

    private int idProjekcija;
    private Film film;
    private Proj_Sala sala;
    private Date vremePocetka;
    private boolean zavrseno;
    private boolean premijera;

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

    public Date getVremePocetka() {
        return vremePocetka;
    }

    public void setVremePocetka(Timestamp vremePocetka) {
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

}