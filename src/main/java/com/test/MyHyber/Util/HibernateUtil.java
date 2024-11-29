package com.test.MyHyber.Util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;

import com.test.MyHyber.Entity.*;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(Utilisateur.class);
            configuration.addAnnotatedClass(Admin.class);
            configuration.addAnnotatedClass(Enseignant.class);
            configuration.addAnnotatedClass(Etudiant.class);
            configuration.addAnnotatedClass(Inscription.class);
            configuration.addAnnotatedClass(Matiere.class);
            configuration.addAnnotatedClass(Notes.class);
            configuration.addAnnotatedClass(Resultat.class);
            configuration.addAnnotatedClass(Cours.class);

            // Build the SessionFactory
            sessionFactory = configuration.buildSessionFactory();
            System.out.println("SessionFactory initialized successfully.");

            // Debug: List all mapped entities
            System.out.println("Mapped entities:");
            sessionFactory.getMetamodel().getEntities().forEach(entity -> {
                System.out.println(" - " + entity.getName());
            });

        } catch (HibernateException ex) {
            System.err.println("Error during SessionFactory initialization: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Returns the unique instance of SessionFactory.
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Shuts down the SessionFactory to release resources.
     */
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
