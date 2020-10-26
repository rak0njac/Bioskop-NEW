package com.bioskop.servlet;

import com.bioskop.dao.MultiplexDAO;
import com.bioskop.dao.ProjekcijaDAO;
import com.bioskop.helpers.DateHelper;
import com.bioskop.model.Film;
import com.bioskop.model.Multiplex;
import com.bioskop.model.Projekcija;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@WebServlet("/index.html")
public class homePage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ArrayList<Projekcija> projekcije;
        Set<String> datumi = new HashSet<>();
        Set<String> multiplexi = new HashSet<>();

        ArrayList<DateHelper> tempDH = new ArrayList<>();
        Set<DateHelper> dh = new TreeSet<>(Comparator.comparing(DateHelper::getLocalDate));

        try {
            projekcije = ProjekcijaDAO.findAll();

            for(Projekcija p : projekcije)
            {
                tempDH.add(new DateHelper(p.getVremePocetka()));

                String multiplex = p.getSala().getMultiplex().getNaziv();
                multiplexi.add(multiplex);
            }

            for(DateHelper d : tempDH)
            {
                if(d.getLocalDateTime().compareTo(LocalDateTime.now().plusMinutes(45)) > 0)
                {
                    dh.add(d);
                }
            }

            req.setAttribute("dat", dh);
            req.setAttribute("mul", multiplexi);

            req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

}