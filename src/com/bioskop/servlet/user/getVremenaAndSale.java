package com.bioskop.servlet.user;

import com.bioskop.dao.FilmDAO;
import com.bioskop.dao.KartaDAO;
import com.bioskop.dao.ProjekcijaDAO;
import com.bioskop.model.Karta;
import com.bioskop.model.Projekcija;
import com.bioskop.model.Sediste;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

@WebServlet("/projekcija/getVremenaAndSale")
public class getVremenaAndSale extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("frmId"));
        String datum = req.getParameter("frmDatum");

        try {
            Projekcija projekcija = ProjekcijaDAO.findById(id);
            ArrayList<String> vremenaAndSale = ProjekcijaDAO.getTimeAndPSListByDateAndFilm(datum, projekcija.getFilm());
            ArrayList<Sediste> sedista = KartaDAO.getFreeSeatsByProjId(id);

            resp.setContentType("application/json");
            new Gson().toJson(vremenaAndSale, resp.getWriter());

        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }


    }
}