package com.mawujun.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.mawujun.exception.BusinessException;
import com.mawujun.repository.idEntity.IdEntity;

/**
 * 在建工程仓库盘点日报
 * @author mawujun 16064988@qq.com  
 *
 */
@Entity
@Table(name="report_builddayreport")
@IdClass(BuildDayReport_PK.class)  
public class BuildDayReport implements IdEntity<BuildDayReport_PK>{
	@Id
	@Column(length=8)
	private Integer month;//201401这种形式
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
	
	private Integer lastnum;//上月结余
	private Integer nownum;//本月结余
	private Integer storeinnum;//新增
	private Integer installoutnum;//领用
	@Column(length=500)
	private String memo;//备注
	
	
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
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
	public Integer getStoreinnum() {
		return storeinnum;
	}
	public void setStoreinnum(Integer storeinnum) {
		this.storeinnum = storeinnum;
	}
	public Integer getInstalloutnum() {
		return installoutnum;
	}
	public void setInstalloutnum(Integer installoutnum) {
		this.installoutnum = installoutnum;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Override
	public void setId(BuildDayReport_PK id) {
		// TODO Auto-generated method stub
		//throw new BusinessException("这个还没有做，现在还不需要做");
	}
	@Override
	public BuildDayReport_PK getId() {
		// TODO Auto-generated method stub
		BuildDayReport_PK id=new BuildDayReport_PK();
		id.setBrand_id(this.getBrand_id());
		id.setMonth(month);
		id.setProd_id(prod_id);
		id.setStore_id(store_id);
		id.setStyle(style);
		id.setSubtype_id(subtype_id);
		return id;
	}

}
