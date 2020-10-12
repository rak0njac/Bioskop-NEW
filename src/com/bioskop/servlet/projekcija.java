package com.bioskop.servlet;

import com.bioskop.dao.KartaDAO;
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

@WebServlet("/projekcija")
public class projekcija extends HttpServlet {
//    ArrayList<Projekcija> projekcije;
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//
//        try {
//            projekcije = ProjekcijaDAO.findAll();
//
//
//                req.setAttribute("atr", projekcije);
//
//            req.getRequestDispatcher("movieList.jsp").forward(req, resp);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("frmId"));
        String datum = req.getParameter("frmDatum");
        System.out.println(datum);
        try {
            Projekcija projekcija = ProjekcijaDAO.findById(id);
            ArrayList<String> datumi = ProjekcijaDAO.getDateList(projekcija.getFilm());
            ArrayList<String> vremenaAndSale = ProjekcijaDAO.getTimeAndPSListByDateAndFilm(datum, projekcija.getFilm());
            ArrayList<String> sedista = KartaDAO.getFreeSeatsByProjId(id);
            req.setAttribute("vs", vremenaAndSale);
            req.setAttribute("film", projekcija);
            req.setAttribute("datumi", datumi);
            req.setAttribute("sed", sedista);

            req.getRequestDispatcher("projekcija.jsp").forward(req, resp);
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }


    }
}