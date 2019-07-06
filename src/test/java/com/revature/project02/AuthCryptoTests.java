package com.revature.project02;

import javax.crypto.BadPaddingException;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


import com.revature.project02.util.AuthCryptoUtil;

//Tests non-rng AuthCryptoUtil stuff
public class AuthCryptoTests {

	public AuthCryptoTests() {
		super();
	}
	
	@Test(expected=javax.crypto.BadPaddingException.class)
	public void appendPKCS7InvalidPadlen() throws BadPaddingException
	{
		String shortUnpad = "unpad";
		AuthCryptoUtil.appendPKCS7(shortUnpad, 0);
	}
	
	@Test
	public void appendPKCS7TestNoLen()
	{
		String nolenUnpad = "wordwordwordword"; // 16 chars
		try {
			assertTrue(AuthCryptoUtil.appendPKCS7(nolenUnpad, 16).length() == 32);
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void appendPKCS7TestNormal()
	{
		String nolenUnpad = "wordwordwordwordword"; // 20 chars
		try {
			assertTrue(AuthCryptoUtil.appendPKCS7(nolenUnpad, 16).length() == 32);
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected=javax.crypto.BadPaddingException.class)
	public void removePKCS7InvalidPadlen() throws BadPaddingException
	{
		String shortUnpad = "unpad";
		AuthCryptoUtil.removePKCS7(shortUnpad, 0);
	}
	
	@Test(expected=javax.crypto.BadPaddingException.class)
	public void removePKCS7Unpadded() throws BadPaddingException
	{
		String shortUnpad = "unpad";
		AuthCryptoUtil.removePKCS7(shortUnpad, 16);
	}
	
	@Test
	public void removePKCS7TandemNolen()
	{
		String nolenUnpad = "wordwordwordword"; // 16 chars
		try {
			String pad = AuthCryptoUtil.appendPKCS7(nolenUnpad, 16);
			assertTrue(nolenUnpad.equals(AuthCryptoUtil.removePKCS7(pad, 16)));
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void removePKCS7TandemNormal()
	{
		String nolenUnpad = "wordwordwordwordword"; // 20 chars
		try {
			String pad = AuthCryptoUtil.appendPKCS7(nolenUnpad, 16);
			assertTrue(nolenUnpad.equals(AuthCryptoUtil.removePKCS7(pad, 16)));
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void symmetricEncDecAESNoProblems()
	{
		AuthCryptoUtil.init();
		String toEnc = "wordwordwordwordwordword"; // 24 chars
		String enc = AuthCryptoUtil.encrypt(toEnc);
		String dec = AuthCryptoUtil.decrypt(enc);
		assertTrue(toEnc.equals(dec));
	}
}
