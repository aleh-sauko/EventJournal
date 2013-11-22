package com.sauko.aleh.eventjournal.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sauko.aleh.eventjournal.domain.Comment;

@Repository
public class CommentDAOImpl implements CommentDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Comment getComment(Integer commentId) {
		return (Comment) sessionFactory.getCurrentSession().createQuery("from Comment where commentId=?")
				.setParameter(0, commentId).uniqueResult();
	}

	@Override
	public void removeComment(Integer commentId) {
		Comment comment = (Comment) sessionFactory.getCurrentSession().load(
				Comment.class, commentId);
		if (comment != null) {
			sessionFactory.getCurrentSession().delete(comment);
		}
	}

	@Override
	public void addComment(Comment comment) {
		if (comment != null) {
			sessionFactory.getCurrentSession().save(comment);
		}
	}
	
}
