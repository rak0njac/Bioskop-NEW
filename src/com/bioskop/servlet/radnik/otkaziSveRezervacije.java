package com.bioskop.servlet.radnik;

import com.bioskop.dao.KartaDAO;
import com.bioskop.model.Karta;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/radnik/otkaziSveRezervacije")
public class otkaziSveRezervacije extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idProjekcija = Integer.parseInt(req.getParameter("id-karta"));

        try {
            ArrayList<Karta> karte = KartaDAO.findByIdProj(idProjekcija);
            for(Karta k : karte)
            {
                KartaDAO.cancelReservation(k.getIdKarta());
            }
            resp.sendRedirect("/radnik");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
