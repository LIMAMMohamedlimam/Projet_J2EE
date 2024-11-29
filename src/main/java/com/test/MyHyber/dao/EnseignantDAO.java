package com.test.MyHyber.dao;
import com.test.MyHyber.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.test.MyHyber.Entity.Enseignant;

import java.util.List;

public class EnseignantDAO {

    public void saveTeacher(Enseignant enseignant) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(enseignant);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Enseignant findTeacherById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Enseignant.class, id);
        }
    }

    public List<Enseignant> searchByName(String keyword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Enseignant where utilisateur.nom like :keyword or utilisateur.prenom like :keyword";
            return session.createQuery(hql, Enseignant.class)
                    .setParameter("keyword", "%" + keyword + "%")
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Enseignant> getAllTeachers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Enseignant", Enseignant.class).list();
        }
    }

    public void updateTeacher(Enseignant enseignant) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(enseignant);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteTeacher(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Enseignant enseignant = session.get(Enseignant.class, id);
            if (enseignant != null) {
                session.remove(enseignant);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}