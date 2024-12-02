package com.cytech.projet_jakarta;


import java.io.IOException;
import java.util.List;
import java.sql.Date;


import com.cytech.projet_jakarta.dao.CoursDAO;
import com.cytech.projet_jakarta.dao.EnseignantDAO;
import com.cytech.projet_jakarta.dao.EtudiantDAO;
import com.cytech.projet_jakarta.dao.MatiereDAO;
import com.cytech.projet_jakarta.model.Cours;
import com.cytech.projet_jakarta.model.Matiere;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CoursServlet")
public class CoursServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CoursDAO coursDAO;
    private EnseignantDAO enseignantDAO;
    private EtudiantDAO etudiantDAO;

    @Override
    public void init() {

        enseignantDAO = new EnseignantDAO();
        etudiantDAO = new EtudiantDAO();
        coursDAO = new CoursDAO(); // Assuming this is already initialized
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("CoursServlet doGet ");
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
                    deleteCours(request, response);
                    break;
                case "search":
                    validateKeywordParameter(request);
                    searchCours(request, response);
                    break;
                case "assign":
                    assignTeacherAndStudentToCourse(request, response);
                    break;
                case "listForAssignment":
                    listCoursForAssignment(request, response);
                    break;
                default:
                    listCours(request, response);
                    break;
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    private void listCours(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Cours> coursList = coursDAO.getAllCours();
        request.setAttribute("coursList", coursList);
        request.getRequestDispatcher("Admin/cours-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Admin/cours-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Cours existingCours = coursDAO.findCoursById(id);

            if (existingCours == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Cours not found");
                return;
            }

            request.setAttribute("cours", existingCours); // Set the existing course for the JSP
            request.getRequestDispatcher("Admin/cours-form.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
            e.printStackTrace();
        }
    }


    private void deleteCours(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        coursDAO.deleteCours(id);
        response.sendRedirect("CoursServlet");
    }

    private void searchCours(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Cours> coursList = coursDAO.searchCoursByName(keyword);
        request.setAttribute("coursList", coursList);
        request.getRequestDispatcher("cours-list.jsp").forward(request, response);
    }

    private void listCoursForAssignment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Cours> coursList = coursDAO.getAllCoursForAssignment(); // Récupère les cours avec les relations nécessaires
        request.setAttribute("coursList", coursList);
        request.getRequestDispatcher("Admin/cours-assign.jsp").forward(request, response); // Utilisez une JSP dédiée si nécessaire
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = request.getParameter("id");
            String matiereId = request.getParameter("matiere");
            String dateString = request.getParameter("date");

            if (matiereId == null || matiereId.isEmpty() || dateString == null || dateString.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Matiere ID and Date are required fields.");
                return;
            }

            try {
                Date date = Date.valueOf(dateString);
                MatiereDAO matiereDAO = new MatiereDAO();
                Matiere matiere = matiereDAO.findMatiereById(Integer.parseInt(matiereId));
                if (matiere == null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Matiere ID");
                    return;
                }

                Cours cours = (id == null || id.isEmpty()) ? new Cours() : coursDAO.findCoursById(Integer.parseInt(id));
                if (cours == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Cours not found");
                    return;
                }

                cours.setMatiere(matiere);
                cours.setDate(date);

                if (id == null || id.isEmpty()) {
                    coursDAO.saveCours(cours);
                } else {
                    coursDAO.updateCours(cours);
                }

                response.sendRedirect("CoursServlet");
            } catch (IllegalArgumentException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format.");
            }
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
    private void assignTeacherAndStudentToCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idEnseignant = request.getParameter("idEnseignant");
        String[] idEtudiants = request.getParameterValues("idEtudiant");
        String idCours = request.getParameter("idCours");

        try {
            if (idEnseignant != null && !idEnseignant.isEmpty()) {
                coursDAO.assignTeacherToCourse(Integer.parseInt(idEnseignant), Integer.parseInt(idCours));
            }

            if (idEtudiants != null) {
                for (String idEtudiant : idEtudiants) {
                    if (idEtudiant != null && !idEtudiant.isEmpty()) {
                        coursDAO.assignStudentToCourse(Integer.parseInt(idEtudiant), Integer.parseInt(idCours));
                    }
                }
            }

            // Use the DAO instances to fetch data
            request.setAttribute("teachers", enseignantDAO.getAllTeachersList());
            request.setAttribute("students", etudiantDAO.getAllStudentsList());
            request.setAttribute("cours", coursDAO.findCoursById(Integer.parseInt(idCours)));

            request.getRequestDispatcher("Admin/cours-assign.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format.");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
        }
    }

}

