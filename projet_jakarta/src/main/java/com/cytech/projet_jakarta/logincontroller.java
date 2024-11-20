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

        JSONObject jsonResponse = new JSONObject() ;


        // Retrieve email and password from request
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        System.out.println(email +" " + password);

        try {
            // Check if the user exists in the database
            UtilisateurEntity utilisateur = utilisateurDAO.findByEmailAndPassword(email, password);

            if (utilisateur != null) {

                // User found: Respond with success
                String jwt = JwtUtil.generateJwt(utilisateur);
                //request.setAttribute("jwt", jwt);

                response.setContentType("application/json");
                response.getWriter().write(jwt);
                System.out.println(response);

                //request.setAttribute("Nom", utilisateur.getNom());
                //request.setAttribute("Prenom", utilisateur.getPrenom());
                //String linkToIndexPage = getIndexPage(utilisateur.getRole()) ;
                //RequestDispatcher dispatcher = request.getRequestDispatcher(linkToIndexPage);// change it to the corresponding page
                //dispatcher.forward(request, response);
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
                jsonResponse.put("erreur", "Invalid email or password");
                response.setContentType("application/json");
                response.getWriter().write(jsonResponse.toString());
                //response.sendRedirect("loginpage.html?error=Invalid+email+or+password");
            }
        } catch (Exception e) {
            // Handle unexpected errors
            jsonResponse.put("erreur", "Exception occured");
            response.setContentType("application/json");
            response.getWriter().write(jsonResponse.toString());
            //response.sendRedirect("loginpage.html?error=An+unexpected+error+occurred");
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

        return "loginpage.html" ;

    }
}