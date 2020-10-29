package com.bioskop.dao;

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

            film.setIdFilm(rs.getInt("idFilm"));
            film.setGodina(rs.getInt("godina"));
            film.setNaziv(rs.getNString("naziv"));
            film.setOpis(rs.getNString("opis"));
            film.setReziser(rs.getNString("reziser"));
            film.setTrajanje(rs.getTime("trajanje").toLocalTime());
            film.setUrlTrailer(rs.getString("urltrailer"));
            film.setZanr(rs.getNString("zanr"));
            film.setCoverPath(rs.getString("coverpath"));
            filmList.add(film);
        }
        return filmList;
    }

    public static Film findById(int id) throws SQLException {
        ps = con.prepareStatement("select * from FILM where idFilm = ?");
        ps.setInt(1, id);
        rs = ps.executeQuery();
        rs.next();
        Film film = new Film();

        film.setIdFilm(rs.getInt("idFilm"));
        film.setGodina(rs.getInt("godina"));
        film.setNaziv(rs.getNString("naziv"));
        film.setOpis(rs.getNString("opis"));
        film.setReziser(rs.getNString("reziser"));
        film.setTrajanje(rs.getTime("trajanje").toLocalTime());
        film.setUrlTrailer(rs.getString("urltrailer"));
        film.setZanr(rs.getNString("zanr"));
        film.setCoverPath(rs.getString("coverpath"));

        return film;
    }

    public static int insert(Film film) throws SQLException {
        ps = con.prepareStatement("insert into film(naziv, zanr, reziser, godina, trajanje, opis, urltrailer, coverpath) values (?,?,?,?,?,?,?,?)");
        ps.setString(1, film.getNaziv());
        ps.setString(2, film.getZanr());
        ps.setString(3, film.getReziser());
        ps.setInt(4, film.getGodina());
        ps.setTime(5, Time.valueOf(film.getTrajanje()));
        ps.setString(6, film.getOpis());
        ps.setString(7, film.getUrlTrailer());
        ps.setString(8, film.getCoverPath());

        return ps.executeUpdate();
    }

    public static int update(Film film) throws SQLException {
        ps = con.prepareStatement("update film set naziv=?, zanr=?, reziser=?, godina=?, trajanje=?, opis=?, urltrailer=?, coverpath=? where idfilm = ?");
        ps.setString(1, film.getNaziv());
        ps.setString(2, film.getZanr());
        ps.setString(3, film.getReziser());
        ps.setInt(4, film.getGodina());
        ps.setTime(5, Time.valueOf(film.getTrajanje()));
        ps.setString(6, film.getOpis());
        ps.setString(7, film.getUrlTrailer());
        ps.setString(8, film.getCoverPath());
        ps.setInt(9, film.getIdFilm());

        return ps.executeUpdate();
    }
}
