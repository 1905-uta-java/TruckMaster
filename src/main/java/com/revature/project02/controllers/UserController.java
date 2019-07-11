package com.revature.project02.controllers;
//imports

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project02.exceptions.UnauthorizedException;
import com.revature.project02.models.Admin;
import com.revature.project02.models.Driver;
import com.revature.project02.models.Manager;
import com.revature.project02.models.UnencryptedAuthenticationToken;
import com.revature.project02.models.User;
import com.revature.project02.services.UserService;
import com.revature.project02.util.AuthTokenUtil;

/**
 * Description - Controller that handles requests from the client relating to driver
 * @author mattd
 * @version 07.06.2019
 */
@CrossOrigin(origins = {"*"})
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
	@GetMapping(value="/userid-{id}")
	public ResponseEntity<User> getUserProfile(@PathVariable("id") Integer id, @RequestHeader("token") String token) {
		//Unencrypt the token
		UnencryptedAuthenticationToken uat = AuthTokenUtil.fromEncryptedAuthenticationToken(token);
		if(uat == null && AuthTokenUtil.authTokenTimedOut(uat))
			throw new UnauthorizedException("Unauthorized Access!");
		
		//Execute getting user profile after authentication
		User u = userService.getUserById(id, uat);
		if(u == null)
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity(u, HttpStatus.OK);
	}
	
	@GetMapping(value="/username-{username}")
	public ResponseEntity<User> getUserProfile(@PathVariable("username") String username, @RequestHeader("token") String token) {
		//Unencrypt the token
		UnencryptedAuthenticationToken uat = AuthTokenUtil.fromEncryptedAuthenticationToken(token);
		if(uat == null && AuthTokenUtil.authTokenTimedOut(uat))
			throw new UnauthorizedException("Unauthorized Access!");
		
		//Execute getting user profile after authentication
		User u = userService.getUserByName(username, uat);
		if(u == null)
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity(u, HttpStatus.OK);
	}
	
	/**
	 * Description - Receives new user information from the client to update a user
	 * @param id - Integer representation of the user id that is to be updated
	 * @param u - User object containing the updated information for the specified user
	 * @return - returns Accepted status code if success, else a bad request
	 */
	@PutMapping(value="/userid-{id}")
	public User updateUser(@PathVariable("id") Integer id, @RequestBody User u, @RequestHeader("token") String token) {
		//Unencrypt the token
		UnencryptedAuthenticationToken uat = AuthTokenUtil.fromEncryptedAuthenticationToken(token);
		if(uat == null && AuthTokenUtil.authTokenTimedOut(uat))
			throw new UnauthorizedException("Unauthorized Access!");
		return userService.updateUser(u, uat);
		
	}
	
	/**
	 * Description - Deletes the requested user
	 * @param id - Integer representation of the user's id
	 * @return - An httpstatus of ok, else it will throw a BadRequestException
	 * @throws BadRequestException - if the user cannot be found
	 */
	@DeleteMapping(value= "/userid-{id}")
	public HttpStatus deleteUser(@PathVariable("id") Integer id, @RequestHeader("token") String token) {
		//Unencrypt the token
		UnencryptedAuthenticationToken uat = AuthTokenUtil.fromEncryptedAuthenticationToken(token);
		if(uat == null && AuthTokenUtil.authTokenTimedOut(uat))
			throw new UnauthorizedException("Unauthorized Access!");
		
		//Execute getting user profile after authentication
		userService.deleteUser(userService.getUserById(id, uat), uat);
		return HttpStatus.OK;
	}
	
	

} //end of class
