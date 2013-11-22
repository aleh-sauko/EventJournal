package com.sauko.aleh.eventjournal.dao;

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sauko.aleh.eventjournal.domain.User;
import com.sauko.aleh.eventjournal.util.Sender;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addUser(User user) {
    	new Sender().send(user.getEmail(), "Successful registration on Event Journal.\n"
    			+ "Your login    : " + user.getFirstname() + "\n"
    			//+ "Your password : " + user.getPassword()
    			);
		sessionFactory.getCurrentSession().save(user);
	}

	@Override
	public User getUser(String name) {
		return (User) sessionFactory.getCurrentSession().createQuery("from User where firstname=?")
				.setParameter(0, name).uniqueResult();
	}

	@Override
	public User getUser(Integer id) {
		return (User) sessionFactory.getCurrentSession().createQuery("from User where userId=?")
				.setParameter(0, id).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> listUser() {
		return sessionFactory.getCurrentSession().createQuery("from User")
				.list();
	}

	@Override
	public void setAdmin(Integer id) {
		User user = (User) sessionFactory.getCurrentSession().load(
				User.class, id);
		if (null != user) {
			user.setRole("ROLE_ADMIN");
		}
	}

	@Override
	public void setNewPass(Integer id) {
		User user = (User) sessionFactory.getCurrentSession().load(
				User.class, id);
		if (null != user) {
			String pass = RandomStringUtils.randomAlphanumeric(10);
			user.setPassword(pass); 
			new Sender().send(user.getEmail(), "New password : " + pass);
		}
	}

	@Override
	public void removeUser(Integer id) {
		User user = (User) sessionFactory.getCurrentSession().load(
				User.class, id);
		if (null != user) {
			sessionFactory.getCurrentSession().delete(user);
		}
	}

	@Override
	public void updateUser(User user) {
		if (null != user && user.getUserId() != null) {
			sessionFactory.getCurrentSession().update(user);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> listTopUser(Integer count) {
		String hql = "FROM User u ORDER BY u.rate DESC";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setFirstResult(0);
		query.setMaxResults(count);
		return (List<User>) query.list();
	}
}
