package com.bioskop.servlet.admin.projections;

import com.bioskop.dao.*;
import com.bioskop.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@WebServlet("/admin/projections/addprojection")
public class addprojection extends HttpServlet {
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
                    ArrayList<Multiplex> mplexes = MultiplexDAO.findAll();
                    ArrayList<Proj_Sala> sale = Proj_SalaDAO.findAll();
                    ArrayList<Sediste> sedista = new ArrayList<>();

                    for(Proj_Sala ps : sale)
                    {
                        sedista.addAll(SedisteDAO.findBySala(ps.getIdSala()));
                    }
                    req.setAttribute("film", film);
                    req.setAttribute("mplexes", mplexes);
                    req.setAttribute("sale", sale);
                    req.setAttribute("sedista", sedista);
                    req.getRequestDispatcher("/WEB-INF/jsp/admin/projections/projectionform.jsp").forward(req,resp);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            else{
                try {
                    ArrayList<Film> movies = FilmDAO.findAll();
                    movies.sort(Comparator.comparing(Film::getNaziv));
                    req.setAttribute("movies", movies);
                    req.getRequestDispatcher("/WEB-INF/jsp/admin/projections/addprojection.jsp").forward(req,resp);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
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
            Projekcija proj = new Projekcija();

            try {
                proj.setFilm(FilmDAO.findById(Integer.parseInt(req.getParameter("id"))));

                LocalDate date = LocalDate.parse(req.getParameter("datum"));
                LocalTime time = LocalTime.parse(req.getParameter("vreme"));
                LocalDateTime dateTime = LocalDateTime.of(date, time);
                proj.setVremePocetka(dateTime);
                ArrayList<String> tipoviSedista = new ArrayList<>();
                Map<String, Double> tipovi = new HashMap<>();
                for(Enumeration<String> s = req.getParameterNames(); s.hasMoreElements();){
                    String par = s.nextElement();
                    System.out.println(par);
                    if(par.contains("cena")){
                        tipovi.put(par.replace("cena", ""), Double.parseDouble(req.getParameter(par)));
                    }
                }

                System.out.println(tipovi);

                proj.setSala(Proj_SalaDAO.findById(Integer.parseInt(req.getParameter("sala"))));
                proj.setPremijera(Boolean.parseBoolean(req.getParameter("premijera")));


                proj.setIdProjekcija(ProjekcijaDAO.insert(proj));

                ArrayList<Sediste> sedista = SedisteDAO.findBySala(proj.getSala().getIdSala());
                for (Map.Entry<String, Double> entry : tipovi.entrySet()) {
                    KartaDAO.insert(proj, entry.getKey(), entry.getValue());
                }
                //for( s : sedista)
                //{
                //    KartaDAO.insert(proj.getIdProjekcija(), s.getIdSediste());
                //}
                req.setAttribute("state", "USPESNO UBACENA PROJEKCIJA");
                req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                req.setAttribute("state", "GRESKA PRILIKOM UBACIVANJA FILMA: " + throwables.getMessage() + throwables.getLocalizedMessage());
                req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
            }
        }
    }
}
