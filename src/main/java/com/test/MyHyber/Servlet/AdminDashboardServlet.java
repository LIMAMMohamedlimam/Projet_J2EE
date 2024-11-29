package com.test.MyHyber.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/AdminDashboardServlet")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userRole = (String) request.getSession().getAttribute("userRole");

        if (userRole == null || !"ADMIN".equals(userRole)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied: Unauthorized User");
            return;
        }

        String dashboardFilter = request.getParameter("filter");
        if (dashboardFilter != null && !dashboardFilter.matches("^[a-zA-Z0-9_]+$")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid filter parameter");
            return;
        }

        request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
    }}
