
package com.test.MyHyber.Servlet;

import com.test.MyHyber.Entity.Cours;
import com.test.MyHyber.Entity.Etudiant;
import com.test.MyHyber.Entity.Inscription;
import com.test.MyHyber.dao.CoursDAO;
import com.test.MyHyber.dao.EtudiantDAO;
import com.test.MyHyber.dao.InscriptionDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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
        List<Etudiant> etudiants = etudiantDAO.getAllStudentsList(); // Corrected method
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
        List<Etudiant> etudiants = etudiantDAO.getAllStudentsList(); // Corrected method
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
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required.");
                return;
            }

            LocalDate dateInscription = LocalDate.parse(dateString);
            Etudiant etudiant = etudiantDAO.findStudentById(Integer.parseInt(etudiantId));
            Cours cours = coursDAO.findCoursById(Integer.parseInt(coursId));

            if (etudiant == null || cours == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Etudiant or Cours ID.");
                return;
            }

            Inscription inscription = (id == null || id.isEmpty()) ? new Inscription() : inscriptionDAO.findInscriptionById(Integer.parseInt(id));
            if (inscription == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Inscription not found");
                return;
            }

            inscription.setIdEtudiant(etudiant);
            inscription.setCours(cours);
            inscription.setDateInscription(dateInscription);

            if (id == null || id.isEmpty()) {
                inscriptionDAO.saveInscription(inscription);
            } else {
                inscriptionDAO.updateInscription(inscription);
            }
            response.sendRedirect("InscriptionServlet");
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
}
