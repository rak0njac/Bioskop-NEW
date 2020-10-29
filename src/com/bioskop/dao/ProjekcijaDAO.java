package com.bioskop.dao;

import com.bioskop.helpers.DateHelper;
import com.bioskop.model.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProjekcijaDAO {

    private static Connection con = setup();
    private static PreparedStatement ps;
    private static ResultSet rs;
    private static ArrayList<Projekcija> projList = new ArrayList<Projekcija>();

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

    public static ArrayList<Projekcija> findAll() throws SQLException {
        projList.clear();
        ps = con.prepareStatement("select * from PROJEKCIJA");
        rs = ps.executeQuery();
        while(rs.next()){
            Projekcija proj = new Projekcija();

            proj.setIdProjekcija(rs.getInt("idProjekcija"));
            proj.setFilm(FilmDAO.findById(rs.getInt("idFilm")));
            proj.setPremijera(rs.getBoolean("premijera"));
            proj.setVremePocetka(new DateHelper(rs.getTimestamp("vremepocetka").toLocalDateTime()));
            proj.setZavrseno(rs.getBoolean("zavrseno"));
            proj.setSala(Proj_SalaDAO.findById(rs.getInt("idSala")));
            proj.setKarte((ArrayList<Karta>) KartaDAO.findByIdProj(proj.getIdProjekcija()).clone());
            projList.add(proj);
        }
        return projList;
    }

    public static Projekcija findById(int id) throws SQLException {
        ps = con.prepareStatement("select * from PROJEKCIJA where idProjekcija = ?");
        ps.setInt(1, id);
        rs = ps.executeQuery();
        rs.next();
        Projekcija proj = new Projekcija();

        proj.setIdProjekcija(rs.getInt("idProjekcija"));
        proj.setFilm(FilmDAO.findById(rs.getInt("idFilm")));
        proj.setPremijera(rs.getBoolean("premijera"));
        proj.setVremePocetka(new DateHelper(rs.getTimestamp("vremepocetka").toLocalDateTime()));
        proj.setZavrseno(rs.getBoolean("zavrseno"));
        proj.setSala(Proj_SalaDAO.findById(rs.getInt("idSala")));

        return proj;
    }


    public static ArrayList<Projekcija> findByDateAndMultiplex(String date, int idMplex) throws SQLException, ParseException {
        projList.clear();
        String st;

        st = "select * from PROJEKCIJA where datediff(day, VremePocetka, ?) = 0 " +
                "and idsala in (select idsala from proj_sala where idmultiplex = ?)";

        ps = con.prepareStatement(st);

        ps.setString(1, date);
        ps.setInt(2, idMplex);
        rs = ps.executeQuery();

        System.out.println(idMplex);

        while(rs.next()){
            Film film = FilmDAO.findById(rs.getInt("idFilm"));
            Boolean ok = true;
            for(Projekcija p : projList)
            {
                if(p.getFilm().getIdFilm() == film.getIdFilm() && !(p.isPremijera())) {
                    ok = false;
                    break;
                }
            }
            if(ok)
            {
                Projekcija proj = new Projekcija();

                proj.setIdProjekcija(rs.getInt("idProjekcija"));
                proj.setFilm(film);
                proj.setPremijera(rs.getBoolean("premijera"));
                proj.setVremePocetka(new DateHelper(rs.getTimestamp("vremepocetka").toLocalDateTime()));
                proj.setZavrseno(rs.getBoolean("zavrseno"));
                proj.setSala(Proj_SalaDAO.findById(rs.getInt("idSala")));
                projList.add(proj);
            }
        }
        return projList;
    }

    public static int insert(Projekcija proj) throws SQLException {
        ps = con.prepareStatement("insert into projekcija (idfilm, idsala, vremepocetka, premijera) values (?,?,?,?)", ps.RETURN_GENERATED_KEYS);
        ps.setInt(1, proj.getFilm().getIdFilm());
        ps.setInt(2, proj.getSala().getIdSala());
        ps.setTimestamp(3, Timestamp.valueOf(proj.getVremePocetka().getLocalDateTime()));
        ps.setBoolean(4, proj.isPremijera());
        ps.executeUpdate();

        rs = ps.getGeneratedKeys();
        rs.next();
        return rs.getInt(1);
    }
}