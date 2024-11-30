package com.test.MyHyber.dao;
import com.test.MyHyber.Util.HibernateUtil;
import com.test.MyHyber.Entity.Inscription;
import com.test.MyHyber.Entity.Cours;
import com.test.MyHyber.Entity.Etudiant;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class InscriptionDAO {

    public void saveInscription(Inscription inscription) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(inscription);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Inscription findInscriptionById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Inscription.class, id);
        }
    }

    public List<Inscription> getAllInscriptions() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Inscription", Inscription.class).list();
        }
    }

    public void updateInscription(Inscription inscription) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(inscription);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteInscription(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Inscription inscription = session.get(Inscription.class, id);
            if (inscription != null) {
                session.remove(inscription);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    public void enrollStudentInCourse(int IdEtudiant, int idCours) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Cours cours = session.get(Cours.class, idCours);
            Etudiant etudiant = session.get(Etudiant.class, IdEtudiant);

            if (cours == null || etudiant == null) {
                throw new RuntimeException("Course or Student not found");
            }

            Inscription inscription = new Inscription();
            inscription.setCours(cours);
            inscription.setEtudiant(etudiant);

            session.persist(inscription);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException("Error enrolling student in course", e);
        }
    }}