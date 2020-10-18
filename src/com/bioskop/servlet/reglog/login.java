package com.bioskop.servlet.reglog;

import com.bioskop.dao.KorisnikDAO;

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
            if(KorisnikDAO.findByUserAndPass(user, pass) == "user")
            {
                req.getSession().setAttribute("user", user);
                resp.sendRedirect("/index.html");
            }
            else if(KorisnikDAO.findByUserAndPass(user, pass) == "admin")
            {
                req.getSession().setAttribute("admin", user);
                resp.sendRedirect("/admin");
            }
            else
            {
                req.setAttribute("state", "NETACNI PODACI");
                req.getRequestDispatcher("WEB-INF/jsp/rezervacija.jsp").forward(req,resp);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
