package com.bioskop.dao;

import com.bioskop.model.Korisnik;

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


    public static Korisnik findByUserAndPass(String user, String pass) throws SQLException {
        ps = con.prepareStatement("select * from korisnik where username = ? and password = ?");
        ps.setString(1,user);
        ps.setString(2,pass);
        rs = ps.executeQuery();
        rs.next();

        Korisnik k = new Korisnik();

        k.setBrPoena(rs.getInt("brpoena"));
        k.setBrTel(rs.getNString("brtel"));
        k.setDatRodj(rs.getDate("datrodj").toLocalDate());
        k.setEmail(rs.getNString("email"));
        k.setIdKorisnik(rs.getInt("idkorisnik"));
        k.setImePrezime(rs.getNString("imeprezime"));
        k.setPassword(pass);
        k.setStatus(rs.getNString("status"));
        k.setTip(rs.getNString("tip"));
        k.setUsername(user);

        return k;
    }

    public static int banByUser(String user) throws SQLException {
        ps = con.prepareStatement("update korisnik set status = 'Neaktivan' where username = ?");
        ps.setString(1, user);
        return ps.executeUpdate();
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

    public static void removePoints(int i, String username) throws SQLException {
        ps = con.prepareStatement("update korisnik set brpoena = brpoena - ? where username = ?");
        ps.setInt(1, i);
        ps.setString(2, username);

        ps.executeUpdate();
    }

    public static Korisnik findById(int idkorisnik) throws SQLException {
        ps = con.prepareStatement("select * from korisnik where idkorisnik = ?");
        ps.setInt(1, idkorisnik);
        rs = ps.executeQuery();
        if(rs.next())
        {
            Korisnik k = new Korisnik();

            k.setBrPoena(rs.getInt("brpoena"));
            k.setBrTel(rs.getNString("brtel"));
            k.setDatRodj(rs.getDate("datrodj").toLocalDate());
            k.setEmail(rs.getNString("email"));
            k.setIdKorisnik(idkorisnik);
            k.setImePrezime(rs.getNString("imeprezime"));
            k.setStatus(rs.getNString("status"));
            k.setTip(rs.getNString("tip"));
            k.setUsername(rs.getNString("username"));

            return k;
        }
        else return null;
    }
}
