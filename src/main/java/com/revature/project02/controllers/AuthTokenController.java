package com.revature.project02.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project02.exceptions.InvalidAuthenticationException;
import com.revature.project02.models.UnencryptedAuthenticationToken;
import com.revature.project02.models.User;
import com.revature.project02.services.UserService;
import com.revature.project02.util.AuthTokenUtil;
import com.revature.project02.util.HashUtil;

@RestController
@RequestMapping("/authenticate")
public class AuthTokenController {

	@Autowired
	private UserService userService;
	
	@PostMapping
	public String getAuthenticationToken(@RequestParam(name="username") String username, @RequestParam(name="password") String password, HttpServletRequest request)
	{
		System.out.println("AUTH TOKEN GENERATE ATTEMPT: username:"+username+",password:"+password);
		
		
		User user = userService.getUserByName(username);
		if(user == null) throw new InvalidAuthenticationException("No such user.");
		
		String hashpass = HashUtil.hashStr(password);
		if(user.getPassHash() != hashpass) throw new InvalidAuthenticationException("Invalid password.");
		
		String ip = request.getRemoteAddr(); // NOT TO BE CHECKED, ANGULAR DOES NOT GIVE CLIENT END IP
		
		Long curtime = System.currentTimeMillis();
		
		UnencryptedAuthenticationToken uat = new UnencryptedAuthenticationToken(user.getId(), user.getUsername(), user.getClass().toString(), ip, curtime);
		System.out.println(uat);
		
		String eat = AuthTokenUtil.toEncryptedAuthenticationToken(uat);
		
		return eat;
	}
	
}
