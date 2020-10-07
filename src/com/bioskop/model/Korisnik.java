package com.bioskop.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Korisnik implements Serializable {
    public Korisnik() {
    }

    private int idKorisnik;
    private String ImePrezime;
    private LocalDate datRodj;
    private String username;
    private String password;
    private String email;
    private String brTel;
    private String tip;
    private String status;
    private int brPoena;

    public int getIdKorisnik() {
        return idKorisnik;
    }

    public String getImePrezime() {
        return ImePrezime;
    }

    public void setImePrezime(String imePrezime) {
        ImePrezime = imePrezime;
    }

    public LocalDate getDatRodj() {
        return datRodj;
    }

    public void setDatRodj(LocalDate datRodj) {
        this.datRodj = datRodj;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBrTel() {
        return brTel;
    }

    public void setBrTel(String brTel) {
        this.brTel = brTel;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBrPoena() {
        return brPoena;
    }

    public void setBrPoena(int brPoena) {
        this.brPoena = brPoena;
    }
}