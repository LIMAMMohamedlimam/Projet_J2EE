package com.test.MyHyber;

import com.test.MyHyber.Entity.Etudiant;
import com.test.MyHyber.dao.EtudiantDAO;

import java.util.List;

public class HibernateTest {
    private static EtudiantDAO etudiantDAO;

    public static void main(String[] args) {
        init();
        try {
            List<Etudiant> students = etudiantDAO.getAllStudentsList();

            if (students == null || students.isEmpty()) {
                System.out.println("No students found.");
            } else {
                for (Etudiant student : students) {
                    System.out.println("ID Student: " + student.getUtilisateur().getId());
                    System.out.println("Student Name: " + student.getUtilisateur().getNom());
                    System.out.println("Student Username: " + student.getUtilisateur().getPrenom());
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static void init() {
        etudiantDAO = new EtudiantDAO();
    }
}
