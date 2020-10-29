package com.bioskop.servlet.admin.korisnici;

import com.bioskop.dao.KorisnikDAO;
import com.bioskop.model.Korisnik;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/korisnici/zabrani_pristup")
public class zabraniPristup extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO: Validacija u JSP

        if(!((Korisnik) req.getSession().getAttribute("user")).getTip().equals("Admin"))
        {
            req.setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
        else{
            String username = req.getParameter("username");
            try {
                KorisnikDAO.banByUser(username);
            req.setAttribute("state", "USPESNO BANOVAN");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                req.setAttribute("state", "GRESKA PRILIKOM BANOVANJA KORISNIKA");
            }
            req.getRequestDispatcher("/WEB-INF/jsp/admin/korisnici/zabrani_pristup.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!((Korisnik) req.getSession().getAttribute("user")).getTip().equals("Admin"))
        {
            req.setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
        else{
            req.getRequestDispatcher("/WEB-INF/jsp/admin/korisnici/zabrani_pristup.jsp").forward(req, resp);
        }
    }
}
