package com.test.MyHyber.dao;
import com.test.MyHyber.Util.HibernateUtil;
import com.test.MyHyber.Entity.Matiere;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class MatiereDAO {

    public void saveMatiere(Matiere matiere) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(matiere);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Matiere findMatiereById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Matiere.class, id);
        }
    }

    public List<Matiere> getAllMatieres() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Matiere", Matiere.class).list();
        }
    }

    public void updateMatiere(Matiere matiere) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(matiere);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Matiere> searchByName(String keyword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Matiere where nom like :keyword";
            return session.createQuery(hql, Matiere.class)
                    .setParameter("keyword", "%" + keyword + "%")
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteMatiere(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Matiere matiere = session.get(Matiere.class, id);
            if (matiere != null) {
                session.remove(matiere);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}