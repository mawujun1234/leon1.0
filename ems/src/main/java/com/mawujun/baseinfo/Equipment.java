package com.mawujun.baseinfo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;
import com.mawujun.store.IEcodeCache;

@Entity
@Table(name="ems_equipment")
public class Equipment implements IdEntity<String>,IEcodeCache{
	@Id
	@Column(length=25)
	private String ecode;
//	@Column(length=2)
//	private String type_id;//类型id
	@Column(length=4)
	private String subtype_id;//子类型id
	@Column(length=6)
	private String prod_id;//品名id
	@Column(length=15)
	private String brand_id;//品牌id
	@Column(length=3)
	private String supplier_id;//供应商id
	@Column(length=50)
	private String style;//型号
	@Column(length=36)
	private String orderlist_id;//订单明细id
	
	//private Date lastInDate;//最新一次入库时间
	
	
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean isnew=true;//是否是新产品
	//@org.hibernate.annotations.Type(type="yes_no")
	//private String islocked;//在领用和借用的时候才会对这个设备进行锁定
	@Column(length=100)
	private String memo;

	//外键于表EquipmentStatus
	//private Integer status=0;//
	@Enumerated(EnumType.STRING)
	@Column(length=20)
	private EquipmentStatus status=EquipmentStatus.no_storage;
	
	@Enumerated(EnumType.STRING)
	@Column(length=20)
	private EquipmentPlace place;
	
	//@Column(precision=10,scale=2)
	//private Double unitPrice;//价格
	//@Column(precision=10,scale=2)
	//private Double unitPrice_nav;//资产净值  Net Asset Value,简称NAV
	private Double value_original;//原值,建立订单的时候的值
	private Double value_net;//净值
	@Column(updatable=false)
	private Date first_install_date;//第一次安装的时间,现在是在任务确认的时候设置初次安装时间
	
	
	@Column(updatable=false)
	private Date fisData;//first in stock date第一次入库时间
	
//	@Column(length=36)
//	private String store_id;//仓库id，所属仓库
//	@Column(length=36)
//	private String workUnit_id;//作业单位id，所属的作业单位，和store_id，同时只能有一个有值
//	@Column(length=36)
//	private String pole_id;//杆位，所属的杆位，store_id，workUnit_id，pole_id三个职能存在一个
	
	
	@Column(length=36)
	private String last_installIn_id;//最后一次入库的入库id，这个字段是返库，无论坏件还是好贱返库的时候的id
	@Column(length=36)
	private String last_borrow_id;//在借用的时候设置的	
	@Column(length=36)
	private String last_workunit_id;//最后一次设备经手的工作单位,在领用或借用出库的时候设置的
	
	private Date last_install_date;//最近的安装时间，该设备安装在这个杆位上时的时间，包括最新安装和维修安装
	@Column(length=36)
	private String last_task_id;//最新一次任务id，在任务提交或确认的时候设置的，注意和currt_task_id的转换
	@Column(length=36)
	private String last_pole_id;//在安装上点位的时候，也是在任务提交或确认的时候，和last_task_id是同步设置的
	//作为判断 该设备是否已经被任务扫描过了的一句
	@Column(length=36)
	private String currt_task_id;//当期那任务的id，只要任务提交或者确认，这个任务id就为null，只有在作业单位在扫描后，这个才会有值，
	


	
	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.ecode=id;
	}
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return ecode;
	}
	
	
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
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
	public String getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(String supplier_id) {
		this.supplier_id = supplier_id;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}

	public Boolean getIsnew() {
		return isnew;
	}
	public void setIsnew(Boolean isnew) {
		this.isnew = isnew;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

	
	public Date getFisData() {
		return fisData;
	}
	public void setFisData(Date fisData) {
		this.fisData = fisData;
	}
	

//	public String getStore_id() {
//		return store_id;
//	}
//	public void setStore_id(String store_id) {
//		this.store_id = store_id;
//	}
//	public String getWorkUnit_id() {
//		return workUnit_id;
//	}
//	public void setWorkUnit_id(String workUnit_id) {
//		this.workUnit_id = workUnit_id;
//	}
//	public String getPole_id() {
//		return pole_id;
//	}
//	public void setPole_id(String pole_id) {
//		this.pole_id = pole_id;
//	}
	
	
	public Date getLast_install_date() {
		return last_install_date;
	}
	public void setLast_install_date(Date last_install_date) {
		this.last_install_date = last_install_date;
	}
	public String getOrderlist_id() {
		return orderlist_id;
	}
	public void setOrderlist_id(String orderlist_id) {
		this.orderlist_id = orderlist_id;
	}
	public EquipmentStatus getStatus() {
		return status;
	}
	public void setStatus(EquipmentStatus status) {
		this.status = status;
	}
	public String getLast_task_id() {
		return last_task_id;
	}
	public void setLast_task_id(String last_task_id) {
		this.last_task_id = last_task_id;
	}
	public String getLast_installIn_id() {
		return last_installIn_id;
	}
	public void setLast_installIn_id(String last_installIn_id) {
		this.last_installIn_id = last_installIn_id;
	}
	public String getLast_workunit_id() {
		return last_workunit_id;
	}
	public void setLast_workunit_id(String last_workunit_id) {
		this.last_workunit_id = last_workunit_id;
	}
	public EquipmentPlace getPlace() {
		return place;
	}
	public void setPlace(EquipmentPlace place) {
		this.place = place;
	}
	public String getLast_borrow_id() {
		return last_borrow_id;
	}
	public void setLast_borrow_id(String last_borrow_id) {
		this.last_borrow_id = last_borrow_id;
	}
	public String getCurrt_task_id() {
		return currt_task_id;
	}
	public void setCurrt_task_id(String currt_task_id) {
		this.currt_task_id = currt_task_id;
	}

	public Date getFirst_install_date() {
		return first_install_date;
	}
	public void setFirst_install_date(Date first_install_date) {
		this.first_install_date = first_install_date;
	}
	public String getLast_pole_id() {
		return last_pole_id;
	}
	public void setLast_pole_id(String last_pole_id) {
		this.last_pole_id = last_pole_id;
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


}
