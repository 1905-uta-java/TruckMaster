package com.revature.project02.util;

import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

public class AuthCryptoUtil {

	private static boolean initialized = false;

	private static byte[] key;
	
	private static SecureRandom sr;
	
	private static char splitChar = ';';

	public static void init()
	{
		if (!initialized)
		{
			key = null;
			setSR();
			setKey();
			
			initialized = true;
		}
	}
	
	public static void setSR()
	{
		sr = new SecureRandom();
	}
	
	public static void setKey()
	{
		key = new byte[16];
		
		sr.nextBytes(key);
	}

	public static String spacePrepad(String unpadded, int blocklen)
	{
		String prepadString = "";
		for (int iter = 0; iter < blocklen; iter ++)
		{
			prepadString += ' ';
		}
		return prepadString + unpadded;
	}
	
	public static String noncePrepad(String unpadded, int blocklen)
	{
		String prepadString = "";
		byte[] prepadBytes = new byte[blocklen];
		sr.nextBytes(prepadBytes);
		for (int iter = 0; iter < prepadBytes.length; iter++)
		{
			if(prepadBytes[iter] < 0x20) prepadBytes[iter] = ' ';
		}
		
		prepadString = new String(prepadBytes);
		
		return prepadString + unpadded;
	}
	
	//PKCS7 Padding magic
	public static String appendPKCS7(String unpad, int blocklen) throws BadPaddingException
	{
		if(blocklen <= 0 || blocklen > 255) throw new javax.crypto.BadPaddingException();
		
		byte padlen = 0;
		if (unpad.length() % blocklen == 0)
		{
			padlen = (byte) blocklen;
		}
		else {
			padlen = (byte)(blocklen - (unpad.length() % blocklen));
		}
		
		String pad = "";
		for (int iter = 0; iter < padlen; iter++)
		{
			pad += (char)padlen;
		}
		
		return unpad + pad;
	}
	
	public static String removePKCS7(String padded, int blocklen) throws BadPaddingException
	{
		if(blocklen <= 0 || blocklen > 255 || padded.length() % blocklen != 0) throw new javax.crypto.BadPaddingException();

		byte padlen = (byte) padded.charAt(padded.length()-1);
		
		//validate padding
		for (int iter = padded.length()-1; iter > padded.length()-1-padlen; iter--)
		{
			if ((byte) padded.charAt(iter) != padlen) throw new javax.crypto.BadPaddingException();
		}
		
		return padded.substring(0,padded.length()-padlen);
	}
	
	public static String encrypt(String encryptString)
	{
		try
		{
			//Adding PKCS7 Padding
			encryptString = appendPKCS7(encryptString, 16);			

			SecretKey aeskey = new SecretKeySpec(key, "AES");
			SecretKey hmackey = new SecretKeySpec(key, "HMAC-SHA256");
			
			Mac mac = Mac.getInstance("HMACSHA256");
			mac.init(hmackey);
			byte[] hmac_digest = mac.doFinal(encryptString.getBytes());
			String hmac_phase = Base64.encodeBase64URLSafeString(hmac_digest);
			
			
			//Overpadding to avert the problems of unknown IV
			encryptString = noncePrepad(encryptString, 16);
			
			byte[] iv = new byte[16]; //get that nonce
			sr.nextBytes(iv);
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, aeskey, ivspec);
			String aes_phase =  Base64.encodeBase64URLSafeString(cipher.doFinal(encryptString.getBytes("UTF-8")));
			
			return aes_phase.trim() + ((Character) splitChar).toString() + hmac_phase;
		}
		catch(Exception e)
		{
			System.out.println("Error during encryption time: " + e.toString());
		}
		
		return null;
	}
	
	public static String decrypt(String decryptString)
	{
		try {
			String[] datasplit = decryptString.split(((Character)splitChar).toString());
			
			byte[] decryptData = Base64.decodeBase64(datasplit[0]);
			SecretKey aeskey = new SecretKeySpec(key, "AES");
			SecretKey hmackey = new SecretKeySpec(key, "HMAC-SHA256");
			
			Mac mac = Mac.getInstance("HMACSHA256");
			mac.init(hmackey);
			
			byte[] iv = new byte[16]; //get that nonce
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, aeskey, ivspec);
			byte[] all = cipher.doFinal(decryptData);
			
			byte[] toDigest = new byte[all.length -16];
			for(int iter = 16; iter < all.length; iter++)
			{
				toDigest[iter-16] = all[iter];
			}
			
			byte[] hmacDigestOrig = Base64.decodeBase64(datasplit[1]);
			byte[] hmac_digest = mac.doFinal(toDigest);
			
			
			if(!(new String(hmac_digest).equals(new String(hmacDigestOrig))))
			{
				System.out.println("HMAC NON MATCH");
				System.out.println(new String(hmacDigestOrig));
				System.out.println(new String(hmac_digest));
				return null;
			}
			
			return new String(toDigest).trim();
		}
		catch (Exception e)
		{
			System.out.println("Error during decryption time: " + e.toString());
		}
		return null;
	}
	
}
