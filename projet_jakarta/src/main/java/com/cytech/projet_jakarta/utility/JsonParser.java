package com.cytech.projet_jakarta.utility;

import com.cytech.projet_jakarta.model.UtilisateurEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
}