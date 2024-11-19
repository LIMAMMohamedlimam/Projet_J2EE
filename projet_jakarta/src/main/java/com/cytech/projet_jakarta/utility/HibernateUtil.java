package com.cytech.projet_jakarta.utility;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    public static Session getSession()  {
        Session session = null;
        SessionFactory sessionFactory = null;
        sessionFactory = new Configuration().configure("hibernate.cfg.xml")
                .buildSessionFactory();
        session = sessionFactory.openSession();
        return session;
    }
}
