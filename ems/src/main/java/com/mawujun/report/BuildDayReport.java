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
	
	//==========================下面就是每天的数据
	//private Integer day_in;
	//private Integer day_out;
	
//	private Integer day1_in;
//	private Integer day1_out;	
//	private Integer day2_in;
//	private Integer day2_out;
//	private Integer day3_in;
//	private Integer day3_out;
//	private Integer day4_in;
//	private Integer day4_out;
//	private Integer day5_in;
//	private Integer day5_out;
//	private Integer day6_in;
//	private Integer day6_out;
//	private Integer day7_in;
//	private Integer day7_out;
//	private Integer day8_in;
//	private Integer day8_out;
//	private Integer day9_in;
//	private Integer day9_out;
//	private Integer day10_in;
//	private Integer day10_out;
//	private Integer day11_in;
//	private Integer day11_out;
//	private Integer day12_in;
//	private Integer day12_out;
//	private Integer day13_in;
//	private Integer day13_out;
//	private Integer day14_in;
//	private Integer day14_out;
//	private Integer day15_in;
//	private Integer day15_out;
//	private Integer day16_in;
//	private Integer day16_out;
//	private Integer day17_in;
//	private Integer day17_out;
//	private Integer day18_in;
//	private Integer day18_out;
//	private Integer day19_in;
//	private Integer day19_out;
//	private Integer day20_in;
//	private Integer day20_out;
//	private Integer day21_in;
//	private Integer day21_out;
//	private Integer day22_in;
//	private Integer day22_out;
//	private Integer day23_in;
//	private Integer day23_out;
//	private Integer day24_in;
//	private Integer day24_out;
//	private Integer day25_in;
//	private Integer day25_out;
//	private Integer day26_in;
//	private Integer day26_out;
//	private Integer day27_in;
//	private Integer day27_out;
//	private Integer day28_in;
//	private Integer day28_out;
//	private Integer day29_in;
//	private Integer day29_out;
//	private Integer day30_in;
//	private Integer day30_out;
//	private Integer day31_in;
//	private Integer day31_out;
//	

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
