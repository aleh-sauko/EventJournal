package com.sauko.aleh.eventjournal.dao;

import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.sauko.aleh.eventjournal.domain.Comment;
import com.sauko.aleh.eventjournal.domain.Event;
import com.sauko.aleh.eventjournal.domain.User;

@Repository
public class EventDAOImpl implements EventDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public void addEvent(Event event) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		User user = userDAO.getUser(name);
		user.setRate(user.getRate() + 1);
		event.addUser(user);
		user.addEvent(event);
		sessionFactory.getCurrentSession().save(event);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Event> listEvent() {
		return (List<Event>) sessionFactory.getCurrentSession().createQuery("from Event")
				.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Event> listMyEvent() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		return (List<Event>) sessionFactory.getCurrentSession().createQuery("select e from Event e join e.users u where u.firstname = :firstname ")
				.setString("firstname", name).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Event> listIncomeEvent() {
		return (List<Event>) sessionFactory.getCurrentSession().createQuery("from Event where status = ?")
				.setBoolean(0, true).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Event> listPastEvent() {
		return (List<Event>) sessionFactory.getCurrentSession().createQuery("from Event where status = ?")
				.setBoolean(0, false).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Event> listMySubscriptionsEvent() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		List<String> categories = userDAO.getUser(name).getCategories();
		return (List<Event>) sessionFactory.getCurrentSession().
				createQuery("select e from Event e join e.categories c where c in (:categories) ")
				.setParameterList("categories", categories).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Event> listEventInCategory(String category) {
		return (List<Event>) sessionFactory.getCurrentSession().
				createQuery("select e from Event e join e.categories c where c = :category ")
				.setString("category", category).list();
	}
	
	@Override
	public void removeEvent(Integer id) {
		Event event = (Event) sessionFactory.getCurrentSession().load(
				Event.class, id);
		if (null != event) {
			sessionFactory.getCurrentSession().delete(event);
		}
	}

	@Override
	public Event getEvent(Integer eventId) {
		Event event = (Event) sessionFactory.getCurrentSession().createQuery("from Event where eventId=?")
				.setParameter(0, eventId).uniqueResult();
		return event;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> listComment() {
		return (List<Comment>) sessionFactory.getCurrentSession().createQuery("select comments from Event")
				.list();
	}

	@Override
	public void updateEvent(Event event) {
		if (null != event && event.getEventId() != null) {
			sessionFactory.getCurrentSession().update(event);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Event> listFixEvent(Integer count) {
		String hql = "FROM Event e where e.status=1 ORDER BY e.eventId DESC";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setFirstResult(0);
		query.setMaxResults(count);
		return (List<Event>) query.list();
	}

	@Override
	public void reindex() {
		FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.getCurrentSession());
		try {
			fullTextSession.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			System.out.println("Bad reindex.");
			e.printStackTrace();
		} 
	}
	
	@Override
	public List<Event> search(String content) {
		FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.getCurrentSession());

		// create native Lucene query
		String[] fields = new String[]{"title", "place", "date", "description", "tags", "categories", 
				"comments.content", "users.firstname"};
		@SuppressWarnings("deprecation")
		MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, new StandardAnalyzer());
		org.apache.lucene.search.Query query = null;
		try {
			query = parser.parse(content);
		} catch (ParseException e) {
			System.out.println("Bad parse.");
			e.printStackTrace();
		}

		// wrap Lucene query in a org.hibernate.Query
		org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(query, Event.class);

		// execute search
		@SuppressWarnings("unchecked")
		List<Event> result = (List<Event>) hibQuery.list();
		
		return result;
	}
	
	
	
	@Override
	public List<Event> searchTag(String content) {
		FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.getCurrentSession());

		// create native Lucene query
		String[] fields = new String[]{"tags"};
		@SuppressWarnings("deprecation")
		MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, new StandardAnalyzer());
		org.apache.lucene.search.Query query = null;
		try {
			query = parser.parse(content);
		} catch (ParseException e) {
			System.out.println("Bad parse.");
			e.printStackTrace();
		}

		// wrap Lucene query in a org.hibernate.Query
		org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(query, Event.class);

		// execute search
		@SuppressWarnings("unchecked")
		List<Event> result = (List<Event>) hibQuery.list();
		
		return result;
	}
}
