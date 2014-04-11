package com.mawujun.utils;
public final class T {
public static final class leon_Constant {
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
public static final class leon_ConstantItem {
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
public static final class leon_ConstantType {
	public static final String text="text";
	public static final String remark="remark";
	public static final String discriminator="discriminator";
	public static final String id="id";
}
public static final class leon_DesktopConfig {
	public static final String id="id";
	public static final String wallpaper="wallpaper";
	public static final String wallpaperStretch="wallpaperStretch";
	public static final String menubarDock="menubarDock";
	public static final String taskbarDock="taskbarDock";
	public static final String taskbarAutoHide="taskbarAutoHide";
	public static final String menubarAutoHide="menubarAutoHide";
	public static final String authMsg="authMsg";
}
public static final class leon_QuickStart {
	 /**
	 * 这个是复合主键。里面的是复合组件的组成列的列名
	 */
	public static final class Id {
		public static final String user_id="user_id";
		public static final String menuItem_id="menuItem_id";
			
	}
}
public static final class leon_Fun {
	public static final String code="code";
	public static final String elementId="elementId";
	public static final String text="text";
	public static final String isEnable="isEnable";
	public static final String url="url";
	public static final String helpContent="helpContent";
	public static final String roleNames="roleNames";
	/**
	* 访问外键的列名，用于sql的时候，返回的是bussinessType_id
	*/
	public static final String bussinessType_id="bussinessType_id";
	public static final String funEnum="funEnum";
	/**
	* 访问外键的列名，用于sql的时候，返回的是parent_id
	*/
	public static final String parent_id="parent_id";
	public static final String icon="icon";
	public static final String iconCls="iconCls";
	public static final String leaf="leaf";
	public static final String checked="checked";
	public static final String expanded="expanded";
	public static final String edit="edit";
	public static final String id="id";
}
public static final class leon_PropertyConfig {
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
public static final class leon_menuItem {
	public static final String code="code";
	public static final String text="text";
	public static final String javaClass="javaClass";
	public static final String scripts="scripts";
	public static final String iconCls="iconCls";
	public static final String iconCls32="iconCls32";
	public static final String reportCode="reportCode";
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
public static final class leon_Parameter {
	public static final String id="id";
	public static final String name="name";
	public static final String desc="desc";
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
public static final class leon_Role {
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
public static final class leon_Role_Fun {
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
public static final class leon_SwitchUser {
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
public static final class leon_User {
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
