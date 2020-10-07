package com.bioskop.servlet;

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

@WebServlet("/index.html")
public class homepage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<Projekcija> projekcije;
        ArrayList<Multiplex> multiplexi;
        ArrayList<String> datumi;

        try {
            projekcije = ProjekcijaDAO.findAll();
            multiplexi = MultiplexDAO.findAll();
            datumi = ProjekcijaDAO.getDateList(projekcije);


            if(req.getAttribute("atr") == null)
                req.setAttribute("atr", projekcije);
            else
                System.out.println("called * 2");

            req.setAttribute("dat", datumi);
            req.setAttribute("mul", multiplexi);
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //if(req.getAttribute("atr") == null)
        //    req.setAttribute("atr", projekcije);
        //else
            System.out.println("called * 2");

        //req.setAttribute("dat", datumi);
        //req.setAttribute("mul", multiplexi);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}