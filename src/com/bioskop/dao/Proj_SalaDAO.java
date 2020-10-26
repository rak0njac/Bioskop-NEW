package com.bioskop.dao;

import com.bioskop.model.Proj_Sala;

import java.sql.*;
import java.util.ArrayList;

public class Proj_SalaDAO {

    private static Connection con = setup();
    private static PreparedStatement ps;
    private static ResultSet rs;
    private static ArrayList<Proj_Sala> psList = new ArrayList<Proj_Sala>();

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

    public static ArrayList<Proj_Sala> findAll() throws SQLException {
        psList.clear();
        ps = con.prepareStatement("select * from PROJ_SALA");
        rs = ps.executeQuery();
        while(rs.next()){
            Proj_Sala ps = new Proj_Sala();

            ps.setIdSala(rs.getInt("idSala"));
            ps.setBroj(rs.getInt("broj"));
            ps.setMultiplex(MultiplexDAO.findById(rs.getInt("idMultiplex")));
            psList.add(ps);
        }
        return psList;
    }

    public static ArrayList<Proj_Sala> findByMultiplexId(int id) throws SQLException {
        psList.clear();
        ps = con.prepareStatement("select * from PROJ_SALA where idMultiplex=" + id);
        rs = ps.executeQuery();
        while(rs.next()){
            Proj_Sala ps = new Proj_Sala();

            ps.setIdSala(rs.getInt("idSala"));
            ps.setBroj(rs.getInt("broj"));
            ps.setMultiplex(MultiplexDAO.findById(id));
            psList.add(ps);
        }
        return psList;
    }

    public static Proj_Sala findById(int id) throws SQLException {
        ps = con.prepareStatement("select * from PROJ_SALA where idSala = " + id);
        rs = ps.executeQuery();
        rs.next();
        Proj_Sala ps = new Proj_Sala();

        ps.setIdSala(rs.getInt("idSala"));
        ps.setBroj(rs.getInt("broj"));
        ps.setMultiplex(MultiplexDAO.findById(rs.getInt("idMultiplex")));

        return ps;
    }
}
