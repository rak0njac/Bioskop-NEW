package com.bioskop.dao;

import com.bioskop.dbconfig;
import com.bioskop.model.Korisnik;
import com.bioskop.model.Sediste;

import java.sql.*;
import java.util.ArrayList;

public class KorisnikDAO {

    private static Connection con = setup();
    private static PreparedStatement ps;
    private static ResultSet rs;
    private static ArrayList<Korisnik> korisnikList = new ArrayList<>();

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


    public static Boolean findByUserAndPass(String user, String pass) throws SQLException {
        ps = con.prepareStatement("select count(*) from korisnik where username = '" + user + "' and password = '" + pass + "'");
        rs = ps.executeQuery();
        rs.next();
        if(rs.getInt(1) == 1)
            return true;
        else return false;
    }

    public static int insert(String ImePrezime, Timestamp DatRodj, String Username, String Password, String Email, String BrTel, String Tip) throws SQLException {
        ps = con.prepareStatement("insert into korisnik(imeprezime, datrodj, username, password, email, brtel, tip) values (?,?,?,?,?,?,?)");
        ps.setString(1, ImePrezime);
        ps.setTimestamp(2, DatRodj);
        ps.setString(3, Username);
        ps.setString(4, Password);
        ps.setString(5, Email);
        ps.setString(6, BrTel);
        ps.setString(7, Tip);
        System.out.println(ps);
        return ps.executeUpdate();
    }
}
