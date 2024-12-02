package com.cytech.projet_jakarta.model;

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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idEnseignant", nullable = false)
	private Enseignant enseignant;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idMatiere")
	private Matiere matiere;

	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(name = "jour")
	private String jour;


	@Column(name = "horaire")
	private String horaire;


	@ManyToMany
	@JoinTable(
			name = "student_course",
			joinColumns = @JoinColumn(name = "idCours"),
			inverseJoinColumns = @JoinColumn(name = "idEtudiant")
	)
	private Set<Etudiant> students = new HashSet<>();

	// Getters and Setters
	public int getIdCours() {
		return idCours;
	}

	public void setIdCours(int idCours) {
		this.idCours = idCours;
	}

	public Enseignant getEnseignant() {
		return enseignant;
	}

	public void setEnseignant(Enseignant enseignant) {
		this.enseignant = enseignant;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getHoraire() {
		return horaire;
	}

	public void setHoraire(String horaire) {
		this.horaire = horaire;
	}

	public String getJour() {
		return jour;
	}

	public void setJour(String jour) {
		this.jour = jour;
	}

	public Matiere getMatiere() {
		return matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}
	public void addStudent(Etudiant etudiant) {
		this.students.add(etudiant);
	}

	public void removeStudent(Etudiant etudiant) {
		this.students.remove(etudiant);
	}


	public Set<Etudiant> getStudents() {
		return students;
	}

	public void setStudents(Set<Etudiant> students) {
		this.students = students;
	}
}
