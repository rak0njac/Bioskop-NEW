package com.bioskop.dao;

import com.bioskop.dbconfig;
import com.bioskop.model.Film;
import com.bioskop.model.Multiplex;
import com.bioskop.model.Proj_Sala;
import com.bioskop.model.Projekcija;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

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
            proj.setVremePocetka(rs.getTimestamp("vremepocetka"));
            proj.setZavrseno(rs.getBoolean("zavrseno"));
            proj.setSala(Proj_SalaDAO.findById(rs.getInt("idSala")));
            projList.add(proj);
        }
        return projList;
    }

    public static Projekcija findById(int id) throws SQLException {
        ps = con.prepareStatement("select * from PROJEKCIJA where idProjekcija = " + id);
        rs = ps.executeQuery();
        rs.next();
        Projekcija proj = new Projekcija();

        proj.setIdProjekcija(rs.getInt("idProjekcija"));
        proj.setFilm(FilmDAO.findById(rs.getInt("idFilm")));
        proj.setPremijera(rs.getBoolean("premijera"));
        proj.setVremePocetka(rs.getTimestamp("vremepocetka"));
        proj.setZavrseno(rs.getBoolean("zavrseno"));
        proj.setSala(Proj_SalaDAO.findById(rs.getInt("idSala")));

        return proj;
    }

    public static ArrayList<String> getDateList(ArrayList<Projekcija> list){
        ArrayList<String> datumi = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");

        for(Projekcija p : list){
            Boolean skip = false;
            String datum = formatter.format(p.getVremePocetka());
            for(String d : datumi){
                if(d.equals(datum))
                {
                    skip = true;
                    break;
                }
            }
            if(!skip)
            {
                datumi.add(datum);
            }
        }
        return datumi;
    }

    public static ArrayList<String> getDateList(Film film) throws SQLException, ParseException {
        ps = con.prepareStatement("select VremePocetka from Projekcija where idFilm=" + film.getIdFilm());
        rs = ps.executeQuery();

        ArrayList<Projekcija> projekcije = new ArrayList<>();

        while(rs.next()){
            Projekcija p = new Projekcija();
            p.setVremePocetka(rs.getTimestamp("vremepocetka"));
            projekcije.add(p);
        }

        return getDateList(projekcije);
    }

    public static ArrayList<String> getTimeAndPSListByDateAndFilm(String date, Film film) throws SQLException, ParseException {
        java.util.Date d = new SimpleDateFormat("dd.MM.yyyy.").parse(date);
        date = new SimpleDateFormat("MM/dd/yyyy").format(d);

        ps = con.prepareStatement("select idsala, vremepocetka from PROJEKCIJA where datediff(day, VremePocetka, '" + date + "') = 0 and idFilm =" + film.getIdFilm());
        rs = ps.executeQuery();
        ArrayList<Projekcija> projekcijas = new ArrayList<>();
        ArrayList<String> vremenaAndSale = new ArrayList<>();

        while(rs.next()){
            Proj_Sala ps = Proj_SalaDAO.findById(rs.getInt("idsala"));
            Timestamp vreme = rs.getTimestamp("vremepocetka");

            Projekcija p = new Projekcija();
            p.setSala(ps);
            p.setVremePocetka(vreme);
            projekcijas.add(p);
        }

        Collections.sort(projekcijas, new Comparator<Projekcija>() {
            public int compare(Projekcija o1, Projekcija o2) {
                return o1.getVremePocetka().compareTo(o2.getVremePocetka());
            }
        });

        for(Projekcija p : projekcijas){
            String st = new SimpleDateFormat("hh:mm").format(p.getVremePocetka()) + " - Sala " + p.getSala().getBroj();
            vremenaAndSale.add(st);
        }
        return vremenaAndSale;
    }

    public static ArrayList<Projekcija> findByDateAndMultiplex(String date, String mulNaziv) throws SQLException, ParseException {
        projList.clear();
        String st;
//        if(date.equals("ALL") && mulNaziv.equals("ALL"))
//        {
//            return findAll();
//        }
//        else if(date.equals("ALL") && !mulNaziv.equals("ALL"))
//        {
//            Multiplex mplex = MultiplexDAO.findByNaziv(mulNaziv);
//            st = "select * from PROJEKCIJA where idsala in (select idsala from proj_sala where idmultiplex = " + mplex.getIdMultiplex() + ")";
//        }
//        else if(!date.equals("ALL") && mulNaziv.equals("ALL"))
//        {
//            java.util.Date d = new SimpleDateFormat("dd.MM.yyyy.").parse(date);
//            date = new SimpleDateFormat("MM/dd/yyyy").format(d);
//
//            st = "select * from PROJEKCIJA where datediff(day, VremePocetka, '" + date + "') = 0 ";
//        }
//        else{
            java.util.Date d = new SimpleDateFormat("dd.MM.yyyy.").parse(date);
            date = new SimpleDateFormat("MM/dd/yyyy").format(d);
            Multiplex mplex = MultiplexDAO.findByNaziv(mulNaziv);

            st = "select * from PROJEKCIJA where datediff(day, VremePocetka, '" + date + "') = 0 " +
                    "and idsala in (select idsala from proj_sala where idmultiplex = " + mplex.getIdMultiplex() + ")";
//        }
        ps = con.prepareStatement(st);
        rs = ps.executeQuery();

        System.out.println(st);

        while(rs.next()){
            Projekcija proj = new Projekcija();

            proj.setIdProjekcija(rs.getInt("idProjekcija"));
            proj.setFilm(FilmDAO.findById(rs.getInt("idFilm")));
            proj.setPremijera(rs.getBoolean("premijera"));
            proj.setVremePocetka(rs.getTimestamp("vremepocetka"));
            proj.setZavrseno(rs.getBoolean("zavrseno"));
            proj.setSala(Proj_SalaDAO.findById(rs.getInt("idSala")));
            projList.add(proj);

        }
        return projList;
    }
}
