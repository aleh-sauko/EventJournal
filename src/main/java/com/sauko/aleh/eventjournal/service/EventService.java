package com.sauko.aleh.eventjournal.service;

import java.util.List;

import com.sauko.aleh.eventjournal.domain.Comment;
import com.sauko.aleh.eventjournal.domain.Event;

public interface EventService {
	
	public void addEvent(Event event);
	public Event getEvent(Integer eventId);
	
	public List<Event> listEvent();
	public List<Event> listMyEvent();
	public List<Event> listIncomeEvent();
	public List<Event> listPastEvent();
	public List<Event> listFixEvent(Integer count);
	public List<Event> listMySubscriptionsEvent();
	public List<Event> listEventInCategory(String category);
	
	public List<Comment> listComment();
	
	public void updateEvent(Event event);
	public void removeEvent(Integer id);
	public void closeEvent(Event event);
	
	public List<Event> search(String content);
	public List<Event> searchTag(String content);
	public void reindex();
}
