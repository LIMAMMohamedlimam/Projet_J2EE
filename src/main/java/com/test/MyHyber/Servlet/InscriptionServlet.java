package com.test.MyHyber.Servlet;

import com.test.MyHyber.Entity.Cours;
import com.test.MyHyber.Entity.Etudiant;
import com.test.MyHyber.Entity.Inscription;
import com.test.MyHyber.dao.CoursDAO;
import com.test.MyHyber.dao.EtudiantDAO;
import com.test.MyHyber.dao.InscriptionDAO;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/InscriptionServlet")
public class InscriptionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private InscriptionDAO inscriptionDAO;
    private EtudiantDAO etudiantDAO;
    private CoursDAO coursDAO;

    @Override
    public void init() {
        inscriptionDAO = new InscriptionDAO();
        etudiantDAO = new EtudiantDAO();
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
                    deleteInscription(request, response);
                    break;
                case "enroll":
                    enrollStudent(request, response);
                    break;
                default:
                    listInscriptions(request, response);
                    break;
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    private void listInscriptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Inscription> inscriptions = inscriptionDAO.getAllInscriptions();
        request.setAttribute("inscriptions", inscriptions);
        request.getRequestDispatcher("inscription-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Etudiant> etudiants = etudiantDAO.getAllStudents();
        List<Cours> coursList = coursDAO.getAllCours();
        request.setAttribute("etudiants", etudiants);
        request.setAttribute("coursList", coursList);
        request.getRequestDispatcher("inscription-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Inscription existingInscription = inscriptionDAO.findInscriptionById(id);
        if (existingInscription == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Inscription not found");
            return;
        }
        List<Etudiant> etudiants = etudiantDAO.getAllStudents();
        List<Cours> coursList = coursDAO.getAllCours();
        request.setAttribute("inscription", existingInscription);
        request.setAttribute("etudiants", etudiants);
        request.setAttribute("coursList", coursList);
        request.getRequestDispatcher("inscription-form.jsp").forward(request, response);
    }

    private void deleteInscription(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        inscriptionDAO.deleteInscription(id);
        response.sendRedirect("InscriptionServlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = request.getParameter("id");
            String etudiantId = request.getParameter("etudiant");
            String coursId = request.getParameter("cours");
            String dateString = request.getParameter("date");

            if (etudiantId == null || etudiantId.isEmpty() || coursId == null || coursId.isEmpty() || dateString == null || dateString.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields (Etudiant, Cours, Date) are required.");
                return;
            }

            try {
                Date dateInscription = Date.valueOf(dateString);
                Etudiant etudiant = etudiantDAO.findStudentById(Integer.parseInt(etudiantId));
                Cours cours = coursDAO.findCoursById(Integer.parseInt(coursId));

                if (etudiant == null || cours == null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Etudiant or Cours ID.");
                    return;
                }

                Inscription inscription = (id == null || id.isEmpty()) ? new Inscription() : inscriptionDAO.findInscriptionById(Integer.parseInt(id));
                if (inscription == null && id != null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Inscription not found");
                    return;
                }

                inscription.setEtudiant(etudiant);
                inscription.setCours(cours);
                inscription.setDateInscription(dateInscription);

                if (id == null || id.isEmpty()) {
                    inscriptionDAO.saveInscription(inscription);
                } else {
                    inscriptionDAO.updateInscription(inscription);
                }
                response.sendRedirect("InscriptionServlet");
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

    private void enrollStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String IdEtudiant = request.getParameter("IdEtudiant");
        String idCours = request.getParameter("idCours");
        if (IdEtudiant != null && idCours != null) {
            inscriptionDAO.enrollStudentInCourse(Integer.parseInt(IdEtudiant), Integer.parseInt(idCours));
        }
        response.sendRedirect("InscriptionServlet");
    }
}
