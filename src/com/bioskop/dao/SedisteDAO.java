package com.bioskop.dao;

import com.bioskop.model.Sediste;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SedisteDAO {

    private static Connection con = setup();
    private static PreparedStatement ps;
    private static ResultSet rs;
    private static ArrayList<Sediste> sedisteList = new ArrayList<>();

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

//    public static ArrayList<Sediste> findAll() throws SQLException {
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

    public static Sediste findById(int id) throws SQLException {
        ps = con.prepareStatement("select * from sediste where idsediste = " + id);
        rs = ps.executeQuery();
        rs.next();
        Sediste sediste = new Sediste();

        sediste.setIdSediste(rs.getInt("idsediste"));
        sediste.setBroj(rs.getInt("broj"));
        sediste.setTip(rs.getNString("tip"));
        sediste.setSala(Proj_SalaDAO.findById(rs.getInt("idsala")));

        return sediste;
    }

    public static ArrayList<Sediste> findBySala(int idSala) throws SQLException {
        sedisteList.clear();

        ps = con.prepareStatement("select * from sediste where idsala = " + idSala);
        rs = ps.executeQuery();

        while(rs.next())
        {
            Sediste sediste = new Sediste();

            sediste.setIdSediste(rs.getInt("idsediste"));
            sediste.setBroj(rs.getInt("broj"));
            sediste.setTip(rs.getNString("tip"));
            sediste.setSala(Proj_SalaDAO.findById(idSala));

            sedisteList.add(sediste);
        }

        return sedisteList;
    }
}
