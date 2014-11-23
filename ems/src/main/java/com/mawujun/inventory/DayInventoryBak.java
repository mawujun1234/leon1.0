package com.mawujun.inventory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

/**
 * 日结库存
 * @author mawujun 16064988@qq.com  
 *
 */
@Entity
@Table(name="ems_dayinventorybak")
@IdClass(DayInventoryBak_PK.class)  
public class DayInventoryBak  implements IdEntity<DayInventoryBak_PK>{
	@Id
	@Column(length=14)
	private String version;//备份的版本号，例如20141109112209
	@Id
	@Column(length=8)
	private Integer daykey;//20140101这种形式
	@Id
	@Column(length=6)
	private String subtype_id;
	@Id
	@Column(length=6)
	private String prod_id;//品名id

	@Id
	@Column(length=15)
	private String brand_id;//品牌id

	@Id
	@Column(length=50)
	private String style;
	@Id
	@Column(length=36)
	private String store_id;//仓库id，所属仓库
	private Integer store_type;//1:在建仓库，2：维修中心,3:备品备件仓库
	
	@Column(columnDefinition="INT default 0")
	private Integer fixednum;//额定数量，预先定的数量，手工填的
	@Column(columnDefinition="INT default 0")
	private Integer lastnum;//上月结余
	@Column(columnDefinition="INT default 0")
	private Integer purchasenum;//采购新增
	@Column(columnDefinition="INT default 0")
	private Integer oldnum;//旧品新增,旧品新增，指的是取消某个杆位退回的数量
	@Column(columnDefinition="INT default 0")
	private Integer installoutnum;//本期领用
	@Column(columnDefinition="INT default 0")
	private Integer repairinnum;//本期维修返还数
	@Column(columnDefinition="INT default 0")
	private Integer scrapoutnum;//报废出库数量
	@Column(columnDefinition="INT default 0")
	private Integer repairoutnum;//维修出库数量，维修中心对这个仓库，出库的数量
	@Column(columnDefinition="INT default 0")
	private Integer adjustoutnum;//借用数，=临时借用于在建项目使用+临时借用外勤维护用
	@Column(columnDefinition="INT default 0")
	private Integer adjustinnum;//返还数，=临时借用于在建项目使用归还+临时借用外勤维护用
	@Column(columnDefinition="INT default 0")
	private Integer nownum;//本月结余，，通过前面的数据++--弄出来的
	@Column(columnDefinition="INT default 0")
	private Integer nownum_query;//本月结余，,通过查询出来的，会不准，例如重新计算历史的月结库存的时候
	@Column(columnDefinition="INT default 0")
	private Integer supplementnum;//增补数，也是手工填的3
	@Column(length=500)
	private String memo;//备注
	
	
	/**
	 * 获取本期新增,主要用于在建仓库
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	public Integer getNow_in(){
		if(this.getPurchasenum()==null){
			this.setPurchasenum(0);
		}
		if(this.getAdjustinnum()==null){
			this.setAdjustinnum(0);
		}
		return this.getPurchasenum()+this.getAdjustinnum();
	}
	/**
	 * 获取本期领用,主要用于在建仓库
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	public Integer getNow_out(){
		if(this.getInstalloutnum()==null){
			this.setInstalloutnum(0);
		}
		if(this.getAdjustoutnum()==null){
			this.setAdjustoutnum(0);
		}
		return this.getInstalloutnum()+this.getAdjustoutnum();
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
	public Integer getNownum_query() {
		return nownum_query;
	}
	public void setNownum_query(Integer nownum_query) {
		this.nownum_query = nownum_query;
	}
	public Integer getSupplementnum() {
		return supplementnum;
	}
	public void setSupplementnum(Integer supplementnum) {
		this.supplementnum = supplementnum;
	}


	public Integer getDaykey() {
		return daykey;
	}

	public void setDaykey(Integer daykey) {
		this.daykey = daykey;
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
	public Integer getStore_type() {
		return store_type;
	}
	public void setStore_type(Integer store_type) {
		this.store_type = store_type;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Override
	public void setId(DayInventoryBak_PK id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public DayInventoryBak_PK getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
