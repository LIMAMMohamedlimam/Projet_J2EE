package com.cytech.projet_jakarta.utility;

import java.security.SecureRandom;
import java.util.Base64;

public class Singleton {
    private static Singleton instance;
    private String key = null ;
    private Singleton() {
    }
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
            instance.setKey();
        }
        return instance;
    }
    private String generateKeyJWT(){
        // Generate a secure 256-bit key
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32]; // 256 bits
        random.nextBytes(keyBytes);

        return Base64.getEncoder().encodeToString(keyBytes);
    }
    public String getKey() {
        return key;
    }
    public void setKey() {
        this.key = generateKeyJWT();
    }
}
