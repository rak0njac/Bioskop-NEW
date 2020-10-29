//TODO: BOOTSTRAP GROWL, JS VALIDACIJA

package com.bioskop.servlet;

import com.bioskop.dao.MultiplexDAO;
import com.bioskop.dao.ProjekcijaDAO;
import com.bioskop.helpers.DateHelper;
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
import java.util.*;

@WebServlet("/index.html")
public class pocetna extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ArrayList<Projekcija> projekcije;
        Set<Multiplex> multiplexi = new TreeSet<>(Comparator.comparing(Multiplex::getIdMultiplex)); //TODO: sort po id ili nazivu?

        ArrayList<DateHelper> sviDatumi = new ArrayList<>(); //svi datumi
        Set<DateHelper> filtriraniDatumi = new TreeSet<>(Comparator.comparing(DateHelper::getLocalDate)); //filtrirani datumi
        //TODO: Izbeci dve kolekcije DH implementacijom kolone Zavrseno u tabeli Projekcija

        try {
            projekcije = ProjekcijaDAO.findAll();

            for(Projekcija p : projekcije)
            {
                sviDatumi.add(p.getVremePocetka());

                Multiplex multiplex = MultiplexDAO.findByNaziv(p.getSala().getMultiplex().getNaziv());
                multiplexi.add(multiplex);
            }

            for(DateHelper d : sviDatumi)
            {
                if(d.getLocalDateTime().compareTo(LocalDateTime.now().plusMinutes(45)) > 0)
                {
                    filtriraniDatumi.add(d);
                }
            }

            req.setAttribute("datumi", filtriraniDatumi);
            req.setAttribute("multiplexi", multiplexi);

            req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            req.getSession().setAttribute("state", "FATALNA GRESKA");
            req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req,resp);
        }


    }

}