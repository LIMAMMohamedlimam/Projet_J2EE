package com.test.MyHyber.Servlet;

import com.test.MyHyber.Entity.Note;
import com.test.MyHyber.Entity.Etudiant;
import com.test.MyHyber.Entity.Cours;
import com.test.MyHyber.dao.NotesDAO;
import com.test.MyHyber.dao.EtudiantDAO;
import com.test.MyHyber.dao.CoursDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/NotesServlet")
public class NotesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private NotesDAO notesDAO;
    private EtudiantDAO etudiantDAO;
    private CoursDAO coursDAO;

    @Override
    public void init() {
        notesDAO = new NotesDAO();
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
                    validateIdParameter(request, "id");
                    showEditForm(request, response);
                    break;
                case "delete":
                    validateIdParameter(request, "id");
                    deleteNotes(request, response);
                    break;
                default:
                    listNotes(request, response);
                    break;
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    private void listNotes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Note> notesList = notesDAO.getAllNotes();
        request.setAttribute("notesList", notesList);
        request.getRequestDispatcher("notes-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Etudiant> etudiants = etudiantDAO.getAllStudentsList(); // Utilisation de la méthode correcte
        List<Cours> coursList = coursDAO.getAllCours();
        request.setAttribute("etudiants", etudiants);
        request.setAttribute("coursList", coursList);
        request.getRequestDispatcher("notes-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Note existingNotes = notesDAO.findNotesById(id);
        if (existingNotes == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Notes not found");
            return;
        }
        List<Etudiant> etudiants = etudiantDAO.getAllStudentsList(); // Utilisation de la méthode correcte
        List<Cours> coursList = coursDAO.getAllCours();
        request.setAttribute("notes", existingNotes);
        request.setAttribute("etudiants", etudiants);
        request.setAttribute("coursList", coursList);
        request.getRequestDispatcher("notes-form.jsp").forward(request, response);
    }

    private void deleteNotes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        notesDAO.deleteNotes(id);
        response.sendRedirect("NotesServlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = request.getParameter("id");
            String etudiantId = request.getParameter("etudiant");
            String coursId = request.getParameter("cours");
            String valeur = request.getParameter("valeur");

            if (etudiantId == null || etudiantId.isEmpty() || coursId == null || coursId.isEmpty() || valeur == null || valeur.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required.");
                return;
            }

            BigDecimal noteValue = new BigDecimal(valeur);
            Etudiant etudiant = etudiantDAO.findStudentById(Integer.parseInt(etudiantId));
            Cours cours = coursDAO.findCoursById(Integer.parseInt(coursId));

            if (etudiant == null || cours == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Etudiant or Cours ID.");
                return;
            }

            Note note = (id == null || id.isEmpty()) ? new Note() : notesDAO.findNotesById(Integer.parseInt(id));
            if (note == null && id != null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Notes not found");
                return;
            }

            note.setIdEtudiant(etudiant);
            note.setIdCours(cours.getIdCours());
            note.setValeur(noteValue);

            if (id == null || id.isEmpty()) {
                notesDAO.saveNotes(note);
            } else {
                notesDAO.updateNotes(note);
            }
            response.sendRedirect("NotesServlet");
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
