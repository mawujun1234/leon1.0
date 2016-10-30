package com.mawujun.inventory;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.mawujun.inventory.Equipment_Store_Day.PK;
import com.mawujun.repository.idEntity.IdEntity;

/**
 * 每天仓库拥有的设备库存记录
 * @author mawujun
 *
 */
@Entity
@Table(name="report_equipment_store_day")
@IdClass(PK.class)
public class Equipment_Store_Day implements IdEntity<Equipment_Store_Day.PK>{

    @Id
	@Column(length=25)
	private String ecode;
    @Id
	private Integer day_key;
    @Id
   	@Column(length=36,nullable=false)
   	private String store_id;
    
    //这三个值最终是以AssetClean中的值为准，因为那边可能会数据调整，导致变化
    private Double value_original;//原值
	private Double value_old;//折旧
	private Double value_net;//净值
	
	@Override
	public void setId(PK id) {
		if(id!=null){
			this.ecode=id.getEcode();
			this.day_key=id.getDay_key();
			this.store_id=id.getStore_id();
		}
	}
	@Override
	public PK getId() {
		return new Equipment_Store_Day.PK(ecode,day_key,store_id);
	}
	
	
	public static class PK implements Serializable {
		private String ecode;
		private Integer day_key;
		private String store_id;
		
		public PK() {
			super();
		}
		public PK(String ecode, Integer day_key,String store_id) {
			super();
			this.ecode = ecode;
			this.day_key = day_key;
			this.store_id = store_id;
		}
		public String getEcode() {
			return ecode;
		}
		public void setEcode(String ecode) {
			this.ecode = ecode;
		}
		public Integer getDay_key() {
			return day_key;
		}
		public void setDay_key(Integer day_key) {
			this.day_key = day_key;
		}
		public String getStore_id() {
			return store_id;
		}
		public void setStore_id(String store_id) {
			this.store_id = store_id;
		}
		
	}
	
	
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}
	public Integer getDay_key() {
		return day_key;
	}
	public void setDay_key(Integer day_key) {
		this.day_key = day_key;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public Double getValue_original() {
		return value_original;
	}
	public void setValue_original(Double value_original) {
		this.value_original = value_original;
	}
	public Double getValue_old() {
		return value_old;
	}
	public void setValue_old(Double value_old) {
		this.value_old = value_old;
	}
	public Double getValue_net() {
		return value_net;
	}
	public void setValue_net(Double value_net) {
		this.value_net = value_net;
	}
	
    

}
