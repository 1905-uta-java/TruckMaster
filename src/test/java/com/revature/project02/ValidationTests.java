package com.revature.project02;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.revature.project02.util.ValidationUtil;

public class ValidationTests {

	public ValidationTests()
	{
		super();
	}
	
	@Test
	public void ValidationTestsSanity()
	{
		assertTrue(true);
	}

	//validEmail tests
	@Test
	public void validEmailSqlInject()
	{
		assertFalse(ValidationUtil.validEmail("wm@efoe.com\'); SELECT"));
	}
	
	@Test
	public void validEmailBadCharsUser()
	{
		assertFalse(ValidationUtil.validEmail("in#al!d@email.com"));
	}
	
	@Test
	public void validEmailBadCharsDomain()
	{
		assertFalse(ValidationUtil.validEmail("invalid@em*al.c$m"));
	}
	
	@Test
	public void validEmailNoUser()
	{
		assertFalse(ValidationUtil.validEmail("@google.com"));
	}
	
	@Test
	public void validEmailNoDomain()
	{
		assertFalse(ValidationUtil.validEmail("dude@"));
	}
	
	@Test
	public void validEmailImpossiblyShort()
	{
		assertFalse(ValidationUtil.validEmail("@.c"));
	}
	
	@Test
	public void validEmailTooLong()
	{
		assertFalse(ValidationUtil.validEmail("deadbeef00@way.too.long.domain.co"));
	}
	
	@Test
	public void validEmailTrailingPeriod()
	{
		assertFalse(ValidationUtil.validEmail("invalid@email.com."));
	}
	
	@Test
	public void validEmailStartingPeriod()
	{
		assertFalse(ValidationUtil.validEmail(".invalid@email.com"));
	}
	
	@Test
	public void validEmailNoAt()
	{
		assertFalse(ValidationUtil.validEmail("dudegoogle.com"));
	}
	
	@Test
	public void validEmailShortSimple()
	{
		assertTrue(ValidationUtil.validEmail("wsm@efoe.com"));
	}
	
	@Test
	public void validEmailComplex()
	{
		assertTrue(ValidationUtil.validEmail("nicole.paiva@nevada.unr.edu"));
	}

	//validPassword tests
	@Test
	public void validPasswordTooShort()
	{
		assertFalse(ValidationUtil.validPassword("aaaaa"));
	}
	
	@Test
	public void validPasswordTooLong()
	{
		assertFalse(ValidationUtil.validPassword("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
	}
	
	@Test
	public void validPasswordBadCharacters()
	{
		assertFalse(ValidationUtil.validPassword("abc123#$%^\"!"));
	}
	
	@Test
	public void validPasswordValidSimple()
	{
		assertTrue(ValidationUtil.validPassword("abcdef0123"));
	}
	
	@Test
	public void validPasswordValidComplex()
	{
		assertTrue(ValidationUtil.validPassword("abc!_123.?"));
	}

	//validUsername tests
	@Test
	public void validUsernameTooShort()
	{
		assertFalse(ValidationUtil.validUsername("aaaaa"));
	}
	
	@Test
	public void validUsernameTooLong()
	{
		assertFalse(ValidationUtil.validUsername("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
	}
	
	@Test
	public void validUsernameBadCharacters()
	{
		assertFalse(ValidationUtil.validUsername("sql_injection!\'\"\';"));
	}
	
	@Test
	public void validUsernameBadCharactersPasswordValidChars()
	{
		assertFalse(ValidationUtil.validUsername("invalid_username?"));
	}
	
	@Test
	public void validUsernameValidSimple()
	{
		assertTrue(ValidationUtil.validUsername("validUsername"));
	}
	
	@Test
	public void validUsernameValidComplex()
	{
		assertTrue(ValidationUtil.validUsername("Valid_Username01"));
	}

}
