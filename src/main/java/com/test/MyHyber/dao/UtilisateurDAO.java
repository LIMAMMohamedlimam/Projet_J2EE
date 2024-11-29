package com.test.MyHyber.DAO;

import com.test.MyHyber.Entity.Utilisateur;
import com.test.MyHyber.Util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UtilisateurDAO {

    public int save(Utilisateur utilisateur) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(utilisateur);
            tx.commit();
            return 1; // Success
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return -1; // Failure
        } finally {
            session.close();
        }
    }

    public int update(Utilisateur utilisateur) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.merge(utilisateur);
            tx.commit();
            return 1; // Success
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return -1; // Failure
        } finally {
            session.close();
        }
    }

    public Utilisateur findByEmailAndPassword(String email, String password) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Utilisateur> query = session.createQuery("FROM Utilisateur WHERE email = :email AND password = :password", Utilisateur.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            return query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
            return null; // No result
        } finally {
            session.close();
        }
    }

    public List<Utilisateur> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Utilisateur> query = session.createQuery("FROM Utilisateur", Utilisateur.class);
            return query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public int delete(Utilisateur utilisateur) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.remove(utilisateur);
            tx.commit();
            return 1; // Success
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return -1; // Failure
        } finally {
            session.close();
        }
    }
}
