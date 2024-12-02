package com.test.MyHyber.Servlet;

import com.test.MyHyber.dao.UtilisateurDAO;
import com.test.MyHyber.Entity.Utilisateur;
import com.test.MyHyber.Util.JsonParser;
import com.test.MyHyber.Util.JwtUtil;
import com.test.MyHyber.Util.UserCredentials;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "logincontroller", value = "/logincontroller")
public class loginController extends HttpServlet {

    private UtilisateurDAO utilisateurDAO;

    @Override
    public void init() throws ServletException {
        utilisateurDAO = new UtilisateurDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject jsonResponse = new JSONObject();

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
            String email = userCredentials.getEmail();
            String password = userCredentials.getPassword();

            // Log the received data
            System.out.println("Email: " + email);
            System.out.println("Password: " + password);

            // Check if the user exists in the database
            Utilisateur utilisateur = utilisateurDAO.findByEmailAndPassword(email, password);

            if (utilisateur != null) {
                // User found: Respond with success
                String jwt = JwtUtil.generateJwt(utilisateur);
                System.out.println("response" + response);
                String role = utilisateur.getRole(); // Assurez-vous que "role" existe dans votre entité.
                String page = getIndexPage(role);

                // Rediriger vers la page appropriée
                response.sendRedirect(page);

                response.setContentType("application/json");
                response.getWriter().write("{\"jwt\":\"" + jwt + "\"}");
                response.setStatus(HttpServletResponse.SC_OK);

            } else {
                // User not found: Respond with an error message
                jsonResponse.put("error", "Invalid email or password");
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(jsonResponse.toString());
            }
        } catch (Exception e) {
            // Handle unexpected errors
            jsonResponse.put("error", "An unexpected error occurred.");
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(jsonResponse.toString());
            e.printStackTrace();
        }
    }

    private String getIndexPage(String role) {
        switch (role) {
            case "admin":
                return "Admin/admin-dashboard.jsp";
            case "student":
                return "student-dashboard.jsp";
            case "teacher":
                return "teacher-dashboard.jsp";
            default:
                return "loginpage.html";
        }
    }
}
