package com.bioskop.dao;

import com.bioskop.model.Film;
import com.bioskop.model.Multiplex;
import com.bioskop.model.Proj_Sala;
import com.bioskop.model.Projekcija;

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
            proj.setVremePocetka(rs.getTimestamp("vremepocetka").toLocalDateTime());
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
        proj.setVremePocetka(rs.getTimestamp("vremepocetka").toLocalDateTime());
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

    public static ArrayList<Projekcija> findByFilmId(int id) throws SQLException {
        projList.clear();
        ps = con.prepareStatement("select * from Projekcija where idFilm=" + id);
        rs = ps.executeQuery();

        while(rs.next()){
            Projekcija proj = new Projekcija();

            proj.setIdProjekcija(rs.getInt("idProjekcija"));
            proj.setFilm(FilmDAO.findById(rs.getInt("idFilm")));
            proj.setPremijera(rs.getBoolean("premijera"));
            proj.setVremePocetka(rs.getTimestamp("vremepocetka").toLocalDateTime());
            proj.setZavrseno(rs.getBoolean("zavrseno"));
            proj.setSala(Proj_SalaDAO.findById(rs.getInt("idSala")));
            projList.add(proj);
        }
        return projList;
    }

    public static ArrayList<String> getDateList(Film film) throws SQLException, ParseException {
        ps = con.prepareStatement("select VremePocetka from Projekcija where idFilm=" + film.getIdFilm());
        rs = ps.executeQuery();

        ArrayList<Projekcija> projekcije = new ArrayList<>();

        while(rs.next()){
            Projekcija p = new Projekcija();
            p.setVremePocetka(rs.getTimestamp("vremepocetka").toLocalDateTime());
            projekcije.add(p);
        }

        return getDateList(projekcije);
    }

    public static ArrayList<String> getTimeAndPSListByDateAndFilm(String date, Film film) throws SQLException, ParseException {
        java.util.Date d = new SimpleDateFormat("dd.MM.yyyy.").parse(date);
        date = new SimpleDateFormat("MM/dd/yyyy").format(d);


        ps = con.prepareStatement("select idsala, vremepocetka from PROJEKCIJA where zavrseno=0 and datediff(day, VremePocetka, '" + date + "') = 0 and idFilm =" + film.getIdFilm());
        rs = ps.executeQuery();
        ArrayList<Projekcija> projekcijas = new ArrayList<>();
        ArrayList<String> vremenaAndSale = new ArrayList<>();

        while(rs.next()){
            Proj_Sala ps = Proj_SalaDAO.findById(rs.getInt("idsala"));
            LocalDateTime vreme = rs.getTimestamp("vremepocetka").toLocalDateTime();

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

        //LocalDate ld = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
        //date = ld.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        //date = new SimpleDateFormat("MM/dd/yyyy").format(d);
        //Multiplex mplex = MultiplexDAO.findByNaziv(mulNaziv);

        st = "select * from PROJEKCIJA where datediff(day, VremePocetka, ?) = 0 " +
                "and idsala in (select idsala from proj_sala where idmultiplex = (select idmultiplex from multiplex where naziv = ?))";

        ps = con.prepareStatement(st);

        ps.setString(1, date);
        ps.setString(2, mulNaziv);
        rs = ps.executeQuery();

        System.out.println(st);

        while(rs.next()){
            Film film = FilmDAO.findById(rs.getInt("idFilm"));
            Boolean ok = true;
            for(Projekcija p : projList)
            {
                if(p.getFilm().getIdFilm() == film.getIdFilm()) {
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
                proj.setVremePocetka(rs.getTimestamp("vremepocetka").toLocalDateTime());
                proj.setZavrseno(rs.getBoolean("zavrseno"));
                proj.setSala(Proj_SalaDAO.findById(rs.getInt("idSala")));
                projList.add(proj);
            }
        }
        return projList;
    }

    public static int insert(Projekcija proj) throws SQLException {
        ps = con.prepareStatement("insert into projekcija (idfilm, idsala, vremepocetka, premijera) values (?,?,?,?)");
        ps.setInt(1, proj.getFilm().getIdFilm());
        ps.setInt(2, proj.getSala().getIdSala());
        ps.setTimestamp(3, Timestamp.valueOf(proj.getVremePocetka()));
        ps.setBoolean(4, proj.isPremijera());

        return ps.executeUpdate();
    }
}
