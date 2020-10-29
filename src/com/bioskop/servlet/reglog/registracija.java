package com.bioskop.servlet.reglog;

import com.bioskop.dao.KorisnikDAO;
import com.bioskop.model.Korisnik;
import com.sun.xml.internal.fastinfoset.stax.util.StAXParserWrapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

@WebServlet("/registracija")
public class registracija extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/registracija.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO: Validacija u JSP

        Korisnik k = new Korisnik();

        k.setUsername(req.getParameter("username"));
        k.setPassword(req.getParameter("password"));
        k.setImePrezime(req.getParameter("ime") + " " + req.getParameter("prezime"));
        k.setDatRodj(LocalDate.parse(req.getParameter("datrodj")));
        k.setBrTel(req.getParameter("brtel"));
        k.setEmail(req.getParameter("email"));
        k.setTip("User");
        k.setStatus("Aktivan");
        k.setBrPoena(0);

        try {
            k.setIdKorisnik(KorisnikDAO.insert(k));
            req.getSession().setAttribute("user", k);
            req.setAttribute("state", "USPESNA REGISTRACIJA. ULOGOVANI STE.");
            req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req,resp);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            req.setAttribute("state", "GRESKA PRILIKOM REGISTRACIJE");
            req.getRequestDispatcher("/WEB-INF/jsp/registracija.jsp").forward(req,resp);
        }
    }
}