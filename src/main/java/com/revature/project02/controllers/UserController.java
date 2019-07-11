package com.revature.project02.controllers;
//imports

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project02.exceptions.BadRequestException;
import com.revature.project02.exceptions.UnauthorizedException;
import com.revature.project02.models.Driver;
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
	 * Description - Provides JSON object of the user from the client request, requires authority over user.
	 * @param id - Integer representation of the user id (found in the uri)
	 * @param token - String representing the encrypted authentication token
	 * @return - JSON object representing the user
	 * @throws UnauthorizedException - representing malformed token or id
	 */
	@GetMapping(value="/userid-{id}")
	public ResponseEntity<User> getUserProfile(@PathVariable("id") Integer id, @RequestHeader("token") String token) {
		//Unencrypt the token
		UnencryptedAuthenticationToken uat = AuthTokenUtil.fromEncryptedAuthenticationToken(token);
		if(uat == null) throw new UnauthorizedException("Unauthorized Access!");
		
		//Execute getting user profile after authentication
		User u = userService.getUserById(id, uat);
		
		//Multilevel auth processsing
		if (u==null) return new ResponseEntity(HttpStatus.BAD_REQUEST);
		if("class com.revature.project02.models.Driver".equals(uat.getRole()))
		{
			if(u.getId()==uat.getUserId()) return new ResponseEntity<User>(u,HttpStatus.OK);
			else throw new UnauthorizedException("Unauthorized Access.");
		}
		else if("class com.revature.project02.models.Manager".equals(uat.getRole()))
		{
			if(u.getId()==uat.getUserId()) return new ResponseEntity<User>(u,HttpStatus.OK);
			if("class com.revature.project02.models.Driver".equals(u.getClass().toString()))
			{
				Driver d = (Driver) u;
				if(uat.getUserId() == d.getManager().getId()) return new ResponseEntity<User>(u,HttpStatus.OK);
			}
			else throw new UnauthorizedException("Unauthorized Access.");
		}
		else if("class com.revature.project02.models.Admin".equals(uat.getRole()))
		{
			return new ResponseEntity<User>(u,HttpStatus.OK);
		}
		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Description - Provides JSON object of the user from the client request, requires authority over user.
	 * @param id - Integer representation of the user id (found in the uri)
	 * @param token - String representing the encrypted authentication token
	 * @return - JSON object representing the user
	 * @throws UnauthorizedException - representing malformed token or id
	 */
	@GetMapping(value="/username-{username}")
	public ResponseEntity<User> getUserProfile(@PathVariable("username") String username, @RequestHeader("token") String token) {
		//Unencrypt the token
		UnencryptedAuthenticationToken uat = AuthTokenUtil.fromEncryptedAuthenticationToken(token);
		if(uat == null) throw new UnauthorizedException("Unauthorized Access!");
		
		//Execute getting user profile after authentication
		User u = userService.getUserByName(username, uat);
		
		//Multilevel auth processsing
		if (u==null) return new ResponseEntity(HttpStatus.BAD_REQUEST);
		if("class com.revature.project02.models.Driver".equals(uat.getRole()))
		{
			if(u.getId()==uat.getUserId()) return new ResponseEntity<User>(u,HttpStatus.OK);
			else throw new UnauthorizedException("Unauthorized Access.");
		}
		else if("class com.revature.project02.models.Manager".equals(uat.getRole()))
		{
			if(u.getId()==uat.getUserId()) return new ResponseEntity<User>(u,HttpStatus.OK);
			if("class com.revature.project02.models.Driver".equals(u.getClass().toString()))
			{
				Driver d = (Driver) u;
				if(uat.getUserId() == d.getManager().getId()) return new ResponseEntity<User>(u,HttpStatus.OK);
			}
			else throw new UnauthorizedException("Unauthorized Access.");
		}
		else if("class com.revature.project02.models.Admin".equals(uat.getRole()))
		{
			return new ResponseEntity<User>(u,HttpStatus.OK);
		}
		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Description - Receives new user information from the client to update a user
	 * @param id - Integer representation of the user id that is to be updated
	 * @param u - User object containing the updated information for the specified user
	 * @return - returns Accepted status code if success, else a bad request
	 * @throws UnauthorizedException - inadequate authorization.
	 * @throws BadRequestException - malformed request
	 */
	@PutMapping
	public User updateUser(@RequestBody User u, @RequestHeader("token") String token) {
		//Unencrypt the token
		UnencryptedAuthenticationToken uat = AuthTokenUtil.fromEncryptedAuthenticationToken(token);
		if(uat == null) throw new UnauthorizedException("Unauthorized Access!");

		boolean occur = false;
		
		//Multilevel auth processsing
		if (u==null) throw new BadRequestException("No such user.");
		if("class com.revature.project02.models.Driver".equals(uat.getRole()))
		{
			if(u.getId()==uat.getUserId()) occur = true;
			else throw new UnauthorizedException("Unauthorized Access.");
		}
		else if("class com.revature.project02.models.Manager".equals(uat.getRole()))
		{
			if(u.getId()==uat.getUserId()) occur = true;
			else if("class com.revature.project02.models.Driver".equals(u.getClass().toString()))
			{
				Driver d = (Driver) u;
				if(uat.getUserId() == d.getManager().getId()) occur = true;
			}
			else throw new UnauthorizedException("Unauthorized Access.");
		}
		else if("class com.revature.project02.models.Admin".equals(uat.getRole()))
		{
			occur = true;
		}
		
		if(!occur) throw new UnauthorizedException("Unauthorized Access.");
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
		if(uat == null) throw new UnauthorizedException("Unauthorized Access!");
	
		User u = userService.getUserById(id, uat);
		boolean occur = false;
		
		//Multilevel auth processsing
		if (u==null) throw new BadRequestException("No such user.");
		if("class com.revature.project02.models.Driver".equals(uat.getRole()))
		{
			if(u.getId()==uat.getUserId()) occur = true;
			else throw new UnauthorizedException("Unauthorized Access.");
		}
		else if("class com.revature.project02.models.Manager".equals(uat.getRole()))
		{
			if(u.getId()==uat.getUserId()) occur = true;
			else if("class com.revature.project02.models.Driver".equals(u.getClass().toString()))
			{
				Driver d = (Driver) u;
				if(uat.getUserId() == d.getManager().getId()) occur = true;
			}
			else throw new UnauthorizedException("Unauthorized Access.");
		}
		else if("class com.revature.project02.models.Admin".equals(uat.getRole()))
		{
			occur = true;
		}
		
		if(!occur) throw new UnauthorizedException("Unauthorized Access.");
		userService.deleteUser(u, uat);
		return HttpStatus.OK;
	}
	
	

} //end of class
