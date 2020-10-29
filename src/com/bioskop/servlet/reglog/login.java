package com.bioskop.servlet.reglog;

import com.bioskop.dao.KorisnikDAO;
import com.bioskop.model.Korisnik;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class login extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("user");
        String pass = req.getParameter("pass");


        try {
            Korisnik k = KorisnikDAO.findByUserAndPass(user, pass);

            if(k == null)
            {
                req.setAttribute("state", "NETACNI PODACI");
                req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req,resp);
            }
            else if(k.getStatus().equals("Neaktivan")){
                req.setAttribute("state", "ZABRANJEN PRISTUP");
                req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req,resp);
            }
            else if(k.getTip().equals("User"))
            {
                req.getSession().setAttribute("user", k);
                resp.sendRedirect("/index.html");
            }
            else if(k.getTip().equals("Admin"))
            {
                req.getSession().setAttribute("user", k);
                req.getSession().setAttribute("admin", k);
                resp.sendRedirect("/admin");
            }
            else if(k.getTip().equals("Radnik"))
            {
                req.getSession().setAttribute("user", k);
                req.getSession().setAttribute("radnik", k);
                resp.sendRedirect("/radnik");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
