package com.test.MyHyber.dao;

import com.test.MyHyber.Entity.Etudiant;
import com.test.MyHyber.Entity.Utilisateur;
import com.test.MyHyber.Util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

import static com.test.MyHyber.Util.JsonParser.parseSingleInput;

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

    public int saveStudentData(Utilisateur util)  {
        Etudiant newEtudiant = new Etudiant();
        newEtudiant.setNom(util.getNom());
        newEtudiant.setPrenom(util.getPrenom());
        newEtudiant.setEtudUtiFk(util.getId());
        newEtudiant.setDateDeNaissance(util.getDateDeNaissance());
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(newEtudiant);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
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

    public List<Etudiant> findByNameAndPronoun(String nom, String prenom) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select e from Etudiant e   where e.nom like :nom and e.prenom like :prenom ";
            return session.createQuery(hql, Etudiant.class)
                    .setParameter("nom", "%" + nom + "%")
                    .setParameter("prenom", "%" + prenom + "%")
                    .getResultList();
        }
    }

    public String getAllStudents() {
        String jsonresponse = "{";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Etudiant> etudiants = session.createQuery("select e from Etudiant e", Etudiant.class).list();
            int i = 0;
            for (Etudiant etu : etudiants) {
                String json = "{";
                i++;

                System.out.println(etu.toString());
                json = parseSingleInput(etu.toString());
                jsonresponse += "\"" + "etu" + (i - 1) + "\":" + json;
                if (i < etudiants.size()) jsonresponse = jsonresponse + ",";
            }
            jsonresponse += "}";
        }
        return jsonresponse;

    }

    public List<Etudiant> getAllStudentsList() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select e from Etudiant e", Etudiant.class).list();
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
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
    public void deleteStudent(String email) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Utilisateur util = new UtilisateurDAO().findByEmail(email) ;
            if (util != null) {
                Etudiant etudiant = new EtudiantDAO().findByFkUtil(util.getId()) ;
                System.out.println(etudiant);
                if (etudiant != null) {
                    session.remove(etudiant);
                    session.remove(util);
                }
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Etudiant findByFkUtil(int Etud_uti_fk) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select e from Etudiant e where e.etudUtiFk = :Etud_uti_fk";
            return session.createQuery(hql, Etudiant.class)
                    .setParameter("Etud_uti_fk", Etud_uti_fk)
                    .getSingleResult();
        }
    }

}

