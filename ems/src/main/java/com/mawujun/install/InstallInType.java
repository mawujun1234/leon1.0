package com.mawujun.install;

/**
 * 返库的时候，是坏件返库 还是好件返库
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public enum InstallInType {
	good("好件入库"),bad("坏件入库");
	private String name;
	InstallInType(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
