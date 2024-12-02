package com.test.MyHyber.Entity.utility;

import com.test.MyHyber.Entity.Etudiant;
import com.test.MyHyber.Entity.Utilisateur;
import org.hibernate.HibernateException;
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

        private static SessionFactory sessionFactory;

        static {
            try {
                Configuration configuration = new Configuration().configure();

                configuration.addAnnotatedClass(Utilisateur.class);
                //configuration.addAnnotatedClass(AdminEntity.class);
                //configuration.addAnnotatedClass(EnseignantEntity.class);
                configuration.addAnnotatedClass(Etudiant.class);
                //configuration.addAnnotatedClass(InscriptionEntity.class);
                //configuration.addAnnotatedClass(MatiereEntity.class);
                //configuration.addAnnotatedClass(NotesEntity.class);
                //configuration.addAnnotatedClass(ResultatEntity.class);
                //configuration.addAnnotatedClass(CoursEntity.class);

                sessionFactory = configuration.buildSessionFactory();
            } catch (HibernateException ex) {
                System.err.println("Erreur lors de la création de la SessionFactory : " + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }

        /**
         * Retourne l'instance unique de SessionFactory.
         */
        public static SessionFactory getSessionFactory() {
            return sessionFactory;
        }

        /**
         * Ferme le SessionFactory pour libérer les ressources.
         */
        public static void shutdown() {
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }
    }

