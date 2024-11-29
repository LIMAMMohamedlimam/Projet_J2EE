package com.test.MyHyber.Servlet;

import com.test.MyHyber.Entity.*;
import com.test.MyHyber.dao.AdminDAO;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdminDAO adminDAO;

    @Override
    public void init() {
        adminDAO = new AdminDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    validateIdParameter(request);
                    showEditForm(request, response);
                    break;
                case "delete":
                    validateIdParameter(request);
                    deleteAdmin(request, response);
                    break;
                case "search":
                    validateKeywordParameter(request);
                    searchAdmins(request, response);
                    break;
                default:
                    listAdmins(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException("Error processing request", e);
        }
    }

    private void listAdmins(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Admin> admins = adminDAO.getAllAdmins();
        request.setAttribute("admins", admins);
        request.getRequestDispatcher("admin-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("admin-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Admin existingAdmin = adminDAO.findAdminById(id);
        if (existingAdmin == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Admin not found");
            return;
        }
        request.setAttribute("admin", existingAdmin);
        request.getRequestDispatcher("admin-form.jsp").forward(request, response);
    }

    private void deleteAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        adminDAO.deleteAdmin(id);
        response.sendRedirect("AdminServlet");
    }

    private void searchAdmins(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Admin> admins = adminDAO.searchByName(keyword);
        request.setAttribute("admins", admins);
        request.getRequestDispatcher("admin-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = request.getParameter("id");
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");

            if (nom == null || nom.isEmpty() || prenom == null || prenom.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nom and Prenom are required fields.");
                return;
            }

            Admin admin = (id == null || id.isEmpty()) ? new Admin() : adminDAO.findAdminById(Integer.parseInt(id));
            if (admin == null && id != null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Admin not found");
                return;
            }

            Utilisateur utilisateur = admin.getUtilisateur();
            if (utilisateur == null) {
                utilisateur = new Utilisateur();
                admin.setUtilisateur(utilisateur);
            }

            utilisateur.setNom(nom);
            utilisateur.setPrenom(prenom);

            if (id == null || id.isEmpty()) {
                adminDAO.saveAdmin(admin);
            } else {
                adminDAO.updateAdmin(admin);
            }

            response.sendRedirect("AdminServlet");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format.");
        } catch (Exception e) {
            throw new ServletException("Error saving or updating admin", e);
        }
    }

    private void validateIdParameter(HttpServletRequest request) throws ServletException {
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            throw new ServletException("ID parameter is required");
        }
        try {
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid ID format", e);
        }
    }

    private void validateKeywordParameter(HttpServletRequest request) throws ServletException {
        String keyword = request.getParameter("keyword");
        if (keyword == null || keyword.isEmpty()) {
            throw new ServletException("Keyword parameter is required for search");
        }
    }}
