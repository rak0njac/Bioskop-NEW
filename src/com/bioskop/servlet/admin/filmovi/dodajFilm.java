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

@WebServlet("/admin/filmovi/dodaj_film")
public class dodajFilm extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!((Korisnik) req.getSession().getAttribute("user")).getTip().equals("Admin"))
        {
            req.setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
        else{
            req.getRequestDispatcher("/WEB-INF/jsp/admin/filmovi/dodaj_film.jsp").forward(req,resp);
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
                req.getRequestDispatcher("/WEB-INF/jsp/admin/admin.jsp").forward(req, resp);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                req.setAttribute("state", "GRESKA PRILIKOM UBACIVANJA FILMA: " + throwables.getMessage() + throwables.getLocalizedMessage());
                req.getRequestDispatcher("/WEB-INF/jsp/admin/filmovi/dodaj_film.jsp").forward(req,resp);
            }
        }
    }
}
