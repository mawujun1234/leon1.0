package com.mawujun.controller.spring;

import org.junit.Assert;
import org.junit.Test;

import com.mawujun.controller.spring.mvc.JsonStringToWhereInfo_FastJson;
import com.mawujun.utils.page.WhereInfo;

public class JsonStringToSortInfo_FastJsonTest {
	@Test
	public void test(){
		JsonStringToWhereInfo_FastJson aa=new JsonStringToWhereInfo_FastJson();
		String wheresStr="[{prop:'name',op:'like',value:'张三'},{prop:'name',op:'>',value:'李四'}]";
		WhereInfo[] list=aa.convert(wheresStr);
		Assert.assertEquals(2, list.length);
		Assert.assertEquals("name",list[0].getProp());
		Assert.assertEquals("like",list[0].getOp().toString());
		Assert.assertEquals(">",list[1].getOp().toString());
	}
}
