package com.bioskop.dao;

import com.bioskop.dbconfig;
import com.bioskop.model.Film;
import com.bioskop.model.Karta;
import com.bioskop.model.Projekcija;
import com.bioskop.model.Sediste;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class KartaDAO {

    private static Connection con = setup();
    private static PreparedStatement ps;
    private static ResultSet rs;
    private static ArrayList<Karta> kartaList = new ArrayList<>();

    private static Connection setup(){
        try {
            return dbconfig.con();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Sediste> getFreeSeatsByProjId(int id) throws SQLException {
        ps = con.prepareStatement("select idsediste from karta where idprojekcija=" + id);
        rs = ps.executeQuery();
        ArrayList<Sediste> sedista = new ArrayList<>();

        while(rs.next()){
            Sediste sediste = SedisteDAO.findById(rs.getInt("idsediste"));
            sedista.add(sediste);
        }
        return sedista;
    }

    public static ArrayList<Karta> findByProjList(ArrayList<Projekcija> list) throws SQLException {
                kartaList.clear();
                for(Projekcija p : list){
                    ps = con.prepareStatement("select * from KARTA where idprojekcija = "+p.getIdProjekcija());
                    rs = ps.executeQuery();
                    Projekcija projekcija = ProjekcijaDAO.findById(p.getIdProjekcija());
                    while(rs.next()){
                        Karta karta = new Karta();

                        karta.setIdKarta(rs.getInt("IdKarta"));
                        karta.setCena(rs.getDouble("cena"));
                        karta.setProjekcija(projekcija);
                        karta.setSediste(SedisteDAO.findById(rs.getInt("idSediste")));
                        karta.setStatus(rs.getNString("status"));
                        kartaList.add(karta);
                    }

                }
        return kartaList;

    }

//    public static ArrayList<Film> findAll() throws SQLException {
//        filmList.clear();
//        ps = con.prepareStatement("select * from FILM");
//        rs = ps.executeQuery();
//        while(rs.next()){
//            Film film = new Film();
//
//            film.setIdFilm(rs.getInt("idFilm"));
//            film.setGodina(rs.getInt("godina"));
//            film.setNaziv(rs.getNString("naziv"));
//            film.setOpis(rs.getNString("opis"));
//            film.setReziser(rs.getNString("reziser"));
//            film.setTrajanje(rs.getTime("trajanje"));
//            film.setUrlTrailer(rs.getString("urltrailer"));
//            film.setZanr(rs.getNString("zanr"));
//            film.setCoverPath(rs.getString("coverpath"));
//            filmList.add(film);
//        }
//        return filmList;
//    }
//
//    public static Film findById(int id) throws SQLException {
//        ps = con.prepareStatement("select * from FILM where idFilm = " + id);
//        rs = ps.executeQuery();
//        rs.next();
//        Film film = new Film();
//
//        film.setIdFilm(rs.getInt("idFilm"));
//        film.setGodina(rs.getInt("godina"));
//        film.setNaziv(rs.getNString("naziv"));
//        film.setOpis(rs.getNString("opis"));
//        film.setReziser(rs.getNString("reziser"));
//        film.setTrajanje(rs.getTime("trajanje"));
//        film.setUrlTrailer(rs.getString("urltrailer"));
//        film.setZanr(rs.getNString("zanr"));
//        film.setCoverPath(rs.getString("coverpath"));
//
//        return film;
//    }
}
