package com.bioskop.servlet.admin.korisnici;

import com.bioskop.dao.KorisnikDAO;
import com.bioskop.dao.MultiplexDAO;
import com.bioskop.model.Korisnik;
import com.bioskop.model.Multiplex;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

@WebServlet("/admin/korisnici/registruj_admina")
public class registrujAdmina extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!((Korisnik) req.getSession().getAttribute("user")).getTip().equals("Admin")){
            req.getSession().setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
        else {

            req.getRequestDispatcher("/WEB-INF/jsp/admin/korisnici/registruj_admina.jsp").forward(req,resp);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO: Validacija u JSP
        try {
        Korisnik k = new Korisnik();

        k.setUsername(req.getParameter("username"));
        k.setPassword("admin");
        k.setImePrezime(req.getParameter("ime") + " " + req.getParameter("prezime"));
        k.setDatRodj(LocalDate.parse(req.getParameter("datrodj")));
        k.setBrTel(req.getParameter("brtel"));
        k.setEmail(req.getParameter("email"));
        k.setTip("Admin");
        k.setStatus("Aktivan");
        k.setBrPoena(0);



            k.setIdKorisnik(KorisnikDAO.insert(k));
            req.getSession().setAttribute("user", k);
            req.getSession().setAttribute("state", "USPESNO REGISTROVAN ADMIN");
            resp.sendRedirect("/admin");
        } catch (Exception throwables) {
            throwables.printStackTrace();
            req.getSession().setAttribute("state", "GRESKA PRILIKOM REGISTROVANJA ADMINA");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }

    }
}