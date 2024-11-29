package com.test.MyHyber.Servlet;

import com.test.MyHyber.Entity.*;
import com.test.MyHyber.dao.CoursDAO;
import com.test.MyHyber.dao.EnseignantDAO;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EnseignantServlet")
public class EnseignantServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EnseignantDAO enseignantDAO;
    private CoursDAO coursDAO;

    @Override
    public void init() {
        enseignantDAO = new EnseignantDAO();
        coursDAO = new CoursDAO();
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
                    deleteTeacher(request, response);
                    break;
                case "search":
                    validateKeywordParameter(request);
                    searchTeachers(request, response);
                    break;
                case "assignToCourse":
                    assignTeacherToCourse(request, response);
                    break;
                default:
                    listTeachers(request, response);
                    break;
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    private void listTeachers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Enseignant> enseignants = enseignantDAO.getAllTeachers();
        request.setAttribute("enseignants", enseignants);
        request.getRequestDispatcher("enseignant-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("enseignant-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Enseignant existingEnseignant = enseignantDAO.findTeacherById(id);
        if (existingEnseignant == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Enseignant not found");
            return;
        }
        request.setAttribute("enseignant", existingEnseignant);
        request.getRequestDispatcher("enseignant-form.jsp").forward(request, response);
    }

    private void deleteTeacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        enseignantDAO.deleteTeacher(id);
        response.sendRedirect("EnseignantServlet");
    }

    private void searchTeachers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Enseignant> enseignants = enseignantDAO.searchByName(keyword);
        request.setAttribute("enseignants", enseignants);
        request.getRequestDispatcher("enseignant-list.jsp").forward(request, response);
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

            Enseignant enseignant = (id == null || id.isEmpty()) ? new Enseignant() : enseignantDAO.findTeacherById(Integer.parseInt(id));
            if (enseignant == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Enseignant not found");
                return;
            }

            Utilisateur utilisateur = enseignant.getUtilisateur();
            if (utilisateur == null) {
                utilisateur = new Utilisateur();
                enseignant.setUtilisateur(utilisateur);
            }

            utilisateur.setNom(nom);
            utilisateur.setPrenom(prenom);

            if (id == null || id.isEmpty()) {
                enseignantDAO.saveTeacher(enseignant);
            } else {
                enseignantDAO.updateTeacher(enseignant);
            }

            response.sendRedirect("EnseignantServlet");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format.");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
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
    }
    private void assignTeacherToCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idEnseignant = request.getParameter("idEnseignant");
        String idCours = request.getParameter("courseId");
        if (idEnseignant != null && idCours != null) {
            coursDAO.assignTeacherToCourse(Integer.parseInt(idEnseignant), Integer.parseInt(idCours));
        }
        response.sendRedirect("EnseignantServlet");
    }
}
