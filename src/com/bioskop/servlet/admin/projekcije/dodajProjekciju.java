package com.bioskop.servlet.admin.projekcije;

import com.bioskop.dao.*;
import com.bioskop.helpers.DateHelper;
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

@WebServlet("/admin/projekcije/dodaj_projekciju")
public class dodajProjekciju extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!((Korisnik) req.getSession().getAttribute("user")).getTip().equals("Admin"))
        {
            req.getSession().setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
        else{
            if(req.getParameter("id") != null) //TODO: Promeniti u id-film
            {
                int idFilm = Integer.parseInt(req.getParameter("id"));
                try {
                    Film film = FilmDAO.findById(idFilm);
                    ArrayList<Multiplex> multiplexi = MultiplexDAO.findAll();
                    ArrayList<Proj_Sala> sale = Proj_SalaDAO.findAll();
                    ArrayList<Sediste> sedista = new ArrayList<>();

                    for(Proj_Sala ps : sale)
                    {
                        sedista.addAll(SedisteDAO.findBySala(ps.getIdSala()));
                    }
                    req.setAttribute("film", film);
                    req.setAttribute("multiplexi", multiplexi);
                    req.setAttribute("sale", sale);
                    req.setAttribute("sedista", sedista);

                    req.getRequestDispatcher("/WEB-INF/jsp/admin/projekcije/dodaj_projekciju_forma.jsp").forward(req,resp);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            else{
                try {
                    ArrayList<Film> movies = FilmDAO.findAll();
                    movies.sort(Comparator.comparing(Film::getNaziv));
                    req.setAttribute("filmovi", movies);

                    req.getRequestDispatcher("/WEB-INF/jsp/admin/projekcije/dodaj_projekciju.jsp").forward(req,resp);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO: Validacija u JSP

        if(!((Korisnik) req.getSession().getAttribute("user")).getTip().equals("Admin"))
        {
            req.getSession().setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
        else{
            Projekcija proj = new Projekcija();

            try {
                proj.setFilm(FilmDAO.findById(Integer.parseInt(req.getParameter("id"))));

                LocalDate datum = LocalDate.parse(req.getParameter("datum"));
                LocalTime vreme = LocalTime.parse(req.getParameter("vreme"));
                LocalDateTime datumVreme = LocalDateTime.of(datum, vreme);
                proj.setVremePocetka(new DateHelper(datumVreme));
                Map<String, Double> tipoviSedista = new HashMap<>();

                for(Enumeration<String> s = req.getParameterNames(); s.hasMoreElements();){
                    String param = s.nextElement();
                    if(param.contains("cena")){
                        tipoviSedista.put(param.replace("cena", ""), Double.parseDouble(req.getParameter(param)));
                    }
                }

                proj.setSala(Proj_SalaDAO.findById(Integer.parseInt(req.getParameter("sala"))));
                if(req.getParameter("premijera")!=null)
                    proj.setPremijera(true);
                else proj.setPremijera(false);

                proj.setIdProjekcija(ProjekcijaDAO.insert(proj));

                for (Map.Entry<String, Double> entry : tipoviSedista.entrySet()) {
                    KartaDAO.insert(proj, entry.getKey(), entry.getValue());
                }

                req.getSession().setAttribute("state", "USPESNO UBACENA PROJEKCIJA");
                resp.sendRedirect("/admin/projekcije/dodaj_projekciju");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                req.getSession().setAttribute("state", "GRESKA PRILIKOM UBACIVANJA PROJEKCIJE: " + throwables.getMessage() + throwables.getLocalizedMessage());
                resp.sendRedirect("/admin/projekcije/dodaj_projekciju");
            }
        }
    }
}
