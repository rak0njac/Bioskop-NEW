package com.bioskop.servlet.projekcija;

import com.bioskop.dao.MultiplexDAO;
import com.bioskop.dao.ProjekcijaDAO;
import com.bioskop.model.Multiplex;
import com.bioskop.model.Projekcija;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

@WebServlet("/listaProjekcija")
public class listaProjekcija extends HttpServlet {
    ArrayList<Projekcija> projekcije;
    //@Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//
//        try {
//            projekcije = ProjekcijaDAO.findAll();
//
//
//                req.setAttribute("atr", projekcije);
//
//            req.getRequestDispatcher("listaProjekcija.jsp").forward(req, resp);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String datum = req.getParameter("datum");
        String mplex = req.getParameter("mplex");

        //System.out.println(mplex + "servlet");

        try {
            projekcije = ProjekcijaDAO.findByDateAndMultiplex(datum, mplex);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        req.setAttribute("atr", projekcije);

        req.getRequestDispatcher("WEB-INF/jsp/listaProjekcija.jsp").forward(req, resp);
    }
}