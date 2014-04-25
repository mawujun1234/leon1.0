package com.mawujun.parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 参数数据源的形式
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public enum ParameterValuesSourceEnum {
	STRING("字符串"),NUMBER("数字"),
	//EXPRESSION("表达式"),
	BOOLEAN("布尔值"),ARRAY("数组"),DATE("日期"),TIME("时间"),
	SCHEDULED("定时调度"),
	//RANGE("范围"),//使用规则来替代这个，例如超出这个范围就报错
	//SQL("sql查询"),//使用sql读取出数据
	JAVA("java类(动态)");//使用java类去读取数据
	
	private String name;
	ParameterValuesSourceEnum(String name){
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
		ParameterValuesSourceEnum[] models=ParameterValuesSourceEnum.values();
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		for(ParameterValuesSourceEnum model:models){
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
		if(this==ParameterValuesSourceEnum.STRING){
			return new ShowModelEnum[]{ShowModelEnum.TEXTFIELD};
		} else if(this==ParameterValuesSourceEnum.NUMBER){
			return new ShowModelEnum[]{ShowModelEnum.NUMBERFIELD};
//		}else if(this==ParameterValuesSourceEnum.EXPRESSION){
//			return new ShowModelEnum[]{ShowModelEnum.TEXTFIELD};
		}else if(this==ParameterValuesSourceEnum.BOOLEAN){
			return new ShowModelEnum[]{ShowModelEnum.COMBOBOX,ShowModelEnum.RADIOGROUP};
		}else if(this==ParameterValuesSourceEnum.ARRAY){
			return new ShowModelEnum[]{ShowModelEnum.COMBOBOX,ShowModelEnum.CHECKBOXGROUP,ShowModelEnum.RADIOGROUP};
		} else if(this==ParameterValuesSourceEnum.DATE){
			return new ShowModelEnum[]{ShowModelEnum.DATEFIELD};
		} else if(this==ParameterValuesSourceEnum.TIME){
				return new ShowModelEnum[]{ShowModelEnum.TIMEFIELD};
//		} else if(this==ParameterValuesSourceEnum.RANGE){
//			return new ShowModelEnum[]{ShowModelEnum.RANGENUMBERBOX};
		} else if(this==ParameterValuesSourceEnum.SCHEDULED){
			return new ShowModelEnum[]{ShowModelEnum.TEXTFIELD};
		//} else if(this==ParameterValuesSourceEnum.SQL){
		//	return new ShowModelEnum[]{ShowModelEnum.COMBOBOX,ShowModelEnum.CHECKBOXGROUP,ShowModelEnum.RADIOGROUP};
		} else if(this==ParameterValuesSourceEnum.JAVA){
			return new ShowModelEnum[]{ShowModelEnum.COMBOBOX,ShowModelEnum.CHECKBOXGROUP,ShowModelEnum.RADIOGROUP};
		}
		return ShowModelEnum.values();
	}
	
}
