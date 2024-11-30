package com.test.MyHyber.Util;

public class UserData {
    private String name;
    private String surname;
    private String dob;
    private String contact;

    @Override
    public String toString() {
        return this.name + " " + this.surname + " " + this.dob + " " + this.contact;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}