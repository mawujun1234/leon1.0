package com.mawujun.controller.spring;

import org.junit.Assert;
import org.junit.Test;

import com.mawujun.controller.spring.mvc.StringToWhereInfo_JsonLib;
import com.mawujun.utils.page.WhereInfo;

public class StringToWhereInfo_JsonLibTest {
	@Test
	public void test(){
		StringToWhereInfo_JsonLib aa=new StringToWhereInfo_JsonLib();
		String wheresStr="[{property:'name',op:'like',value:'张三'},{property:'name',op:'>',value:'李四'}]";
		WhereInfo[] list=aa.convert(wheresStr);
		Assert.assertEquals(2, list.length);
		Assert.assertEquals("name",list[0].getProperty());
		Assert.assertEquals("like",list[0].getOp().toString());
		Assert.assertEquals(">",list[1].getOp().toString());
	}
}
