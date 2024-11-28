package com.cytech.projet_jakarta.utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonParser {
    public static UserCredentials getUserCredentials(String rawJson) {
        UserCredentials userCredentials = null ;
        try {

            // Create ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Parse JSON to JsonNode or directly to a custom class
            // Option 1: Parse to JsonNode
            JsonNode jsonNode = objectMapper.readTree(rawJson);

            // Access values from JsonNode
            String email = jsonNode.get("email").asText();
            String password = jsonNode.get("password").asText();

            //System.out.println("Email: " + email);
            //System.out.println("Password: " + password);

            // Option 2: Parse to a custom class
             userCredentials = objectMapper.readValue(rawJson, UserCredentials.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userCredentials ;
    }

    public static UserData getUserDataFromRequest(HttpServletRequest request) throws IOException {
            // Read JSON body from the request
            StringBuilder jsonBuilder = new StringBuilder();
            try (BufferedReader reader = request.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonBuilder.append(line);
                }
            }

            // Convert JSON string to UserData object
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonBuilder.toString(), UserData.class);
        }

    public static String parseSingleInput(String input) {
        // Regex to match "Lastname Firstname YYYY-MM-DD"
        String regex = "(\\w+)\\s+(\\w+)\\s+(\\d{4}-\\d{2}-\\d{2})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            // Extract details
            String lastName = matcher.group(1);
            String firstName = matcher.group(2);
            String dateOfBirth = matcher.group(3);

            // Return formatted string
            return  "\""+lastName+"\""  + " : {" +
                    "\"nom\": \"" + lastName + "\", " +
                    "\"prenom\": \"" + firstName + "\", " +
                    "\"dateDeNaissance\": \"" + dateOfBirth + "\"" +
                    "}";
        } else {
            // Handle invalid input
            return "Invalid format";
        }
    }
}
