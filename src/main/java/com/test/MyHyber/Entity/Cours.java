package com.test.MyHyber.Entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cours")
public class Cours {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCours;

	@Column(name = "idEnseignant", nullable = false) // Store the teacher ID directly
	private int idEnseignant;

	@Temporal(TemporalType.DATE)
	private Date date;

	@ManyToOne
	@JoinColumn(name = "idMatiere")
	private Matiere matiere;

	@ManyToMany
	@JoinTable(
			name = "course_student",
			joinColumns = @JoinColumn(name = "course_id"),
			inverseJoinColumns = @JoinColumn(name = "student_id")
	)
	private Set<Etudiant> students = new HashSet<>();

	// Getters and Setters
	public int getIdCours() {
		return idCours;
	}

	public void setIdCours(int idCours) {
		this.idCours = idCours;
	}

	public int getIdEnseignant() {
		return idEnseignant;
	}

	public void setIdEnseignant(int idEnseignant) {
		this.idEnseignant = idEnseignant;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Matiere getMatiere() {
		return matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}

	public Set<Etudiant> getStudents() {
		return students;
	}

	public void setStudents(Set<Etudiant> students) {
		this.students = students;
	}
}
