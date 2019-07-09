package com.revature.project02;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.revature.project02.models.UnencryptedAuthenticationToken;
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
		UnencryptedAuthenticationToken uat = new UnencryptedAuthenticationToken(25, "username", "manager", "0:0:0:0:0:1", System.currentTimeMillis()-AuthTokenUtil.MAX_TOKEN_LIFETIME_MILLI);
		assertTrue(AuthTokenUtil.authTokenTimedOut(uat));
	}
	
	@Test
	public void authTokenTimedOutNew()
	{
		UnencryptedAuthenticationToken uat = new UnencryptedAuthenticationToken(25, "username", "manager", "0:0:0:0:0:1", System.currentTimeMillis());
		assertFalse(AuthTokenUtil.authTokenTimedOut(uat));
	}

}
