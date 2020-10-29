package com.bioskop.servlet.radnik;

import com.bioskop.dao.ProjekcijaDAO;
import com.bioskop.model.Karta;
import com.bioskop.model.Korisnik;
import com.bioskop.model.Projekcija;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/radnik")
public class radnik extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!((Korisnik) req.getSession().getAttribute("user")).getTip().equals("Radnik")) {
            req.getSession().setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
        else{
            try {
                ArrayList<Projekcija> projekcije = ProjekcijaDAO.findAll();
                req.setAttribute("projekcije", projekcije);

            } catch (SQLException throwables) {
                req.getSession().setAttribute("state", "GRESKA PRILIKOM POTRAZIVANJA PROJEKCIJA");
                throwables.printStackTrace();
            }

            req.getRequestDispatcher("/WEB-INF/jsp/radnik/radnik.jsp").forward(req, resp);
        }
    }
}
