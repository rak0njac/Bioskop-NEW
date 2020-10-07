package com.bioskop.dao;

import com.bioskop.dbconfig;
import com.bioskop.model.Film;

import java.sql.*;
import java.util.ArrayList;

public class FilmDAO {

    private static Connection con = setup();
    private static PreparedStatement ps;
    private static ResultSet rs;
    private static ArrayList<Film> filmList = new ArrayList<Film>();

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

    public static ArrayList<Film> findAll() throws SQLException {
        filmList.clear();
        ps = con.prepareStatement("select * from FILM");
        rs = ps.executeQuery();
        while(rs.next()){
            Film film = new Film();

            film.setGodina(rs.getInt("godina"));
            film.setNaziv(rs.getNString("naziv"));
            film.setOpis(rs.getNString("opis"));
            film.setReziser(rs.getNString("reziser"));
            film.setTrajanje(rs.getTime("trajanje"));
            film.setUrlTrailer(rs.getString("urltrailer"));
            film.setZanr(rs.getNString("zanr"));
            filmList.add(film);
        }
        return filmList;
    }

    public static Film findById(int id) throws SQLException {
        ps = con.prepareStatement("select * from FILM where idFilm = " + id);
        rs = ps.executeQuery();
        rs.next();
        Film film = new Film();

        film.setGodina(rs.getInt("godina"));
        film.setNaziv(rs.getNString("naziv"));
        film.setOpis(rs.getNString("opis"));
        film.setReziser(rs.getNString("reziser"));
        film.setTrajanje(rs.getTime("trajanje"));
        film.setUrlTrailer(rs.getString("urltrailer"));
        film.setZanr(rs.getNString("zanr"));

        return film;
    }
}
