package com.mawujun.parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ParameterValueEnum {
	STRING("字符串"),NUMBER("数字"),
	//EXPRESSION("表达式"),
	BOOLEAN("布尔值"),ARRAY("数组"),DATE("日期"),TIME("时间"),
	SCHEDULED("定时调度"),
	//RANGE("范围"),//使用规则来替代这个，例如超出这个范围就报错
	SQL("sql查询"),//使用sql读取出数据
	JAVA("java类");//使用java类去读取数据
	
	private String name;
	ParameterValueEnum(String name){
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
		ParameterValueEnum[] models=ParameterValueEnum.values();
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		for(ParameterValueEnum model:models){
			Map<String,String> map=new HashMap<String,String>();
			map.put("key", model.getKey());
			map.put("name", model.getName());
			list.add(map);
		}
		return list;
	}
	/**
	 * 获取展示方式，不同的数值的展示方式是不一杨的
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @return
	 */
	public ShowModelEnum[] getShowModel(){
		if(this==ParameterValueEnum.STRING){
			return new ShowModelEnum[]{ShowModelEnum.TEXTFIELD};
		} else if(this==ParameterValueEnum.NUMBER){
			return new ShowModelEnum[]{ShowModelEnum.NUMBERFIELD};
//		}else if(this==ParameterValueEnum.EXPRESSION){
//			return new ShowModelEnum[]{ShowModelEnum.TEXTFIELD};
		}else if(this==ParameterValueEnum.BOOLEAN){
			return new ShowModelEnum[]{ShowModelEnum.COMBOBOX,ShowModelEnum.RADIOGROUP};
		}else if(this==ParameterValueEnum.ARRAY){
			return new ShowModelEnum[]{ShowModelEnum.COMBOBOX,ShowModelEnum.CHECKBOXGROUP,ShowModelEnum.RADIOGROUP};
		} else if(this==ParameterValueEnum.DATE){
			return new ShowModelEnum[]{ShowModelEnum.DATEFIELD};
		} else if(this==ParameterValueEnum.TIME){
				return new ShowModelEnum[]{ShowModelEnum.TIMEFIELD};
//		} else if(this==ParameterValueEnum.RANGE){
//			return new ShowModelEnum[]{ShowModelEnum.RANGENUMBERBOX};
		} else if(this==ParameterValueEnum.SCHEDULED){
			return new ShowModelEnum[]{ShowModelEnum.TEXTFIELD};
		} else if(this==ParameterValueEnum.SQL){
			return new ShowModelEnum[]{ShowModelEnum.TEXTFIELD};
		} else if(this==ParameterValueEnum.JAVA){
			return new ShowModelEnum[]{ShowModelEnum.TEXTFIELD};
		}
		return ShowModelEnum.values();
	}
	
}