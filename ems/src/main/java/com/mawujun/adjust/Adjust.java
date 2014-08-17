package com.mawujun.adjust;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

/**
 * 调拨单
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
@Entity
@Table(name="ems_adjust")
public class Adjust implements IdEntity<String>{
	@Id
	@Column(length=18)
	private String id;
	@Column(length=8)
	private String status;//edit,carry,over
	
	@Column(length=36)
	private String str_out_id;//出库仓库
	private Date str_out_date;//创建时间
	@Column(length=36)
	private String str_out_oper_id;//创建人
	@Column(length=36)
	private String str_in_id;//入库仓库
	private Date str_in_date;//结束的时间,入库时间
	@Column(length=36)
	private String str_in_oper_id;//入库的人
	@Column(length=500)
	private String memo;
	
	public String getStatus_name() {
		return AdjustStatus.valueOf(this.getStatus()).getName();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getStr_out_id() {
		return str_out_id;
	}
	public void setStr_out_id(String str_out_id) {
		this.str_out_id = str_out_id;
	}
	public Date getStr_out_date() {
		return str_out_date;
	}
	public void setStr_out_date(Date str_out_date) {
		this.str_out_date = str_out_date;
	}
	public String getStr_out_oper_id() {
		return str_out_oper_id;
	}
	public void setStr_out_oper_id(String str_out_oper_id) {
		this.str_out_oper_id = str_out_oper_id;
	}
	public String getStr_in_id() {
		return str_in_id;
	}
	public void setStr_in_id(String str_in_id) {
		this.str_in_id = str_in_id;
	}
	public Date getStr_in_date() {
		return str_in_date;
	}
	public void setStr_in_date(Date str_in_date) {
		this.str_in_date = str_in_date;
	}
	public String getStr_in_oper_id() {
		return str_in_oper_id;
	}
	public void setStr_in_oper_id(String str_in_oper_id) {
		this.str_in_oper_id = str_in_oper_id;
	}


}
