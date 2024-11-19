package com.cytech.projet_jakarta;

import com.cytech.projet_jakarta.dao.UtilisateurDAO;
import com.cytech.projet_jakarta.model.UtilisateurEntity;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

@WebServlet(name = "logincontroller", value = "/logincontroller")
public class logincontroller extends HttpServlet {

    private UtilisateurDAO utilisateurDAO ;

    @Override
    public void init() throws ServletException {
        utilisateurDAO = new UtilisateurDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Retrieve email and password from request
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Check if the user exists in the database
            UtilisateurEntity utilisateur = utilisateurDAO.findByEmailAndPassword(email, password);

            if (utilisateur != null) {
                // User found: Respond with success
                response.setContentType("text/html");
                response.getWriter().println("<h1>Login successful!</h1>");
                response.getWriter().println("<p>Welcome, " + utilisateur.getNom()+" "+utilisateur.getPrenom() + ".</p>");
            } else {
                // User not found: Respond with error
                response.setContentType("text/html");
                response.getWriter().println("<h1>Login failed!</h1>");
                response.getWriter().println("<p>Invalid email or password.</p>");
            }
        } catch (Exception e) {
            // Handle errors
            response.setContentType("text/html");
            response.getWriter().println("<h1>Error</h1>");
            response.getWriter().println("<p>There was an error processing your request. Please try again later.</p>");
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}