package com.revature.project02.repositories;
//imports
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.project02.models.User;

/**
 * Description - interface for User db table
 * @author mattd
 * @version - 07.06.2019
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	/**
	 * Description - Returns a list of Users by their ID field
	 * @param id - Integer representation of user id
	 * @return - a list of users
	 * @throws - nothing.
	 */
	public List<User> getAllUserByid(Integer id);
	
	/**
	 * Description - Returns a list of Users by their Username field
	 * @param username - String representation of the User's username
	 * @return - a list of users
	 * @throws - nothing.
	 */
	public List<User> getAllUserByUsername(String username);
	
	public User getUserByUsername(String username);
	
}
