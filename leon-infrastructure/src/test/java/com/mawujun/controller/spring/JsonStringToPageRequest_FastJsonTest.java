package com.mawujun.controller.spring;

import org.junit.Assert;
import org.junit.Test;

import com.mawujun.controller.spring.JsonStringToPageRequest_FastJson;
import com.mawujun.utils.page.PageRequest;

//@RunWith(SpringJUnit4ClassRunner.class)
public class JsonStringToPageRequest_FastJsonTest {
	@Test
	public void test(){
		JsonStringToPageRequest_FastJson toP=new JsonStringToPageRequest_FastJson();
		String jsonStr="{}";
		PageRequest pr=toP.convert(jsonStr);
		Assert.assertEquals(0, pr.getStart());
		Assert.assertEquals(-1, pr.getPageSize());
		Assert.assertEquals(1, pr.getPageNo());
		Assert.assertNull(pr.getWheres());
		Assert.assertNull( pr.getSorts());
		
		
		jsonStr="{start:0,limit:10}";
		pr=toP.convert(jsonStr);
		Assert.assertEquals(0, pr.getStart());
		Assert.assertEquals(10, pr.getPageSize());
		Assert.assertEquals(1, pr.getPageNo());
		Assert.assertNull(pr.getWheres());
		Assert.assertNull( pr.getSorts());
		这里有问题start应该等于11
		jsonStr="{start:11,limit:10}";
		pr=toP.convert(jsonStr);
		Assert.assertEquals(10, pr.getStart());
		Assert.assertEquals(10, pr.getPageSize());
		Assert.assertEquals(2, pr.getPageNo());
		Assert.assertNull(pr.getWheres());
		Assert.assertNull( pr.getSorts());
		
	}
	
	@Test
	public void test1(){
		JsonStringToPageRequest_FastJson toP=new JsonStringToPageRequest_FastJson();
		String jsonStr="{wheres:[{property:'name',op:'EQ',value:'张三'}]}";
		PageRequest pr=toP.convert(jsonStr);
		Assert.assertEquals(0, pr.getStart());
		Assert.assertEquals(-1, pr.getPageSize());
		Assert.assertEquals(1, pr.getPageNo());
		Assert.assertEquals(1, pr.getWheres().length);
		Assert.assertNull( pr.getSorts());
		Assert.assertEquals("name", pr.getWheres()[0].getProperty());
		Assert.assertEquals("=", pr.getWheres()[0].getOpSymbol().toString());
		Assert.assertEquals("张三", pr.getWheres()[0].getValue());
		
		
		jsonStr="{wheres:[{property:'name',value:'张三'}]}";
		pr=toP.convert(jsonStr);
		Assert.assertEquals(0, pr.getStart());
		Assert.assertEquals(-1, pr.getPageSize());
		Assert.assertEquals(1, pr.getPageNo());
		Assert.assertEquals(1, pr.getWheres().length);
		//Assert.assertEquals(0, pr.getSorts().length);
		Assert.assertNull( pr.getSorts());
		Assert.assertEquals("name", pr.getWheres()[0].getProperty());
		Assert.assertEquals("=", pr.getWheres()[0].getOpSymbol());
		Assert.assertEquals("张三", pr.getWheres()[0].getValue());
		
		jsonStr="{wheres:[{property:'name',value:'张三'}]}";
		pr=toP.convert(jsonStr);
		Assert.assertEquals(0, pr.getStart());
		Assert.assertEquals(-1, pr.getPageSize());
		Assert.assertEquals(1, pr.getPageNo());
		Assert.assertEquals(1, pr.getWheres().length);
		Assert.assertNull( pr.getSorts());
		Assert.assertEquals("name", pr.getWheres()[0].getProperty());
		Assert.assertEquals("=", pr.getWheres()[0].getOpSymbol());
		Assert.assertEquals("张三", pr.getWheres()[0].getValue());
		
		jsonStr="{wheres:[{property:'name',value:'张三'},{property:'name',value:'李四'}]}";
		pr=toP.convert(jsonStr);
		Assert.assertEquals(0, pr.getStart());
		Assert.assertEquals(-1, pr.getPageSize());
		Assert.assertEquals(1, pr.getPageNo());
		Assert.assertEquals(2, pr.getWheres().length);
		Assert.assertNull( pr.getSorts());
		Assert.assertEquals("name", pr.getWheres()[0].getProperty());
		Assert.assertEquals("=", pr.getWheres()[0].getOpSymbol());
		Assert.assertEquals("张三", pr.getWheres()[0].getValue());
		
		Assert.assertEquals("name", pr.getWheres()[1].getProperty());
		Assert.assertEquals("=", pr.getWheres()[1].getOpSymbol());
		Assert.assertEquals("李四", pr.getWheres()[1].getValue());
	}
	
	@Test
	public void test2(){
		JsonStringToPageRequest_FastJson toP=new JsonStringToPageRequest_FastJson();
		String jsonStr="{sorts:[{property:'name',dir:'asc'}],wheres:[{property:'name',value:'张三'},{property:'name',value:'李四'}]}";
		PageRequest pr=toP.convert(jsonStr);
		Assert.assertEquals(0, pr.getStart());
		Assert.assertEquals(-1, pr.getPageSize());
		Assert.assertEquals(1, pr.getPageNo());
		Assert.assertEquals(2, pr.getWheres().length);
		Assert.assertEquals(1, pr.getSorts().length);
		Assert.assertEquals("name", pr.getSorts()[0].getProperty());
		Assert.assertEquals("asc", pr.getSorts()[0].getDirection());
	}

}
