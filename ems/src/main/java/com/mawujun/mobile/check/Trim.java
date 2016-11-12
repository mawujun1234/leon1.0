package com.mawujun.mobile.check;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 调整单，这时单据，时用来生成生命周期的原始数据
 * @author mawujun
 *
 */
@Entity
@Table(name="ems_trim")
public class Trim {
	@Id
	private Integer id;
	@Column(length=25)
	private String ecode;
	@Column(length=36)
	private String orginal_id;//调整前所在单位置id
	@Column(length=36)
	private String orginal_type;//调整前的位置的类型，点位，仓库，作业单位，维修中心
	@Column(length=36)
	private String target_id;//调整后所在位置的id
	@Column(length=36)
	private String target_type;//调整后所在位置的类型，点位，仓库，作业单位，维修中心
	
	@Column(length=50)
	private String creater;//创建者
	private Date createDate;//创建时间
	
	
	private Integer check_id;//盘点单id


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getEcode() {
		return ecode;
	}


	public void setEcode(String ecode) {
		this.ecode = ecode;
	}


	public String getOrginal_id() {
		return orginal_id;
	}


	public void setOrginal_id(String orginal_id) {
		this.orginal_id = orginal_id;
	}


	public String getOrginal_type() {
		return orginal_type;
	}


	public void setOrginal_type(String orginal_type) {
		this.orginal_type = orginal_type;
	}


	public String getTarget_id() {
		return target_id;
	}


	public void setTarget_id(String target_id) {
		this.target_id = target_id;
	}


	public String getTarget_type() {
		return target_type;
	}


	public void setTarget_type(String target_type) {
		this.target_type = target_type;
	}


	public String getCreater() {
		return creater;
	}


	public void setCreater(String creater) {
		this.creater = creater;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Integer getCheck_id() {
		return check_id;
	}


	public void setCheck_id(Integer check_id) {
		this.check_id = check_id;
	}
	

}
