package com.bioskop.dao;

import com.bioskop.model.Sediste;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SedisteDAO {

    private static Connection con = setup();
    private static PreparedStatement ps;
    private static ResultSet rs;
    private static ArrayList<Sediste> sedisteList = new ArrayList<>();

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

    public static Sediste findById(int id) throws SQLException {
        ps = con.prepareStatement("select * from sediste where idsediste = ?");
        ps.setInt(1, id);
        rs = ps.executeQuery();
        rs.next();
        Sediste sediste = new Sediste();

        sediste.setIdSediste(rs.getInt("idsediste"));
        sediste.setBroj(rs.getInt("broj"));
        sediste.setTip(rs.getNString("tip"));
        sediste.setSala(Proj_SalaDAO.findById(rs.getInt("idsala")));

        return sediste;
    }

    public static ArrayList<Sediste> findBySala(int idSala) throws SQLException {
        sedisteList.clear();

        ps = con.prepareStatement("select * from sediste where idsala = ?");
        ps.setInt(1, idSala);
        rs = ps.executeQuery();

        while(rs.next())
        {
            Sediste sediste = new Sediste();

            sediste.setIdSediste(rs.getInt("idsediste"));
            sediste.setBroj(rs.getInt("broj"));
            sediste.setTip(rs.getNString("tip"));
            sediste.setSala(Proj_SalaDAO.findById(idSala));

            sedisteList.add(sediste);
        }

        return sedisteList;
    }
}
