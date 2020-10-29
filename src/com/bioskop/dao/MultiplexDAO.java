package com.bioskop.dao;

import com.bioskop.model.Multiplex;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class MultiplexDAO {

    private static Connection con = setup();
    private static PreparedStatement ps;
    private static ResultSet rs;
    private static ArrayList<Multiplex> mplexList = new ArrayList<Multiplex>();

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

    public static ArrayList<Multiplex> findAll() throws SQLException {
        mplexList.clear();
        ps = con.prepareStatement("select * from MULTIPLEX");
        rs = ps.executeQuery();
        while(rs.next()){
            Multiplex mplex = new Multiplex();

            mplex.setIdMultiplex(rs.getInt("idMultiplex"));
            mplex.setNaziv(rs.getString("naziv"));
            mplexList.add(mplex);
        }
        return mplexList;
    }

    public static Multiplex findById(int id) throws SQLException {
        ps = con.prepareStatement("select * from MULTIPLEX where idMultiplex = ?");
        ps.setInt(1, id);
        rs = ps.executeQuery();
        rs.next();
        Multiplex mplex = new Multiplex();

        mplex.setIdMultiplex(rs.getInt("idMultiplex"));
        mplex.setNaziv(rs.getString("naziv"));

        return mplex;
    }

    public static Multiplex findByNaziv(String naziv) throws SQLException {
        ps = con.prepareStatement("select * from MULTIPLEX where naziv = ?");
        ps.setString(1, naziv);
        rs = ps.executeQuery();
        rs.next();
        Multiplex mplex = new Multiplex();

        mplex.setIdMultiplex(rs.getInt("idmultiplex"));
        mplex.setNaziv(rs.getString("naziv"));

        return mplex;
    }

    public static int delete(Multiplex mplex) throws SQLException {
        ps = con.prepareStatement("delete from multiplex where idmultiplex = ?");
        ps.setInt(1, mplex.getIdMultiplex());
        return ps.executeUpdate();
    }

    public static int insert(Multiplex multiplex) throws SQLException {
        ps = con.prepareStatement("insert into multiplex(naziv) values (?)");
        ps.setString(1, multiplex.getNaziv());
        return ps.executeUpdate();

    }

    public static void insert(String naziv, Map<Integer, String[]> myMap) throws SQLException { //TODO: pozivati insert iz Proj_SalaDAO i SedisteDAO umesto ovde ih insertovati implicitno
        ps = con.prepareStatement("insert into multiplex(naziv) values (?)", ps.RETURN_GENERATED_KEYS);
        ps.setString(1, naziv);
        ps.executeUpdate();
        int idMultiplex = 0;

        try (ResultSet rs = ps.getGeneratedKeys()) {
            rs.next();
            idMultiplex = rs.getInt(1);
        }

        for (Map.Entry<Integer, String[]> entry : myMap.entrySet()) {
            ps = con.prepareStatement("insert into proj_sala(broj, idmultiplex) values (?,?)", ps.RETURN_GENERATED_KEYS);
            ps.setInt(1, entry.getKey());
            ps.setInt(2, idMultiplex);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int idSala = rs.getInt(1);
            int count = 0;

            for(String s : entry.getValue())
            {
                count++;
                ps = con.prepareStatement("insert into sediste(broj, tip, idsala) values (?,?,?)");
                ps.setInt(1, count);
                ps.setString(2, s);
                ps.setInt(3, idSala);
                ps.executeUpdate();
            }
        }

    }
}
