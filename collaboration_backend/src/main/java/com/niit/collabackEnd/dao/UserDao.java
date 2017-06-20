package com.niit.collabackEnd.dao;

import java.util.List;

import com.niit.collabackEnd.model.User;

public interface UserDao {
public List<User> list();
	
	public User getuser(String userid);
    public boolean save(User userdetails);
	
	public boolean update(User userdetails);
		
		public boolean delete(String userid);
		
		public void setOnLine(String userid);
		public void setOffLine(String userid);
	
	
	public User authenticate(String username,String password);
}
