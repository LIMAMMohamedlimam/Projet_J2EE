package com.cytech.projet_jakarta.dao;

import com.cytech.projet_jakarta.model.UtilisateurEntity;
import com.cytech.projet_jakarta.utility.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.util.List;

public class UtilisateurDAO implements DAOInterface <UtilisateurEntity> {
    Session session = null ;
    Transaction tx = null ;
    @Override
    public int saveData(UtilisateurEntity data)  {
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
    public int updateData(UtilisateurEntity data) {
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
    public int removeData(UtilisateurEntity data) {
        return 0;
    }

    @Override
    public List<UtilisateurEntity> getAllData() {
        return null ;
    }

    public UtilisateurEntity findByEmailAndPassword(String email, String password) {
        UtilisateurEntity utilisateur = null;

        // Open Hibernate session
        try (Session session = HibernateUtil.getSession()) {
            // Create HQL query to find user by email and password
            String hql = "FROM UtilisateurEntity WHERE email = :email AND password = :password";
            Query<UtilisateurEntity> query = session.createQuery(hql, UtilisateurEntity.class);
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
