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
	
	private String subtype_name;
	private String prod_name;
	private String brand_name;
	private String store_name;
	private String unit;//单位
	
	//@Column(columnDefinition="INT default 0")
	private Integer lastmonthnum;//上月结余

	public Integer getLastmonthnum() {
		return lastmonthnum;
	}

	public void setLastmonthnum(Integer lastmonthnum) {
		this.lastmonthnum = lastmonthnum;
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
