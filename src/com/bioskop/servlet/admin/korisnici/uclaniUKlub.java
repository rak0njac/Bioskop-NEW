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

@WebServlet("/admin/korisnici/uclani_u_klub")
public class uclaniUKlub extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!((Korisnik) req.getSession().getAttribute("user")).getTip().equals("Admin")){

            req.getSession().setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
        else{
            String username = req.getParameter("username");
            String klub = req.getParameter("klub");
            try {
                KorisnikDAO.enrollInClub(username, klub);
                req.getSession().setAttribute("state", "USPESNO DODAT U KLUB");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            req.getSession().setAttribute("state", "GRESKA PRILIKOM DODAVANJA U KLUB");
        }
            resp.sendRedirect("/admin/korisnici/uclani_u_klub");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!((Korisnik) req.getSession().getAttribute("user")).getTip().equals("Admin")){

            req.getSession().setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
        else{

            req.getRequestDispatcher("/WEB-INF/jsp/admin/korisnici/uclani_u_klub.jsp").forward(req, resp);
        }
    }
}
