package com.test.MyHyber.dao;
import com.test.MyHyber.Util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.test.MyHyber.Entity.Enseignant;
import com.test.MyHyber.Entity.Utilisateur;

import java.util.ArrayList;
import java.util.List;

import static com.test.MyHyber.Util.JsonParser.parseSingleInput;

public class EnseignantDAO {
    public void saveTeacher(Enseignant Enseignant) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(Enseignant);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public int saveTeacherData(Utilisateur util)  {
        Enseignant newEnseignant = new Enseignant();
        newEnseignant.setNom(util.getNom());
        newEnseignant.setPrenom(util.getPrenom());
        newEnseignant.setEnseignantUtilisateurFk(util.getId());
        newEnseignant.setDateDeNaissance(util.getDateDeNaissance());
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(newEnseignant);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    public Enseignant findTeacherById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Enseignant.class, id);
        }
    }

    public List<Enseignant> searchByName(String keyword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Enseignant where nom like :keyword or prenom like :keyword";
            return session.createQuery(hql, Enseignant.class)
                    .setParameter("keyword", "%" + keyword + "%")
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Enseignant> findByNameAndPronoun(String nom, String prenom) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select e from Enseignant e   where e.nom like :nom and e.prenom like :prenom ";
            return session.createQuery(hql, Enseignant.class)
                    .setParameter("nom", "%" + nom + "%")
                    .setParameter("prenom", "%" + prenom + "%")
                    .getResultList();
        }
    }

    public String getAllTeachers() {
        String jsonresponse = "{";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Enseignant> Enseignants = session.createQuery("select e from Enseignant e", Enseignant.class).list();
            int i = 0;
            for (Enseignant etu : Enseignants) {
                String json = "{";
                i++;

                System.out.println(etu.toString());
                json = parseSingleInput(etu.toString());
                jsonresponse += "\"" + "etu" + (i - 1) + "\":" + json;
                if (i < Enseignants.size()) jsonresponse = jsonresponse + ",";
            }
            jsonresponse += "}";
        }
        return jsonresponse;

    }

    public List<Enseignant> getAllTeachersList() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select e from Enseignant e", Enseignant.class).list();
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }



    public void updateTeacher(Enseignant Enseignant) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(Enseignant);
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

            Enseignant Enseignant = session.get(Enseignant.class, id);
            if (Enseignant != null) {
                session.remove(Enseignant);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    public void deleteTeacher(String email) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Utilisateur util = new UtilisateurDAO().findByEmail(email) ;
            if (util != null) {
                Enseignant Enseignant = new EnseignantDAO().findByFkUtil(util.getId()) ;
                System.out.println(Enseignant);
                if (Enseignant != null) {
                    session.remove(Enseignant);
                    session.remove(util);
                }
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Enseignant findByFkUtil(int Etud_uti_fk) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select e from Enseignant e where e.etudUtiFk = :Etud_uti_fk";
            return session.createQuery(hql, Enseignant.class)
                    .setParameter("Etud_uti_fk", Etud_uti_fk)
                    .getSingleResult();
        }
    }

}
