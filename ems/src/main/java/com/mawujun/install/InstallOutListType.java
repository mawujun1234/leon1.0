package com.mawujun.install;

/**
 * 领用出去的设备的用途，如果领出去又返回就变成借用，如果领出去没有返回就是借用了
 * 如果任务提交了，那就把该设备标记为领用，否则为借用
 * 参考BorrowListType
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public enum InstallOutListType {
	installout("领用"),
	borrow("借用");
	
	private String name;
	InstallOutListType(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
