package com.bioskop.servlet.user.film;

import com.bioskop.dao.KartaDAO;
import com.bioskop.dao.ProjekcijaDAO;
import com.bioskop.helpers.DateHelper;
import com.bioskop.model.Film;
import com.bioskop.model.Karta;
import com.bioskop.model.Projekcija;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@WebServlet("/film")
public class filmInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        Set<DateHelper> datumi = new HashSet<>();

        try {
            Projekcija projekcija = ProjekcijaDAO.findById(id);
            Film film = projekcija.getFilm();
            ArrayList<Karta> karte = KartaDAO.findByIdFilm(film.getIdFilm());

            for(Karta k : karte)
            {
                LocalDate date = k.getProjekcija().getVremePocetka().toLocalDate();
                datumi.add(new DateHelper(date));
            }

            req.setAttribute("datumi", datumi);
            req.setAttribute("film", film);
            req.setAttribute("karte", karte);


            req.getRequestDispatcher("/WEB-INF/jsp/film.jsp").forward(req, resp);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}