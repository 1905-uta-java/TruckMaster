package com.revature.project02.services;
//imports

import java.util.List;

import com.revature.project02.models.User;

/**
 * Description - Interface for driver to implement basic CRUD methods relating to the driver class and db table
 * @author mattd
 * @version - 07.06.2019
 */
public interface UserService {
	
	public List<User> getAllUsers();
	public User getUserByName(String username);
	public User getUserById(Integer id);
	public User addUser(User u);
	public User updateUser(User u);
	public void deleteUser(User u);

}
