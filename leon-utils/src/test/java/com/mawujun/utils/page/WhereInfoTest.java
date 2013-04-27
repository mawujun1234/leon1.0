package com.mawujun.utils.page;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class WhereInfoTest {
	@Test
	public void parse(){
		WhereInfo eq=WhereInfo.parse("name_eq", "mawujun");
		assertEquals("mawujun", eq.getValue());
		assertEquals("=", eq.getOpSymbol());
		assertEquals("name", eq.getProperty());
		
		WhereInfo eq1=WhereInfo.parse("name", "mawujun");
		assertEquals("mawujun", eq1.getValue());
		assertEquals("=", eq1.getOpSymbol());
		assertEquals("name", eq1.getProperty());
		
		WhereInfo eq2=WhereInfo.parse("parent.id", "mawujun");
		assertEquals("mawujun", eq2.getValue());
		assertEquals("=", eq2.getOpSymbol());
		assertEquals("parent.id", eq2.getProperty());
		
		WhereInfo LT=WhereInfo.parse("age_LT", "10");
		assertEquals("10", LT.getValue());
		assertEquals("<", LT.getOpSymbol());
		assertEquals("age", LT.getProperty());
		
		WhereInfo GT=WhereInfo.parse("age_GT", "10");
		assertEquals("10", GT.getValue());
		assertEquals(">", GT.getOpSymbol());
		assertEquals("age", GT.getProperty());
		
		WhereInfo LE=WhereInfo.parse("age_LE", "10");
		assertEquals("10", LE.getValue());
		assertEquals("<=", LE.getOpSymbol());
		assertEquals("age", LE.getProperty());
		
		WhereInfo gE=WhereInfo.parse("age_gE", "10");
		assertEquals("10", gE.getValue());
		assertEquals(">=", gE.getOpSymbol());
		assertEquals("age", gE.getProperty());
		
		WhereInfo isNull=WhereInfo.parse("age_isNull", "111");
		assertEquals("111",isNull.getValue());
		assertEquals("is null", isNull.getOpSymbol());
		assertEquals("age", isNull.getProperty());
		
		WhereInfo _isnotNull=WhereInfo.parse("age_isnotNull", "111222");
		assertEquals("111222",_isnotNull.getValue());
		assertEquals("is not null", _isnotNull.getOpSymbol());
		assertEquals("age", _isnotNull.getProperty());
		
		WhereInfo like=WhereInfo.parse("name_like", "ma");
		assertEquals("%ma%",like.getValue());
		assertEquals("like", like.getOpSymbol());
		assertEquals("name", like.getProperty());
		
		WhereInfo likestart=WhereInfo.parse("name_likestart", "ma");
		assertEquals("ma%",likestart.getValue());
		assertEquals("like", likestart.getOpSymbol());
		assertEquals("name", likestart.getProperty());
		
		WhereInfo likeend=WhereInfo.parse("name_likeend", "ma");
		assertEquals("%ma",likeend.getValue());
		assertEquals("like", likeend.getOpSymbol());
		assertEquals("name", likeend.getProperty());
		
		WhereInfo ilike=WhereInfo.parse("name_ilike", "Ma");
		assertEquals("%ma%",ilike.getValue());
		assertEquals("like", ilike.getOpSymbol());
		assertEquals("lower(name)", ilike.getProperty());
		
		WhereInfo ilikestart=WhereInfo.parse("name_ilikestart", "MA");
		assertEquals("ma%",ilikestart.getValue());
		assertEquals("like", ilikestart.getOpSymbol());
		assertEquals("lower(name)", ilikestart.getProperty());
		
		WhereInfo ilikeend=WhereInfo.parse("name_ilikeend", "mA");
		assertEquals("%ma",ilikeend.getValue());
		assertEquals("like", ilikeend.getOpSymbol());
		assertEquals("lower(name)", ilikeend.getProperty());

		WhereInfo between=WhereInfo.parse("name_between", "1,10");
		assertEquals("1",((Object[])between.getValue())[0]);
		assertEquals("10",((Object[])between.getValue())[1]);
		assertEquals("between", between.getOpSymbol());
		assertEquals("name", between.getProperty());
		
		
		WhereInfo in=WhereInfo.parse("name_in", "1,10,11");
		assertEquals("1",((Object[])in.getValue())[0]);
		assertEquals("10",((Object[])in.getValue())[1]);
		assertEquals("11",((Object[])in.getValue())[2]);
		assertEquals("in", in.getOpSymbol());
		assertEquals("name", in.getProperty());
		
		WhereInfo in1=WhereInfo.parse("name_in", "1,");
		assertEquals("1",((Object[])in1.getValue())[0]);
		assertEquals("in", in1.getOpSymbol());
		assertEquals("name", in1.getProperty());
		
		WhereInfo in2=WhereInfo.parse("name_in", "1");
		assertEquals("1",((Object[])in2.getValue())[0]);
		assertEquals("in", in2.getOpSymbol());
		assertEquals("name", in2.getProperty());
		
		
		WhereInfo in3=WhereInfo.parse("name_in", ",1");
		assertEquals("",((Object[])in3.getValue())[0]);
		assertEquals("1",((Object[])in3.getValue())[1]);
		assertEquals("in", in3.getOpSymbol());
		assertEquals("name", in3.getProperty());
		assertEquals("name", in3.getProperty());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testExceptionbetween1(){
		WhereInfo between=WhereInfo.parse("name_between", "1");
	}
	@Test(expected=IllegalArgumentException.class)
	public void testExceptionbetween2(){
		WhereInfo between=WhereInfo.parse("name_between", "1,");
	}
	
	@Test
	public void parseMap(){
		Map<String,String> searchParams=new HashMap<String,String>();
		searchParams.put("name_like", "ma");
		searchParams.put("age_between", "1,10");
		searchParams.put("id_in", "1,2,3,4,5");
		searchParams.put("height_isnull", "sdfsdf");
		searchParams.put("weight_isnotnull", "sdfsdf");
		searchParams.put("income_le", "1000");
		Map<String, WhereInfo> whereInfos=WhereInfo.parse(searchParams);
		
		assertEquals(6, whereInfos.size());
		assertEquals("%ma%",whereInfos.get("name_like").getValue());
		assertEquals("like",whereInfos.get("name_like").getOpSymbol());
		assertEquals("name",whereInfos.get("name_like").getProperty());
		
		assertEquals("1",((Object[])whereInfos.get("age_between").getValue())[0]);
		assertEquals("10",((Object[])whereInfos.get("age_between").getValue())[1]);
		assertEquals("between",whereInfos.get("age_between").getOpSymbol());
		assertEquals("age",whereInfos.get("age_between").getProperty());
		
		assertEquals("1",((Object[])whereInfos.get("id_in").getValue())[0]);
		assertEquals("2",((Object[])whereInfos.get("id_in").getValue())[1]);
		assertEquals("3",((Object[])whereInfos.get("id_in").getValue())[2]);
		assertEquals("4",((Object[])whereInfos.get("id_in").getValue())[3]);
		assertEquals("5",((Object[])whereInfos.get("id_in").getValue())[4]);
		assertEquals("in",whereInfos.get("id_in").getOpSymbol());
		assertEquals("id",whereInfos.get("id_in").getProperty());
		
		assertEquals("sdfsdf",whereInfos.get("height_isnull").getValue());
		assertEquals("is null",whereInfos.get("height_isnull").getOpSymbol());
		assertEquals("height",whereInfos.get("height_isnull").getProperty());
		
		assertEquals("sdfsdf",whereInfos.get("weight_isnotnull").getValue());
		assertEquals("is not null",whereInfos.get("weight_isnotnull").getOpSymbol());
		assertEquals("weight",whereInfos.get("weight_isnotnull").getProperty());
		
		assertEquals("1000",whereInfos.get("income_le").getValue());
		assertEquals("<=",whereInfos.get("income_le").getOpSymbol());
		assertEquals("income",whereInfos.get("income_le").getProperty());
	}
	
	@Test
	public void parseKeySet(){
		WhereInfo eq=new WhereInfo();
		eq.setKey("name_eq");
		eq.setValue("mawujun");
		assertEquals("mawujun", eq.getValue());
		assertEquals("=", eq.getOpSymbol());
		assertEquals("name", eq.getProperty());
		
		//WhereInfo LT=WhereInfo.parse("age_LT", "10");
		WhereInfo LT=new WhereInfo();
		LT.setKey("age_LT");
		LT.setValue("10");
		assertEquals("10", LT.getValue());
		assertEquals("<", LT.getOpSymbol());
		assertEquals("age", LT.getProperty());
		
		//WhereInfo GT=WhereInfo.parse("age_GT", "10");
		WhereInfo GT=new WhereInfo();
		GT.setKey("age_GT");
		GT.setValue("10");
		assertEquals("10", GT.getValue());
		assertEquals(">", GT.getOpSymbol());
		assertEquals("age", GT.getProperty());
		
		//WhereInfo LE=WhereInfo.parse("age_LE", "10");
		WhereInfo LE=new WhereInfo();
		LE.setKey("age_LE");
		LE.setValue("10");
		assertEquals("10", LE.getValue());
		assertEquals("<=", LE.getOpSymbol());
		assertEquals("age", LE.getProperty());
		
		//WhereInfo gE=WhereInfo.parse("age_gE", "10");
		WhereInfo gE=new WhereInfo();
		gE.setKey("age_gE");
		gE.setValue("10");
		assertEquals("10", gE.getValue());
		assertEquals(">=", gE.getOpSymbol());
		assertEquals("age", gE.getProperty());
		
		//WhereInfo isNull=WhereInfo.parse("age_isNull", "111");
		WhereInfo isNull=new WhereInfo();
		isNull.setKey("age_isNull");
		isNull.setValue("111");
		assertEquals("111",isNull.getValue());
		assertEquals("is null", isNull.getOpSymbol());
		assertEquals("age", isNull.getProperty());
		
		//WhereInfo _isnotNull=WhereInfo.parse("age_isnotNull", "111222");
		WhereInfo _isnotNull=new WhereInfo();
		_isnotNull.setKey("age_isnotNull");
		_isnotNull.setValue("111222");
		assertEquals("111222",_isnotNull.getValue());
		assertEquals("is not null", _isnotNull.getOpSymbol());
		assertEquals("age", _isnotNull.getProperty());
		
		//WhereInfo like=WhereInfo.parse("name_like", "ma");
		WhereInfo like=new WhereInfo();
		like.setKey("name_like");
		like.setValue("ma");
		assertEquals("%ma%",like.getValue());
		assertEquals("like", like.getOpSymbol());
		assertEquals("name", like.getProperty());
		
		//WhereInfo likestart=WhereInfo.parse("name_likestart", "ma");
		WhereInfo likestart=new WhereInfo();
		likestart.setKey("name_likestart");
		likestart.setValue("ma");
		assertEquals("ma%",likestart.getValue());
		assertEquals("like", likestart.getOpSymbol());
		assertEquals("name", likestart.getProperty());
		
		//WhereInfo likeend=WhereInfo.parse("name_likeend", "ma");
		WhereInfo likeend=new WhereInfo();
		likeend.setKey("name_likeend");
		likeend.setValue("ma");
		assertEquals("%ma",likeend.getValue());
		assertEquals("like", likeend.getOpSymbol());
		assertEquals("name", likeend.getProperty());
		
		//WhereInfo ilike=WhereInfo.parse("name_ilike", "Ma");
		WhereInfo ilike=new WhereInfo();
		ilike.setKey("name_ilike");
		ilike.setValue("Ma");
		assertEquals("%ma%",ilike.getValue());
		assertEquals("like", ilike.getOpSymbol());
		assertEquals("lower(name)", ilike.getProperty());
		
		//WhereInfo ilikestart=WhereInfo.parse("name_ilikestart", "MA");
		WhereInfo ilikestart=new WhereInfo();
		ilikestart.setKey("name_ilikestart");
		ilikestart.setValue("MA");
		assertEquals("ma%",ilikestart.getValue());
		assertEquals("like", ilikestart.getOpSymbol());
		assertEquals("lower(name)", ilikestart.getProperty());
		
		//WhereInfo ilikeend=WhereInfo.parse("name_ilikeend", "mA");
		WhereInfo ilikeend=new WhereInfo();
		ilikeend.setKey("name_ilikeend");
		ilikeend.setValue("mA");
		assertEquals("%ma",ilikeend.getValue());
		assertEquals("like", ilikeend.getOpSymbol());
		assertEquals("lower(name)", ilikeend.getProperty());

		//WhereInfo between=WhereInfo.parse("name_between", "1,10");
		WhereInfo between=new WhereInfo();
		between.setKey("name_between");
		between.setValue("1,10");
		assertEquals("1",((Object[])between.getValue())[0]);
		assertEquals("10",((Object[])between.getValue())[1]);
		assertEquals("between", between.getOpSymbol());
		assertEquals("name", between.getProperty());
		
		
		//WhereInfo in=WhereInfo.parse("name_in", "1,10,11");
		WhereInfo in=new WhereInfo();
		in.setKey("name_in");
		in.setValue("1,10,11");
		assertEquals("1",((Object[])in.getValue())[0]);
		assertEquals("10",((Object[])in.getValue())[1]);
		assertEquals("11",((Object[])in.getValue())[2]);
		assertEquals("in", in.getOpSymbol());
		assertEquals("name", in.getProperty());
		
		//WhereInfo in1=WhereInfo.parse("name_in", "1,");
		WhereInfo in1=new WhereInfo();
		in1.setKey("name_in");
		in1.setValue("1,");
		assertEquals("1",((Object[])in1.getValue())[0]);
		assertEquals("in", in1.getOpSymbol());
		assertEquals("name", in1.getProperty());
		
		//WhereInfo in2=WhereInfo.parse("name_in", "1");
		WhereInfo in2=new WhereInfo();
		in2.setKey("name_in");
		in2.setValue("1");
		assertEquals("1",((Object[])in2.getValue())[0]);
		assertEquals("in", in2.getOpSymbol());
		assertEquals("name", in2.getProperty());
		
		
		//WhereInfo in3=WhereInfo.parse("name_in", ",1");
		WhereInfo in3=new WhereInfo();
		in3.setKey("name_in");
		in3.setValue(",1");
		assertEquals("",((Object[])in3.getValue())[0]);
		assertEquals("1",((Object[])in3.getValue())[1]);
		assertEquals("in", in3.getOpSymbol());
		assertEquals("name", in3.getProperty());
		assertEquals("name", in3.getProperty());
	}
	
}
