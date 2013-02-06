package com.mawujun.utils.spring;

import org.junit.Assert;

import org.junit.Test;

import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.spring.mvc.StringToPageRequest_JsonLib;

//@RunWith(SpringJUnit4ClassRunner.class)
public class StringToPageRequest_JsonLibTest {
	@Test
	public void test(){
		StringToPageRequest_JsonLib toP=new StringToPageRequest_JsonLib();
		String jsonStr="{}";
		PageRequest pr=toP.convert(jsonStr);
		Assert.assertEquals(1, pr.getStart());
		Assert.assertEquals(-1, pr.getPageSize());
		Assert.assertEquals(1, pr.getPageNo());
		Assert.assertEquals(0, pr.getWheres().length);
		Assert.assertEquals(0, pr.getSorts().length);
		
		
		jsonStr="{start:1,limit:10}";
		pr=toP.convert(jsonStr);
		Assert.assertEquals(1, pr.getStart());
		Assert.assertEquals(10, pr.getPageSize());
		Assert.assertEquals(1, pr.getPageNo());
		Assert.assertEquals(0, pr.getWheres().length);
		Assert.assertEquals(0, pr.getSorts().length);
		
		jsonStr="{start:11,limit:10}";
		pr=toP.convert(jsonStr);
		Assert.assertEquals(11, pr.getStart());
		Assert.assertEquals(10, pr.getPageSize());
		Assert.assertEquals(2, pr.getPageNo());
		Assert.assertEquals(0, pr.getWheres().length);
		Assert.assertEquals(0, pr.getSorts().length);
		
	}
	
	@Test
	public void test1(){
		StringToPageRequest_JsonLib toP=new StringToPageRequest_JsonLib();
		String jsonStr="{wheres:[{property:'name',op:'=',value:'张三'}]}";
		PageRequest pr=toP.convert(jsonStr);
		Assert.assertEquals(1, pr.getStart());
		Assert.assertEquals(-1, pr.getPageSize());
		Assert.assertEquals(1, pr.getPageNo());
		Assert.assertEquals(1, pr.getWheres().length);
		Assert.assertEquals(0, pr.getSorts().length);
		Assert.assertEquals("name", pr.getWheres()[0].getProperty());
		Assert.assertEquals("=", pr.getWheres()[0].getOp().toString());
		Assert.assertEquals("张三", pr.getWheres()[0].getValue());
		
		
		jsonStr="{wheres:[{property:'name',value:'张三'}]}";
		pr=toP.convert(jsonStr);
		Assert.assertEquals(1, pr.getStart());
		Assert.assertEquals(-1, pr.getPageSize());
		Assert.assertEquals(1, pr.getPageNo());
		Assert.assertEquals(1, pr.getWheres().length);
		Assert.assertEquals(0, pr.getSorts().length);
		Assert.assertEquals("name", pr.getWheres()[0].getProperty());
		Assert.assertEquals("=", pr.getWheres()[0].getOp().toString());
		Assert.assertEquals("张三", pr.getWheres()[0].getValue());
		
		jsonStr="{wheres:[{property:'name',value:'张三'}]}";
		pr=toP.convert(jsonStr);
		Assert.assertEquals(1, pr.getStart());
		Assert.assertEquals(-1, pr.getPageSize());
		Assert.assertEquals(1, pr.getPageNo());
		Assert.assertEquals(1, pr.getWheres().length);
		Assert.assertEquals(0, pr.getSorts().length);
		Assert.assertEquals("name", pr.getWheres()[0].getProperty());
		Assert.assertEquals("=", pr.getWheres()[0].getOp().toString());
		Assert.assertEquals("张三", pr.getWheres()[0].getValue());
		
		jsonStr="{wheres:[{property:'name',value:'张三'},{property:'name',value:'李四'}]}";
		pr=toP.convert(jsonStr);
		Assert.assertEquals(1, pr.getStart());
		Assert.assertEquals(-1, pr.getPageSize());
		Assert.assertEquals(1, pr.getPageNo());
		Assert.assertEquals(2, pr.getWheres().length);
		Assert.assertEquals(0, pr.getSorts().length);
		Assert.assertEquals("name", pr.getWheres()[0].getProperty());
		Assert.assertEquals("=", pr.getWheres()[0].getOp().toString());
		Assert.assertEquals("张三", pr.getWheres()[0].getValue());
		
		Assert.assertEquals("name", pr.getWheres()[1].getProperty());
		Assert.assertEquals("=", pr.getWheres()[1].getOp().toString());
		Assert.assertEquals("李四", pr.getWheres()[1].getValue());
	}
	
	@Test
	public void test2(){
		StringToPageRequest_JsonLib toP=new StringToPageRequest_JsonLib();
		String jsonStr="{sorts:[{property:'name',dir:'asc'}],wheres:[{property:'name',value:'张三'},{property:'name',value:'李四'}]}";
		PageRequest pr=toP.convert(jsonStr);
		Assert.assertEquals(1, pr.getStart());
		Assert.assertEquals(-1, pr.getPageSize());
		Assert.assertEquals(1, pr.getPageNo());
		Assert.assertEquals(2, pr.getWheres().length);
		Assert.assertEquals(1, pr.getSorts().length);
		Assert.assertEquals("name", pr.getSorts()[0].getProperty());
		Assert.assertEquals("asc", pr.getSorts()[0].getDirection());
	}

}
