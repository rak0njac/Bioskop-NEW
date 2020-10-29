package com.bioskop.servlet;

import com.bioskop.dao.KartaDAO;
import com.bioskop.dao.KorisnikDAO;
import com.bioskop.model.Karta;
import com.bioskop.model.Korisnik;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

@WebServlet("/profil")
public class profil extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("user") == null)
        {
            req.getSession().setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
        else{
            try {
                Korisnik korisnik = (Korisnik)req.getSession().getAttribute("user");
                ArrayList<Karta> rezervacije = KartaDAO.findByIdKorisnik(korisnik.getIdKorisnik());
                req.setAttribute("rezervacije", rezervacije);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                req.getSession().setAttribute("state", "GRESKA PRILIKOM POTRAZIVANJA PODATAKA");
            }

            req.getRequestDispatcher("/WEB-INF/jsp/profil.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO: JSP validacija

        if(req.getSession().getAttribute("user") == null)
        {
            req.getSession().setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
        else{
            if(req.getParameter("submit").equals("izmena-podataka")){
                Korisnik k = (Korisnik)req.getSession().getAttribute("user");
                k.setEmail(req.getParameter("email"));
                k.setImePrezime(req.getParameter("ime-prezime"));
                k.setBrTel(req.getParameter("br-tel"));
                k.setDatRodj(LocalDate.parse(req.getParameter("dat-rodj")));

                try {
                    KorisnikDAO.update(k);
                    req.getSession().setAttribute("state", "USPESNO IZMENJENI PODACI");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    req.getSession().setAttribute("state", "GRESKA PRILIKOM IZMENE PODATAKA");
                }
                resp.sendRedirect("/profil");
            }
            else if(req.getParameter("submit").equals("izmena-lozinke")){
                Korisnik korisnik = (Korisnik)req.getSession().getAttribute("user");
                String oldPass = req.getParameter("old-pass");
                String newPass = req.getParameter("new-pass");
                if(korisnik.getPassword().equals(oldPass)){
                    korisnik.setPassword(newPass);
                    try {
                        KorisnikDAO.update(korisnik);
                        req.getSession().setAttribute("state", "USPESNO IZMENJENA LOZINKA");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        req.getSession().setAttribute("state", "GRESKA PRILIKOM IZMENE LOZINKE");
                    }
                    resp.sendRedirect("/profil");
                }
                else{
                    req.getSession().setAttribute("state", "NETACNA STARA LOZINKA");
                    resp.sendRedirect("/profil");
                }
            }
            else{
                req.getSession().setAttribute("state", "NEISPRAVAN ZAHTEV");
                resp.sendRedirect("/profil");
            }
        }
    }
}