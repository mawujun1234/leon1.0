package com.mawujun.inventory;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Day_sparepart_prod extends Day_sparepart{
	//private String prod_id;
	private String prod_name;
	private String prod_unit;
	private String prod_style;
	private String store_name;
	private String brand_name;
	
	private BigDecimal value_net;//净值
	public BigDecimal getValue_net() {
		if(value_net!=null){
			return value_net.setScale(2, RoundingMode.HALF_UP);
		}
		return new BigDecimal(0);
	}
	public void setValue_net(BigDecimal value_net) {
		this.value_net = value_net;
	}
	
	public String getkey(){
		return this.getProd_id()+"_"+this.getStore_id();
	}

	public String getProd_name() {
		return prod_name;
	}
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	public String getProd_unit() {
		return prod_unit;
	}
	public void setProd_unit(String prod_unit) {
		this.prod_unit = prod_unit;
	}
	public String getProd_style() {
		return prod_style;
	}
	public void setProd_style(String prod_style) {
		this.prod_style = prod_style;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
}
