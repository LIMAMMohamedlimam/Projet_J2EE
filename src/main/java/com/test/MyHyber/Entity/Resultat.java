package com.test.MyHyber.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "resultat")
public class Resultat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idResultat;

    @Column(length = 50)
    private String releve;

    @ManyToOne
    @JoinColumn(name = "idEtudiant", nullable = false)
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "idMatiere", nullable = false)
    private Matiere matiere;

	public int getIdResultat() {
		return idResultat;
	}

	public void setIdResultat(int idResultat) {
		this.idResultat = idResultat;
	}

	public String getReleve() {
		return releve;
	}

	public void setReleve(String releve) {
		this.releve = releve;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	public Matiere getMatiere() {
		return matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}
}

