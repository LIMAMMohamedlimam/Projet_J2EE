package com.test.MyHyber.Servlet;

import com.test.MyHyber.Entity.*;
import com.test.MyHyber.dao.EtudiantDAO;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EtudiantServlet")
public class EtudiantServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EtudiantDAO etudiantDAO;

    @Override
    public void init() {
        etudiantDAO = new EtudiantDAO();
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
                    deleteEtudiant(request, response);
                    break;
                case "search":
                    validateKeywordParameter(request);
                    searchEtudiants(request, response);
                    break;
                default:
                    listEtudiants(request, response);
                    break;
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    public void listEtudiants(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch students from the DAO
        List<Etudiant> etudiants = etudiantDAO.getAllStudents();

        // Log fetched students to debug
        System.out.println("Fetched students: " + etudiants);

        // Set the "etudiants" attribute for the JSP
        request.setAttribute("etudiants", etudiants);

        // Forward the request to the JSP
        request.getRequestDispatcher("/Admin/etudiant-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("etudiant-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Etudiant existingEtudiant = etudiantDAO.findStudentById(id);
        if (existingEtudiant == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Etudiant not found");
            return;
        }
        request.setAttribute("etudiant", existingEtudiant);
        request.getRequestDispatcher("etudiant-form.jsp").forward(request, response);
    }

    private void deleteEtudiant(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        etudiantDAO.deleteStudent(id);
        response.sendRedirect("EtudiantServlet");
    }

    private void searchEtudiants(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Etudiant> etudiants = etudiantDAO.searchByName(keyword);
        request.setAttribute("etudiants", etudiants);
        request.getRequestDispatcher("etudiant-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = request.getParameter("id");
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String dateDeNaissance = request.getParameter("dateDeNaissance");
            String email = request.getParameter("email");
            String contact = request.getParameter("contact");
            String role = request.getParameter("role");

            if (nom == null || prenom == null || email == null || role == null ||
                    nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || role.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required.");
                return;
            }

            Etudiant etudiant;
            if (id == null || id.isEmpty()) {
                etudiant = new Etudiant(); // New student
                etudiant.setUtilisateur(new Utilisateur()); // Link a new Utilisateur
            } else {
                etudiant = etudiantDAO.findStudentById(Integer.parseInt(id)); // Existing student
                if (etudiant == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Etudiant not found");
                    return;
                }
            }

            Utilisateur utilisateur = etudiant.getUtilisateur();
            utilisateur.setNom(nom);
            utilisateur.setPrenom(prenom);
            utilisateur.setDateDeNaissance(dateDeNaissance.isEmpty() ? null : java.sql.Date.valueOf(dateDeNaissance));
            utilisateur.setEmail(email);
            utilisateur.setContact(contact);
            utilisateur.setRole(Utilisateur.Role.valueOf(role.toUpperCase()));

            if (id == null || id.isEmpty()) {
                etudiantDAO.saveStudent(etudiant);
            } else {
                etudiantDAO.updateStudent(etudiant);
            }

            response.sendRedirect("EtudiantServlet");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request.");
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
