package com.test.MyHyber.Servlet;

import com.test.MyHyber.Entity.*;
import com.test.MyHyber.dao.CoursDAO;
import com.test.MyHyber.dao.EnseignantDAO;

import com.test.MyHyber.dao.UtilisateurDAO;
import com.test.MyHyber.Util.UserData;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

import static com.test.MyHyber.Util.JsonParser.getUserDataFromRequest;

@WebServlet("/EnseignantServlet")
public class EnseignantServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EnseignantDAO EnseignantDAO;
    private JSONObject responseMessage = new JSONObject();

    @Override
    public void init() {
        EnseignantDAO = new EnseignantDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //UserData userData = getUserDataFromRequest(request);
        //response.setContentType("application/json");
        //response.getWriter().write("getAction");
        String action = request.getParameter("action");
        System.out.println(action);
        if (action == null) action = "list";

        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteEnseignant(request, response);
                break;
            case "search":
                searchEnseignants(request, response);
                break;
            default:
                //System.out.println("hello");
                listEnseignants(request, response);
                break;
        }
    }

    private void listEnseignants(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //List<Enseignant> Enseignants = EnseignantDAO.getAllTeachers();
        //for (Enseignant Enseignant : Enseignants) {}
        //request.setAttribute("Enseignants", Enseignants);
        //request.getRequestDispatcher("Enseignant-list.jsp").forward(request, response);
        response.setContentType("application/json");
        //System.out.println("liste Enseignant "+ EnseignantDAO.getAllTeachers());
        response.getWriter().write(EnseignantDAO.getAllTeachers());
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Enseignant-form.jsp").forward(request, response);
        EnseignantDAO = new EnseignantDAO();
        List<Enseignant> etudinatlist = EnseignantDAO.getAllTeachersList() ;
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Enseignant existingEnseignant = EnseignantDAO.findTeacherById(id);
        request.setAttribute("Enseignant", existingEnseignant);
        request.getRequestDispatcher("Enseignant-form.jsp").forward(request, response);
    }

    private void deleteEnseignant(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        EnseignantDAO.deleteTeacher(id);
        response.sendRedirect("EnseignantServlet");
    }

    private void searchEnseignants(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Enseignant> Enseignants = EnseignantDAO.searchByName(keyword);
        request.setAttribute("Enseignants", Enseignants);
        request.getRequestDispatcher("Enseignant-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserData userData ;
        UtilisateurDAO utiDao = new UtilisateurDAO();
        userData = getUserDataFromRequest(request);
        System.out.println(userData.toString());
        String nom = userData.getName();
        String email = userData.getEmail() ;
        String password = userData.getPassword() ;
        String prenom = userData.getSurname();
        String dob = userData.getDob();
        System.out.println(dob);
        String contact =  userData.getContact() ;
        Enseignant newEnseignant ;

        //List <Enseignant> Enseignants = EnseignantDAO.findByNameAndPronoun(nom , prenom) ;
        Utilisateur utilisateurEnseignant = utiDao.findByNameAndPronoun(prenom, nom);
        if(utilisateurEnseignant != null)
            System.out.println("utilisateur: "+utilisateurEnseignant);
        if(utilisateurEnseignant != null) {
            newEnseignant = new Enseignant();
            newEnseignant.setEnseignantUtilisateurFk(utilisateurEnseignant.getId());
            newEnseignant.setNom(userData.getName());
            newEnseignant.setPrenom(userData.getSurname());
            newEnseignant.setDateDeNaissance(utilisateurEnseignant.getDateDeNaissance());
            EnseignantDAO.updateTeacher(newEnseignant);
            response.setStatus(HttpServletResponse.SC_OK);
            responseMessage.put("message","Enseignant: "+newEnseignant.getContact() +" "+newEnseignant.getPrenom() + " est mis à jour." ) ;
            response.setContentType("application/json");
            response.getWriter().write(responseMessage.toString());
        }else{
            LocalDate localDate = LocalDate.parse(dob);
            System.out.println(localDate);
            newEnseignant = new Enseignant();
            utilisateurEnseignant = new Utilisateur();
            utilisateurEnseignant.setNom(nom);
            utilisateurEnseignant.setPrenom(prenom);
            utilisateurEnseignant.setEmail(email);
            utilisateurEnseignant.setPassword(password);
            utilisateurEnseignant.setContact(contact);
            utilisateurEnseignant.setRole("Enseignant");
            utilisateurEnseignant.setDateDeNaissance(localDate);
            utiDao.saveData(utilisateurEnseignant);
            newEnseignant.setPrenom(prenom);
            newEnseignant.setNom(nom);
            newEnseignant.setContact(contact) ;
            newEnseignant.setDateDeNaissance(localDate);
            System.out.println(localDate);
            EnseignantDAO.saveTeacherData(utilisateurEnseignant);

            response.setStatus(HttpServletResponse.SC_OK);
            responseMessage.put("message","Enseignant: "+newEnseignant.getContact() +" "+newEnseignant.getPrenom() + " est bien ajouter." ) ;
            response.setContentType("application/json");
            response.getWriter().write(responseMessage.toString());

        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Parse the JSON body
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String requestBody = sb.toString();
        System.out.println("Request Body: " + requestBody);

        Gson gson = new Gson();
        UserData Teacher = gson.fromJson(requestBody, UserData.class);
        String email = Teacher.getEmail();
        EnseignantDAO.deleteTeacher(email);

        // Now you have the Teacher data
        System.out.println("Name: " + Teacher.getName());
        System.out.println("Email: " + Teacher.getEmail());

        // Respond to the client
        response.setStatus(HttpServletResponse.SC_OK);
        responseMessage.put("message" ,"Data deleted for: " + Teacher.getName() + " with email " + Teacher.getEmail() );
        response.setContentType("application/json");
        response.getWriter().write(responseMessage.toString());
    }

}

