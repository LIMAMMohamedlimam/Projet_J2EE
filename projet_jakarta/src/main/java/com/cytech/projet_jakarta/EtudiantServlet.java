package com.cytech.projet_jakarta ;


import java.io.IOException;
import java.util.List;


import com.cytech.projet_jakarta.dao.EtudiantDAO;
import com.cytech.projet_jakarta.dao.UtilisateurDAO;
import com.cytech.projet_jakarta.model.Etudiant;
import com.cytech.projet_jakarta.model.Utilisateur;
import com.cytech.projet_jakarta.utility.UserData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.cytech.projet_jakarta.utility.JsonParser.getUserDataFromRequest;


@WebServlet("/EtudiantServlet")
public class EtudiantServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EtudiantDAO etudiantDAO;

    @Override
    public void init() {
        etudiantDAO = new EtudiantDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //UserData userData = getUserDataFromRequest(request);
        //response.setContentType("application/json");
        //response.getWriter().write("getAction");
        String action = request.getParameter("data");
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
        String prenom = userData.getSurname();
        String dob = userData.getDob();
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
        }else{
            System.out.println("\nnull\n");
            newEtudiant = new Etudiant();
            utilisateurEtudiant = new Utilisateur();
            utilisateurEtudiant.setNom(userData.getName());
            utilisateurEtudiant.setPrenom(userData.getSurname());
            utilisateurEtudiant.setEmail("NNNNNNNNNN");
            utilisateurEtudiant.setPassword("nnnnnnnnn");
            utilisateurEtudiant.setRole("etudiant");
            utiDao.saveData(utilisateurEtudiant);
            newEtudiant.setPrenom(userData.getSurname());
            newEtudiant.setNom(userData.getName());
            etudiantDAO.saveStudentData(utilisateurEtudiant);
        }

//
        //Etudiant etudiant = (id == null || id.isEmpty()) ? new Etudiant() : etudiantDAO.findStudentById(Integer.parseInt(id));
//
        //Utilisateur utilisateur = etudiant.getUtilisateur();
        //if (utilisateur == null) {
        //    utilisateur = new Utilisateur();
        //    etudiant.setUtilisateur(utilisateur);
        //}
//
        //utilisateur.setNom(nom);
        //utilisateur.setPrenom(prenom);
//
        //if (id == null || id.isEmpty()) {
        //    etudiantDAO.saveStudent(etudiant);
        //} else {
        //    etudiantDAO.updateStudent(etudiant);
        //}
//
        //response.sendRedirect("EtudiantServlet");
    }

}