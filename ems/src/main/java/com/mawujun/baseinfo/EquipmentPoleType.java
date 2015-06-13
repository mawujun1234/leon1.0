package com.mawujun.baseinfo;

/**
 * 安装到杆位上的类型：安装上去的，维修替换上去的
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public enum EquipmentPoleType {
	install("安装");//,repair("维修");
	private String name;
	
	EquipmentPoleType(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}

}
