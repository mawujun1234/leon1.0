package com.mawujun.parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ShowModelEnum {
	COMBOBOX("下拉框"),CHECKBOXGROUP("复选框"),RADIOGROUP("单选框"),TEXTFIELD("文本框"),NUMBERFIELD("数字框"),DATEFIELD("日期框"),TIMEFIELD("时间框");//,RANGENUMBERBOX("数字范围");
	
	private String name;
	ShowModelEnum(String name){
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
		ShowModelEnum[] models=ShowModelEnum.values();
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		for(ShowModelEnum model:models){
			Map<String,String> map=new HashMap<String,String>();
			map.put("key", model.getKey());
			map.put("name", model.getName());
			list.add(map);
		}
		return list;
	}
	
	public static  List<Map<String,String>> getShowModel(ParameterValuesSourceEnum parameterValueEnum){
		 ShowModelEnum[] models= parameterValueEnum.getShowModel();
		 List<Map<String,String>> list=new ArrayList<Map<String,String>>();
			for(ShowModelEnum model:models){
				Map<String,String> map=new HashMap<String,String>();
				map.put("key", model.getKey());
				map.put("name", model.getName());
				list.add(map);
			}
			return list;
	}
}
