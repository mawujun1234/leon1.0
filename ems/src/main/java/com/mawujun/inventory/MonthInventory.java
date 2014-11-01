package com.mawujun.inventory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

/**
 * 月结库存
 * @author mawujun 16064988@qq.com  
 *
 */
@Entity
@Table(name="ems_monthinventory")
@IdClass(MonthInventory_PK.class)  
public class MonthInventory implements IdEntity<MonthInventory_PK>{
	@Id
	@Column(length=8)
	private Integer monthkey;//201401这种形式
	@Id
	@Column(length=6)
	private String subtype_id;
	@Id
	@Column(length=6)
	private String prod_id;//品名id
	@Id
	@Column(length=6)
	private String brand_id;//品牌id
	@Id
	@Column(length=50)
	private String style;
	@Id
	@Column(length=36)
	private String store_id;//仓库id，所属仓库

	@Column(columnDefinition="INT default 0")
	private Integer nownum;//库存
	@Column(columnDefinition="INT default 0")
	private Integer lastnum;//上期库存
	
	@Override
	public void setId(MonthInventory_PK id) {
		// TODO Auto-generated method stub
		//throw new BusinessException("这个还没有做，现在还不需要做");
	}
	@Override
	public MonthInventory_PK getId() {
		// TODO Auto-generated method stub
		MonthInventory_PK id=new MonthInventory_PK();
		id.setBrand_id(this.getBrand_id());
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

	public String getProd_id() {
		return prod_id;
	}

	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
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
	public Integer getNownum() {
		return nownum;
	}
	public void setNownum(Integer nownum) {
		this.nownum = nownum;
	}
	public Integer getLastnum() {
		return lastnum;
	}
	public void setLastnum(Integer lastnum) {
		this.lastnum = lastnum;
	}


	
}
