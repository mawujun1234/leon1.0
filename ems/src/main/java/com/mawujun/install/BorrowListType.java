package com.mawujun.install;

/**
 * 借用出去的设备的用途，如果借出去又返回的还是i借用，如果借出去没有返回就是领用了
 * 如果任务提交了，那就把该设备标记为领用，否则为借用
 * 参考InstallOutListType
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public enum BorrowListType {
	installout("领用"),borrow("借用");
	
	private String name;
	BorrowListType(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
