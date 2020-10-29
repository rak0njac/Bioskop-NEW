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

@WebServlet("/radnik/potvrdiRezervaciju")
public class potvrdiRezervaciju extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idKarta = Integer.parseInt(req.getParameter("id-karta"));

        try {
            KorisnikDAO.addPoints((Korisnik)req.getSession().getAttribute("user"), idKarta);
            KartaDAO.approveReservation(idKarta);
            resp.sendRedirect("/radnik");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
