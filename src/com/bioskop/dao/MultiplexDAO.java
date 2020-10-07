package com.bioskop.dao;

import com.bioskop.dbconfig;
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

        mplex.setNaziv(rs.getString("naziv"));

        return mplex;
    }
}
