package com.test.MyHyber;
import com.test.MyHyber.Entity.Etudiant;
import com.test.MyHyber.dao.EtudiantDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class HibernateTest {
    public static void main(String[] args) {
        EtudiantDAO dao = new EtudiantDAO();
        List<Etudiant> students = dao.getAllStudents();

        if (students == null || students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Etudiant student : students) {
                System.out.println("id student: " + student.getUtilisateur().getId());
                System.out.println("Student name: " + student.getUtilisateur().getNom());
                System.out.println("Student username: " + student.getUtilisateur().getPrenom());
            }
        }
    }
}
