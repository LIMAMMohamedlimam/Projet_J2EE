package com.test.MyHyber.Servlet;

import com.test.MyHyber.Entity.Notes;
import com.test.MyHyber.Entity.Etudiant;
import com.test.MyHyber.Entity.Cours;
import com.test.MyHyber.dao.NotesDAO;
import com.test.MyHyber.dao.EtudiantDAO;
import com.test.MyHyber.dao.CoursDAO;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
                case "viewByCourse":
                    validateIdParameter(request, "courseId");
                    viewNotesByCourse(request, response);
                    break;
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
                case "manageGrades":
                    manageGrades(request, response);
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
        List<Notes> notesList = notesDAO.getAllNotes();
        request.setAttribute("notesList", notesList);
        request.getRequestDispatcher("notes-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Etudiant> etudiants = etudiantDAO.getAllStudents();
        List<Cours> coursList = coursDAO.getAllCours();
        request.setAttribute("etudiants", etudiants);
        request.setAttribute("coursList", coursList);
        request.getRequestDispatcher("notes-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Notes existingNotes = notesDAO.findNotesById(id);
        if (existingNotes == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Notes not found");
            return;
        }
        List<Etudiant> etudiants = etudiantDAO.getAllStudents();
        List<Cours> coursList = coursDAO.getAllCours();
        request.setAttribute("notes", existingNotes);
        request.setAttribute("etudiants", etudiants);
        request.setAttribute("coursList", coursList);
        request.getRequestDispatcher("notes-form.jsp").forward(request, response);
    }

    private void viewNotesByCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idCours = Integer.parseInt(request.getParameter("courseId"));
        List<Notes> notesList = notesDAO.getNotesByCourse(idCours);
        request.setAttribute("notesList", notesList);
        request.setAttribute("courseId", idCours);
        request.getRequestDispatcher("notes-by-course.jsp").forward(request, response);
    }

    private void deleteNotes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        notesDAO.deleteNotes(id);
        response.sendRedirect("NotesServlet");
    }

    private void manageGrades(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idEnseignant = request.getParameter("idEnseignant");
        String idCours = request.getParameter("courseId");

        if (idEnseignant != null && idCours != null) {
            boolean isAuthorized = notesDAO.isTeacherAuthorizedForCourse(Integer.parseInt(idEnseignant), Integer.parseInt(idCours));
            if (!isAuthorized) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized to manage grades for this course.");
                return;
            }

            // Fetch grades for this course
            List<Notes> grades = notesDAO.getNotesByCourse(Integer.parseInt(idCours));
            request.setAttribute("grades", grades);
            request.setAttribute("courseId", idCours);
            request.getRequestDispatcher("manage-grades.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Teacher ID and Course ID are required.");
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
    }}
