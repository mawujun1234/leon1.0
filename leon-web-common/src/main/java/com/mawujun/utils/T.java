package com.mawujun.utils;
public final class T {
public static final class leon_constant {
	public static final String code="code";
	public static final String text="text";
	public static final String remark="remark";
	/**
	* 访问外键的列名，用于sql的时候，返回的是constantType_id
	*/
	public static final String constantType_id="constantType_id";
	public static final String discriminator="discriminator";
	public static final String id="id";
}
public static final class leon_constantitem {
	public static final String code="code";
	public static final String text="text";
	public static final String remark="remark";
	public static final String ordering="ordering";
	/**
	* 访问外键的列名，用于sql的时候，返回的是constant_id
	*/
	public static final String constant_id="constant_id";
	public static final String discriminator="discriminator";
	public static final String id="id";
}
public static final class leon_constanttype {
	public static final String text="text";
	public static final String remark="remark";
	public static final String discriminator="discriminator";
	public static final String id="id";
}
public static final class leon_desktopconfig {
	public static final String id="id";
	public static final String wallpaper="wallpaper";
	public static final String wallpaperStretch="wallpaperStretch";
	public static final String menubarDock="menubarDock";
	public static final String taskbarDock="taskbarDock";
	public static final String taskbarAutoHide="taskbarAutoHide";
	public static final String menubarAutoHide="menubarAutoHide";
	public static final String authMsg="authMsg";
}
public static final class leon_quickstart {
	 /**
	 * 这个是复合主键。里面的是复合组件的组成列的列名
	 */
	public static final class Id {
		public static final String user_id="user_id";
		public static final String menuItem_id="menuItem_id";
			
	}
}
public static final class leon_fun {
	public static final String id="id";
	public static final String text="text";
	public static final String isEnable="isEnable";
	public static final String url="url";
	public static final String helpContent="helpContent";
	public static final String roleNames="roleNames";
	/**
	* 访问外键的列名，用于sql的时候，返回的是bussinessType_id
	*/
	public static final String bussinessType_id="bussinessType_id";
}
public static final class leon_propertyconfig {
	public static final String subjectName="subjectName";
	public static final String property="property";
	public static final String label="label";
	public static final String showModel="showModel";
	public static final String id="id";
}
public static final class leon_group {
	public static final String name="name";
	/**
	* 访问外键的列名，用于sql的时候，返回的是parent_id
	*/
	public static final String parent_id="parent_id";
	public static final String id="id";
}
public static final class leon_group_role {
	 /**
	 * 这个是复合主键。里面的是复合组件的组成列的列名
	 */
	public static final class GroupRolePK {
		public static final String role_id="role_id";
		public static final String group_id="group_id";
			
	}
	public static final String createDate="createDate";
}
public static final class leon_group_user {
	 /**
	 * 这个是复合主键。里面的是复合组件的组成列的列名
	 */
	public static final class GroupUserPK {
		public static final String user_id="user_id";
		public static final String group_id="group_id";
			
	}
	public static final String createDate="createDate";
}
public static final class leon_help {
	public static final String name="name";
	public static final String path="path";
	public static final String funId="funId";
	public static final String id="id";
}
public static final class leon_menu {
	public static final String default_id="default_id";
	public static final String text="text";
	public static final String id="id";
}
public static final class leon_menuitem {
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
	* 访问外键的列名，用于sql的时候，返回的是menu_id
	*/
	public static final String menu_id="menu_id";
	/**
	* 访问外键的列名，用于sql的时候，返回的是fun_id
	*/
	public static final String fun_id="fun_id";
	/**
	* 访问外键的列名，用于sql的时候，返回的是parent_id
	*/
	public static final String parent_id="parent_id";
	public static final String id="id";
}
public static final class leon_org {
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
	* 访问外键的列名，用于sql的时候，返回的是orgType_id
	*/
	public static final String orgType_id="orgType_id";
	public static final String id="id";
}
public static final class leon_orgdimenssion {
	public static final String id="id";
	public static final String name="name";
}
public static final class leon_orgrelation {
	 /**
	 * 这个是复合主键。里面的是复合组件的组成列的列名
	 */
	public static final class Id {
		public static final String parent_id="parent_id";
		public static final String child_id="child_id";
		public static final String orgDimenssion_id="orgDimenssion_id";
			
	}
	/**
	* 访问外键的列名，用于sql的时候，返回的是parent_id
	*/
	public static final String parent_id="parent_id";
	/**
	* 访问外键的列名，用于sql的时候，返回的是child_id
	*/
	public static final String child_id="child_id";
	/**
	* 访问外键的列名，用于sql的时候，返回的是orgDimenssion_id
	*/
	public static final String orgDimenssion_id="orgDimenssion_id";
}
public static final class leon_orgtype {
	public static final String id="id";
	public static final String name="name";
	public static final String iconCls="iconCls";
}
public static final class panera_country {
	public static final String name_en="name_en";
	public static final String name="name";
	public static final String continent="continent";
	public static final String continent_name="continent_name";
	public static final String id="id";
}
public static final class panera_contact {
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
public static final class panera_customer {
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
public static final class panera_followup {
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
public static final class panera_customerproperty {
	public static final String name="name";
	public static final String description="description";
	public static final String id="id";
}
public static final class panera_customersource {
	public static final String name="name";
	public static final String description="description";
	public static final String id="id";
}
public static final class leon_parameter {
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
public static final class leon_parameter_subject {
	public static final String parameterValue="parameterValue";
	 /**
	 * 这个是复合主键。里面的是复合组件的组成列的列名
	 */
	public static final class Id {
		public static final String subject_id="subject_id";
		public static final String parameter_id="parameter_id";
		public static final String subjectType="subjectType";
			
	}
}
public static final class leon_role {
	public static final String name="name";
	public static final String description="description";
	public static final String roleEnum="roleEnum";
	/**
	* 访问外键的列名，用于sql的时候，返回的是category_id
	*/
	public static final String category_id="category_id";
	public static final String icon="icon";
	public static final String iconCls="iconCls";
	public static final String leaf="leaf";
	public static final String checked="checked";
	public static final String expanded="expanded";
	public static final String edit="edit";
	public static final String id="id";
}
public static final class leon_role_fun {
	/**
	* 访问外键的列名，用于sql的时候，返回的是role_id
	*/
	public static final String role_id="role_id";
	/**
	* 访问外键的列名，用于sql的时候，返回的是fun_id
	*/
	public static final String fun_id="fun_id";
	public static final String permissionEnum="permissionEnum";
	public static final String createDate="createDate";
	public static final String id="id";
}
public static final class leon_switchuser {
	/**
	* 访问外键的列名，用于sql的时候，返回的是master_id
	*/
	public static final String master_id="master_id";
	/**
	* 访问外键的列名，用于sql的时候，返回的是switchUser_id
	*/
	public static final String switchUser_id="switchUser_id";
	public static final String id="id";
}
public static final class leon_user {
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
public static final class leon_user_role {
	 /**
	 * 这个是复合主键。里面的是复合组件的组成列的列名
	 */
	public static final class UserRolePK {
		public static final String user_id="user_id";
		public static final String role_id="role_id";
			
	}
	public static final String createDate="createDate";
}
}
