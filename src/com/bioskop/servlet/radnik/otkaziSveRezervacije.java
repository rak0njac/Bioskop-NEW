package com.bioskop.servlet.radnik;

import com.bioskop.dao.KartaDAO;
import com.bioskop.model.Karta;
import com.bioskop.model.Korisnik;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/radnik/otkazi_sve_rezervacije")
public class otkaziSveRezervacije extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!((Korisnik) req.getSession().getAttribute("user")).getTip().equals("Radnik")){
            req.getSession().setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
        else{
            int idProjekcija = Integer.parseInt(req.getParameter("id-karta")); //TODO: koristi se id-karta, treba dodati novi input id-projekcija

            try {
                ArrayList<Karta> karte = KartaDAO.findByIdProj(idProjekcija);
                for(Karta k : karte)
                {
                    KartaDAO.cancelReservation(k.getIdKarta());
                }
                req.getSession().setAttribute("state", "SVE REZERVACIJE SU USPESNO OTKAZANE");
                resp.sendRedirect("/radnik");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                req.getSession().setAttribute("state", "GRESKA PRILIKOM OTKAZIVANJA REZERVACIJA");
                resp.sendRedirect("/radnik");
            }
        }
    }
}
