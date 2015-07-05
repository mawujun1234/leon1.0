package com.mawujun.adjust;
/**
 * carry("在途"),noallin("未全入"),noreturn("未归还"),over("完成");
 * noreturn 表示该调拨单是借用单，还未归还的。
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public enum AdjustStatus {
	carry("在途"),noallin("未全入"),noreturn("未归还"),partreturn("部分归还"),over("完成");
	private String name;
	AdjustStatus(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}

}
