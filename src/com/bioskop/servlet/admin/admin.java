package com.bioskop.servlet.admin;

import com.bioskop.model.Korisnik;

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
        if(!((Korisnik) req.getSession().getAttribute("user")).getTip().equals("Admin"))
        {
            req.getSession().setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
        else{

            req.getRequestDispatcher("/WEB-INF/jsp/admin/admin.jsp").forward(req, resp);
        }
    }
}
