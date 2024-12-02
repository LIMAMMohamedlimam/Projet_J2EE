package com.cytech.projet_jakarta ;


import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


import com.cytech.projet_jakarta.dao.EtudiantDAO;
import com.cytech.projet_jakarta.dao.UtilisateurDAO;
import com.cytech.projet_jakarta.model.Etudiant;
import com.cytech.projet_jakarta.model.Utilisateur;
import com.cytech.projet_jakarta.utility.UserData;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

import static com.cytech.projet_jakarta.utility.JsonParser.getUserDataFromRequest;


@WebServlet("/EtudiantServlet")
public class EtudiantServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EtudiantDAO etudiantDAO;
    private JSONObject responseMessage = new JSONObject();

    @Override
    public void init() {
        etudiantDAO = new EtudiantDAO();
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
                deleteEtudiant(request, response);
                break;
            case "search":
                searchEtudiants(request, response);
                break;
            default:
                //System.out.println("hello");
                listEtudiants(request, response);
                break;
        }
    }

    private void listEtudiants(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //List<Etudiant> etudiants = etudiantDAO.getAllStudents();
        //for (Etudiant etudiant : etudiants) {}
        //request.setAttribute("etudiants", etudiants);
        //request.getRequestDispatcher("etudiant-list.jsp").forward(request, response);
        response.setContentType("application/json");
        //System.out.println("liste etudiant "+ etudiantDAO.getAllStudents());
        response.getWriter().write(etudiantDAO.getAllStudents());
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("etudiant-form.jsp").forward(request, response);
        etudiantDAO = new EtudiantDAO();
        List<Etudiant> etudinatlist = etudiantDAO.getAllStudentsList() ;
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Etudiant existingEtudiant = etudiantDAO.findStudentById(id);
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
        Etudiant newEtudiant ;

        //List <Etudiant> etudiants = etudiantDAO.findByNameAndPronoun(nom , prenom) ;
        Utilisateur utilisateurEtudiant = utiDao.findByNameAndPronoun(prenom, nom);
        if(utilisateurEtudiant != null)
            System.out.println("utilisateur: "+utilisateurEtudiant);
        if(utilisateurEtudiant != null) {
            newEtudiant = new Etudiant();
            newEtudiant.setEtudUtiFk(utilisateurEtudiant.getId());
            newEtudiant.setNom(userData.getName());
            newEtudiant.setPrenom(userData.getSurname());
            newEtudiant.setDateDeNaissance(utilisateurEtudiant.getDateDeNaissance());
            etudiantDAO.updateStudent(newEtudiant);
            response.setStatus(HttpServletResponse.SC_OK);
            responseMessage.put("message","Etudiant: "+newEtudiant.getContact() +" "+newEtudiant.getPrenom() + " est mis à jour." ) ;
            response.setContentType("application/json");
            response.getWriter().write(responseMessage.toString());
        }else{
            LocalDate localDate = LocalDate.parse(dob);
            System.out.println(localDate);
            newEtudiant = new Etudiant();
            utilisateurEtudiant = new Utilisateur();
            utilisateurEtudiant.setNom(nom);
            utilisateurEtudiant.setPrenom(prenom);
            utilisateurEtudiant.setEmail(email);
            utilisateurEtudiant.setPassword(password);
            utilisateurEtudiant.setContact(contact);
            utilisateurEtudiant.setRole("etudiant");
            utilisateurEtudiant.setDateDeNaissance(localDate);
            utiDao.saveData(utilisateurEtudiant);
            newEtudiant.setPrenom(prenom);
            newEtudiant.setNom(nom);
            newEtudiant.setContact(contact) ;
            newEtudiant.setDateDeNaissance(localDate);
            System.out.println(localDate);
            etudiantDAO.saveStudentData(utilisateurEtudiant);

            response.setStatus(HttpServletResponse.SC_OK);
            responseMessage.put("message","Etudiant: "+newEtudiant.getContact() +" "+newEtudiant.getPrenom() + " est bien ajouter." ) ;
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

        // Convert JSON string to an object (e.g., using Gson or Jackson)
        Gson gson = new Gson();
        UserData student = gson.fromJson(requestBody, UserData.class);
        String email = student.getEmail();
        etudiantDAO.deleteStudent(email);

        // Now you have the student data
        System.out.println("Name: " + student.getName());
        System.out.println("Email: " + student.getEmail());

        // Respond to the client
        response.setStatus(HttpServletResponse.SC_OK);
        responseMessage.put("message" ,"Data deleted for: " + student.getName() + " with email " + student.getEmail() );
        response.setContentType("application/json");
        response.getWriter().write(responseMessage.toString());
    }

    }

