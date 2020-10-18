package com.bioskop.servlet.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin")
public class admin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("admin") == null)
        {
            req.setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("WEB-INF/jsp/rezervacija.jsp").forward(req, resp);
        }
        else{
            req.getRequestDispatcher("WEB-INF/jsp/admin.jsp").forward(req, resp);
        }
    }
}
