package com.test.MyHyber.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/EnseignantDashboardServlet")
public class EnseignantDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userRole = (String) request.getSession().getAttribute("userRole");

        if (!"ENSEIGNANT".equals(userRole)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied: Unauthorized User");
            return;
        }
        request.getRequestDispatcher("Enseignant-dashboard.jsp").forward(request, response);
    }
}
