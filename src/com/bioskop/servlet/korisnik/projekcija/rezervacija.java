package com.bioskop.servlet.korisnik.projekcija;

import com.bioskop.dao.KartaDAO;
import com.bioskop.dao.KorisnikDAO;
import com.bioskop.model.Karta;
import com.bioskop.model.Korisnik;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/rezervacija")
public class rezervacija extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("user") == null)
        {
            req.setAttribute("state", "NISTE ULOGOVANI");
        }
        else{
            String tipSedista = req.getParameter("sediste");
            int idProjekcija = Integer.parseInt(req.getParameter("projekcija"));
            System.out.println(req.getParameter("sediste") + " " + req.getParameter("projekcija"));
            Korisnik korisnik = (Korisnik)req.getSession().getAttribute("user");
            int kolicina = Integer.parseInt(req.getParameter("kolicina"));
            int popust = 0;
            if(!(req.getParameter("popust") == null))
                popust = Integer.parseInt(req.getParameter("popust"));
            if(req.getParameter("senior")!=null || req.getParameter("kids")!=null)
                popust+=20;

            try {
                String state = "";
                for(int i = 0; i < kolicina; i++)
                {
                    Karta karta = KartaDAO.findByProjAndSeat(idProjekcija, tipSedista);
                    if(popust > 0)
                    {
                        KartaDAO.discount(karta, popust);                           //TODO: HITNO PREPRAVITI!!! Funkcija ne sme po kolicini popusta da gleda da li je korisnik uclanjen u klub!
                        if(popust == 10)
                            KorisnikDAO.removePoints(100, korisnik.getUsername());
                        else if(popust == 25)
                            KorisnikDAO.removePoints(200, korisnik.getUsername());
                    }
                    state = KartaDAO.reserveTicket(karta, korisnik.getUsername());
                }

                req.getSession().setAttribute("user", KorisnikDAO.findById(korisnik.getIdKorisnik()));
                req.setAttribute("state", state);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        req.getRequestDispatcher("/WEB-INF/jsp/profil.jsp").forward(req,resp);
    }
}
