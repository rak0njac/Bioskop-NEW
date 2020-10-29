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
        if(rs.next())
        {
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
            k.setKlub(rs.getNString("klub"));

            return k;

        }
else return null;
    }

    public static int banByUser(String user) throws SQLException {
        ps = con.prepareStatement("update korisnik set status = 'Neaktivan' where username = ?");
        ps.setString(1, user);
        return ps.executeUpdate();
    }

    public static int insert(Korisnik k) throws SQLException {
        ps = con.prepareStatement("insert into korisnik(imeprezime, datrodj, username, password, email, brtel, tip) values (?,?,?,?,?,?,?)", ps.RETURN_GENERATED_KEYS);
        ps.setString(1, k.getImePrezime());
        ps.setDate(2, Date.valueOf(k.getDatRodj()));
        ps.setString(3, k.getUsername());
        ps.setString(4, k.getPassword());
        ps.setString(5, k.getEmail());
        ps.setString(6, k.getBrTel());
        ps.setString(7, k.getTip());

        System.out.println(ps);
        ps.executeUpdate();

        rs = ps.getGeneratedKeys();
        rs.next();
        return rs.getInt(1);
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
            k.setKlub(rs.getNString("klub"));


            return k;
        }
        else return null;
    }

    public static void addPoints(Korisnik user, int idKarta) throws SQLException {
        ps = con.prepareStatement("update korisnik set brpoena = brpoena + (select cena * 0.1 from karta where idkarta = ?) where idkorisnik = ?");
        ps.setInt(1, idKarta);
        ps.setInt(2, user.getIdKorisnik());
        ps.executeUpdate();
    }

    public static int update(Korisnik k) throws SQLException {
        ps = con.prepareStatement("update korisnik set email = ?, imeprezime = ?, brtel = ?, datrodj = ?, password = ? where idkorisnik = ?");
        ps.setString(1, k.getEmail());
        ps.setString(2, k.getImePrezime());
        ps.setString(3, k.getBrTel());
        ps.setDate(4, Date.valueOf(k.getDatRodj()));
        ps.setString(5, k.getPassword());
        ps.setInt(6, k.getIdKorisnik());

        return ps.executeUpdate();
    }

    public static int enrollInClub(String username, String klub) throws SQLException {
        ps = con.prepareStatement("update korisnik set klub = ? where username = ?");
        ps.setString(1, klub);
        ps.setString(2, username);
        return ps.executeUpdate();
    }
}
