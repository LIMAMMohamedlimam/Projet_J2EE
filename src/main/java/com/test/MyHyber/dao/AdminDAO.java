package com.test.MyHyber.dao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

import com.test.MyHyber.Entity.Admin;
import com.test.MyHyber.Util.HibernateUtil;

public class AdminDAO {
	public void saveAdmin(Admin admin) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(admin);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			e.printStackTrace();
		}
	}

	public List<Admin> searchByName(String keyword) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			String hql = "from Admin a join fetch a.utilisateur u where u.nom like :keyword or u.prenom like :keyword";
			return session.createQuery(hql, Admin.class)
					.setParameter("keyword", "%" + keyword + "%")
					.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public List<Admin> getAllAdmins() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Admin", Admin.class).list();
		}
	}

	public Admin findAdminById(int id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.get(Admin.class, id);
		}
	}

	public void updateAdmin(Admin admin) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(admin);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			e.printStackTrace();
		}
	}

	public void deleteAdmin(int id) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Admin admin = session.get(Admin.class, id);
			if (admin != null) {
				session.remove(admin);
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			e.printStackTrace();
		}
	}
}