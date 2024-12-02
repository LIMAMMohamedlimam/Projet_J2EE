package com.cytech.projet_jakarta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Inscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idInscription", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idEtudiant", nullable = false)
    private Etudiant idEtudiant;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCours", nullable = false)
    private Cours idCours;

    @Column(name = "dateInscription")
    private LocalDate dateInscription;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Etudiant getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(Etudiant idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public Cours getIdCours() {
        return idCours;
    }

    public void setIdCours(Cours idCours) {
        this.idCours = idCours;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

}