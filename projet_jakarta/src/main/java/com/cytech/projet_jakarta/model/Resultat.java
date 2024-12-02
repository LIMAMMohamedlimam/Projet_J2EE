package com.cytech.projet_jakarta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
public class Resultat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idResultat", nullable = false)
    private Integer id;


    @Column(name = "releve", precision = 5, scale = 2)
    private BigDecimal releve;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idEtudiant", nullable = false)
    private Etudiant idEtudiant;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idMatiere", nullable = false)
    private Matiere idMatiere;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getReleve() {
        return releve;
    }

    public void setReleve(BigDecimal releve) {
        this.releve = releve;
    }

    public Etudiant getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(Etudiant idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public Matiere getIdMatiere() {
        return idMatiere;
    }

    public void setIdMatiere(Matiere idMatiere) {
        this.idMatiere = idMatiere;
    }

}