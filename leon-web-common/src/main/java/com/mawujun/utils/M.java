package com.mawujun.utils;
public final class M {
public static final class Constant {
	public static final String serialVersionUID="serialVersionUID";
	public static final String code="code";
	public static final String text="text";
	public static final String remark="remark";
	/**
	* 访问关联类的id，用于hql的时候，返回的是constantType.id
	*/
	public static final String constantType_id="constantType.id";
	/**
	* 这里一般是集合属性，返回的是constantItemes
	*/
	public static final String constantItemes="constantItemes";
	public static final String discriminator="discriminator";
}
public static final class ConstantItem {
	public static final String serialVersionUID="serialVersionUID";
	public static final String code="code";
	public static final String text="text";
	public static final String remark="remark";
	public static final String ordering="ordering";
	/**
	* 访问关联类的id，用于hql的时候，返回的是constant.id
	*/
	public static final String constant_id="constant.id";
	public static final String discriminator="discriminator";
}
public static final class ConstantType {
	public static final String serialVersionUID="serialVersionUID";
	public static final String text="text";
	public static final String remark="remark";
	/**
	* 这里一般是集合属性，返回的是constants
	*/
	public static final String constants="constants";
	public static final String discriminator="discriminator";
}
public static final class DesktopConfig {
	public static final String serialVersionUID="serialVersionUID";
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
}
public static final class QuickStart {
	public static final String serialVersionUID="serialVersionUID";
	/**
	* 访问关联类的id，用于hql的时候，返回的是id.id
	*/
	public static final String id_id="id.id";
}
public static final class Fun {
	public static final String serialVersionUID="serialVersionUID";
	public static final String code="code";
	public static final String elementId="elementId";
	public static final String text="text";
	public static final String isEnable="isEnable";
	public static final String url="url";
	public static final String helpContent="helpContent";
	public static final String roleNames="roleNames";
	/**
	* 访问关联类的id，用于hql的时候，返回的是bussinessType.id
	*/
	public static final String bussinessType_id="bussinessType.id";
	/**
	* 访问关联类的id，用于hql的时候，返回的是funEnum.id
	*/
	public static final String funEnum_id="funEnum.id";
	/**
	* 访问关联类的id，用于hql的时候，返回的是parent.id
	*/
	public static final String parent_id="parent.id";
}
public static final class PropertyConfig {
	public static final String serialVersionUID="serialVersionUID";
	public static final String subjectName="subjectName";
	public static final String property="property";
	public static final String label="label";
	public static final String showModel="showModel";
}
public static final class Group {
	public static final String serialVersionUID="serialVersionUID";
	public static final String name="name";
	/**
	* 访问关联类的id，用于hql的时候，返回的是parent.id
	*/
	public static final String parent_id="parent.id";
	/**
	* 这里一般是集合属性，返回的是children
	*/
	public static final String children="children";
}
public static final class GroupRole {
	public static final String serialVersionUID="serialVersionUID";
	/**
	* 访问关联类的id，用于hql的时候，返回的是id.id
	*/
	public static final String id_id="id.id";
	public static final String createDate="createDate";
}
public static final class GroupUser {
	/**
	* 访问关联类的id，用于hql的时候，返回的是id.id
	*/
	public static final String id_id="id.id";
	public static final String createDate="createDate";
}
public static final class Help {
	public static final String serialVersionUID="serialVersionUID";
	public static final String name="name";
	public static final String path="path";
	public static final String funId="funId";
}
public static final class Menu {
	public static final String default_id="default_id";
	public static final String serialVersionUID="serialVersionUID";
	public static final String text="text";
}
public static final class MenuItem {
	public static final String serialVersionUID="serialVersionUID";
	public static final String code="code";
	public static final String text="text";
	public static final String javaClass="javaClass";
	public static final String scripts="scripts";
	public static final String iconCls="iconCls";
	public static final String iconCls32="iconCls32";
	public static final String reportCode="reportCode";
	public static final String leaf="leaf";
	/**
	* 访问关联类的id，用于hql的时候，返回的是menu.id
	*/
	public static final String menu_id="menu.id";
	/**
	* 访问关联类的id，用于hql的时候，返回的是fun.id
	*/
	public static final String fun_id="fun.id";
	/**
	* 访问关联类的id，用于hql的时候，返回的是parent.id
	*/
	public static final String parent_id="parent.id";
}
public static final class Role {
	public static final String serialVersionUID="serialVersionUID";
	public static final String name="name";
	public static final String description="description";
	/**
	* 访问关联类的id，用于hql的时候，返回的是roleEnum.id
	*/
	public static final String roleEnum_id="roleEnum.id";
	/**
	* 访问关联类的id，用于hql的时候，返回的是category.id
	*/
	public static final String category_id="category.id";
	/**
	* 这里一般是集合属性，返回的是funes
	*/
	public static final String funes="funes";
}
public static final class RoleFun {
	public static final String serialVersionUID="serialVersionUID";
	/**
	* 访问关联类的id，用于hql的时候，返回的是role.id
	*/
	public static final String role_id="role.id";
	/**
	* 访问关联类的id，用于hql的时候，返回的是fun.id
	*/
	public static final String fun_id="fun.id";
	/**
	* 访问关联类的id，用于hql的时候，返回的是permissionEnum.id
	*/
	public static final String permissionEnum_id="permissionEnum.id";
	public static final String createDate="createDate";
}
public static final class SwitchUser {
	public static final String serialVersionUID="serialVersionUID";
	/**
	* 访问关联类的id，用于hql的时候，返回的是master.id
	*/
	public static final String master_id="master.id";
	/**
	* 访问关联类的id，用于hql的时候，返回的是switchUser.id
	*/
	public static final String switchUser_id="switchUser.id";
}
public static final class User {
	public static final String serialVersionUID="serialVersionUID";
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
}
public static final class UserRole {
	/**
	* 访问关联类的id，用于hql的时候，返回的是id.id
	*/
	public static final String id_id="id.id";
	public static final String createDate="createDate";
}
}
