package com.mawujun.utils.spring;

import org.junit.Assert;

import org.junit.Test;

import com.mawujun.utils.page.WhereInfo;
import com.mawujun.utils.spring.mvc.JsonStringToWhereInfo_FastJson;

public class JsonStringToSortInfo_FastJsonTest {
	@Test
	public void test(){
		JsonStringToWhereInfo_FastJson aa=new JsonStringToWhereInfo_FastJson();
		String wheresStr="[{property:'name',op:'LIKE',value:'张三'},{property:'name',op:'GT',value:'李四'}]";
		WhereInfo[] list=aa.convert(wheresStr);
		Assert.assertEquals(2, list.length);
		Assert.assertEquals("name",list[0].getProperty());
		Assert.assertEquals("like",list[0].getOp().toString());
		Assert.assertEquals(">",list[1].getOp().toString());
	}
}
