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
	private String store_id;//仓库id，所属仓库
	@Id
	@Column(length=36)
	private String prod_id;//品名id


	
	
	@Column(length=36)
	private Integer monthkey;//201401 这种情况，主要用来进行月报表统计的

//	@Column(columnDefinition="INT default 0")
//	private Integer fixednum;//额定数量，预先定的数量，手工填的
//	@Column(columnDefinition="INT default 0")
//	private Integer lastmonthnum;//上月结余
	@Column(columnDefinition="INT default 0")
	private Integer todaynum=0;//今日结余=昨天结余+采购新增+旧品新增+(-本期领用数)+维修返还数+(-本期借用数)+本期归还数
	@Column(columnDefinition="INT default 0")
	private Integer yesterdaynum=0;//上期结余，也就是昨天结余，因为是任意时间段，查询，上期 就结余到昨天
	@Column(columnDefinition="INT default 0")
	private Integer purchasenum=0;//采购新增
	@Column(columnDefinition="INT default 0")
	private Integer oldnum=0;//旧品新增
	@Column(columnDefinition="INT default 0")
	private Integer installoutnum=0;//本期领用
	@Column(columnDefinition="INT default 0")
	private Integer repairinnum=0;//本期维修返还数
	@Column(columnDefinition="INT default 0")
	private Integer scrapoutnum=0;//报废数
	@Column(columnDefinition="INT default 0")
	private Integer repairoutnum=0;//维修出库数量，
	@Column(columnDefinition="INT default 0")
	private Integer borrownum=0;//借用数，
	@Column(columnDefinition="INT default 0")
	private Integer borrowreturnnum=0;//返还数，
	
	
//	@Column(columnDefinition="INT default 0")
//	private Integer adjustoutnum;//借用数，
//	@Column(columnDefinition="INT default 0")
//	private Integer adjustinnum;//返还数，
	
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public Integer getMonthkey() {
		return monthkey;
	}

	public void setMonthkey(Integer monthkey) {
		this.monthkey = monthkey;
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


}
