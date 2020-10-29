package com.bioskop.servlet.admin.filmovi;

import com.bioskop.dao.FilmDAO;
import com.bioskop.model.Film;
import com.bioskop.model.Korisnik;

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

@WebServlet("/admin/filmovi/izmeni_film")
public class izmeniFilm extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!((Korisnik) req.getSession().getAttribute("user")).getTip().equals("Admin"))
        {
            req.setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
        else{
            if(req.getParameter("id") != null) //TODO: Promeniti u id-film
            {
                int idFilm = Integer.parseInt(req.getParameter("id"));
                try {
                    Film film = FilmDAO.findById(idFilm);
                    req.setAttribute("film", film);
                    req.getRequestDispatcher("/WEB-INF/jsp/admin/filmovi/izmeni_film_forma.jsp").forward(req,resp);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    req.setAttribute("state", "GRESKA PRILIKOM UBACIVANJA FILMA: " + throwables.getMessage() + throwables.getLocalizedMessage());
                    req.getRequestDispatcher("/WEB-INF/jsp/admin/movies/izmeni_film.jsp").forward(req,resp);
                }
            }
            else{
                try {
                    ArrayList<Film> filmovi = FilmDAO.findAll();
                    filmovi.sort(Comparator.comparing(Film::getNaziv));
                    req.setAttribute("filmovi", filmovi);
                    req.getRequestDispatcher("/WEB-INF/jsp/admin/movies/izmeni_film.jsp").forward(req,resp);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    req.setAttribute("state", "GRESKA PRILIKOM UBACIVANJA FILMA: " + throwables.getMessage() + throwables.getLocalizedMessage());
                    req.getRequestDispatcher("/WEB-INF/jsp/admin/movies/izmeni_film.jsp").forward(req,resp);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO: Validacija u JSP

        if(!((Korisnik) req.getSession().getAttribute("user")).getTip().equals("Admin"))
        {
            req.setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
        else {
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
                req.getRequestDispatcher("/WEB-INF/jsp/admin/movies/izmeni_film.jsp").forward(req,resp);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                req.setAttribute("state", "GRESKA PRILIKOM UBACIVANJA FILMA: " + throwables.getMessage() + throwables.getLocalizedMessage());
                req.getRequestDispatcher("/WEB-INF/jsp/admin/movies/izmeni_film.jsp").forward(req,resp);
            }

        }
    }
}
