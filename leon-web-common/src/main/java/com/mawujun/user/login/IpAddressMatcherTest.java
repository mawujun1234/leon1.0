package com.mawujun.user.login;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.security.web.util.IpAddressMatcher;


public class IpAddressMatcherTest {

	@Test
	public void test() {
		IpAddressMatcher aa=new IpAddressMatcher("172.16.3.5/24");
		boolean bool=aa.matches("172.16.3.5");
		assertEquals(true, bool);
	}
	@Test
	public void test0() {
		IpAddressMatcher aa=new IpAddressMatcher("172.16.3.5");
		boolean bool=aa.matches("172.16.3.6");
		assertEquals(true, bool);
	}
	
	@Test
	public void test1() {
		IpAddressMatcher aa=new IpAddressMatcher("172.16.3.5/24");
		boolean bool=aa.matches("172.16.3.5");
		assertEquals(true, bool);
	}

}
