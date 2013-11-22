package com.sauko.aleh.eventjournal.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sauko.aleh.eventjournal.domain.Tag;

@Repository
public class TagDAOImpl implements TagDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Tag getTag(String name) {
		return (Tag) sessionFactory.getCurrentSession().createQuery("from Tag where name=?")
				.setParameter(0, name).uniqueResult();
	}
	
	@Override
	public void addTag(Tag tag) {
		if (getTag(tag.getName()) == null)
			sessionFactory.getCurrentSession().save(tag);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tag> listTags() {
		return sessionFactory.getCurrentSession().createQuery("from Tag")
				.list();
	}

}
