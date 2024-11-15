CREATE DATABASE GestionScolarite;

USE GestionScolarite; 

CREATE TABLE Utilisateur (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    dateDeNaissance DATE,
    email VARCHAR(100) UNIQUE NOT NULL,
    contact VARCHAR(15),
    motDePasse VARCHAR(255) NOT NULL,
    role ENUM('etudiant', 'enseignant', 'admin') NOT NULL
);

CREATE TABLE Etudiant (
    id INT PRIMARY KEY, 
    FOREIGN KEY (id) REFERENCES Utilisateur(id) ON DELETE CASCADE
);

CREATE TABLE Enseignant (
    id INT PRIMARY KEY,  
    FOREIGN KEY (id) REFERENCES Utilisateur(id) ON DELETE CASCADE
);

CREATE TABLE Admin (
    id INT PRIMARY KEY,  
    FOREIGN KEY (id) REFERENCES Utilisateur(id) ON DELETE CASCADE
);

CREATE TABLE Matiere (
    idMatiere INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(50) NOT NULL
);

CREATE TABLE Cours (
    idCours INT PRIMARY KEY AUTO_INCREMENT,
    idEnseignant INT NOT NULL,
    date DATE,
    idMatiere INT,
    FOREIGN KEY (idEnseignant) REFERENCES Enseignant(id) ON DELETE CASCADE,
    FOREIGN KEY (idMatiere) REFERENCES Matiere(idMatiere) ON DELETE CASCADE
);

CREATE TABLE Inscription (
    idInscription INT PRIMARY KEY AUTO_INCREMENT,
    idEtudiant INT NOT NULL,
    idCours INT NOT NULL,
    dateInscription DATE,
    FOREIGN KEY (idEtudiant) REFERENCES Etudiant(id) ON DELETE CASCADE,
    FOREIGN KEY (idCours) REFERENCES Cours(idCours) ON DELETE CASCADE
);

CREATE TABLE Notes (
    idNotes INT PRIMARY KEY AUTO_INCREMENT,
    idEtudiant INT NOT NULL,
    idCours INT NOT NULL,
    valeur DECIMAL(5, 2),
    FOREIGN KEY (idEtudiant) REFERENCES Etudiant(id) ON DELETE CASCADE,
    FOREIGN KEY (idCours) REFERENCES Cours(idCours) ON DELETE CASCADE
);

CREATE TABLE Resultat (
    idResultat INT PRIMARY KEY AUTO_INCREMENT,
    releve VARCHAR(50),
    idEtudiant INT NOT NULL,
    idMatiere INT NOT NULL,
    FOREIGN KEY (idEtudiant) REFERENCES Etudiant(id) ON DELETE CASCADE,
    FOREIGN KEY (idMatiere) REFERENCES Matiere(idMatiere) ON DELETE CASCADE
);

CREATE INDEX idx_etudiant_nom ON Utilisateur (nom);
CREATE INDEX idx_cours_date ON Cours (date);
CREATE INDEX idx_notes_etudiant ON Notes (idEtudiant);
