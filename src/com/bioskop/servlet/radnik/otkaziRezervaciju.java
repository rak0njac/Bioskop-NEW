package com.bioskop.servlet.radnik;

import com.bioskop.dao.KartaDAO;
import com.bioskop.model.Korisnik;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/radnik/otkazi_rezervaciju")
public class otkaziRezervaciju extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!((Korisnik) req.getSession().getAttribute("user")).getTip().equals("Radnik")) {
            req.getSession().setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        } else {
            int idKarta = Integer.parseInt(req.getParameter("id-karta"));
            try {
                KartaDAO.cancelReservation(idKarta);
                req.getSession().setAttribute("state", "USPESNO OTKAZANA REZERVACIJA");
                resp.sendRedirect("/radnik");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                req.getSession().setAttribute("state", "GRESKA PRILIKOM OTKAZIVANJA REZERVACIJE");
                resp.sendRedirect("/radnik");
            }
        }
    }
}