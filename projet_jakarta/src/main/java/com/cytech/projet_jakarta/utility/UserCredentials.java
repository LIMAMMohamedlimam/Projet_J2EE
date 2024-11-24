package com.cytech.projet_jakarta.utility;

import jakarta.json.Json;

public class UserCredentials {
    private String email;
    private String password;


    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}