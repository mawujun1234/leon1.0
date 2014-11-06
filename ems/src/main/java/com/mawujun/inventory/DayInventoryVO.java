package com.mawujun.inventory;


/**
 * 日报表
 * 
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
//@Entity
//@Table(name="report_dayreport")
//@IdClass(DayInventory_PK.class)  
public class DayInventoryVO extends DayInventory{// implements IdEntity<DayInventory_PK>{
	
	
	//@Column(columnDefinition="INT default 0")
	private Integer lastmonthnum;//上月结余

	public Integer getLastmonthnum() {
		return lastmonthnum;
	}

	public void setLastmonthnum(Integer lastmonthnum) {
		this.lastmonthnum = lastmonthnum;
	}
	
}
