package com.sauko.aleh.eventjournal.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sauko.aleh.eventjournal.dao.CommentDAO;
import com.sauko.aleh.eventjournal.domain.Comment;
import com.sauko.aleh.eventjournal.domain.Event;
import com.sauko.aleh.eventjournal.domain.User;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDAO commentDAO;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EventService eventService;
	
	public Comment filledComment(Event event, String data) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user =  userService.getUser(userName);
		Comment comment = new Comment();
		comment.setEvent(event);
		String content = null;
		try {
			content = new String(data.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Bad cast encoding.");
		}
		comment.setContent(content);
		comment.setAuthor(user.getFirstname());
		comment.setDate(new Date());
		return comment;
	}
	
	@Override
	@Transactional
	public void addComment(Integer eventId, String content) {
		Event event = eventService.getEvent(eventId);
		Comment comment = filledComment(event, content);
		commentDAO.addComment(comment);
		event.getComments().add(comment);
		eventService.updateEvent(event);
	}

	
	@Override
	@Transactional
	public Comment getComment(Integer id) {
		return commentDAO.getComment(id);
	}

	@Override
	@Transactional
	public void removeComment(Integer id) {
		commentDAO.removeComment(id);
	}

	
}
