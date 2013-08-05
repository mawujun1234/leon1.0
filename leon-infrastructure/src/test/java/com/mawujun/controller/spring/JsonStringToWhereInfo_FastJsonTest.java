package com.mawujun.controller.spring;

import org.junit.Assert;
import org.junit.Test;

import com.mawujun.controller.spring.mvc.JsonStringToSortInfo_FastJson;
import com.mawujun.utils.page.SortInfo;

public class JsonStringToWhereInfo_FastJsonTest {
	@Test
	public void test(){
		JsonStringToSortInfo_FastJson aa=new JsonStringToSortInfo_FastJson();
		String sortStr="[{prop:'name',dir:'asc'},{prop:'date',dir:'desc'}]";
		SortInfo[] list=aa.convert(sortStr);
		Assert.assertEquals(2, list.length);
		Assert.assertEquals("name",list[0].getProp());
		Assert.assertEquals("asc",list[0].getDir());
		
		Assert.assertEquals("date",list[1].getProp());
		Assert.assertEquals("desc",list[1].getDir());
	}
}
