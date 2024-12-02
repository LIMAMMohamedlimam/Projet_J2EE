package com.test.MyHyber.dao;
import com.test.MyHyber.Util.HibernateUtil;
import com.test.MyHyber.Entity.Resultat;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ResultatDAO {

    public void saveResultat(Resultat resultat) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(resultat);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Resultat findResultatById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Resultat.class, id);
        }
    }

    public List<Resultat> getAllResultats() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Resultat", Resultat.class).list();
        }
    }
    
    public Double calculateAverageByStudent(int IdEtudiant) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT AVG(n.valeur) FROM Note n WHERE n.idEtudiant = :IdEtudiant";
            return session.createQuery(hql, Double.class) 
                          .setParameter("IdEtudiant", IdEtudiant)
                          .uniqueResult();
        }
    }

    public List<Resultat> getResultsByStudent(int IdEtudiant) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Resultat r WHERE r.etudiant.id = :IdEtudiant";
            return session.createQuery(hql, Resultat.class)
                    .setParameter("IdEtudiant", IdEtudiant)
                    .list();
        }
    }

    public void updateResultat(Resultat resultat) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(resultat);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteResultat(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Resultat resultat = session.get(Resultat.class, id);
            if (resultat != null) {
                session.remove(resultat);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
