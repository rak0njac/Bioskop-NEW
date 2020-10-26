package com.bioskop.dao;

import com.bioskop.model.Multiplex;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
        ps = con.prepareStatement("select * from MULTIPLEX where idMultiplex = " + id);
        rs = ps.executeQuery();
        rs.next();
        Multiplex mplex = new Multiplex();

        mplex.setIdMultiplex(rs.getInt("idMultiplex"));
        mplex.setNaziv(rs.getString("naziv"));

        return mplex;
    }

    public static Multiplex findByNaziv(String naziv) throws SQLException {
        ps = con.prepareStatement("select * from MULTIPLEX where naziv = '" + naziv + "'");
        rs = ps.executeQuery();
        rs.next();
        Multiplex mplex = new Multiplex();

        mplex.setIdMultiplex(rs.getInt("idmultiplex"));
        mplex.setNaziv(rs.getString("naziv"));

        return mplex;
    }

    public static int delete(Multiplex mplex) throws SQLException {
        ps = con.prepareStatement("delete from multiplex where multiplexid = ?");
        ps.setInt(1, mplex.getIdMultiplex());
        return ps.executeUpdate();
    }

    public static int insert(Multiplex multiplex) throws SQLException {
        ps = con.prepareStatement("insert into multiplex(naziv) values (?)");
        ps.setString(1, multiplex.getNaziv());
        return ps.executeUpdate();

    }
}
