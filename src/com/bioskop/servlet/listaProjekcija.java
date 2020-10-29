package com.bioskop.servlet;

import com.bioskop.dao.ProjekcijaDAO;
import com.bioskop.model.Projekcija;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

@WebServlet("/lista_projekcija")
public class listaProjekcija extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String datum = req.getParameter("datum");
        int idMultiplex = Integer.parseInt( req.getParameter("id-multiplex") ); //TODO: Promeniti u id-multiplex
        ArrayList<Projekcija> projekcije;

        try {
            projekcije = ProjekcijaDAO.findByDateAndMultiplex(datum, idMultiplex);

            req.setAttribute("projekcije", projekcije);
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
            req.getSession().setAttribute("state", "GRESKA PRILIKOM POTRAZIVANJA PROJEKCIJA");
            req.getRequestDispatcher("/WEB-INF/jsp/lista_projekcija.jsp").forward(req,resp);
        }


        req.getRequestDispatcher("/WEB-INF/jsp/lista_projekcija.jsp").forward(req, resp);
    }
}