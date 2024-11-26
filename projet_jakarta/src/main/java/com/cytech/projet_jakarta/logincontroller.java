package com.cytech.projet_jakarta;

import com.cytech.projet_jakarta.dao.UtilisateurDAO;
import com.cytech.projet_jakarta.model.UtilisateurEntity;
import com.cytech.projet_jakarta.utility.JsonParser;
import com.cytech.projet_jakarta.utility.JwtUtil;
import com.cytech.projet_jakarta.utility.UserCredentials;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;


import java.io.BufferedReader;
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

        JSONObject jsonResponse = new JSONObject() ;


        // Read JSON data from the request body
        StringBuilder jsonData = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonData.append(line);
            }
        }

        // Debugging: Print the raw JSON string
        System.out.println("Raw JSON: " + jsonData);

        try {
            // Parse the JSON data
            UserCredentials userCredentials = JsonParser.getUserCredentials(jsonData.toString());
            String email = userCredentials.getEmail(); ;
            String password = userCredentials.getPassword() ;

            // Log or process the received data
            System.out.println("Email: " + email);
            System.out.println("Password: " + password);


            // Check if the user exists in the database
            UtilisateurEntity utilisateur = utilisateurDAO.findByEmailAndPassword(email, password);

            if (utilisateur != null) {

                // User found: Respond with success
                String jwt = JwtUtil.generateJwt(utilisateur);
                System.out.println("JWT: " + jwt);
                System.out.println( "response"+response);
                //request.setAttribute("jwt", jwt);

                response.setContentType("application/json");
                response.getWriter().write(jwt);
                response.setStatus(HttpServletResponse.SC_OK);



            } else {
                // User not found: Redirect to login page with an error message
                jsonResponse.put("erreur", "Invalid email or password");
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(jsonResponse.toString());
                //response.sendRedirect("loginpage.html?error=Invalid+email+or+password");
            }
        } catch (Exception e) {
            // Handle unexpected errors
            jsonResponse.put("erreur", "Exception occured");
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(jsonResponse.toString());
            //response.sendRedirect("loginpage.html?error=An+unexpected+error+occurred");
            e.printStackTrace();
        }

    }

    private String getIndexPage(String role) {
        switch (role) {
            case "admin":
                return "index.html";
            case "student":
                return "index.html";
            case "teacher":
                return "index.html";
        }

        return "loginpage.html" ;

    }
}