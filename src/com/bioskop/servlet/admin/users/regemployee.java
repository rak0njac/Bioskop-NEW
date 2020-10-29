package com.bioskop.servlet.admin.users;

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

@WebServlet("/admin/users/regemployee")
public class regemployee extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        req.getRequestDispatcher("/WEB-INF/jsp/admin/users/regEmployee.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            req.getSession().setAttribute("radnik", k);
            req.getSession().setAttribute("admin", null);
            resp.sendRedirect("/admin");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}