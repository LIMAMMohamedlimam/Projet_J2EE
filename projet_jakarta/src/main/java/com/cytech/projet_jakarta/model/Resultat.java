package com.cytech.projet_jakarta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Resultat {
    @Id
    @Column(name = "idResultat", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "releve", length = 50)
    private String releve;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idEtudiant", nullable = false)
    private Etudiant idEtudiant;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReleve() {
        return releve;
    }

    public void setReleve(String releve) {
        this.releve = releve;
    }

    public Etudiant getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(Etudiant idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

}