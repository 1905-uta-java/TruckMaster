package com.revature.project02.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project02.models.UnencryptedAuthenticationToken;
import com.revature.project02.models.User;

public class AuthTokenUtil {

	// 15 Minutes as milliseconds: 15*60*1000 = 15*6000
	// Use in appropriate delegate/controller code
	public static int MAX_TOKEN_LIFETIME_MILLI = 900000;
	
	private static ObjectMapper omapper = new ObjectMapper();
	
	public static UnencryptedAuthenticationToken fromEncryptedAuthenticationToken(String eat)
	{
		AuthCryptoUtil.init();
		UnencryptedAuthenticationToken ret = null;
		String unparsedUAT = AuthCryptoUtil.decrypt(eat);
		if (unparsedUAT == null) return null;
		try {
			ret = omapper.readValue(unparsedUAT, UnencryptedAuthenticationToken.class);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return ret;
	}
	
	public static String toEncryptedAuthenticationToken(UnencryptedAuthenticationToken uat)
	{
		AuthCryptoUtil.init();
		String uatAsString = null;
		try {
			uatAsString = omapper.writeValueAsString(uat);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
		
		String eatAsString = AuthCryptoUtil.encrypt(uatAsString);
		
		return eatAsString;
	}
	
	public static boolean authTokenTimedOut(UnencryptedAuthenticationToken uat)
	{
		Long current = System.currentTimeMillis();
		if (current-MAX_TOKEN_LIFETIME_MILLI > uat.getTimestampLong())
			return true;
		return false;
	}

	public static boolean baseAuthenticate(UnencryptedAuthenticationToken uat, User user, String ip)
	{
		if (!uat.getUserId().equals(user.getId())) return false;
		// allowing way to bypass ip check, since APPARENTLY angular can't give me an IP the way I want
		if (!"".equals(ip) && !uat.getIp().equals(ip)) return false; 
		if (!uat.getRole().equalsIgnoreCase(user.getClass().toString())) return false;
		if (!uat.getUsername().equals(user.getUsername())) return false;
		if (authTokenTimedOut(uat)) return false;
		
		return true;
	}
	
	//Man, I hope nobody moves these classes.
	public static boolean adminAuthenticate(UnencryptedAuthenticationToken uat, User user, String ip)
	{
		if(!baseAuthenticate(uat,user,ip)) return false;
		if (!"class com.revature.project02.models.Admin".equals(user.getClass().toString())) return false;
		return true;
	}
	
	public static boolean managerAuthenticate(UnencryptedAuthenticationToken uat, User user, String ip)
	{
		if(!baseAuthenticate(uat,user,ip)) return false;
		if (!"class com.revature.project02.models.Manager".equals(user.getClass().toString())) return false;
		return true;
	}
	
	public static boolean driverAuthenticate(UnencryptedAuthenticationToken uat, User user, String ip)
	{
		if(!baseAuthenticate(uat,user,ip)) return false;
		if (!"class com.revature.project02.models.Driver".equals(user.getClass().toString())) return false;
		return true;
	}
	
}
