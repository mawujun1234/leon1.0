package com.mawujun.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.mawujun.repository.idEntity.UUIDEntity;

/**
 * 资产结算表
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
@Entity
@Table(name="report_assetclean",uniqueConstraints = {@UniqueConstraint(columnNames={"ecode", "day_now"})})
public class AssetClean extends UUIDEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Column(length=25)
	private String ecode;

	private Integer day_now;//这是计算当天的数据，用来保存历史数据的
	
	private Integer day_have;//可使用天数
	private Integer day_used;//已经使用天数
	private Double value_original;//原值
	private Double value_depreciation;//折旧
	private Double value_net;//净值
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}
	public Integer getDay_now() {
		return day_now;
	}
	public void setDay_now(Integer day_now) {
		this.day_now = day_now;
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
	public Double getValue_depreciation() {
		return value_depreciation;
	}
	public void setValue_depreciation(Double value_depreciation) {
		this.value_depreciation = value_depreciation;
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
	
	
	
}
