package com.cytech.projet_jakarta.utility;

import com.cytech.projet_jakarta.model.UtilisateurEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.json.simple.JSONObject;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = Singleton.getInstance().getKey();
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    public static String generateToken(String userId) {
        // Create a SecretKey from the SECRET_KEY string
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        return Jwts.builder()
                .setSubject(userId) // User ID
                .setIssuedAt(new Date()) // Token issue time
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Token expiration
                .signWith(key, SignatureAlgorithm.HS256) // Signing with SecretKey
                .compact();
    }

    public static Claims validateToken(String token) throws Exception {
        // Create a SecretKey from the SECRET_KEY string
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        // Use the parserBuilder to parse the JWT and validate it
        return Jwts.parserBuilder()
                .setSigningKey(key) // Set the signing key using SecretKey
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static String generateJwt(UtilisateurEntity utilisateur) throws Exception {

        String userId = utilisateur.getId().toString() ;
        JSONObject jsonJwt = new JSONObject();
        jsonJwt.put("nom", utilisateur.getNom());
        jsonJwt.put("role", utilisateur.getRole());
        jsonJwt.put("nom", utilisateur.getNom());
        jsonJwt.put("prenom", utilisateur.getPrenom());
        jsonJwt.put("Id", utilisateur.getId());
        jsonJwt.put("jwtToken",generateToken(userId) );

        return jsonJwt.toJSONString();

    }
}
