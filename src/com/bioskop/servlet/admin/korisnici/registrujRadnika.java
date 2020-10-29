package com.bioskop.servlet.admin.korisnici;

import com.bioskop.dao.KorisnikDAO;
import com.bioskop.model.Korisnik;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet("/admin/korisnici/registruj_radnika")
public class registrujRadnika extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!((Korisnik) req.getSession().getAttribute("user")).getTip().equals("Admin")){
            req.setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
        else {
            req.getRequestDispatcher("/WEB-INF/jsp/admin/korisnici/registruj_radnika.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO: Validacija u JSP

        if(!((Korisnik) req.getSession().getAttribute("user")).getTip().equals("Admin")){
            req.setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
        else {
            Korisnik k = new Korisnik();

            k.setUsername(req.getParameter("username"));
            k.setPassword("radnik");
            k.setImePrezime(req.getParameter("ime") + " " + req.getParameter("prezime"));
            k.setDatRodj(LocalDate.parse(req.getParameter("datrodj")));
            k.setBrTel(req.getParameter("brtel"));
            k.setEmail(req.getParameter("email"));
            k.setTip("Radnik");
            k.setStatus("Aktivan");
            k.setBrPoena(0);


            try {
                k.setIdKorisnik(KorisnikDAO.insert(k));
                req.getSession().setAttribute("user", k);
                req.setAttribute("state", "USPESNO REGISTROVAN RADNIK");
                req.getRequestDispatcher("/WEB-INF/jsp/radnik/radnik.jsp").forward(req, resp);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                req.setAttribute("state", "GRESKA PRILIKOM REGISTROVANJA RADNIKA");
                req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
            }
        }


    }
}