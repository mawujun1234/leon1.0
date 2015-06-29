package com.mawujun.baseinfo;

/**
 * 在记录设备生命周期的时候，目标的类型
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public enum TargetType {
	store("仓库"),repair("维修中心"),pole("点位"),workunit("作业单位");
	
	private String name;
	
	TargetType(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}
}
