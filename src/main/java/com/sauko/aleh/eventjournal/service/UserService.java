package com.sauko.aleh.eventjournal.service;

import java.util.List;

import com.sauko.aleh.eventjournal.domain.User;

public interface UserService {

	public List<User> listUser();
	public List<User> listTopUser(Integer count);
	
	public void addEvent(Integer eventId);
	public void removeEvent(Integer eventId);
	
	public void addUser(User user);
	public void setAdmin(Integer id);
	public void setNewPass(Integer id);
	public void removeUser(Integer id);
	
	public void updateUser(User user);
	public void updateUser(User user, User data);
	
	public User getUser(String name);
	public User getUser(Integer id);
	
	public boolean inEvent(Integer eventId);
}
