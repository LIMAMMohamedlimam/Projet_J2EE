package com.cytech.projet_jakarta.dao;

import com.cytech.projet_jakarta.model.Utilisateur;
import com.cytech.projet_jakarta.utility.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.util.List;

public class UtilisateurDAO implements DAOInterface <Utilisateur> {
    Session session = null ;
    Transaction tx = null ;
    @Override
    public int saveData(Utilisateur data)  {
        session = HibernateUtil.getSession();
        try {
            tx = session.beginTransaction();
            session.persist(data);
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
            return -1 ;
        }finally {
            session.close();
        }
        return 0;
    }

    @Override
    public int updateData(Utilisateur data) {
        session = HibernateUtil.getSession();
        try {
            tx = session.beginTransaction();
            session.merge(data);
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
            return -1 ;
        }finally {
            session.close();
        }
        return 0;
    }

    @Override
    public int removeData(Utilisateur data) {
        return 0;
    }

    @Override
    public List<Utilisateur> getAllData() {
        return null ;
    }

    public Utilisateur findByEmailAndPassword(String email, String password) {
        Utilisateur utilisateur = null;

        // Open Hibernate session
        try (Session session = HibernateUtil.getSession()) {
            // Create HQL query to find user by email and password
            String hql = "FROM Utilisateur WHERE email = :email AND password = :password";
            Query<Utilisateur> query = session.createQuery(hql, Utilisateur.class);
            query.setParameter("email", email);
            query.setParameter("password", password);

            // Get single result
            utilisateur = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return utilisateur; // Returns null if no user is found
    }


}
