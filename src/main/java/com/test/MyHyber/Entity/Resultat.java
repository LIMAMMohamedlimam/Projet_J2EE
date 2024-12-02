package com.test.MyHyber.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "Resultat")
public class Resultat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idResultat", nullable = false)
	private Integer id;

	@Size(max = 50)
	@Column(name = "note", length = 50)
	private float note;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "idEtudiant", nullable = false)
	private Etudiant etudiant;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "idMatiere", nullable = false)
	private Matiere matiere;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float getNote() {
		return note;
	}

	public void setNote(float note) {
		this.note = note;
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

	@Override
	public String toString() {
		return "Resultat{" +
				"id=" + id +
				", note='" + note + '\'' +
				", etudiant=" + etudiant +
				", matiere=" + matiere +
				'}';
	}
}
