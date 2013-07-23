package com.mawujun.utils.page.sql;

import static org.junit.Assert.*;

import org.junit.Test;

public class CndTest {

	@Test
	public void testWhere() {
		//fail("Not yet implemented");
		Cnd cnd=Cnd.where();
		assertEquals(true, cnd.getWhere().isTop());
		assertEquals(true,cnd.getWhere().isEmpty());
		
		Cnd group1=Cnd.where().and("name", ">", 1);
		assertEquals(false,group1.getWhere().isEmpty());
		
	}

	
	@Test
	public void testExp() {
		fail("Not yet implemented");
	}



	
}
