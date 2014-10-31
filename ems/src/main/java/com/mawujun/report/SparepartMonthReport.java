package com.mawujun.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

@Entity
@Table(name="report_sparepartmonthreport")
@IdClass(SparepartMonthReport_PK.class) 
public class SparepartMonthReport implements IdEntity<SparepartMonthReport_PK>{
	@Id
	@Column(length=8)
	private Integer monthkey;//201401这种形式
	@Id
	@Column(length=6)
	private String subtype_id;
	@Column(length=30)
	private String subtype_name;
	@Id
	@Column(length=6)
	private String prod_id;//品名id
	@Column(length=30)
	private String prod_name;
	@Id
	@Column(length=6)
	private String brand_id;//品牌id
	@Column(length=30)
	private String brand_name;
	@Id
	@Column(length=50)
	private String style;
	@Id
	@Column(length=36)
	private String store_id;//仓库id，所属仓库
	@Column(length=30)
	private String store_name;
	
	@Column(length=10)
	private String unit;//单位
	
	private Integer fixednum;//额定数量，预先定的数量，手工填的
	private Integer lastnum;//上月结余
	private Integer purchasenum;//采购新增
	private Integer oldnum;//旧品新增,旧品新增，指的是取消某个杆位退回的数量
	private Integer installoutnum;//本期领用
	private Integer repairinnum;//本期维修返还数
	private Integer scrapoutnum;//报废出库数量
	private Integer repairoutnum;//维修出库数量
	private Integer adjustoutnum;//借用数，就是调拨出库数量
	private Integer adjustinnum;//返还数，就是调拨入库的数量
	private Integer nownum;//本月结余
	private Integer supplementnum;//增补数，也是手工填的
	@Column(length=500)
	private String memo;//备注
	
	@Override
	public void setId(SparepartMonthReport_PK id) {
		// TODO Auto-generated method stub
		//throw new BusinessException("这个还没有做，现在还不需要做");
	}
	@Override
	public SparepartMonthReport_PK getId() {
		// TODO Auto-generated method stub
		SparepartMonthReport_PK id=new SparepartMonthReport_PK();
		id.setBrand_id(brand_id);
		id.setMonthkey(monthkey);
		id.setProd_id(prod_id);
		id.setStore_id(store_id);
		id.setStyle(style);
		id.setSubtype_id(subtype_id);
		return id;
	}
	public Integer getMonthkey() {
		return monthkey;
	}
	public void setMonthkey(Integer monthkey) {
		this.monthkey = monthkey;
	}
	public String getSubtype_id() {
		return subtype_id;
	}
	public void setSubtype_id(String subtype_id) {
		this.subtype_id = subtype_id;
	}
	public String getSubtype_name() {
		return subtype_name;
	}
	public void setSubtype_name(String subtype_name) {
		this.subtype_name = subtype_name;
	}
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public String getProd_name() {
		return prod_name;
	}
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	public String getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
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
	public Integer getFixednum() {
		return fixednum;
	}
	public void setFixednum(Integer fixednum) {
		this.fixednum = fixednum;
	}
	public Integer getLastnum() {
		return lastnum;
	}
	public void setLastnum(Integer lastnum) {
		this.lastnum = lastnum;
	}
	public Integer getNownum() {
		return nownum;
	}
	public void setNownum(Integer nownum) {
		this.nownum = nownum;
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
	public Integer getAdjustoutnum() {
		return adjustoutnum;
	}
	public void setAdjustoutnum(Integer adjustoutnum) {
		this.adjustoutnum = adjustoutnum;
	}
	public Integer getAdjustinnum() {
		return adjustinnum;
	}
	public void setAdjustinnum(Integer adjustinnum) {
		this.adjustinnum = adjustinnum;
	}
	public Integer getSupplementnum() {
		return supplementnum;
	}
	public void setSupplementnum(Integer supplementnum) {
		this.supplementnum = supplementnum;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
