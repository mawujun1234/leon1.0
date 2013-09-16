package com.mawujun.parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 那些主体可以设置参数，注意只用用户主体获取参数的时候，会依次从各个主体中获取。
 * 还有一个问题，参数是否只用于某几个主体
 * 当请求一个用户的的参数的时候，他是 具有层级关系的 用户--》职位--》组织单元---》用户组---》角色---系统，会依次判断有没有值，直到获取到 有值得
 * ，其他的主体，只要判断自身有没有，如果没有，再从系统里面获取
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public enum SubjectType {
	USER("用户"),POSITION("职位"),UNIT("组织单元"),
	GROUP("用户组"),ROLE("角色"),SYSTEM("系统");
	
	private String name;
	SubjectType(String name){
		this.name=name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getKey(){
		return this.toString();
	}
	
	/**
	 * 以List<Map<String,String>>的形式返回所有的枚举类型
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @return
	 */
	public static List<Map<String,String>> toListMap(){
		SubjectType[] models=SubjectType.values();
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		for(SubjectType model:models){
			Map<String,String> map=new HashMap<String,String>();
			map.put("key", model.getKey());
			map.put("name", model.getName());
			list.add(map);
		}
		return list;
	}

}
