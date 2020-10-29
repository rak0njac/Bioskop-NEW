package com.bioskop.servlet.radnik;

import com.bioskop.dao.KartaDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/radnik/otkaziRezervaciju")
public class otkaziRezervaciju extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idKarta = Integer.parseInt(req.getParameter("id-karta"));

        try {
            KartaDAO.cancelReservation(idKarta);
            resp.sendRedirect("/radnik");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
