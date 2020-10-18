package com.bioskop.servlet.reglog;

import com.bioskop.dao.KorisnikDAO;
import com.bioskop.model.Korisnik;
import com.sun.xml.internal.fastinfoset.stax.util.StAXParserWrapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet("/registracija")
public class registracija extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        req.getRequestDispatcher("WEB-INF/jsp/registracija.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String imeprezime = req.getParameter("ime") + " " + req.getParameter("prezime");
        String datrodj = req.getParameter("datrodj");
        String brtel = req.getParameter("brtel");
        String email = req.getParameter("email");

        Timestamp date = null;
        try {
            java.util.Date d = new SimpleDateFormat("yyyy-MM-dd").parse(datrodj);
            date = new Timestamp(d.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            KorisnikDAO.insert(imeprezime, date,username,password,email,brtel,"User");
            req.getSession().setAttribute("user", username);
            resp.sendRedirect("/index.html");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}