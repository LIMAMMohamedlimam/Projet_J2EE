package com.cytech.projet_jakarta.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
public class Enseignant {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 30)
    @Column(name = "nom", length = 30)
    private String nom;

    @Size(max = 30)
    @Column(name = "prenom", length = 30)
    private String prenom;

    @NotNull
    @Column(name = "Enseignant_Utilisateur_fk", nullable = false)
    private Integer enseignantUtilisateurFk;

    @Column(name = "dateDeNaissance")
    private LocalDate dateDeNaissance;

    @Size(max = 15)
    @Column(name = "contact", length = 15)
    private String contact;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Integer getEnseignantUtilisateurFk() {
        return enseignantUtilisateurFk;
    }

    public void setEnseignantUtilisateurFk(Integer enseignantUtilisateurFk) {
        this.enseignantUtilisateurFk = enseignantUtilisateurFk;
    }

    public LocalDate getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return this.nom + " " + this.prenom + " " + this.dateDeNaissance;
    }

}