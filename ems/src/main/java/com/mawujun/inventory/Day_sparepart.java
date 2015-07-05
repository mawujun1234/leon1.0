package com.mawujun.inventory;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

/**
 * 备品备件仓库，日结数据
 * @author mawujun 16064988@qq.com  
 *
 */
@Entity
@Table(name="report_day_sparepart")
@IdClass(Day_sparepart_PK.class)  
public class Day_sparepart  implements IdEntity<Day_sparepart_PK>{
	@Id
	@Column(length=36)
	private Integer daykey;//20140101这种形式
	@Id
	@Column(length=36)
	private String prod_id;//品名id
	@Id
	@Column(length=36)
	private String store_id;//仓库id，所属仓库
	

	@Column(columnDefinition="INT default 0")
	private Integer fixednum;//额定数量，预先定的数量，手工填的
	@Column(columnDefinition="INT default 0")
//	private Integer lastnum;//上月结余
//	@Column(columnDefinition="INT default 0")
	private Integer purchasenum;//采购新增
	@Column(columnDefinition="INT default 0")
	private Integer oldnum;//旧品新增
	@Column(columnDefinition="INT default 0")
	private Integer installoutnum;//本期领用
	@Column(columnDefinition="INT default 0")
	private Integer repairinnum;//本期维修返还数
	@Column(columnDefinition="INT default 0")
	private Integer scrapoutnum;//报废数
	@Column(columnDefinition="INT default 0")
	private Integer repairoutnum;//维修出库数量，
	@Column(columnDefinition="INT default 0")
	private Integer adjustoutnum;//借用数，
	@Column(columnDefinition="INT default 0")
	private Integer adjustinnum;//返还数，
	
	//下面三个都是计算出啦的
//	@Column(columnDefinition="INT default 0")
//	private Integer nownum;//本月结余
//	@Column(columnDefinition="INT default 0")
//	private Integer supplementnum;//增补数，也是手工填的3e
	
	//第一次创建的时间
	@Column(updatable=false)
	private Date createDate;
//	//更新时间
//	private Date updateDate;

	@Override
	public void setId(Day_sparepart_PK id) {
		this.daykey=id.getDaykey();
		this.prod_id=id.getProd_id();
		this.store_id=id.getStore_id();
	}

	@Override
	public Day_sparepart_PK getId() {
		// TODO Auto-generated method stub
		return new Day_sparepart_PK(daykey,prod_id,store_id);
	}

	public Integer getDaykey() {
		return daykey;
	}

	public void setDaykey(Integer daykey) {
		this.daykey = daykey;
	}

	public String getProd_id() {
		return prod_id;
	}

	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public Integer getFixednum() {
		return fixednum;
	}

	public void setFixednum(Integer fixednum) {
		this.fixednum = fixednum;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
