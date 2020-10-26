package com.bioskop.servlet.admin.users;

import com.bioskop.dao.KorisnikDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/users/ban")
public class ban extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("admin") == null)
        {
            req.setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
        else{
            String username = req.getParameter("username");
            try {
                if(KorisnikDAO.banByUser(username) == 1){
                    req.setAttribute("state", "USPESNO BANOVAN");
                }
                else{
                    req.setAttribute("state", "GRESKA PRILIKOM BANOVANJA KORISNIKA");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("admin") == null)
        {
            req.setAttribute("state", "NEMAS PRISTUP");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req, resp);
        }
        else{
            req.getRequestDispatcher("/WEB-INF/jsp/admin/users/banUsers.jsp").forward(req,resp);
        }
    }
}
