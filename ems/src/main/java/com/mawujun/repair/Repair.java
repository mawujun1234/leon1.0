package com.mawujun.repair;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

@Entity
@Table(name="sys_repair")
public class Repair  implements IdEntity<String>{
	@Id
	@Column(length=15)
	private String id;
	@Column(length=25)
	private String ecode;
	@Column(length=36)
	private String str_out_oper_id;//仓库出库的操作人
	private Date str_out_date;//仓库出库时间，也是维修单创建日期
	@Column(length=36) 
	private String str_out_id;//出库仓库
	
	@Column(length=36) 
	private String rpa_id;//维修中心id
	
	@Column(length=36) 
	private String rpa_in_oper_id;//维修中心入库人
	private Date rpa_in_date;//维修中心入库时间，也是维修单接受日期
	@Column(length=36) 
	private String rpa_out_oper_id;//维修中心出库的操作人
	private Date rpa_out_date;//维修中心出库时间
	
	@Column(length=36) 
	private String str_in_oper_id;//仓库入库的操作人
	private Date str_in_date;//仓库入库时间，也就是维修好后的入库时间
	@Column(length=36) 
	private String str_in_id;//入库仓库
	
	private Integer rpa_type;//维修类型，维修 (1)还是外修(2)
	private Integer status=1;//状态
	
	
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
	public Integer getRpa_type() {
		return rpa_type;
	}
	public void setRpa_type(Integer rpa_type) {
		this.rpa_type = rpa_type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

}
