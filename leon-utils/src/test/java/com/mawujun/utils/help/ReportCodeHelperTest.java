package com.mawujun.utils.help;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ReportCodeHelperTest {
	 @Test  
	public void test(){
		 assertEquals(48,"0".charAt(0));
		 assertEquals(49,"1".charAt(0));
		 assertEquals(57,"9".charAt(0));
		 assertEquals(97,"a".charAt(0));
		 assertEquals(122,"z".charAt(0));
		 String result=ReportCodeHelper.generate(null, 1);
		 assertEquals("a",result);
	}

}
