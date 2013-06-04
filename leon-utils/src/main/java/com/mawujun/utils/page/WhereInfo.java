package com.mawujun.utils.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.mawujun.utils.StringUtils;


public class WhereInfo  implements Serializable{
	//public static WhereOperationEnum whereOperation;
	
	protected void setDefaultValue(String default_value) {
		this.default_value = default_value;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String property;//属性名称，例如name，password
	private Operation op;  //operation具体的操作，例如=，>,like等
	private Object value;//具体的值，可能是字符串，也可能是字符串数组
	private String default_value;//默认的值
	
	private String key;//和property是等效的，这个只是简化的写法name_like,b把property和op结合在一起写了
	
	

	public WhereInfo(){
		
	}
	public WhereInfo(String key,String value){
		this.setKey(key);
		this.setValue(value);
	}

	public WhereInfo(String property, Operation operation, Object value) {
		super();
		this.property=property;
		this.op = operation;
		if(this.op==null){
			this.op=Operation.EQ;
		}
		this.value = value;
		this.flag_temp=false;
		//this.default_value=value2;
	}
	
	
	/**
	 * searchParams中key的格式为FIELDNAME_OPERATOR
	 * 当使用isNull和isnotnull的时候最好value带入任意一个值，系统会自动忽略掉这个值的，而且map也不能放入null
	 * 对in和between操作符，多个值时使用逗号分开：1,2,3,4,key="id_in",value="1,2,3,4";
	 */
	public static Map<String, WhereInfo> parse(Map<String, String> searchParams) {
		Map<String, WhereInfo> filters = Maps.newHashMap();

		for (Entry<String, String> entry : searchParams.entrySet()) {
			// 过滤掉空值
			String key = entry.getKey();
			String value = entry.getValue();
			if (StringUtils.isBlank((String) value)) {
				continue;
			}
			filters.put(key, WhereInfo.parse(key, value));
		}

		return filters;
	}
	/**
	 * searchParams中key的格式为FIELDNAME_OPERATOR
	 * 当使用isNull和isnotnull的时候最好value带入任意一个值，系统会自动忽略掉这个值的，而且map也不能放入null
	 * 对in和between操作符，多个值时使用逗号分开：1,2,3,4,key="id_in",value="1,2,3,4";
	 */
	public static WhereInfo[] parse2Array(Map<String, String> searchParams) {
		List<WhereInfo> filters = new ArrayList<WhereInfo>();

		for (Entry<String, String> entry : searchParams.entrySet()) {
			// 过滤掉空值
			String key = entry.getKey();
			String value = entry.getValue();
			if (StringUtils.isBlank(value)) {
				continue;
			}
			filters.add(WhereInfo.parse(key, value));
		}

		return filters.toArray(new WhereInfo[filters.size()]);
	}
	/**
	 * searchParams中key的格式为FIELDNAME_OPERATOR
	 * 当使用isNull和isnotnull的时候最好value带入任意一个值，系统会自动忽略掉这个值的
	 * 对in和between操作符，多个值时使用逗号分开：1,2,3,4,key="id_in",value="1,2,3,4";
	 * @param value 是字符串
	 */
	public static WhereInfo parse(final String key,final String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		}
		
//		int last=key.lastIndexOf('_');
//		if(last==-1){
//			throw new IllegalArgumentException(key + " is not a valid whereinfo name");
//		}
//		
//		String[] names = new String[2];//.split(key, "_");
//		names[0]=key.substring(0,last);
//		names[1]=key.substring(last+1);
//		
//		Object valueTemp=value;
//		if("between".equalsIgnoreCase(names[1])){
//			String[] values=value.toString().split(",");
//			if(values.length!=2){
//				throw new IllegalArgumentException(value + ": not valid value "); 
//			}
//			valueTemp=values;
//		} else if("in".equalsIgnoreCase(names[1])){
//			valueTemp=value.toString().split(",");
//		}
//		String filedName = names[0];
//		Operation operator = Operation.valueOf(names[1].toUpperCase());
//
//		// 创建searchFilter
//		WhereInfo filter = new WhereInfo(filedName, operator, valueTemp);
//		filter.setDefaultValue(value);
//		filter.setKey(key);
//		return filter;
		
		Object[] result=parseKeyValue(key,value);
		// 创建searchFilter
		WhereInfo filter = new WhereInfo(result[0].toString(), (Operation)result[1], result[2]);
		
		filter.setDefaultValue(value);
		filter.setKey(key);
		return filter;
	}
	
	private static Object[] parseKeyValue(String key,String value){
		int last=key.lastIndexOf('_');
		if(last==-1){
			//默认是等于号
			key=key+"_eq";
			last=key.lastIndexOf('_');
			//throw new IllegalArgumentException(key + " is not a valid whereinfo name");
		}
		
		Object[] names = new Object[3];//.split(key, "_");
		names[0]=key.substring(0,last);
		names[1]=key.substring(last+1);
		
		Object valueTemp=value;
		if("between".equalsIgnoreCase(names[1].toString())){
			String[] values=value.toString().split(",");
			if(values.length!=2){
				throw new IllegalArgumentException(value + ": not valid value "); 
			}
			valueTemp=values;
		} else if("in".equalsIgnoreCase(names[1].toString())){
			valueTemp=value.toString().split(",");
		}
		//String filedName = names[0].toString();
		Operation operator = Operation.valueOf(names[1].toString().toUpperCase());
		
		names[1]=operator;
		names[2]=valueTemp;
		return names;
	}
	
	//在通过key进行设置的时候，作为标志用的
	private boolean flag_temp=false;
	/**
	 * key的值是 对象属性_操作符，例如name_like，age_GT
	 * @param key
	 */
	public void setKey(String key){
		//String[] names=parseKey(key);
		this.key=key;
		
		if(flag_temp){
			Object[] result=parseKeyValue(this.key,this.value.toString());
			this.property=result[0].toString();
			this.op=(Operation)result[1];
			this.value=result[2];
		}
		flag_temp=true;
	}
	public void setValue(String value) {
		this.default_value=value;	
		this.value=value;
		if(flag_temp){
			Object[] result=parseKeyValue(this.key,this.value.toString());
			this.property=result[0].toString();
			this.op=(Operation)result[1];
			this.value=result[2];
		}
		flag_temp=true;
	}
	
	public void setValue(Object value) {
		if(value!=null){
			setValue(value.toString());
		}
		
	}
	
	public String getKey() {
		return key;
	}
	
	/**
	 * 获取前台设置的属性，用于hibernte
	 * @return
	 */
	public String getPropertyToDefault() {
		return property;
	}
	/**
	 * 这是经过处理过后的属性，主要是在like的时候，用来区分大小写
	 * @return
	 */
	public String getProperty() {
//		//在通过反射直接在字段上设置的时候，而不是通过setKey进行设置的时候进行重新格式化
//		if(!flag_temp && this.key!=null){
//			this.setKey(key);
//			this.setValue(this.value.toString());
//		}
		if(this.op==null){
			this.op=Operation.EQ;
		}
		switch(op){
		case LIKE:
			return property;
		case LIKESTART:
			return property;
		case LIKEEND:
			return property;
		case ILIKE:
			return "lower("+property+")";
		case ILIKESTART:
			return "lower("+property+")";
		case ILIKEEND:
			return "lower("+property+")";
		default:
			break;
		}
		return property;
	}
	/**
	 * 把parent.id转换成parent_id
	 * @return
	 */
	public String getPropertyTrans() {
		return getProperty().replaceAll(".", "_");
	}
	
	/**
	 * 获取前台设置的操作符，用于hibernte
	 * @return
	 */
	public Operation getOp() {
		if(op!=null){
			return op;
		}
		return op;
	}
	/**
	 * 这是经过处理过后的操作符，主要是在like的时候，全部转换成sql的like
	 * @return
	 */
	public String getOpSymbol() {
		
		if(op==null){
			return Operation.EQ.getSymbol();
		}
		return op.getSymbol();
	}
	/**
	 * 获取前台设置的值，用于hibernte
	 * @return
	 */
	public Object getValueToDefault() {
		return default_value;
	}
	/**
	 * 这是经过处理过后的属性，主要是在like的时候，全部转换成小写
	 * @return
	 */
	public Object getValue() {
		if(this.op==null){
			this.op=Operation.EQ;
		}
		switch(this.op){
		case LIKE:
			return "%"+value+"%";
		case LIKESTART:
			return value+"%";
		case LIKEEND:
			return "%"+value;
		case ILIKE:
			return "%"+(value.toString()).toLowerCase()+"%";
		case ILIKESTART:
			return (value.toString()).toLowerCase()+"%";
		case ILIKEEND:
			return "%"+(value.toString()).toLowerCase();
//		case BETWEEN:{
//			String[] values=value.toString().split(",");
//			if(values.length!=2){
//				throw new IllegalArgumentException(value + ": not valid value "); 
//			}
//			return values;
//		}
//		case IN:{
//			String[] values=value.toString().split(",");
//			return values;
//		}
		default:
			break;
		}
		return value;
	}
	public void setProperty(String prop) {
		this.property = prop;
	}
	
	/**
	 * 可以使用LT，也可以使用<
	 * @param op
	 */
	public void setOp(String op) {
		if(op==null || "".equals(op)){
			op="=";
		} else if(Operation.getWhereOperationBySymbol(op)==null){
			//
			Operation oper=Operation.getWhereOperation(op);
			if(oper==null){
				throw new RuntimeException("操作符不对,请在WhereOperation类上查找");
			} else {
				this.op=oper;
				return;
			}
		}
		this.op = Operation.getWhereOperationBySymbol(op);
	}
	public void setOpByEnum(Operation op) {
		this.op = op;
	}
	public void setOp(Operation op) {
		this.op = op;
	}
	
	//public boolean is
	
	


}
