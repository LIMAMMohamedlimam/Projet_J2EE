package com.cytech.projet_jakarta.dao;

import com.cytech.projet_jakarta.model.Note;
import com.cytech.projet_jakarta.utility.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class NotesDAO {

    public void saveNotes(Note note) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(note); // Save the Note entity
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Note findNotesById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Note.class, id); // Fetch Note by ID
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Note> getAllNotes() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Note", Note.class).list(); // Correct HQL query
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Note> getNotesByCourse(int idCours) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT n FROM Note n WHERE n.idCours = :idCours";
            return session.createQuery(hql, Note.class)
                    .setParameter("idCours", idCours)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean isTeacherAuthorizedForCourse(int enseignantId, int idCours) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(c) FROM Cours c WHERE c.idCours = :idCours AND c.idEnseignant = :enseignantId";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("idCours", idCours)
                    .setParameter("enseignantId", enseignantId)
                    .uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateNotes(Note note) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(note); // Update the Note entity
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteNotes(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Note note = session.get(Note.class, id);
            if (note != null) {
                session.remove(note); // Delete the Note entity
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
