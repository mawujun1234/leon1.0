package com.mawujun.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

/**
 * 在建工程日报表
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
@Entity
@Table(name="report_builddayreport")
@IdClass(BuildDayReport_PK.class)  
public class BuildDayReport  implements IdEntity<BuildDayReport_PK>{
	@Id
	@Column(length=8)
	private Integer daykey;//20140101这种形式
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
	
	private Integer lastnum;//上期结余,昨天结余
	private Integer nownum;//本期结余，今天结余
	private Integer storeinnum;//本期新增,今天新增
	private Integer installoutnum;//本期领用，今天领用
	@Column(length=500)
	private String memo;//备注
	

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((brand_id == null) ? 0 : brand_id.hashCode());
		result = prime * result + ((daykey == null) ? 0 : daykey.hashCode());
		result = prime * result + ((prod_id == null) ? 0 : prod_id.hashCode());
		result = prime * result
				+ ((store_id == null) ? 0 : store_id.hashCode());
		result = prime * result + ((style == null) ? 0 : style.hashCode());
		result = prime * result
				+ ((subtype_id == null) ? 0 : subtype_id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BuildDayReport other = (BuildDayReport) obj;
		if (brand_id == null) {
			if (other.brand_id != null)
				return false;
		} else if (!brand_id.equals(other.brand_id))
			return false;
		if (daykey == null) {
			if (other.daykey != null)
				return false;
		} else if (!daykey.equals(other.daykey))
			return false;
		if (prod_id == null) {
			if (other.prod_id != null)
				return false;
		} else if (!prod_id.equals(other.prod_id))
			return false;
		if (store_id == null) {
			if (other.store_id != null)
				return false;
		} else if (!store_id.equals(other.store_id))
			return false;
		if (style == null) {
			if (other.style != null)
				return false;
		} else if (!style.equals(other.style))
			return false;
		if (subtype_id == null) {
			if (other.subtype_id != null)
				return false;
		} else if (!subtype_id.equals(other.subtype_id))
			return false;
		return true;
	}
	//private BuildDayReport_PK id;
	@Override
	public void setId(BuildDayReport_PK id) {
		// TODO Auto-generated method stub
		//throw new BusinessException("这个还没有做，现在还不需要做");
		//this.id=id;
	}
	@Override
	public BuildDayReport_PK getId() {
//		if(id!=null){
//			return id;
//		}
		// TODO Auto-generated method stub
		BuildDayReport_PK id=new BuildDayReport_PK();
		id.setBrand_id(this.getBrand_id());
		id.setDaykey(daykey);
		id.setProd_id(prod_id);
		id.setStore_id(store_id);
		id.setStyle(style);
		id.setSubtype_id(subtype_id);
		return id;
	}
	

	public Integer getDaykey() {
		return daykey;
	}
	public void setDaykey(Integer daykey) {
		this.daykey = daykey;
	}

}
