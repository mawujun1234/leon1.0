package com.mawujun.utils;
public final class M {
public static final class Constant {
	public static final String code="code";
	public static final String text="text";
	public static final String remark="remark";
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class constantType {
		public static final String text="constantType.text";
		public static final String remark="constantType.remark";
		public static final String discriminator="constantType.discriminator";
		public static final String id="constantType.id";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "constantType";
	    }
	}
	/**
	* 这里一般是集合属性，返回的是constantItemes
	*/
	public static final String constantItemes="constantItemes";
	public static final String discriminator="discriminator";
	public static final String id="id";
}
public static final class ConstantItem {
	public static final String code="code";
	public static final String text="text";
	public static final String remark="remark";
	public static final String ordering="ordering";
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class constant {
		public static final String code="constant.code";
		public static final String text="constant.text";
		public static final String remark="constant.remark";
		public static final String discriminator="constant.discriminator";
		public static final String id="constant.id";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "constant";
	    }
	}
	public static final String discriminator="discriminator";
	public static final String id="id";
}
public static final class ConstantType {
	public static final String text="text";
	public static final String remark="remark";
	/**
	* 这里一般是集合属性，返回的是constants
	*/
	public static final String constants="constants";
	public static final String discriminator="discriminator";
	public static final String id="id";
}
public static final class DesktopConfig {
	public static final String id="id";
	public static final String wallpaper="wallpaper";
	public static final String wallpaperStretch="wallpaperStretch";
	public static final String menubarDock="menubarDock";
	public static final String taskbarDock="taskbarDock";
	public static final String taskbarAutoHide="taskbarAutoHide";
	public static final String menubarAutoHide="menubarAutoHide";
	public static final String authMsg="authMsg";
	/**
	* 这里一般是集合属性，返回的是switchUsers
	*/
	public static final String switchUsers="switchUsers";
	/**
	* 这里一般是集合属性，返回的是menuItems
	*/
	public static final String menuItems="menuItems";
	/**
	* 这里一般是集合属性，返回的是quickstarts
	*/
	public static final String quickstarts="quickstarts";
	/**
	* 这里一般是集合属性，返回的是autostarts
	*/
	public static final String autostarts="autostarts";
}
public static final class QuickStart {
	 /**
	 * 返回复合主键的组成，，以对象关联的方式:id
	 */
	public static final class id {
		public static final String userId="id.userId";
		public static final String menuItemId="id.menuItemId";
			
	    /**
	    * 返回的是复合主键的属性名称，主要用于属性过滤或以id来查询的时候
	    */
	    public static String name(){ 
		    return "id";
	    }
	}
}
public static final class Fun {
	public static final String id="id";
	public static final String text="text";
	public static final String isEnable="isEnable";
	public static final String url="url";
	public static final String helpContent="helpContent";
	public static final String roleNames="roleNames";
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class bussinessType {
		public static final String code="bussinessType.code";
		public static final String constantCode="bussinessType.constantCode";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "bussinessType";
	    }
	}
}
public static final class PropertyConfig {
	public static final String subjectName="subjectName";
	public static final String property="property";
	public static final String label="label";
	public static final String showModel="showModel";
	public static final String id="id";
}
public static final class Group {
	public static final String name="name";
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class parent {
		public static final String name="parent.name";
		public static final String id="parent.id";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "parent";
	    }
	}
	/**
	* 这里一般是集合属性，返回的是children
	*/
	public static final String children="children";
	public static final String id="id";
}
public static final class GroupRole {
	 /**
	 * 返回复合主键的组成，，以对象关联的方式:id
	 */
	public static final class id {
		public static final String roleId="id.roleId";
		public static final String groupId="id.groupId";
			
	    /**
	    * 返回的是复合主键的属性名称，主要用于属性过滤或以id来查询的时候
	    */
	    public static String name(){ 
		    return "id";
	    }
	}
	public static final String createDate="createDate";
}
public static final class GroupUser {
	 /**
	 * 返回复合主键的组成，，以对象关联的方式:id
	 */
	public static final class id {
		public static final String userId="id.userId";
		public static final String groupId="id.groupId";
			
	    /**
	    * 返回的是复合主键的属性名称，主要用于属性过滤或以id来查询的时候
	    */
	    public static String name(){ 
		    return "id";
	    }
	}
	public static final String createDate="createDate";
}
public static final class Help {
	public static final String name="name";
	public static final String path="path";
	public static final String funId="funId";
	public static final String id="id";
}
public static final class Menu {
	public static final String default_id="default_id";
	public static final String text="text";
	public static final String id="id";
}
public static final class MenuItem {
	public static final String code="code";
	public static final String text="text";
	public static final String javaClass="javaClass";
	public static final String scripts="scripts";
	public static final String iconCls="iconCls";
	public static final String iconCls32="iconCls32";
	public static final String reportCode="reportCode";
	public static final String autostart="autostart";
	public static final String leaf="leaf";
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class menu {
		public static final String default_id="menu.default_id";
		public static final String text="menu.text";
		public static final String id="menu.id";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "menu";
	    }
	}
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class fun {
		public static final String id="fun.id";
		public static final String text="fun.text";
		public static final String isEnable="fun.isEnable";
		public static final String url="fun.url";
		public static final String helpContent="fun.helpContent";
		public static final String roleNames="fun.roleNames";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "fun";
	    }
	}
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class parent {
		public static final String code="parent.code";
		public static final String text="parent.text";
		public static final String javaClass="parent.javaClass";
		public static final String scripts="parent.scripts";
		public static final String iconCls="parent.iconCls";
		public static final String iconCls32="parent.iconCls32";
		public static final String reportCode="parent.reportCode";
		public static final String autostart="parent.autostart";
		public static final String leaf="parent.leaf";
		public static final String id="parent.id";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "parent";
	    }
	}
	public static final String id="id";
}
public static final class Org {
	public static final String name="name";
	public static final String code="code";
	public static final String reportCode="reportCode";
	public static final String phonenumber="phonenumber";
	public static final String fax="fax";
	public static final String address="address";
	public static final String postalcode="postalcode";
	public static final String corporation="corporation";
	public static final String email="email";
	public static final String web="web";
	public static final String introduction="introduction";
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class orgType {
		public static final String id="orgType.id";
		public static final String name="orgType.name";
		public static final String iconCls="orgType.iconCls";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "orgType";
	    }
	}
	public static final String id="id";
}
public static final class OrgDimenssion {
	public static final String id="id";
	public static final String name="name";
}
public static final class OrgRelation {
	 /**
	 * 返回复合主键的组成，，以对象关联的方式:id
	 */
	public static final class id {
		public static final String parent_id="id.parent_id";
		public static final String child_id="id.child_id";
		public static final String orgDimenssion_id="id.orgDimenssion_id";
			
	    /**
	    * 返回的是复合主键的属性名称，主要用于属性过滤或以id来查询的时候
	    */
	    public static String name(){ 
		    return "id";
	    }
	}
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class parent {
		public static final String name="parent.name";
		public static final String code="parent.code";
		public static final String reportCode="parent.reportCode";
		public static final String phonenumber="parent.phonenumber";
		public static final String fax="parent.fax";
		public static final String address="parent.address";
		public static final String postalcode="parent.postalcode";
		public static final String corporation="parent.corporation";
		public static final String email="parent.email";
		public static final String web="parent.web";
		public static final String introduction="parent.introduction";
		public static final String id="parent.id";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "parent";
	    }
	}
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class child {
		public static final String name="child.name";
		public static final String code="child.code";
		public static final String reportCode="child.reportCode";
		public static final String phonenumber="child.phonenumber";
		public static final String fax="child.fax";
		public static final String address="child.address";
		public static final String postalcode="child.postalcode";
		public static final String corporation="child.corporation";
		public static final String email="child.email";
		public static final String web="child.web";
		public static final String introduction="child.introduction";
		public static final String id="child.id";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "child";
	    }
	}
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class orgDimenssion {
		public static final String id="orgDimenssion.id";
		public static final String name="orgDimenssion.name";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "orgDimenssion";
	    }
	}
}
public static final class OrgType {
	public static final String id="id";
	public static final String name="name";
	public static final String iconCls="iconCls";
}
public static final class Country {
	public static final String name_en="name_en";
	public static final String name="name";
	public static final String continent="continent";
	public static final String continent_name="continent_name";
	public static final String id="id";
}
public static final class Contact {
	public static final String name="name";
	public static final String position="position";
	public static final String email="email";
	public static final String phone="phone";
	public static final String fax="fax";
	public static final String mobile="mobile";
	public static final String chatNum="chatNum";
	public static final String isDefault="isDefault";
	public static final String customer_id="customer_id";
	public static final String id="id";
}
public static final class Customer {
	public static final String code="code";
	public static final String name="name";
	public static final String customerSource_id="customerSource_id";
	public static final String customerSource_name="customerSource_name";
	public static final String customerProperty_id="customerProperty_id";
	public static final String customerProperty_name="customerProperty_name";
	public static final String continent_id="continent_id";
	public static final String country_id="country_id";
	public static final String country_name="country_name";
	public static final String address="address";
	public static final String website="website";
	public static final String businessPhase_id="businessPhase_id";
	public static final String businessPhase_name="businessPhase_name";
	public static final String followupNum="followupNum";
	public static final String inquiryDate="inquiryDate";
	public static final String inquiryContent="inquiryContent";
	public static final String deleted="deleted";
	public static final String createDate="createDate";
	public static final String star="star";
	public static final String expYear="expYear";
	public static final String proportion="proportion";
	public static final String customerType="customerType";
	public static final String empNum="empNum";
	public static final String buyMoney="buyMoney";
	public static final String quality="quality";
	public static final String price="price";
	public static final String moq="moq";
	public static final String paymentTerms="paymentTerms";
	public static final String contact_id="contact_id";
	public static final String contact_name="contact_name";
	public static final String contact_position="contact_position";
	public static final String contact_phone="contact_phone";
	public static final String contact_mobile="contact_mobile";
	public static final String contact_chatNum="contact_chatNum";
	public static final String contact_fax="contact_fax";
	public static final String contact_email="contact_email";
	public static final String contact_isDefault="contact_isDefault";
	public static final String id="id";
}
public static final class Followup {
	public static final String createDate="createDate";
	public static final String method="method";
	public static final String content="content";
	public static final String feedbackDate="feedbackDate";
	public static final String feedbackContetnt="feedbackContetnt";
	public static final String nextDate="nextDate";
	public static final String nextContent="nextContent";
	public static final String nextHandled="nextHandled";
	public static final String customer_id="customer_id";
	public static final String customer_name="customer_name";
	public static final String id="id";
}
public static final class CustomerProperty {
	public static final String name="name";
	public static final String description="description";
	public static final String id="id";
}
public static final class CustomerSource {
	public static final String name="name";
	public static final String description="description";
	public static final String id="id";
}
public static final class Parameter {
	public static final String id="id";
	public static final String name="name";
	public static final String description="description";
	public static final String showModel="showModel";
	public static final String valueEnum="valueEnum";
	public static final String content="content";
	public static final String subjects="subjects";
	public static final String sort="sort";
	public static final String validation="validation";
}
public static final class ParameterSubject {
	public static final String parameterValue="parameterValue";
	 /**
	 * 返回复合主键的组成，，以对象关联的方式:id
	 */
	public static final class id {
		public static final String subjectId="id.subjectId";
		public static final String parameterId="id.parameterId";
		public static final String subjectType="id.subjectType";
			
	    /**
	    * 返回的是复合主键的属性名称，主要用于属性过滤或以id来查询的时候
	    */
	    public static String name(){ 
		    return "id";
	    }
	}
}
public static final class Role {
	public static final String name="name";
	public static final String description="description";
	public static final String roleEnum="roleEnum";
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class category {
		public static final String name="category.name";
		public static final String description="category.description";
		public static final String roleEnum="category.roleEnum";
		public static final String icon="category.icon";
		public static final String iconCls="category.iconCls";
		public static final String leaf="category.leaf";
		public static final String checked="category.checked";
		public static final String expanded="category.expanded";
		public static final String edit="category.edit";
		public static final String id="category.id";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "category";
	    }
	}
	/**
	* 这里一般是集合属性，返回的是funes
	*/
	public static final String funes="funes";
	public static final String icon="icon";
	public static final String iconCls="iconCls";
	public static final String leaf="leaf";
	public static final String checked="checked";
	public static final String expanded="expanded";
	public static final String edit="edit";
	public static final String id="id";
}
public static final class RoleFun {
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class role {
		public static final String name="role.name";
		public static final String description="role.description";
		public static final String roleEnum="role.roleEnum";
		public static final String icon="role.icon";
		public static final String iconCls="role.iconCls";
		public static final String leaf="role.leaf";
		public static final String checked="role.checked";
		public static final String expanded="role.expanded";
		public static final String edit="role.edit";
		public static final String id="role.id";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "role";
	    }
	}
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class fun {
		public static final String id="fun.id";
		public static final String text="fun.text";
		public static final String isEnable="fun.isEnable";
		public static final String url="fun.url";
		public static final String helpContent="fun.helpContent";
		public static final String roleNames="fun.roleNames";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "fun";
	    }
	}
	public static final String permissionEnum="permissionEnum";
	public static final String createDate="createDate";
	public static final String id="id";
}
public static final class SwitchUser {
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class master {
		public static final String loginName="master.loginName";
		public static final String password="master.password";
		public static final String name="master.name";
		public static final String deleted="master.deleted";
		public static final String deletedDate="master.deletedDate";
		public static final String enable="master.enable";
		public static final String locked="master.locked";
		public static final String createDate="master.createDate";
		public static final String expireDate="master.expireDate";
		public static final String lastLoginDate="master.lastLoginDate";
		public static final String lastIp="master.lastIp";
		public static final String id="master.id";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "master";
	    }
	}
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class switchUser {
		public static final String loginName="switchUser.loginName";
		public static final String password="switchUser.password";
		public static final String name="switchUser.name";
		public static final String deleted="switchUser.deleted";
		public static final String deletedDate="switchUser.deletedDate";
		public static final String enable="switchUser.enable";
		public static final String locked="switchUser.locked";
		public static final String createDate="switchUser.createDate";
		public static final String expireDate="switchUser.expireDate";
		public static final String lastLoginDate="switchUser.lastLoginDate";
		public static final String lastIp="switchUser.lastIp";
		public static final String id="switchUser.id";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "switchUser";
	    }
	}
	public static final String id="id";
}
public static final class User {
	public static final String loginName="loginName";
	public static final String password="password";
	public static final String name="name";
	public static final String deleted="deleted";
	public static final String deletedDate="deletedDate";
	public static final String enable="enable";
	public static final String locked="locked";
	public static final String createDate="createDate";
	public static final String expireDate="expireDate";
	public static final String lastLoginDate="lastLoginDate";
	public static final String lastIp="lastIp";
	public static final String id="id";
}
public static final class UserRole {
	 /**
	 * 返回复合主键的组成，，以对象关联的方式:id
	 */
	public static final class id {
		public static final String userId="id.userId";
		public static final String roleId="id.roleId";
			
	    /**
	    * 返回的是复合主键的属性名称，主要用于属性过滤或以id来查询的时候
	    */
	    public static String name(){ 
		    return "id";
	    }
	}
	public static final String createDate="createDate";
}
}
