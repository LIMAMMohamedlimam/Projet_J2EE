package com.test.MyHyber.dao;

import com.test.MyHyber.Util.HibernateUtil;
import com.test.MyHyber.Entity.Notes;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class NotesDAO {

    public void saveNotes(Notes notes) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(notes);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Notes findNotesById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Notes.class, id);
        }
    }

    public List<Notes> getAllNotes() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Notes", Notes.class).list();
        }
    }

    public List<Notes> getNotesByCourse(int idCours) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Notes where cours.idCours = :idCours";
            return session.createQuery(hql, Notes.class)
                    .setParameter("idCours", idCours)
                    .list();
        }
    }

    public boolean isTeacherAuthorizedForCourse(int EnseignantId, int idCours) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(c) FROM Cours c WHERE c.idCours = :idCours AND c.idEnseignant = :EnseignantId";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("idCours", idCours)
                    .setParameter("EnseignantId", EnseignantId)
                    .uniqueResult();
            return count != null && count > 0;
        }
    }

    public void updateNotes(Notes notes) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(notes);
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
            Notes notes = session.get(Notes.class, id);
            if (notes != null) {
                session.remove(notes);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
