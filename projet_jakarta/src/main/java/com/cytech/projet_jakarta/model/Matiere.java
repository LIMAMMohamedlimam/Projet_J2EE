package com.cytech.projet_jakarta.model;

import jakarta.persistence.*;

@Entity
@Table(name = "matiere")
public class Matiere {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idMatiere;

	@Column(nullable = false, length = 50)
	private String nom;

	public int getIdMatiere() {
		return idMatiere;
	}

	public void setIdMatiere(int idMatiere) {
		this.idMatiere = idMatiere;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
}
