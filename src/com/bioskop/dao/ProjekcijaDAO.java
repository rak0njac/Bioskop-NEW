package com.bioskop.dao;

import com.bioskop.dbconfig;
import com.bioskop.model.Projekcija;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
        ps = con.prepareStatement("select * from PROJEKCIJA where idProj = " + id);
        rs = ps.executeQuery();
        rs.next();
        Projekcija proj = new Projekcija();

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

    public static ArrayList<Projekcija> findByDate(String date) throws SQLException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");
        java.util.Date d = formatter.parse(date);
        formatter = new SimpleDateFormat("MM/dd/yyyy");
        date = formatter.format(d);

        projList.clear();
        String st = "select * from PROJEKCIJA where datediff(day, VremePocetka, '" + date + "') = 0";
        ps = con.prepareStatement(st);
        rs = ps.executeQuery();

        System.out.println(st);

        while(rs.next()){
            Projekcija proj = new Projekcija();

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
