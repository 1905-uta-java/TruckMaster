package com.revature.project02.services;
//imports

import java.util.List;

import com.revature.project02.models.UnencryptedAuthenticationToken;
import com.revature.project02.models.User;

/**
 * Description - Interface for driver to implement basic CRUD methods relating to the driver class and db table
 * @author mattd
 * @version - 07.06.2019
 */
public interface UserService {
	
	public List<User> getAllUsers(UnencryptedAuthenticationToken uat);
	public User getUserByName(String username, UnencryptedAuthenticationToken uat);
	public User getUserByName(String username);
	public User getUserById(Integer id, UnencryptedAuthenticationToken uat);
	public User addUser(User u, UnencryptedAuthenticationToken uat);
	public User updateUser(User u, UnencryptedAuthenticationToken uat);
	public void deleteUser(User u, UnencryptedAuthenticationToken uat);

}
