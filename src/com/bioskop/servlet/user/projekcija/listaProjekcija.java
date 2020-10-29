package com.bioskop.servlet.user.projekcija;

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

@WebServlet("/listaProjekcija")
public class listaProjekcija extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String datum = req.getParameter("datum");
        int mplex = Integer.parseInt( req.getParameter("mplex") );
        ArrayList<Projekcija> projekcije;

        //System.out.println(mplex + "servlet");

        try {
            projekcije = ProjekcijaDAO.findByDateAndMultiplex(datum, mplex);

            req.setAttribute("atr", projekcije);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }



        req.getRequestDispatcher("/WEB-INF/jsp/listaProjekcija.jsp").forward(req, resp);
    }
}