package com.mawujun.inventory;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Id;

public class Month_sparepart_prod {

	private String store_id;//仓库id，所属仓库

	private String prod_id;//品名id

	//private Integer monthkey;//201401 这种情况，主要用来进行月报表统计的


	private Integer todaynum=0;//今日结余=昨天结余+采购新增+旧品新增+(-本期领用数)+维修返还数+(-本期借用数)+本期归还数
	
	private Integer yesterdaynum=0;//上期结余，也就是昨天结余，因为是任意时间段，查询，上期 就结余到昨天
	
	private Integer purchasenum=0;//采购新增
	
	private Integer oldnum=0;//旧品新增
	
	private Integer installoutnum=0;//本期领用
	
	private Integer repairinnum=0;//本期维修返还数
	
	private Integer scrapoutnum=0;//报废数
	
	private Integer repairoutnum=0;//维修出库数量，
	
	private Integer borrownum=0;//借用数，
	
	private Integer borrowreturnnum=0;//返还数，
	
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
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}

	public Integer getTodaynum() {
		return todaynum;
	}
	public void setTodaynum(Integer todaynum) {
		this.todaynum = todaynum;
	}
	public Integer getYesterdaynum() {
		return yesterdaynum;
	}
	public void setYesterdaynum(Integer yesterdaynum) {
		this.yesterdaynum = yesterdaynum;
	}
	public Integer getPurchasenum() {
		return purchasenum;
	}
	public void setPurchasenum(Integer purchasenum) {
		this.purchasenum = purchasenum;
	}
	public Integer getOldnum() {
		return oldnum;
	}
	public void setOldnum(Integer oldnum) {
		this.oldnum = oldnum;
	}
	public Integer getInstalloutnum() {
		return installoutnum;
	}
	public void setInstalloutnum(Integer installoutnum) {
		this.installoutnum = installoutnum;
	}
	public Integer getRepairinnum() {
		return repairinnum;
	}
	public void setRepairinnum(Integer repairinnum) {
		this.repairinnum = repairinnum;
	}
	public Integer getScrapoutnum() {
		return scrapoutnum;
	}
	public void setScrapoutnum(Integer scrapoutnum) {
		this.scrapoutnum = scrapoutnum;
	}
	public Integer getRepairoutnum() {
		return repairoutnum;
	}
	public void setRepairoutnum(Integer repairoutnum) {
		this.repairoutnum = repairoutnum;
	}
	public Integer getBorrownum() {
		return borrownum;
	}
	public void setBorrownum(Integer borrownum) {
		this.borrownum = borrownum;
	}
	public Integer getBorrowreturnnum() {
		return borrowreturnnum;
	}
	public void setBorrowreturnnum(Integer borrowreturnnum) {
		this.borrowreturnnum = borrowreturnnum;
	}
}
