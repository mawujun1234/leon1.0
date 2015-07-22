package com.mawujun.install;

/**
 * 返回的设备是领用出去，没使用就马上返回 ，还是从点位上拆下来返回的设备
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public enum InstallInListType {
	installout("领用返回"),
	takedown("拆回入库");
	private String name;
	InstallInListType(String name) {
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
