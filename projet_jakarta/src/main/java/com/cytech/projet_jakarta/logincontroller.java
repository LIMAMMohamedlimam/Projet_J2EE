package com.cytech.projet_jakarta;

import com.cytech.projet_jakarta.dao.UtilisateurDAO;
import com.cytech.projet_jakarta.model.UtilisateurEntity;
import com.cytech.projet_jakarta.utility.JwtUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;


import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "logincontroller", value = "/logincontroller")
public class logincontroller extends HttpServlet {

    private UtilisateurDAO utilisateurDAO ;

    @Override
    public void init() throws ServletException {
        utilisateurDAO = new UtilisateurDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Retrieve email and password from request
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Check if the user exists in the database
            UtilisateurEntity utilisateur = utilisateurDAO.findByEmailAndPassword(email, password);

            if (utilisateur != null) {

                // User found: Respond with success
                String token = JwtUtil.generateToken(utilisateur.getId() + "");
                request.setAttribute("token", token);

                request.setAttribute("Nom", utilisateur.getNom());
                request.setAttribute("Prenom", utilisateur.getPrenom());
                String linkToIndexPage = getIndexPage(utilisateur.getRole()) ;
                RequestDispatcher dispatcher = request.getRequestDispatcher(linkToIndexPage);// change it to the corresponding page
                dispatcher.forward(request, response);
                //response.setContentType("application/json");
                //response.setCharacterEncoding("UTF-8");
                //response.addHeader("Authorization", "Bearer " + token);
//
                //// Send response with token
                //PrintWriter out = response.getWriter();
                //JSONObject jsonResponse = new JSONObject();
                //jsonResponse.put("token", token);
                //out.print(jsonResponse);
                //out.flush();

                // Redirect to login page


            } else {
                // User not found: Redirect to login page with an error message
                response.sendRedirect("loginpage.jsp?error=Invalid+email+or+password");
            }
        } catch (Exception e) {
            // Handle unexpected errors
            response.sendRedirect("loginpage.jsp?error=An+unexpected+error+occurred");
            e.printStackTrace();
        }

    }

    private String getIndexPage(String role) {
        switch (role) {
            case "admin":
                return "index.jsp";
            case "student":
                return "index.jsp";
            case "teacher":
                return "index.jsp";
        }

        return "loginpage.jsp" ;

    }
}