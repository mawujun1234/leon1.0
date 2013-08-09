package com.mawujun.generator.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mawujun.generator.util.typemapping.ActionScriptDataTypesUtils;

public class ExtjsDataTypesUtils {
	private final static Map<String, String> preferredAsTypeForJavaType = new HashMap<String, String>();

	public static String getPreferredAsType(String javaType) {
		String result = preferredAsTypeForJavaType.get(javaType);
		if (result == null) {
			result = javaType;
		}
		return result;
	}

	
/**
 * Extjs的基本来行有如下的数据类型
 *     auto (Default, implies no conversion)
    string
    int
    float
    boolean
    date
 */
	static {

		preferredAsTypeForJavaType.put("Short", "int");
		preferredAsTypeForJavaType.put("java.lang.Short", "int");
		preferredAsTypeForJavaType.put("short", "int");
		
		preferredAsTypeForJavaType.put("Integer", "int");
		preferredAsTypeForJavaType.put("java.lang.Integer", "int");
		preferredAsTypeForJavaType.put("int", "int");
		
		preferredAsTypeForJavaType.put("Long", "int");
		preferredAsTypeForJavaType.put("java.lang.Long", "int");
		preferredAsTypeForJavaType.put("long", "int");
		
		preferredAsTypeForJavaType.put("Float", "float");
		preferredAsTypeForJavaType.put("java.lang.Float", "float");
		preferredAsTypeForJavaType.put("float", "float");
		
		preferredAsTypeForJavaType.put("Double", "float");
		preferredAsTypeForJavaType.put("java.lang.Double", "float");
		preferredAsTypeForJavaType.put("double", "float");
		
		preferredAsTypeForJavaType.put("Byte", "int");
		preferredAsTypeForJavaType.put("java.lang.Byte", "int");
		preferredAsTypeForJavaType.put("byte", "int");
		
		preferredAsTypeForJavaType.put("java.math.BigDecimal", "float");

		preferredAsTypeForJavaType.put("Boolean", "boolean");
		preferredAsTypeForJavaType.put("java.lang.Boolean", "boolean");
		preferredAsTypeForJavaType.put("boolen", "boolean");

		preferredAsTypeForJavaType.put("char", "string");
		preferredAsTypeForJavaType.put("char[]", "string");
		preferredAsTypeForJavaType.put("java.lang.String", "string");
		preferredAsTypeForJavaType.put("java.sql.Clob", "string");

		preferredAsTypeForJavaType.put("byte[]", "auto ");
		preferredAsTypeForJavaType.put("java.sql.Blob", "auto ");
		preferredAsTypeForJavaType.put("java.sql.Array", "auto");
		preferredAsTypeForJavaType.put("java.lang.reflect.Array", "auto");
		preferredAsTypeForJavaType.put("java.util.Collection", "auto");
		preferredAsTypeForJavaType.put("java.util.List", "auto");
		preferredAsTypeForJavaType.put("java.util.ArrayList", "auto");
		preferredAsTypeForJavaType.put("java.util.ArrayList", "auto");
		
		preferredAsTypeForJavaType.put("java.util.Set", "Object");
		preferredAsTypeForJavaType.put("java.util.HashSet", "Object");
		preferredAsTypeForJavaType.put("java.util.Map", "Object");
		preferredAsTypeForJavaType.put("java.util.HashMap", "Object");

		preferredAsTypeForJavaType.put("java.sql.Date", "date");
		preferredAsTypeForJavaType.put("java.sql.Time", "date");
		preferredAsTypeForJavaType.put("java.util.Date", "date");
		preferredAsTypeForJavaType.put("java.sql.Timestamp", "date");

	}

	public static void main(String[] args) {
		String bb = ExtjsDataTypesUtils.getPreferredAsType(List.class.getName());
		System.out.println(bb);
	}
}
