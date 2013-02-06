package com.mawujun.utils.spring;

import org.junit.Assert;
import org.junit.Test;

import com.mawujun.utils.page.SortInfo;
import com.mawujun.utils.spring.mvc.JsonStringToSortInfo_FastJson;

public class JsonStringToWhereInfo_FastJsonTest {
	@Test
	public void test(){
		JsonStringToSortInfo_FastJson aa=new JsonStringToSortInfo_FastJson();
		String sortStr="[{property:'name',direction:'asc'},{property:'date',direction:'desc'}]";
		SortInfo[] list=aa.convert(sortStr);
		Assert.assertEquals(2, list.length);
		Assert.assertEquals("name",list[0].getProperty());
		Assert.assertEquals("asc",list[0].getDirection());
		
		Assert.assertEquals("date",list[1].getProperty());
		Assert.assertEquals("desc",list[1].getDirection());
	}
}
