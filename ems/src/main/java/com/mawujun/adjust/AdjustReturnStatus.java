package com.mawujun.adjust;
/**
 * neednot("不必归还"),noreturn("未归还"),partreturn("部分归还"),allreturn("全部归还");
 * noreturn 表示该调拨单是借用单，还未归还的。
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public enum AdjustReturnStatus {
	neednot("不必归还"),nonereturn("未归还"),partreturn("部分归还"),over("全部归还");
	private String name;
	AdjustReturnStatus(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}

}
