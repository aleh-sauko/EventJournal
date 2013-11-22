package com.sauko.aleh.eventjournal.dao;

import java.util.List;

import com.sauko.aleh.eventjournal.domain.User;

public interface UserDAO {
	
	public void addUser(User user);
	public User getUser(String name);	
	public User getUser(Integer id);
	
	public List<User> listUser();
	public List<User> listTopUser(Integer count);
	
	public void setAdmin(Integer id);
	public void setNewPass(Integer id);
	public void removeUser(Integer id);
	public void updateUser(User user);
}
