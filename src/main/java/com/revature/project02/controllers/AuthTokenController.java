package com.revature.project02.controllers;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project02.exceptions.BadRequestException;
import com.revature.project02.exceptions.InvalidAuthenticationException;
import com.revature.project02.models.UnencryptedAuthenticationToken;
import com.revature.project02.models.User;
import com.revature.project02.services.UserService;
import com.revature.project02.util.AuthTokenUtil;
import com.revature.project02.util.HashUtil;
import com.revature.project02.util.ValidationUtil;

/**
 * @author Wolfe Magnus <wsm@efoe.com>
 * @version 1.0
 * @since 1.0
 */
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/authenticate")
public class AuthTokenController {

	public static class AuthWrapper implements Serializable{
		
		public static final long serialVersionUID = 1L;

		public String token;
		
		public User user;

		public AuthWrapper(String token, User user)
		{
			this.token = token;
			this.user = user;
		}
	}
	
	@Autowired
	private UserService userService;
	
	/**
	 * getAuthenticationToken: Manufactures an authentication token if user/pass combo is valid
	 * @param username - username from request
	 * @param password - password from request
	 * @param request - the request
	 * @return AuthWrapper - the wrapper containing a token and the user information that is more or less publicly available for that user
	 * @throws InvalidAuthenticationException if user/pass combo is invalid in any way
	 */
	@PostMapping
	public AuthWrapper getAuthenticationToken(@RequestParam(name="username") String username, @RequestParam(name="password") String password, HttpServletRequest request)
	{
		System.out.println("AUTH TOKEN GENERATE ATTEMPT: username:"+username+",password:"+password);
		if (!ValidationUtil.validUsername(username) || !ValidationUtil.validPassword(password)) throw new BadRequestException("Invalid input.");
		
		User user = userService.getUserByName(username);
		if(user == null) throw new InvalidAuthenticationException("Invalid username or username/password pair.");
		
		String hashpass = HashUtil.hashStr(password);
		if(user.getPassHash()==null || !user.getPassHash().equalsIgnoreCase(hashpass)) throw new InvalidAuthenticationException("Invalid username or username/password pair.");
		
		String ip = request.getRemoteAddr(); // NOT TO BE CHECKED, ANGULAR DOES NOT GIVE CLIENT END IP
		
		Long curtime = System.currentTimeMillis();
		
		UnencryptedAuthenticationToken uat = new UnencryptedAuthenticationToken(user.getId(), user.getUsername(), user.getClass().toString(), ip, curtime);
		System.out.println(uat);
		
		String eat = AuthTokenUtil.toEncryptedAuthenticationToken(uat);
		
		AuthWrapper aw = new AuthWrapper(eat, user);
		return aw;
	}
	
}
