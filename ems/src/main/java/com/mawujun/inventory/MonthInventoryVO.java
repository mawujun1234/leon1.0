package com.mawujun.inventory;


/**
 * 在建工程仓库盘点月报
 * @author mawujun 16064988@qq.com  
 *
 */
//@Entity
//@Table(name="report_monthreport")
//@IdClass(MonthInventory_PK.class)  
public class MonthInventoryVO extends MonthInventory{//implements IdEntity<MonthInventory_PK>{
	
	private String subtype_name;
	private String prod_name;
	private String brand_name;
	private String store_name;
	private String unit;//单位
	
	
	//private Integer lastmonthnum;//上月结余

//	public Integer getLastmonthnum() {
//		return lastmonthnum;
//	}
//
//	public void setLastmonthnum(Integer lastmonthnum) {
//		this.lastmonthnum = lastmonthnum;
//	}
	
	/**
	 * 获取本期新增,主要用于在建仓库
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	public Integer getNowAdd(){
		if(this.getPurchasenum()==null){
			this.setPurchasenum(0);
		}
		if(this.getAdjustinnum()==null){
			this.setAdjustinnum(0);
		}
		return this.getPurchasenum()+this.getAdjustinnum();
	}
	/**
	 * 获取本期领用,主要用于在建仓库
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	public Integer getNowSubtract(){
		if(this.getInstalloutnum()==null){
			this.setInstalloutnum(0);
		}
		if(this.getAdjustoutnum()==null){
			this.setAdjustoutnum(0);
		}
		return this.getInstalloutnum()+this.getAdjustoutnum();
	}

	public String getSubtype_name() {
		return subtype_name;
	}

	public void setSubtype_name(String subtype_name) {
		this.subtype_name = subtype_name;
	}

	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	

}
