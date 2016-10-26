package com.mawujun.inventory;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.mawujun.inventory.AssetClean.PK;
import com.mawujun.repository.idEntity.IdEntity;
import com.mawujun.repository.idEntity.UUIDEntity;

/**
 * 资产结算表
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
@Entity
@Table(name="report_assetclean")
@IdClass(PK.class)
//@Table(name="report_assetclean",uniqueConstraints = {@UniqueConstraint(columnNames={"ecode", "day_key"})})
public class AssetClean implements IdEntity<AssetClean.PK>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Id
	@Column(length=25)
	private String ecode;
    @Id
	private Integer day_key;//这是计算当天的数据，用来保存历史数据的
	
	private Integer day_have;//可使用天数
	private Integer day_used;//已经使用天数
	private Double value_original;//原值
	private Double value_old;//折旧
	private Double value_net;//净值
	
	
	@Override
	public void setId(PK id) {
		if(id!=null){
			this.ecode=id.getEcode();
			this.day_key=id.getDay_key();
		}
	}
	@Override
	public PK getId() {
		return new AssetClean.PK(ecode,day_key);
	}
	
	
	public static class PK implements Serializable {
		private String ecode;
		private Integer day_key;
		
		public PK() {
			super();
		}
		public PK(String ecode, Integer day_key) {
			super();
			this.ecode = ecode;
			this.day_key = day_key;
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
	}
	
	
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}


	public Integer getDay_used() {
		return day_used;
	}
	public void setDay_used(Integer day_used) {
		this.day_used = day_used;
	}
	public Double getValue_original() {
		return value_original;
	}
	public void setValue_original(Double value_original) {
		this.value_original = value_original;
	}

	public Double getValue_net() {
		return value_net;
	}
	public void setValue_net(Double value_net) {
		this.value_net = value_net;
	}
	public Integer getDay_have() {
		return day_have;
	}
	public void setDay_have(Integer day_have) {
		this.day_have = day_have;
	}
	public Double getValue_old() {
		return value_old;
	}
	public void setValue_old(Double value_old) {
		this.value_old = value_old;
	}
	public Integer getDay_key() {
		return day_key;
	}
	public void setDay_key(Integer day_key) {
		this.day_key = day_key;
	}

	
	
}
