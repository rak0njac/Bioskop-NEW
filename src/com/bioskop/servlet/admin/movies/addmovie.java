package com.bioskop.servlet.admin.movies;

import com.bioskop.dao.FilmDAO;
import com.bioskop.model.Film;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;

@WebServlet("/admin/movies/addmovie")
public class addmovie extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("admin") == null)
        {
            req.setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
        else{
            req.getRequestDispatcher("/WEB-INF/jsp/admin/movies/addmovie.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("admin") == null)
        {
            req.setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
        else{
            Film film = new Film();

            film.setNaziv(req.getParameter("naziv"));
            film.setZanr(req.getParameter("zanr"));
            film.setReziser(req.getParameter("reziser"));
            film.setGodina(Integer.parseInt(req.getParameter("godina")));
            LocalTime time = LocalTime.parse(req.getParameter("trajanje"));
            film.setTrajanje(time);
            film.setOpis(req.getParameter("opis"));
            film.setUrlTrailer(req.getParameter("urltrailer"));
            film.setCoverPath(req.getParameter("coverpath"));

            try {
                FilmDAO.insert(film);
                req.setAttribute("state", "USPESNO UBACEN FILM");
                req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                req.setAttribute("state", "GRESKA PRILIKOM UBACIVANJA FILMA: " + throwables.getMessage() + throwables.getLocalizedMessage());
                req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
            }
        }
    }
}
