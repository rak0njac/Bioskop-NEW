package com.bioskop.dao;

import com.bioskop.model.Karta;
import com.bioskop.model.Projekcija;
import com.bioskop.model.Sediste;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class KartaDAO {

    private static Connection con = setup();
    private static PreparedStatement ps;
    private static ResultSet rs;
    private static ArrayList<Karta> kartaList = new ArrayList<>();

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

    public static ArrayList<Sediste> getFreeSeatsByProjId(int id) throws SQLException {
        ps = con.prepareStatement("select idsediste from karta where idprojekcija=" + id + " and status = 'Raspolozivo'");
        rs = ps.executeQuery();
        ArrayList<Sediste> sedista = new ArrayList<>();

        while(rs.next()){
            Sediste sediste = SedisteDAO.findById(rs.getInt("idsediste"));
            sedista.add(sediste);
        }
        return sedista;
    }

    public static ArrayList<Karta> findByIdFilmIdMplex(int idFilm, int idMplex) throws SQLException {
                kartaList.clear();
                    ps = con.prepareStatement("select * from karta where idprojekcija in " +
                            "(select idprojekcija from projekcija where idfilm = ? and idsala in " +
                            "(select idsala from proj_sala where idmultiplex = ?)) " +
                            "and status = 'Raspolozivo'");
                    ps.setInt(1, idFilm);
        ps.setInt(2, idMplex);
        rs = ps.executeQuery();
                    while(rs.next()){
                        Karta karta = new Karta();

                        karta.setCena(rs.getDouble("cena"));
                        karta.setProjekcija(ProjekcijaDAO.findById(rs.getInt("idprojekcija")));
                        karta.setSediste(SedisteDAO.findById(rs.getInt("idSediste")));
                        karta.setStatus(rs.getNString("status"));
                        kartaList.add(karta);
                    }

        return kartaList;

    }

    public static Karta findByProjAndSeat(int idProjekcija, String tipSedista) throws SQLException {
        ps = con.prepareStatement("select top 1 * from karta where IdProjekcija = ? " +
                "and IdSediste in (select idsediste from SEDISTE where Tip = ?) " +
                "and idkorisnik is null");
        ps.setInt(1, idProjekcija);
        ps.setString(2, tipSedista);
        rs = ps.executeQuery();

        Karta karta = new Karta();

        if (rs.next()) {
            karta.setIdKarta(rs.getInt("idkarta"));
            karta.setStatus("Raspolozivo");
            karta.setSediste(SedisteDAO.findById(rs.getInt("idsediste")));
            karta.setProjekcija(ProjekcijaDAO.findById(idProjekcija));
            karta.setCena(rs.getDouble("cena"));
        }
        return karta;
    }

    public static String reserveTicket(Karta karta, String username) throws SQLException {
            ps = con.prepareStatement("update karta set idkorisnik = (select idkorisnik from korisnik where username=?) where idkarta = ?");
            ps.setString(1, username);
            ps.setInt(2, karta.getIdKarta());
            if(ps.executeUpdate() > 0)

            return "USPESNO";

        else return "NEMA KARATA";
    }

    public static int insert(Projekcija projekcija, String tipSedista, Double cena) throws SQLException {
        ps = con.prepareStatement("select * from sediste where idsala = ?");
        ps.setInt(1, projekcija.getSala().getIdSala());
        rs = ps.executeQuery();
        while(rs.next())
        {
            if(rs.getString("tip").equals(tipSedista))
            {
                ps = con.prepareStatement("insert into karta(idprojekcija, idsediste, cena) values (?,?,?)");
                ps.setInt(1,projekcija.getIdProjekcija());
                ps.setInt(2, rs.getInt("idsediste"));
                ps.setDouble(3, cena);
                ps.executeUpdate();
            }
        }
        return -1;
    }

    public static void discount(Karta karta, int disc) throws SQLException {
        ps = con.prepareStatement("update karta set cena = ? where idkarta = ?");
        double cena = karta.getCena() - (karta.getCena() * disc / 100);
        ps.setDouble(1, cena);
        ps.setInt(2, karta.getIdKarta());

        ps.executeUpdate();
    }

    public static ArrayList<Karta> findByIdProj(int idProjekcija) throws SQLException {
        kartaList.clear();
        ps = con.prepareStatement("select * from karta where idprojekcija = ?");
        ps.setInt(1, idProjekcija);
        rs = ps.executeQuery();
        while(rs.next())
        {
            Karta karta = new Karta();
            karta.setIdKarta(rs.getInt("idkarta"));
            karta.setCena(rs.getDouble("cena"));
            //karta.setProjekcija(ProjekcijaDAO.findById(idProjekcija));
            karta.setSediste(SedisteDAO.findById(rs.getInt("idsediste")));
            karta.setStatus(rs.getNString("status"));
            karta.setKorisnik(KorisnikDAO.findById(rs.getInt("idkorisnik")));
            kartaList.add(karta);
        }
        return kartaList;
    }

    public static ArrayList<Karta> findByIdKorisnik(int idKorisnik) throws SQLException {
        kartaList.clear();
        ps=con.prepareStatement("select * from karta where idkorisnik = ?");
        ps.setInt(1,idKorisnik);
        rs = ps.executeQuery();
        while(rs.next())
        {
            Karta karta = new Karta();
            karta.setIdKarta(rs.getInt("idkarta"));
            karta.setCena(rs.getDouble("cena"));
            karta.setProjekcija(ProjekcijaDAO.findById(rs.getInt("idprojekcija")));
            karta.setSediste(SedisteDAO.findById(rs.getInt("idsediste")));
            karta.setStatus(rs.getNString("status"));
            karta.setKorisnik(KorisnikDAO.findById(idKorisnik));
            kartaList.add(karta);
        }
        return kartaList;
    }

    public static int approveReservation(int idKarta) throws SQLException {
        ps = con.prepareStatement("update karta set status = 'Rezervisano' where idkarta = ?");
        ps.setInt(1, idKarta);
        return ps.executeUpdate();
    }

    public static int cancelReservation(int idKarta) throws SQLException {
        ps = con.prepareStatement("update karta set idkorisnik = NULL where idkarta = ?");
        ps.setInt(1, idKarta);
        return ps.executeUpdate();
    }

//    public static ArrayList<Film> findAll() throws SQLException {
//        filmList.clear();
//        ps = con.prepareStatement("select * from FILM");
//        rs = ps.executeQuery();
//        while(rs.next()){
//            Film film = new Film();
//
//            film.setIdFilm(rs.getInt("idFilm"));
//            film.setGodina(rs.getInt("godina"));
//            film.setNaziv(rs.getNString("naziv"));
//            film.setOpis(rs.getNString("opis"));
//            film.setReziser(rs.getNString("reziser"));
//            film.setTrajanje(rs.getTime("trajanje"));
//            film.setUrlTrailer(rs.getString("urltrailer"));
//            film.setZanr(rs.getNString("zanr"));
//            film.setCoverPath(rs.getString("coverpath"));
//            filmList.add(film);
//        }
//        return filmList;
//    }
//
//    public static Film findById(int id) throws SQLException {
//        ps = con.prepareStatement("select * from FILM where idFilm = " + id);
//        rs = ps.executeQuery();
//        rs.next();
//        Film film = new Film();
//
//        film.setIdFilm(rs.getInt("idFilm"));
//        film.setGodina(rs.getInt("godina"));
//        film.setNaziv(rs.getNString("naziv"));
//        film.setOpis(rs.getNString("opis"));
//        film.setReziser(rs.getNString("reziser"));
//        film.setTrajanje(rs.getTime("trajanje"));
//        film.setUrlTrailer(rs.getString("urltrailer"));
//        film.setZanr(rs.getNString("zanr"));
//        film.setCoverPath(rs.getString("coverpath"));
//
//        return film;
//    }
}
