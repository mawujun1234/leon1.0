package com.mawujun.utils.help;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ReportCodeHelperTest {
	//@Test  
	public void generate3(){
		 assertEquals(48,"0".charAt(0));
		 assertEquals(49,"1".charAt(0));
		 assertEquals(57,"9".charAt(0));
		 assertEquals(97,"a".charAt(0));
		 assertEquals(122,"z".charAt(0));
		 assertEquals("###",ReportCodeHelper.generate3(null));
		 assertEquals("##$",ReportCodeHelper.generate3("###"));
		 assertEquals("#$#",ReportCodeHelper.generate3("##}"));
		 assertEquals("$##",ReportCodeHelper.generate3("#}}"));
		 

		 assertEquals("###~##$",ReportCodeHelper.generate3("###~###"));
		 assertEquals("###~#$#",ReportCodeHelper.generate3("###~##}"));
		 assertEquals("###~$##",ReportCodeHelper.generate3("###~#}}"));
	}
	
	@Test  
	public void generate(){
		 assertEquals("####",ReportCodeHelper.generate(null,4));
		 assertEquals("###$",ReportCodeHelper.generate("###",4));
		 assertEquals("##$#",ReportCodeHelper.generate("##}",4));
		 assertEquals("#$##",ReportCodeHelper.generate("#}}",4));
		 assertEquals("$###",ReportCodeHelper.generate("}}}",4));
		 

		 assertEquals("####~###$",ReportCodeHelper.generate("####~###",4));
		 assertEquals("####~##$#",ReportCodeHelper.generate("####~##}",4));
		 assertEquals("####~#$##",ReportCodeHelper.generate("####~#}}",4));
		 assertEquals("####~$###",ReportCodeHelper.generate("####~}}}",4));
		 
		 assertEquals("#####~#$###",ReportCodeHelper.generate("#####~##}}}",4));
	}
	
	//@Test(expected=ArithmeticException.class)  
	public void testArithmeticException(){
		assertEquals("###",ReportCodeHelper.generate3("}}}"));
	}
	
	//@Test(expected=ArithmeticException.class)  
	public void testArithmeticException1(){
		assertEquals("###",ReportCodeHelper.generate3("}!}"));
	}

}
