package com.revature.project02;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.revature.project02.util.HashUtil;

public class HashTests {
	
	public HashTests() {
		super();
	}
	
	@Test
	public void hashTestBlank()
	{
		assertTrue("E3B0C44298FC1C149AFBF4C8996FB92427AE41E4649B934CA495991B7852B855".equals(HashUtil.hashStr("")));
	}
	
	@Test
	public void hashTestA()
	{
		assertTrue("DBA36BFFA5CAB0F922D087A3AEB179F9D4E745DF40B323E1B1471402848C8A3E".equals(HashUtil.hashStr("words")));
	}
}
