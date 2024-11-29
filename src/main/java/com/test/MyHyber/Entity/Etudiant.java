package com.test.MyHyber.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "etudiant")
public class Etudiant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne(fetch = FetchType.EAGER)
	@MapsId
	@JoinColumn(name = "id")
	private Utilisateur utilisateur;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	@ManyToMany(mappedBy = "students")
	private Set<Cours> courses = new HashSet<>();

	public Set<Cours> getCourses() {
		return courses;
	}

	public void setCourses(Set<Cours> courses) {
		this.courses = courses;
	}

}
