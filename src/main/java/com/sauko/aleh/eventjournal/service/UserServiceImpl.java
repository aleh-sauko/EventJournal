package com.sauko.aleh.eventjournal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sauko.aleh.eventjournal.dao.EventDAO;
import com.sauko.aleh.eventjournal.dao.UserDAO;
import com.sauko.aleh.eventjournal.domain.Event;
import com.sauko.aleh.eventjournal.domain.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private EventDAO eventDAO;
	
	@Override
	@Transactional
	public void addUser(User user) {
		userDAO.addUser(user);
	}

	@Override
	@Transactional
	public List<User> listUser() {
		return userDAO.listUser();
	}

	@Override
	@Transactional
	public void setAdmin(Integer id) {
		userDAO.setAdmin(id);
	}

	@Override
	@Transactional
	public void setNewPass(Integer id) {
		userDAO.setNewPass(id);
	}

	@Override
	@Transactional
	public void removeUser(Integer id) {
		userDAO.removeUser(id);
	}

	@Override
	@Transactional
	public void updateUser(User user) {
		userDAO.updateUser(user);
	}
	
	@Override
	@Transactional
	public void updateUser(User user, User data) {
		user.setDescription(data.getDescription());
		user.setLanguage(data.getLanguage());
		user.setLastname(data.getLastname());
		user.setEmail(data.getEmail());
		user.setCategories(data.getCategories());
		userDAO.updateUser(user);
	}
	
	@Override
	@Transactional
	public User getUser(String name) {
		return userDAO.getUser(name);
	}

	@Override
	@Transactional
	public User getUser(Integer id) {
		return userDAO.getUser(id);
	}

	@Override
	@Transactional
	public void addEvent(Integer eventId) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user =  getUser(userName);
		Event event = eventDAO.getEvent(eventId);
		user.setRate(user.getRate() + 1);
		user.addEvent(event);
		event.addUser(user);
		eventDAO.updateEvent(event);
		userDAO.updateUser(user);
	}
	
	@Override
	@Transactional
	public void removeEvent(Integer eventId) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user =  getUser(userName);
		Event event = eventDAO.getEvent(eventId);
		user.setRate(user.getRate() - 1);
		user.removeEvent(event);
		event.removeUser(user);
		eventDAO.updateEvent(event);
		userDAO.updateUser(user);
	}

	@Override
	@Transactional
	public boolean inEvent(Integer eventId) {
		boolean result = false;
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		if (userName != null && !userName.equals("guest")) {
			result = getUser(userName).inEvent(eventId);
		}
		return result;
	}

	@Override
	@Transactional
	public List<User> listTopUser(Integer count) {
		return userDAO.listTopUser(count);
	}
}
