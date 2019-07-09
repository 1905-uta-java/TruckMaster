package com.revature.project02.util;

public class ValidationUtil {
	public static final int MAX_PASSWORD_LENGTH = 32;
	public static final int MIN_PASSWORD_LENGTH = 6;
	public static final int MIN_USERNAME_LENGTH = 6;
	public static final int MAX_USERNAME_LENGTH = 32;
	public static final int MIN_EMAIL_SEGMENT_LENGTH = 1;
	public static final int MAX_EMAIL_LENGTH = 32;
	
	private static final String EMAILCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVVWXYZ0123456789.";
	
	public static boolean validEmail(String email)
	{
		if (email.length() < MIN_EMAIL_SEGMENT_LENGTH*3 + 2 || email.length() > MAX_EMAIL_LENGTH) return false;
		boolean atInvoked = false;
		int lastSegmentation = 0;
		boolean sitePeriod = false;
		for (int iter = 0; iter < email.length(); iter++)
		{
			if(!atInvoked)
			{
				if(!EMAILCHARS.contains(((Character)email.charAt(iter)).toString()) && email.charAt(iter) != '@') 
				{
					return false;
				}
				if(iter - lastSegmentation-1 < MIN_EMAIL_SEGMENT_LENGTH && (email.charAt(iter) == '@' || email.charAt(iter) == '.')) return false; 
				if(email.charAt(iter) == '@') 
				{
					atInvoked = true;
					lastSegmentation = iter;
				}
				if(email.charAt(iter) == '.') lastSegmentation = iter;
			}
			else
			{
				if(!EMAILCHARS.contains(((Character)email.charAt(iter)).toString())) return false;
				if(iter - lastSegmentation-1 < MIN_EMAIL_SEGMENT_LENGTH && (email.charAt(iter) == '@' || email.charAt(iter) == '.')) return false; 
				if(email.charAt(iter) == '.') 
				{
					lastSegmentation = iter;
					sitePeriod = true;
				}
			}
		}
		if(atInvoked && sitePeriod && (email.length()-1 != lastSegmentation)) return true;
		return false;
	}
	
	public static boolean validPassword(String pw)
	{
		if (pw.length() < MIN_PASSWORD_LENGTH || pw.length() > MAX_PASSWORD_LENGTH) return false;
		char[] charsPW = pw.toLowerCase().toCharArray();
		
		for(int iter = 0; iter< charsPW.length; iter++)
		{
			if ((charsPW[iter] < 'a' || charsPW[iter] > 'z') && (charsPW[iter] < '0' || charsPW[iter] > '9') && 
					charsPW[iter] != '!' && charsPW[iter] != '.' && charsPW[iter] != '?' && charsPW[iter] != '_') return false;
		}
		return true;
	}

	public static boolean validUsername(String uid)
	{
		if (uid.length() < MIN_USERNAME_LENGTH || uid.length() > MAX_USERNAME_LENGTH) return false;
		
		char[] charsUID = uid.toLowerCase().toCharArray();
		
		for(int iter = 0; iter< charsUID.length; iter++)
		{
			if ((charsUID[iter] < 'a' || charsUID[iter] > 'z') && (charsUID[iter] < '0' || charsUID[iter] > '9') &&
				charsUID[iter] != '_') return false;
		}
		return true;
	}
}
