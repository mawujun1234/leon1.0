package com.mawujun.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jayway.jsonpath.JsonPath;
import com.mawujun.fun.Fun;

public class Test {

	/**
	 * @author mawujun email:mawujun1234@163.com qq:16064988
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Fun fun=new Fun();
		fun.setId("fun1");
		fun.setText("fun1");
		
		MenuItem item1=new MenuItem();
		item1.setId("1");
		item1.setText("1");
		item1.setFun(fun);
		
		MenuItem item2=new MenuItem();
		item2.setId("2");
		item2.setText("2");
		item2.setFun(fun);
		List<MenuItem> funes=new ArrayList<MenuItem>();
		funes.add(item1);
		funes.add(item2);
		
		//http://code.alibabatech.com/wiki/pages/viewpage.action?pageId=5832796
		//循环引用解决方案引起的问题
		//String json="[{'children':[],'fun':{'children':[],'id':'fun1','text':'fun1'},'id':'1','text':'1'},{'children':[],'fun':{\"$ref\":\"$[0].fun\"},'id':'2','text':'2'}]";
		//
		SerializeWriter out = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(out);  
		serializer.config(SerializerFeature.UseSingleQuotes,true);
		serializer.write(funes);
		
		String json=serializer.toString();
		System.out.println(json);
		
		
		//http://code.google.com/p/json-path/
		//首先用字符串正则表达式，取出所需要的数据，然后再进行替换
		Object aa=JsonPath.read(json, "$[0].fun");
		System.out.println(aa);
		
		//http://su1216.iteye.com/blog/1570964 正则表达式
		//Pattern pattern = Pattern.compile("\\{\"$ref\":"+"(.*)"+"\"\\}");  
		Pattern pattern = Pattern.compile("\\{\"\\$ref\":.*\"\\}");  
        Matcher matcher = pattern.matcher(json);
        while (matcher.find())
        {
        	System.out.println("找到匹配的数据");
        	String tmp = matcher.group(0);
        	String jsonPath=tmp.substring(9, 17);
        	System.out.println(jsonPath);
        	
        	System.out.println(tmp);
        	
        	Object bb=JsonPath.read(json, jsonPath);
        	tmp=matcher.replaceAll(bb.toString());
        	System.out.println(tmp);
        	
        }

	}

}
