package com.revature.project02.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project02.models.UnencryptedAuthenticationToken;
import com.revature.project02.models.User;

/**
 * @author Wolfe Magnus <wsm@efoe.com>
 * @version 1.0
 * @since 1.0
 */
public class AuthTokenUtil {

	// 15 Minutes as milliseconds: 15*60*1000 = 15*6000
	// Use in appropriate delegate/controller code
	public static int MAX_TOKEN_LIFETIME_MILLI = 900000;
	
	private static ObjectMapper omapper = new ObjectMapper();
	
	/**
	 * fromEncryptedAuthenticationToken: Decrypts an encrypted Authentication Token and turns it to an UnencryptedAuthenticationToken. 
	 * @param eat - the encrypted authentication token to decrypt and parse
	 * @return UnencryptedAuthenticationToken - the decrypted token
	 */
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
	
	/**
	 * toEncryptedAuthenticationToken: Encrypts an UnencryptedAuthenticationToken.
	 * @param uat - the UnencryptedAuthenticationToken to encrypt
	 * @return String - the Base64 representation of the encrypted token
	 */
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
	
	/**
	 * authTokenTimedOut: Determines if an auth token has timed out.
	 * @param uat - the auth token to test
	 * @return boolean - true if timed out, false if not.
	 */
	public static boolean authTokenTimedOut(UnencryptedAuthenticationToken uat)
	{
		Long current = System.currentTimeMillis();
		if (current-MAX_TOKEN_LIFETIME_MILLI > uat.getTimestampLong())
			return true;
		return false;
	}

	/**
	 * baseAuthenticate: handles as much classless authentication comparison as possible. Has work around for Angular being the only client-side endpoint visible.
	 * @param uat - The UnencryptedAuthenticationToken to authenticate against
	 * @param user - The User to authenticate
	 * @param ip - The ip to do comparison on. Use the empty string ("") to avoid ip validation (as much as it pains me to do so)
	 * @return boolean - true if authentication is valid, false if invalid
	 */
	public static boolean baseAuthenticate(UnencryptedAuthenticationToken uat, User user, String ip)
	{
		if (user == null) return false;
		if (!uat.getUserId().equals(user.getId())) return false;
		// allowing way to bypass ip check, since APPARENTLY angular can't give me an IP the way I want
		if (!"".equals(ip) && !uat.getIp().equals(ip)) return false; 
		if (!uat.getRole().equalsIgnoreCase(user.getClass().toString())) return false;
		if (!uat.getUsername().equals(user.getUsername())) return false;
		if (authTokenTimedOut(uat)) return false;
		
		return true;
	}
	
	//Man, I hope nobody moves these classes.
	/**
	 * adminAuthenticate: Handles admin-specific authentication.
	 * @param uat - the UnencryptedAuthenticationToken to authenticate against
	 * @param user - the user to authenticate
	 * @param ip - the ip to compare. see @see AuthTokenUtil.baseAuthenticate for workaround for angular
	 * @return boolean - true if authentication is valid, false if invalid
	 */
	public static boolean adminAuthenticate(UnencryptedAuthenticationToken uat, User user, String ip)
	{
		if(!baseAuthenticate(uat,user,ip)) return false;
		if (!"class com.revature.project02.models.Admin".equals(user.getClass().toString())) return false;
		return true;
	}
	
	/**
	 * managerAuthenticate: Handles manager-specific authentication.
	 * @param uat - the UnencryptedAuthenticationToken to authenticate against
	 * @param user - the user to authenticate
	 * @param ip - the ip to compare. see @see AuthTokenUtil.baseAuthenticate for workaround for angular
	 * @return boolean - true if authentication is valid, false if invalid
	 */
	public static boolean managerAuthenticate(UnencryptedAuthenticationToken uat, User user, String ip)
	{
		if(!baseAuthenticate(uat,user,ip)) return false;
		if (!"class com.revature.project02.models.Manager".equals(user.getClass().toString())) return false;
		return true;
	}
	
	/**
	 * driverAuthenticate: Handles driver-specific authentication.
	 * @param uat - the UnencryptedAuthenticationToken to authenticate against
	 * @param user - the user to authenticate
	 * @param ip - the ip to compare. see @see AuthTokenUtil.baseAuthenticate for workaround for angular
	 * @return boolean - true if authentication is valid, false if invalid
	 */
	public static boolean driverAuthenticate(UnencryptedAuthenticationToken uat, User user, String ip)
	{
		if(!baseAuthenticate(uat,user,ip)) return false;
		if (!"class com.revature.project02.models.Driver".equals(user.getClass().toString())) return false;
		return true;
	}
	
}
