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
        ps = con.prepareStatement("select idsediste from karta where idprojekcija=" + id + " and status = 'Raspolozivo'");
        rs = ps.executeQuery();
        ArrayList<Sediste> sedista = new ArrayList<>();

        while(rs.next()){
            Sediste sediste = SedisteDAO.findById(rs.getInt("idsediste"));
            sedista.add(sediste);
        }
        return sedista;
    }

    public static ArrayList<Karta> findByIdFilm(int id) throws SQLException {
                kartaList.clear();
                    ps = con.prepareStatement("select * from karta where idprojekcija in (select idprojekcija from projekcija where idfilm = ?) and status = 'Raspolozivo'");
                    ps.setInt(1, id);
                    rs = ps.executeQuery();
                    while(rs.next()){
                        Karta karta = new Karta();
                        //Projekcija projekcija = new Projekcija();
                        //Sediste sediste = new Sediste();
//
                        //projekcija.setIdProjekcija(p.getIdProjekcija());
                        //sediste.setTip();

                        karta.setCena(rs.getDouble("cena"));
                        karta.setProjekcija(ProjekcijaDAO.findById(rs.getInt("idprojekcija")));
                        karta.setSediste(SedisteDAO.findById(rs.getInt("idSediste")));
                        karta.setStatus(rs.getNString("status"));
                        kartaList.add(karta);
                    }

        return kartaList;

    }

    public static String reserveTicket(int idProjekcija, String tipSedista, String username) throws SQLException {

        ps = con.prepareStatement("select top 1 idkarta from karta where IdProjekcija = ? " +
                "and IdSediste in (select idsediste from SEDISTE where Tip = ?) " +
                "and status = 'Raspolozivo'");
        ps.setInt(1, idProjekcija);
        ps.setString(2, tipSedista);
        rs = ps.executeQuery();

        if(rs.next())
        {
            int idKarta = rs.getInt("idkarta");

            ps = con.prepareStatement("update karta set status = 'Rezervisano', idkorisnik = (select idkorisnik from korisnik where username=?) where idkarta = ?");
            ps.setString(1, username);
            ps.setInt(2, idKarta);
            ps.executeUpdate();

            return "USPESNO";
        }
        else return "NEMA KARATA";
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
