package com.cytech.projet_jakarta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
public class Etudiant {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id", nullable = false)
    private Utilisateur utilisateur;

    @Size(max = 30)
    @NotNull
    @Column(name = "nom", nullable = false, length = 30)
    private String nom;

    @Size(max = 30)
    @NotNull
    @Column(name = "prenom", nullable = false, length = 30)
    private String prenom;

    @Column(name = "dateDeNaissance")
    private LocalDate dateDeNaissance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Etud_uti_fk")
    private int etudUtiFk;

    @Size(max = 15)
    @Column(name = "contact", length = 15)
    private String contact;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
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

    public LocalDate getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public int getEtudUtiFk() {
        return etudUtiFk;
    }

    public void setEtudUtiFk(int etudUtiFk) {
        this.etudUtiFk = etudUtiFk;
    }

    @Override
    public String toString() {
        return   this.nom + " " + this.prenom + " " + this.dateDeNaissance;
    }

}