package com.test.MyHyber.Servlet;

import com.test.MyHyber.Entity.Resultat;
import com.test.MyHyber.Entity.Etudiant;
import com.test.MyHyber.Entity.Matiere;
import com.test.MyHyber.dao.ResultatDAO;
import com.test.MyHyber.dao.EtudiantDAO;
import com.test.MyHyber.dao.MatiereDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/ResultatServlet")
public class ResultatServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ResultatDAO resultatDAO;
    private EtudiantDAO etudiantDAO;
    private MatiereDAO matiereDAO;

    @Override
    public void init() {
        resultatDAO = new ResultatDAO();
        etudiantDAO = new EtudiantDAO();
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
                    validateIdParameter(request, "id");
                    showEditForm(request, response);
                    break;
                case "delete":
                    validateIdParameter(request, "id");
                    deleteResultat(request, response);
                    break;
                default:
                    listResultats(request, response);
                    break;
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    private void listResultats(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Resultat> resultatsList = resultatDAO.getAllResultats();
        request.setAttribute("resultatsList", resultatsList);
        request.getRequestDispatcher("resultat-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Etudiant> etudiants = etudiantDAO.getAllStudentsList(); // Utilisation de la méthode correcte
        List<Matiere> matieres = matiereDAO.getAllMatieres();
        request.setAttribute("etudiants", etudiants);
        request.setAttribute("matieres", matieres);
        request.getRequestDispatcher("resultat-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Resultat existingResultat = resultatDAO.findResultatById(id);
        if (existingResultat == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resultat not found");
            return;
        }
        List<Etudiant> etudiants = etudiantDAO.getAllStudentsList(); // Utilisation de la méthode correcte
        List<Matiere> matieres = matiereDAO.getAllMatieres();
        request.setAttribute("resultat", existingResultat);
        request.setAttribute("etudiants", etudiants);
        request.setAttribute("matieres", matieres);
        request.getRequestDispatcher("resultat-form.jsp").forward(request, response);
    }

    private void deleteResultat(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        resultatDAO.deleteResultat(id);
        response.sendRedirect("ResultatServlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = request.getParameter("id");
            String etudiantId = request.getParameter("etudiant");
            String matiereId = request.getParameter("matiere");
            String noteString = request.getParameter("note");

            if (etudiantId == null || etudiantId.isEmpty() || matiereId == null || matiereId.isEmpty() || noteString == null || noteString.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields (Etudiant, Matiere, Note) are required.");
                return;
            }

            float note = Float.parseFloat(noteString);
            Etudiant etudiant = etudiantDAO.findStudentById(Integer.parseInt(etudiantId));
            Matiere matiere = matiereDAO.findMatiereById(Integer.parseInt(matiereId));

            if (etudiant == null || matiere == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Etudiant or Matiere ID.");
                return;
            }

            Resultat resultat = (id == null || id.isEmpty()) ? new Resultat() : resultatDAO.findResultatById(Integer.parseInt(id));
            if (resultat == null && id != null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resultat not found");
                return;
            }

            resultat.setEtudiant(etudiant);
            resultat.setMatiere(matiere);
            resultat.setNote(note);

            if (id == null || id.isEmpty()) {
                resultatDAO.saveResultat(resultat);
            } else {
                resultatDAO.updateResultat(resultat);
            }
            response.sendRedirect("ResultatServlet");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input format.");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
        }
    }

    private void validateIdParameter(HttpServletRequest request, String parameterName) throws ServletException {
        String id = request.getParameter(parameterName);
        if (id == null || id.isEmpty()) {
            throw new ServletException(parameterName + " parameter is required");
        }
        try {
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid " + parameterName + " format", e);
        }
    }
}
