package com.bioskop.servlet.radnik;

import com.bioskop.dao.KartaDAO;
import com.bioskop.dao.KorisnikDAO;
import com.bioskop.model.Korisnik;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/radnik/potvrdi_rezervaciju")
public class potvrdiRezervaciju extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!((Korisnik) req.getSession().getAttribute("user")).getTip().equals("Radnik")) {
            req.getSession().setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
        else {
            int idKarta = Integer.parseInt(req.getParameter("id-karta"));

            try {
                KorisnikDAO.addPoints((Korisnik) req.getSession().getAttribute("user"), idKarta);
                KartaDAO.approveReservation(idKarta);
                req.getSession().setAttribute("state", "USPESNO POTVRDJENA REZERVACIJA");
                resp.sendRedirect("/radnik");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                req.getSession().setAttribute("state", "GRESKA PRILIKOM POTVRDE REZERVACIJE");
                resp.sendRedirect("/radnik");
            }
        }
    }
}