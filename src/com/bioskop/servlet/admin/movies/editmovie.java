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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;

@WebServlet("/admin/movies/editmovie")
public class editmovie extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("admin") == null)
        {
            req.setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
        else{
            if(req.getParameter("id") != null)
            {
                int id = Integer.parseInt(req.getParameter("id"));
                try {
                    Film film = FilmDAO.findById(id);
                    req.setAttribute("film", film);
                    req.getRequestDispatcher("/WEB-INF/jsp/admin/movies/edit.jsp").forward(req,resp);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            else{
                try {
                    ArrayList<Film> movies = FilmDAO.findAll();
                    movies.sort(Comparator.comparing(Film::getNaziv));
                    req.setAttribute("movies", movies);
                    req.getRequestDispatcher("/WEB-INF/jsp/admin/movies/editmovie.jsp").forward(req,resp);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Film film = new Film();

        film.setIdFilm(Integer.parseInt(req.getParameter("id")));
        film.setNaziv(req.getParameter("naziv"));
        film.setZanr(req.getParameter("zanr"));
        film.setReziser(req.getParameter("reziser"));
        film.setGodina(Integer.parseInt(req.getParameter("godina")));
        LocalTime time = LocalTime.parse(req.getParameter("trajanje"));
        film.setTrajanje(time);
        film.setOpis(req.getParameter("opis"));
        film.setUrlTrailer(req.getParameter("urltrailer"));
        String coverPath = req.getParameter("coverpath");
        String oldCover = req.getParameter("oldCover");
        if(coverPath==null)
            film.setCoverPath(oldCover);
        else film.setCoverPath(coverPath);

        try {
            FilmDAO.update(film);
            req.setAttribute("state", "USPESNO IZMENJEN FILM");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            req.setAttribute("state", "GRESKA PRILIKOM UBACIVANJA FILMA: " + throwables.getMessage() + throwables.getLocalizedMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }    }
}
