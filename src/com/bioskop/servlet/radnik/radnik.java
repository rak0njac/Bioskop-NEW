package com.bioskop.servlet.radnik;

import com.bioskop.dao.KartaDAO;
import com.bioskop.dao.ProjekcijaDAO;
import com.bioskop.helpers.DateHelper;
import com.bioskop.model.Film;
import com.bioskop.model.Karta;
import com.bioskop.model.Projekcija;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

@WebServlet("/radnik")
public class radnik extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ArrayList<Projekcija> projekcije = ProjekcijaDAO.findAll();

            //projekcije.sort(Comparator.comparing(Projekcija::getVremePocetka));
            for(Projekcija p : projekcije)
            {
                //System.out.println(p.getKarte());
            }

            req.setAttribute("projekcije", projekcije);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        req.getRequestDispatcher("/WEB-INF/jsp/radnik/radnikPanel.jsp").forward(req, resp);
    }
}
