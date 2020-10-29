package com.bioskop.servlet.user.projekcija;

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
            Korisnik k = (Korisnik)req.getSession().getAttribute("user");
            int kolicina = Integer.parseInt(req.getParameter("kolicina"));
            int popust = 0;
            if(!(req.getParameter("popust") == null))
                popust = Integer.parseInt(req.getParameter("popust"));
            //Boolean popust25 = Boolean.parseBoolean(req.getParameter("popust25"));

            try {
                String state = "";
                for(int i = 0; i < kolicina; i++)
                {
                    Karta karta = KartaDAO.findByProjAndSeat(idProjekcija, tipSedista);
                    if(popust > 0)
                    {
                        KartaDAO.discount(karta, popust);
                        if(popust == 10)
                            KorisnikDAO.removePoints(100, k.getUsername());
                        else KorisnikDAO.removePoints(200, k.getUsername());
                    }
                    state = KartaDAO.reserveTicket(karta, k.getUsername());
                }

                req.getSession().setAttribute("user", KorisnikDAO.findById(k.getIdKorisnik()));
                req.setAttribute("state", state);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req,resp);

    }
}
