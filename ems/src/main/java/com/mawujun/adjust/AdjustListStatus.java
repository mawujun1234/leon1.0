package com.mawujun.adjust;

/**
 * 订单明细情况
 * noin("未入库"),in("已入库"),installout("被领用");
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public enum AdjustListStatus {
	noin("未入库"),in("已入库"),installout("被领用");
	private String name;
	AdjustListStatus(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}
}
