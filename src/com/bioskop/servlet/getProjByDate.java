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
import java.text.ParseException;
import java.util.ArrayList;

@WebServlet("/getProjByDate")
public class getProjByDate extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<Projekcija> projekcije;

        try {

            String p = req.getParameter("param");
            projekcije = ProjekcijaDAO.findByDate(p);


            req.setAttribute("atr", projekcije);
            System.out.println("called * 1");
            req.getRequestDispatcher("index.html").forward(req, resp);
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
    }
}