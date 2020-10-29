package com.bioskop.servlet.admin.multiplexi;

import com.bioskop.dao.MultiplexDAO;
import com.bioskop.model.Korisnik;
import com.bioskop.model.Multiplex;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/admin/multiplexi/upravljanje_multiplexima")
public class upravljanjeMultiplexima extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!((Korisnik) req.getSession().getAttribute("user")).getTip().equals("Admin")){
            req.getSession().setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
        else {
            try {
                ArrayList<Multiplex> multiplexi = MultiplexDAO.findAll();
                multiplexi.sort(Comparator.comparing(Multiplex::getNaziv));
                req.setAttribute("multiplexi", multiplexi);


                req.getRequestDispatcher("/WEB-INF/jsp/admin/multiplexi/upravljanje_multiplexima.jsp").forward(req, resp);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                req.getSession().setAttribute("state", "GRESKA PRILIKOM UPRAVLJANJA MULTIPLEX PODACIMA");
                req.getRequestDispatcher("/WEB-INF/jsp/admin/multiplexi/upravljanje_multiplexima.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!((Korisnik) req.getSession().getAttribute("user")).getTip().equals("Admin")){
            req.getSession().setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
        else{
            String action = req.getParameter("action");
            try {
                if (action.equals("delete")) {
                    MultiplexDAO.delete(MultiplexDAO.findById(Integer.parseInt(req.getParameter("id"))));
                    req.getSession().setAttribute("state", "USPESNO IZBRISAN MULTIPLEX");
                    resp.sendRedirect("/admin/multiplexi/upravljanje_multiplexima");
                }
                else if (action.equals("add")) {
                    Map<String, String[]> parameterMap = req.getParameterMap();
                    Map<Integer, String[]> myMap = new HashMap<>();
                    int i = 0;
                    for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                        if(entry.getKey().contains("sediste"))
                        {
                            i++;
                            myMap.put(i, entry.getValue());
                        }
                    }
                    MultiplexDAO.insert(req.getParameter("naziv"), myMap);

                    req.getSession().setAttribute("state", "USPESNO DODAT MULTIPLEX");
                    resp.sendRedirect("/admin/multiplexi/upravljanje_multiplexima");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                req.getSession().setAttribute("state", "GRESKA PRILIKOM UPRAVLJANJA MULTIPLEX PODACIMA");
                resp.sendRedirect("/admin/multiplexi/upravljanje_multiplexima");
            }
        }
    }
}