package com.revature.project02.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

	public static final int HASH_PASS_EXACT_LEN = 64;
	
	public static String hashStr(String unhashedStr) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashedUTF8Pass = digest.digest(unhashedStr.getBytes(StandardCharsets.UTF_8));
			
			char[] hashedHexPass = new char[HASH_PASS_EXACT_LEN];
			for (int iter = 0; iter < hashedUTF8Pass.length; iter++)
			{
				char cur_low = (char) (hashedUTF8Pass[iter] & 0x0F);
				char cur_hi = (char) ((hashedUTF8Pass[iter] & 0xF0) >> 4);
				
				if (cur_hi >= 0x0A) cur_hi = (char) (cur_hi - 0x0A + 'A');
				else cur_hi += '0';
				if (cur_low >= 0x0A) cur_low = (char) (cur_low - 0x0A + 'A');
				else cur_low += '0';
				
				hashedHexPass[2*iter] = cur_hi;
				hashedHexPass[2*iter+1] = cur_low;
			}
			return new String(hashedHexPass).intern();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}