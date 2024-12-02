package com.cytech.projet_jakarta.dao;

import com.cytech.projet_jakarta.model.Cours;
import com.cytech.projet_jakarta.model.Enseignant;
import com.cytech.projet_jakarta.model.Etudiant;
import com.cytech.projet_jakarta.utility.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CoursDAO {

    public void saveCours(Cours cours) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(cours);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Cours findCoursById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Cours.class, id);
        }
    }

    public List<Cours> getAllCours() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Cours", Cours.class).list();
        }
    }

    public List<Cours> getAllCoursForAssignment() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select c from Cours c join fetch c.matiere m join fetch c.enseignant e";
            return session.createQuery(hql, Cours.class).list();
        }
    }

    public void updateCours(Cours cours) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(cours);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteCours(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Cours cours = session.get(Cours.class, id);
            if (cours != null) {
                session.remove(cours);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Cours> searchCoursByName(String keyword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Cours c join fetch c.matiere m where m.nom like :keyword";
            return session.createQuery(hql, Cours.class)
                    .setParameter("keyword", "%" + keyword + "%")
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void assignTeacherToCourse(int idEnseignant, int idCours) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Cours cours = session.get(Cours.class, idCours);
            Enseignant enseignant = session.get(Enseignant.class, idEnseignant); // Récupère l'objet Enseignant

            if (cours != null && enseignant != null) {
                cours.setEnseignant(enseignant); // Associe l'enseignant au cours
                session.merge(cours);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException("Error assigning teacher to course", e);
        }
    }



    public void assignStudentToCourse(int IdEtudiant, int idCours) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Cours cours = session.get(Cours.class, idCours);
            Etudiant etudiant = session.get(Etudiant.class, IdEtudiant);

            if (cours != null && etudiant != null) {
                cours.getStudents().add(etudiant);
                session.merge(cours);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException("Error assigning student to course", e);
        }
    }}
