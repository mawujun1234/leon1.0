package com.mawujun.repair;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

/**
 * 维修单
 * @author mawujun 16064988@qq.com  
 *
 */
@Entity
@Table(name="ems_repair")
public class Repair  implements IdEntity<String>{
	@Id
	@Column(length=18)
	private String id;
	@Column(length=25)
	private String ecode;
	@Column(length=25)
	private String prod_id;
	@Column(length=36) 
	private String str_out_id;//出库仓库  //以设备出来的仓库作为报废单接收仓库
	@Column(length=36) 
	private String rpa_id;//维修中心id
	@Column(length=36) 
	private String str_in_id;//入库仓库
	
	
	@Column(length=15)
	private String installIn_id;//最后入库的单子
	//下面三行是在创建的时候填的，故障描述是实施人员填的
	private Date repair_date;//报修时间，就是取坏设备入库的时候
	@Column(length=36) 
	private String workunit_id;//报修人,就是维修小组
	@Column(length=36) 
	private String task_id;//最后一个任务
	@Column(length=500) 
	private String broken_memo;//故障描述，故障类型+故障原因
	
	@Column(length=36)
	private String str_out_oper_id;//仓库出库的操作人
	private Date str_out_date;//仓库出库时间，也是维修单创建日期
	
	
	
	@Column(length=36) 
	private String rpa_user_id;//维修人
	
	@Column(length=36) 
	private String rpa_in_oper_id;//维修中心入库人
	private Date rpa_in_date;//维修中心入库时间，也是维修单接受日期
	@Column(length=36) 
	private String rpa_out_oper_id;//维修中心出库的操作人
	private Date rpa_out_date;//维修中心出库时间
	
	
	
	@Column(length=36) 
	private String str_in_oper_id;//仓库入库的操作人
	private Date str_in_date;//仓库入库时间，也就是维修好后的入库时间
	
	@Enumerated(EnumType.STRING)
	@Column(length=8) 
	private RepairType rpa_type=RepairType.innerrpa;//维修类型，维修 (innerrpa)还是外修(outrpa)
	
	@Enumerated(EnumType.STRING)
	@Column(length=18) 
	private RepairStatus status=RepairStatus.to_repair;//状态
	
	private Date scrapDate;//如果这个维修单的设备报废了，那就填写报废时间
	
	@Column(length=500) 
	private String broken_reson;//故障原因
	@Column(length=500) 
	private String handler_method;//处理方法
	@Column(length=500) 
	private String memo;
	
	//====下面的是外修的字段
	private Date send_date;//寄出时间
	@Column(length=30) 
	private String sendno;//运单号
	private Date receive_date;//维修好后 ，收货时间
	@Column(length=80) 
	private String repairFactory;//维修厂方
	
	
	//还可使用的年数，在旧设备订单录入的时候,同时预估原值和还可以使用的年数
			private Integer depreci_year=0;//
			private Integer depreci_month=0;//
			private Integer depreci_day=0;//

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}
	public String getStr_out_oper_id() {
		return str_out_oper_id;
	}
	public void setStr_out_oper_id(String str_out_oper_id) {
		this.str_out_oper_id = str_out_oper_id;
	}
	public Date getStr_out_date() {
		return str_out_date;
	}
	public void setStr_out_date(Date str_out_date) {
		this.str_out_date = str_out_date;
	}
	public String getStr_out_id() {
		return str_out_id;
	}
	public void setStr_out_id(String str_out_id) {
		this.str_out_id = str_out_id;
	}
	public String getRpa_id() {
		return rpa_id;
	}
	public void setRpa_id(String rpa_id) {
		this.rpa_id = rpa_id;
	}
	public String getRpa_in_oper_id() {
		return rpa_in_oper_id;
	}
	public void setRpa_in_oper_id(String rpa_in_oper_id) {
		this.rpa_in_oper_id = rpa_in_oper_id;
	}
	public Date getRpa_in_date() {
		return rpa_in_date;
	}
	public void setRpa_in_date(Date rpa_in_date) {
		this.rpa_in_date = rpa_in_date;
	}
	public String getRpa_out_oper_id() {
		return rpa_out_oper_id;
	}
	public void setRpa_out_oper_id(String rpa_out_oper_id) {
		this.rpa_out_oper_id = rpa_out_oper_id;
	}
	public Date getRpa_out_date() {
		return rpa_out_date;
	}
	public void setRpa_out_date(Date rpa_out_date) {
		this.rpa_out_date = rpa_out_date;
	}
	public String getStr_in_oper_id() {
		return str_in_oper_id;
	}
	public void setStr_in_oper_id(String str_in_oper_id) {
		this.str_in_oper_id = str_in_oper_id;
	}
	public Date getStr_in_date() {
		return str_in_date;
	}
	public void setStr_in_date(Date str_in_date) {
		this.str_in_date = str_in_date;
	}
	public String getStr_in_id() {
		return str_in_id;
	}
	public void setStr_in_id(String str_in_id) {
		this.str_in_id = str_in_id;
	}

	public String getBroken_memo() {
		return broken_memo;
	}
	public void setBroken_memo(String broken_memo) {
		this.broken_memo = broken_memo;
	}
	public String getBroken_reson() {
		return broken_reson;
	}
	public void setBroken_reson(String broken_reson) {
		this.broken_reson = broken_reson;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getWorkunit_id() {
		return workunit_id;
	}
	public void setWorkunit_id(String workunit_id) {
		this.workunit_id = workunit_id;
	}
	public Date getRepair_date() {
		return repair_date;
	}
	public void setRepair_date(Date repair_date) {
		this.repair_date = repair_date;
	}
	public String getInstallIn_id() {
		return installIn_id;
	}
	public void setInstallIn_id(String installIn_id) {
		this.installIn_id = installIn_id;
	}
	public String getRpa_user_id() {
		return rpa_user_id;
	}
	public void setRpa_user_id(String rpa_user_id) {
		this.rpa_user_id = rpa_user_id;
	}
	public RepairType getRpa_type() {
		return rpa_type;
	}
	public void setRpa_type(RepairType rpa_type) {
		this.rpa_type = rpa_type;
	}
	public String getTask_id() {
		return task_id;
	}
	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	public Date getScrapDate() {
		return scrapDate;
	}
	public void setScrapDate(Date scrapDate) {
		this.scrapDate = scrapDate;
	}
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public String getHandler_method() {
		return handler_method;
	}
	public void setHandler_method(String handler_method) {
		this.handler_method = handler_method;
	}
	public RepairStatus getStatus() {
		return status;
	}
	public void setStatus(RepairStatus status) {
		this.status = status;
	}
	public Date getSend_date() {
		return send_date;
	}
	public void setSend_date(Date send_date) {
		this.send_date = send_date;
	}
	public String getSendno() {
		return sendno;
	}
	public void setSendno(String sendno) {
		this.sendno = sendno;
	}
	public Date getReceive_date() {
		return receive_date;
	}
	public void setReceive_date(Date receive_date) {
		this.receive_date = receive_date;
	}
	public String getRepairFactory() {
		return repairFactory;
	}
	public void setRepairFactory(String repairFactory) {
		this.repairFactory = repairFactory;
	}
	public Integer getDepreci_year() {
		return depreci_year;
	}
	public void setDepreci_year(Integer depreci_year) {
		this.depreci_year = depreci_year;
	}
	public Integer getDepreci_month() {
		return depreci_month;
	}
	public void setDepreci_month(Integer depreci_month) {
		this.depreci_month = depreci_month;
	}
	public Integer getDepreci_day() {
		return depreci_day;
	}
	public void setDepreci_day(Integer depreci_day) {
		this.depreci_day = depreci_day;
	}

}
