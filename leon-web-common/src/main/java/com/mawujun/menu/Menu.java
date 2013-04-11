package com.mawujun.menu;

import com.mawujun.repository.idEntity.UUIDEntity;

public class Menu extends UUIDEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String text;
	private String pluginUrl;
	private String scripts;
	private String iconCls;
	private String reportCode;//等级关系代码

}
