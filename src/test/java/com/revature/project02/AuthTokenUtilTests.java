package com.revature.project02;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.revature.project02.models.Admin;
import com.revature.project02.models.Driver;
import com.revature.project02.models.Manager;
import com.revature.project02.models.UnencryptedAuthenticationToken;
import com.revature.project02.models.User;
import com.revature.project02.util.AuthTokenUtil;

public class AuthTokenUtilTests {

	public AuthTokenUtilTests()
	{
		super();
	}
	
	@Test
	public void fullTokenCircle()
	{
		UnencryptedAuthenticationToken uat = new UnencryptedAuthenticationToken(25, "username", "manager", "0:0:0:0:0:1", System.currentTimeMillis());
		System.out.println(uat);
		
		String eat = AuthTokenUtil.toEncryptedAuthenticationToken(uat);
		System.out.println(eat);
		
		UnencryptedAuthenticationToken uat_post = AuthTokenUtil.fromEncryptedAuthenticationToken(eat);
		System.out.println(uat_post);
		assertEquals(uat, uat_post);
	}
	
	@Test
	public void authTokenTimedOutOld()
	{
		UnencryptedAuthenticationToken uat = new UnencryptedAuthenticationToken(25, "username", "manager", "0:0:0:0:0:1", System.currentTimeMillis()-AuthTokenUtil.MAX_TOKEN_LIFETIME_MILLI-1);
		assertTrue(AuthTokenUtil.authTokenTimedOut(uat));
	}
	
	@Test
	public void authTokenTimedOutNew()
	{
		UnencryptedAuthenticationToken uat = new UnencryptedAuthenticationToken(25, "username", "manager", "0:0:0:0:0:1", System.currentTimeMillis());
		assertFalse(AuthTokenUtil.authTokenTimedOut(uat));
	}

	@Test
	public void baseAuthenticateBadId()
	{
		User user = new Manager();
		user.setId(25);
		user.setUsername("username");
		UnencryptedAuthenticationToken uat = new UnencryptedAuthenticationToken(24, user.getUsername(), user.getClass().toString(), "0:0:0:0:0:1", System.currentTimeMillis());
		assertFalse(AuthTokenUtil.baseAuthenticate(uat, user, ""));
	}
	
	@Test
	public void baseAuthenticateBadUsername()
	{
		User user = new Manager();
		user.setId(25);
		user.setUsername("username");
		UnencryptedAuthenticationToken uat = new UnencryptedAuthenticationToken(25, "usrnam", user.getClass().toString(), "0:0:0:0:0:1", System.currentTimeMillis());
		assertFalse(AuthTokenUtil.baseAuthenticate(uat, user, ""));
	}
	
	@Test
	public void baseAuthenticateBadRole()
	{
		User user = new Manager();
		user.setId(25);
		user.setUsername("username");
		UnencryptedAuthenticationToken uat = new UnencryptedAuthenticationToken(25, user.getUsername(), "", "0:0:0:0:0:1", System.currentTimeMillis());
		assertFalse(AuthTokenUtil.baseAuthenticate(uat, user, ""));
	}
	
	@Test
	public void baseAuthenticateBadIP()
	{
		User user = new Manager();
		user.setId(25);
		user.setUsername("username");
		UnencryptedAuthenticationToken uat = new UnencryptedAuthenticationToken(25, user.getUsername(), user.getClass().toString(), "0:0:0:0:0:1", System.currentTimeMillis());
		assertFalse(AuthTokenUtil.baseAuthenticate(uat, user, "1:1:1:1:1:4"));
	}
	
	@Test
	public void baseAuthenticateTimeout()
	{
		User user = new Manager();
		user.setId(25);
		user.setUsername("username");
		UnencryptedAuthenticationToken uat = new UnencryptedAuthenticationToken(25, user.getUsername(), user.getClass().toString(), "0:0:0:0:0:1", System.currentTimeMillis()-AuthTokenUtil.MAX_TOKEN_LIFETIME_MILLI-1);
		assertFalse(AuthTokenUtil.baseAuthenticate(uat, user, ""));
	}
	
	@Test
	public void baseAuthenticateValid()
	{
		User user = new Manager();
		user.setId(25);
		user.setUsername("username");
		UnencryptedAuthenticationToken uat = new UnencryptedAuthenticationToken(25, user.getUsername(), user.getClass().toString(), "0:0:0:0:0:1", System.currentTimeMillis());
		assertTrue(AuthTokenUtil.baseAuthenticate(uat, user, ""));
	}
	
	@Test
	public void adminAuthenticateInvalidToken()
	{
		User user = new Admin();
		user.setId(25);
		user.setUsername("username");
		UnencryptedAuthenticationToken uat = new UnencryptedAuthenticationToken(1, user.getUsername(), user.getClass().toString(), "0:0:0:0:0:1", System.currentTimeMillis());
		assertFalse(AuthTokenUtil.adminAuthenticate(uat, user, ""));
	}
	
	@Test
	public void adminAuthenticateNotAdmin()
	{
		User user = new Manager();
		user.setId(25);
		user.setUsername("username");
		UnencryptedAuthenticationToken uat = new UnencryptedAuthenticationToken(1, user.getUsername(), user.getClass().toString(), "0:0:0:0:0:1", System.currentTimeMillis());
		assertFalse(AuthTokenUtil.adminAuthenticate(uat, user, ""));
	}
	
	@Test
	public void adminAuthenticateValid()
	{
		User user = new Admin();
		user.setId(25);
		user.setUsername("username");
		UnencryptedAuthenticationToken uat = new UnencryptedAuthenticationToken(25, user.getUsername(), user.getClass().toString(), "0:0:0:0:0:1", System.currentTimeMillis());
		assertTrue(AuthTokenUtil.adminAuthenticate(uat, user, ""));		
	}
	
	@Test
	public void managerAuthenticateInvalidToken()
	{
		User user = new Manager();
		user.setId(25);
		user.setUsername("username");
		UnencryptedAuthenticationToken uat = new UnencryptedAuthenticationToken(1, user.getUsername(), user.getClass().toString(), "0:0:0:0:0:1", System.currentTimeMillis());
		assertFalse(AuthTokenUtil.managerAuthenticate(uat, user, ""));
	}
	
	@Test
	public void managerAuthenticateNotManager()
	{
		User user = new Admin();
		user.setId(25);
		user.setUsername("username");
		UnencryptedAuthenticationToken uat = new UnencryptedAuthenticationToken(25, user.getUsername(), user.getClass().toString(), "0:0:0:0:0:1", System.currentTimeMillis());
		assertFalse(AuthTokenUtil.managerAuthenticate(uat, user, ""));
	}
	
	@Test
	public void managerAuthenticateValid()
	{
		User user = new Manager();
		user.setId(25);
		user.setUsername("username");
		UnencryptedAuthenticationToken uat = new UnencryptedAuthenticationToken(25, user.getUsername(), user.getClass().toString(), "0:0:0:0:0:1", System.currentTimeMillis());
		assertTrue(AuthTokenUtil.managerAuthenticate(uat, user, ""));		
	}
	
	@Test
	public void driverAuthenticateInvalidToken()
	{
		User user = new Driver();
		user.setId(25);
		user.setUsername("username");
		UnencryptedAuthenticationToken uat = new UnencryptedAuthenticationToken(1, user.getUsername(), user.getClass().toString(), "0:0:0:0:0:1", System.currentTimeMillis());
		assertFalse(AuthTokenUtil.driverAuthenticate(uat, user, ""));
	}
	
	@Test
	public void driverAuthenticateNotManager()
	{
		User user = new Admin();
		user.setId(25);
		user.setUsername("username");
		UnencryptedAuthenticationToken uat = new UnencryptedAuthenticationToken(25, user.getUsername(), user.getClass().toString(), "0:0:0:0:0:1", System.currentTimeMillis());
		assertFalse(AuthTokenUtil.driverAuthenticate(uat, user, ""));
	}
	
	@Test
	public void driverAuthenticateValid()
	{
		User user = new Driver();
		user.setId(25);
		user.setUsername("username");
		UnencryptedAuthenticationToken uat = new UnencryptedAuthenticationToken(25, user.getUsername(), user.getClass().toString(), "0:0:0:0:0:1", System.currentTimeMillis());
		assertTrue(AuthTokenUtil.driverAuthenticate(uat, user, ""));		
	}
}
