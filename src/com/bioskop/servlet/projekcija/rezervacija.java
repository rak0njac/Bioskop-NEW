package com.bioskop.servlet.projekcija;

import com.bioskop.dao.KartaDAO;
import com.bioskop.dao.Proj_SalaDAO;
import com.bioskop.dao.ProjekcijaDAO;
import com.bioskop.dao.SedisteDAO;
import com.bioskop.model.Karta;
import com.bioskop.model.Proj_Sala;
import com.bioskop.model.Projekcija;
import com.bioskop.model.Sediste;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
            String username = (String) req.getSession().getAttribute("user");

            try {
                String state = KartaDAO.reserveTicket(idProjekcija, tipSedista, username);

                req.setAttribute("state", state);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        req.getRequestDispatcher("WEB-INF/jsp/rezervacija.jsp").forward(req,resp);

    }
}
