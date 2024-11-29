package com.cytech.projet_jakarta.dao;

import com.cytech.projet_jakarta.model.Etudiant;
import com.cytech.projet_jakarta.utility.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;



import java.util.List;

import static com.cytech.projet_jakarta.utility.JsonParser.parseSingleInput;

public class EtudiantDAO {
    public void saveStudent(Etudiant etudiant) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(etudiant);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Etudiant findStudentById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Etudiant.class, id);
        }
    }

    public List<Etudiant> searchByName(String keyword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Etudiant where nom like :keyword or prenom like :keyword";
            return session.createQuery(hql, Etudiant.class)
                    .setParameter("keyword", "%" + keyword + "%")
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getAllStudents() {
            String jsonresponse = "{";
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                List<Etudiant> etudiants = session.createQuery("select e from Etudiant e", Etudiant.class).list();
                int i = 0;
                for (Etudiant etu : etudiants) {
                    String json = "{";
                    i++;

                    System.out.println(etu.toString());
                    json = parseSingleInput(etu.toString());
                    jsonresponse += "\"" + "etu" + (i - 1) + "\":" + json;
                    if (i < etudiants.size()) jsonresponse = jsonresponse + ",";
                }
                jsonresponse += "}";
            }
            return jsonresponse;

    }

    public List<Etudiant> getAllStudentsList() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select e from Etudiant e", Etudiant.class).list();
        }

    }



    public void updateStudent(Etudiant etudiant) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(etudiant);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteStudent(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Etudiant etudiant = session.get(Etudiant.class, id);
            if (etudiant != null) {
                session.remove(etudiant);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
