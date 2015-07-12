package com.mawujun.adjust;

/**
 * 订单明细情况
 * noin("未入库"),in("已入库"),lost("丢失");
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public enum AdjustListStatus {
	noin("未入库"),in("已接收")
	//,returnback("已归还")
	,lost("丢失");//如果把设备修改为lost，那也要注意updateAdjustIsAllReturn方法，把lost统计进去
	private String name;
	AdjustListStatus(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}
}
