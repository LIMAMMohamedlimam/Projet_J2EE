package com.test.MyHyber.Servlet;

import com.test.MyHyber.Entity.Matiere;
import com.test.MyHyber.dao.MatiereDAO;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/MatiereServlet")
public class MatiereServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MatiereDAO matiereDAO;

    @Override
    public void init() {
        matiereDAO = new MatiereDAO();
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
                    deleteMatiere(request, response);
                    break;
                case "search":
                    validateKeywordParameter(request);
                    searchMatieres(request, response);
                    break;
                default:
                    listMatieres(request, response);
                    break;
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    private void listMatieres(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Matiere> matiereList = matiereDAO.getAllMatieres();
        request.setAttribute("matiereList", matiereList);
        request.getRequestDispatcher("matiere-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("matiere-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Matiere existingMatiere = matiereDAO.findMatiereById(id);
        if (existingMatiere == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Matiere not found");
            return;
        }
        request.setAttribute("matiere", existingMatiere);
        request.getRequestDispatcher("matiere-form.jsp").forward(request, response);
    }

    private void searchMatieres(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        if (keyword == null || keyword.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Search keyword cannot be empty");
            return;
        }
        List<Matiere> matieres = matiereDAO.searchByName(keyword);
        request.setAttribute("matiereList", matieres);
        request.getRequestDispatcher("matiere-list.jsp").forward(request, response);
    }

    private void deleteMatiere(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        matiereDAO.deleteMatiere(id);
        response.sendRedirect("MatiereServlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = request.getParameter("id");
            String nom = request.getParameter("nom");

            if (nom == null || nom.trim().isEmpty()) {
                request.setAttribute("error", "Name cannot be empty");
                request.getRequestDispatcher("matiere-form.jsp").forward(request, response);
                return;
            }

            Matiere matiere = (id == null || id.isEmpty()) ? new Matiere() : matiereDAO.findMatiereById(Integer.parseInt(id));
            if (matiere == null && id != null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Matiere not found");
                return;
            }

            matiere.setNom(nom);

            if (id == null || id.isEmpty()) {
                matiereDAO.saveMatiere(matiere);
            } else {
                matiereDAO.updateMatiere(matiere);
            }
            response.sendRedirect("MatiereServlet");
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
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new ServletException("Search keyword cannot be empty");
        }
    }}
