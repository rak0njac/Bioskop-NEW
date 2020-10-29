package com.bioskop.servlet.reglog;

import com.bioskop.dao.KorisnikDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/logout")
public class logout extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try{
            HttpSession ses = req.getSession();
            ses.removeAttribute("user");
            ses.invalidate();
            resp.sendRedirect("/index.html");
        }
        catch(Exception e){
            req.setAttribute("state", "GRESKA");
            req.getRequestDispatcher("/WEB-INF/jsp/DEBUG-MSG.jsp").forward(req,resp);
        }
    }
}
