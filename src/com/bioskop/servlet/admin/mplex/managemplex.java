package com.bioskop.servlet.admin.mplex;

import com.bioskop.dao.FilmDAO;
import com.bioskop.dao.MultiplexDAO;
import com.bioskop.model.Film;
import com.bioskop.model.Multiplex;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;

@WebServlet("/admin/mplex/managemplex")
public class managemplex extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("admin") == null) {
            req.setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        } else {

            try {
                ArrayList<Multiplex> multiplexes = MultiplexDAO.findAll();
                multiplexes.sort(Comparator.comparing(Multiplex::getNaziv));
                req.setAttribute("mplexes", multiplexes);
                req.getRequestDispatcher("/WEB-INF/jsp/admin/mplex/managemplex.jsp").forward(req, resp);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if (action.equals("delete")) {

                MultiplexDAO.delete(MultiplexDAO.findById(Integer.parseInt(req.getParameter("id"))));
                req.setAttribute("state", "USPESNO IZBRISAN MULTIPLEX");
                req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
            }
            else if (action.equals("add")) {
                Multiplex multiplex = new Multiplex();
                multiplex.setNaziv(req.getParameter("naziv"));
                MultiplexDAO.insert(multiplex);

                req.setAttribute("state", "USPESNO DODAT MULTIPLEX");
                req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            req.setAttribute("state", "GRESKA PRILIKOM UPRAVLJANJA MULTIPLEX PODACIMA");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
    }
}