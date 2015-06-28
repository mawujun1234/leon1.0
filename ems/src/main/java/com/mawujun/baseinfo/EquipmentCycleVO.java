package com.mawujun.baseinfo;

public class EquipmentCycleVO extends EquipmentCycle {
	private String target_name;
	
	/**
	 * 获取生命周期的信息
	 * @author mawujun 16064988@qq.com 
	 * @return
	 */
	public String getCycleInfo(){
		return this.getOperateDate()+" ,操作者:"+this.getOperater_name()+",操作类型:"+this.getOperateType().getName()+"("+this.getTarget_id()+")"+",目标:"+this.getTarget_name();
	}

	public String getTarget_name() {
		return target_name;
	}

	public void setTarget_name(String target_name) {
		this.target_name = target_name;
	}
	
	
}
