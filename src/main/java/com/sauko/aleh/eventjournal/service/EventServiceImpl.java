package com.sauko.aleh.eventjournal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sauko.aleh.eventjournal.dao.EventDAO;
import com.sauko.aleh.eventjournal.domain.Comment;
import com.sauko.aleh.eventjournal.domain.Event;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	private EventDAO eventDAO;
	
	@Override
	@Transactional
	public void addEvent(Event event) {
		eventDAO.addEvent(event);
	}

	@Override
	@Transactional
	public List<Event> listEvent() {
		return eventDAO.listEvent();
	}

	@Override
	@Transactional
	public void removeEvent(Integer id) {
		eventDAO.removeEvent(id);
	}

	@Override
	@Transactional
	public List<Event> listMyEvent() {
		return eventDAO.listMyEvent();
	}

	@Override
	@Transactional
	public List<Event> listIncomeEvent() {
		return eventDAO.listIncomeEvent();
	}

	@Override
	@Transactional
	public List<Event> listPastEvent() {
		return eventDAO.listPastEvent();
	}

	@Override
	@Transactional
	public List<Comment> listComment() {
		return eventDAO.listComment();
	}
	
	@Override
	@Transactional
	public Event getEvent(Integer eventId) {
		return eventDAO.getEvent(eventId);
	}

	@Override
	@Transactional
	public void updateEvent(Event event) {
		eventDAO.updateEvent(event);
	}
	
	@Override
	@Transactional
	public void closeEvent(Event event) {
		event.setStatus(false);
		eventDAO.updateEvent(event);
	}

	@Override
	@Transactional
	public List<Event> listFixEvent(Integer count) {
		return eventDAO.listFixEvent(count);
	}

	@Override
	@Transactional
	public List<Event> listMySubscriptionsEvent() {
		return eventDAO.listMySubscriptionsEvent();
	}
	
	@Override
	@Transactional
	public List<Event> listEventInCategory(String category) {
		return eventDAO.listEventInCategory(category);
	}
	
	@Override
	@Transactional
	public List<Event> search(String content) {
		return eventDAO.search(content);
	}
	
	@Override
	@Transactional
	public List<Event> searchTag(String content) {
		return eventDAO.searchTag(content);
	}
	
	@Override
	@Transactional
	public void reindex() {
		eventDAO.reindex();
	}
}
