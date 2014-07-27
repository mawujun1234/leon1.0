package com.mawujun.utils;
public final class M {
public static final class EquipmentProd {
	public static final String id="id";
	public static final String text="text";
	public static final String status="status";
	public static final String level="level";
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class parent {
		public static final String id="parent.id";
		public static final String text="parent.text";
		public static final String status="parent.status";
		public static final String level="parent.level";
		public static final String parent_id="parent.parent_id";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "parent";
	    }
	}
	public static final String parent_id="parent_id";
}
public static final class EquipmentSubtype {
	public static final String id="id";
	public static final String text="text";
	public static final String status="status";
	public static final String level="level";
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class parent {
		public static final String id="parent.id";
		public static final String text="parent.text";
		public static final String status="parent.status";
		public static final String level="parent.level";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "parent";
	    }
	}
	public static final String parent_id="parent_id";
}
public static final class EquipmentType {
	public static final String id="id";
	public static final String text="text";
	public static final String status="status";
	public static final String level="level";
}
public static final class PropertyConfig {
	public static final String subjectName="subjectName";
	public static final String property="property";
	public static final String label="label";
	public static final String showModel="showModel";
	public static final String id="id";
}
public static final class DataRole {
	public static final String text="text";
	public static final String leaf="leaf";
	public static final String memo="memo";
	public static final String parentId="parentId";
	public static final String cls="cls";
	public static final String iconCls="iconCls";
	public static final String checked="checked";
	public static final String id="id";
}
public static final class FunRole {
	public static final String text="text";
	public static final String leaf="leaf";
	public static final String memo="memo";
	public static final String parentId="parentId";
	public static final String cls="cls";
	public static final String iconCls="iconCls";
	public static final String checked="checked";
	public static final String id="id";
}
public static final class Navigation {
	public static final String text="text";
	public static final String link="link";
	public static final String parentId="parentId";
	public static final String leaf="leaf";
	public static final String memo="memo";
	public static final String reportCode="reportCode";
	public static final String cls="cls";
	public static final String iconCls="iconCls";
	public static final String checked="checked";
	public static final String id="id";
}
public static final class User {
	public static final String username="username";
	public static final String password="password";
	public static final String name="name";
	public static final String phone="phone";
	public static final String email="email";
	public static final String address="address";
	public static final String type="type";
	public static final String loginDate="loginDate";
	public static final String id="id";
}
}
