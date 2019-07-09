package com.revature.project02.controllers;
//imports

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project02.models.User;
import com.revature.project02.services.UserService;

/**
 * Description - Controller that handles requests from the client relating to driver
 * @author mattd
 * @version 07.06.2019
 */
@RestController
@RequestMapping("/profile-user")
public class UserController { //class header
	//class attributes
	@Autowired
	private UserService userService;
	
	/**
	 * Description - Provides JSON object of the user from the client request
	 * @param id - Integer representation of the user id (found in the uri)
	 * @return - JSON object representing the user
	 */
	@GetMapping(value="/{id}")
	public User getUserProfile(@PathVariable("id") Integer id) {
		return userService.getUserById(id);
	}
	
	/**
	 * Description - Receives new user information from the client to update a user
	 * @param id - Integer representation of the user id that is to be updated
	 * @param u - User object containing the updated information for the specified user
	 * @return - returns the newly updated user
	 */
	@PutMapping("/{id}")
	public User updateUser(@PathVariable("id") Integer id, @RequestBody User u) {
		u.setId(id);
		return userService.updateUser(u);
	}
	
	

} //end of class
