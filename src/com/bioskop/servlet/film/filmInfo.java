package com.bioskop.servlet.film;

import com.bioskop.dao.FilmDAO;
import com.bioskop.dao.KartaDAO;
import com.bioskop.dao.ProjekcijaDAO;
import com.bioskop.model.Film;
import com.bioskop.model.Karta;
import com.bioskop.model.Projekcija;
import com.bioskop.model.Sediste;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

@WebServlet("/film")
public class filmInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("frmId"));
        String datum = req.getParameter("frmDatum");
        //System.out.println(datum);
        try {
            Projekcija projekcija = ProjekcijaDAO.findById(id);
            Film film = projekcija.getFilm();
            //ArrayList<Projekcija> projekcije = ProjekcijaDAO.findByFilmId(film.getIdFilm());
            //ArrayList<Sediste> sedista = KartaDAO.getFreeSeatsByProjId(id);
            ArrayList<Karta> karte = KartaDAO.findByIdFilm(film.getIdFilm());
            Gson gson = new Gson();


            req.setAttribute("film", film);
            //req.setAttribute("projekcije", projekcije);
            //req.setAttribute("sed", sedista);
            req.setAttribute("karteJSON", gson.toJson(karte));
            req.setAttribute("karte", karte);


            req.getRequestDispatcher("WEB-INF/jsp/film.jsp").forward(req, resp);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}