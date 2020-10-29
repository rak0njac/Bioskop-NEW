package com.bioskop.servlet;

import com.bioskop.dao.KartaDAO;
import com.bioskop.dao.MultiplexDAO;
import com.bioskop.dao.ProjekcijaDAO;
import com.bioskop.helpers.DateHelper;
import com.bioskop.model.Film;
import com.bioskop.model.Karta;
import com.bioskop.model.Multiplex;
import com.bioskop.model.Projekcija;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@WebServlet("/film")
public class film extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int idProjekcija = Integer.parseInt(req.getParameter("id-projekcija"));
        int idMultiplex = Integer.parseInt(req.getParameter("id-multiplex"));

        Set<DateHelper> datumi = new HashSet<>();

        try {
            Projekcija projekcija = ProjekcijaDAO.findById(idProjekcija);
            Film film = projekcija.getFilm();
            Multiplex multiplex = MultiplexDAO.findById(idMultiplex);
            ArrayList<Karta> karte = KartaDAO.findByFilmMplex(film, multiplex);

            for(Karta k : karte)
            {
                datumi.add(k.getProjekcija().getVremePocetka());
            }

            req.setAttribute("datumi", datumi);
            req.setAttribute("film", film);
            req.setAttribute("karte", karte);


            req.getRequestDispatcher("/WEB-INF/jsp/film.jsp").forward(req, resp);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            req.getSession().setAttribute("state", "GRESKA PRILIKOM POTRAZIVANJA FILMA");
            req.getRequestDispatcher("/WEB-INF/jsp/film.jsp").forward(req,resp);
        }
    }
}