package com.cytech.projet_jakarta.dao;

import com.cytech.projet_jakarta.model.Etudiant;
import com.cytech.projet_jakarta.utility.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;



import java.util.List;

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

    public List<Etudiant> getAllStudents() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Etudiant", Etudiant.class).list();
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
