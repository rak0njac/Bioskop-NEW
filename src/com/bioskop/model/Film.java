package com.bioskop.model;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalTime;

public class Film implements Serializable {
    public Film(){}



    private int idFilm;
    private String naziv;
    private String zanr;
    private String reziser;
    private int godina;
    private Time trajanje;
    private String opis;
    private String urlTrailer;

    public int getIdFilm() {
        return idFilm;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getZanr() {
        return zanr;
    }

    public void setZanr(String zanr) {
        this.zanr = zanr;
    }

    public String getReziser() {
        return reziser;
    }

    public void setReziser(String reziser) {
        this.reziser = reziser;
    }

    public int getGodina() {
        return godina;
    }

    public void setGodina(int godina) {
        this.godina = godina;
    }

    public Time getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(Time trajanje) {
        this.trajanje = trajanje;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getUrlTrailer() {
        return urlTrailer;
    }

    public void setUrlTrailer(String urlTrailer) {
        this.urlTrailer = urlTrailer;
    }
}
