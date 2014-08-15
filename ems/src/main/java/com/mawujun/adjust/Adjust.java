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
	private String store_out_id;//出库仓库
	
	private String store_in_id;//入库仓库
	
	private Date createDate;//创建时间
	@Column(length=36)
	private String creater;//创建人
	
	private Date overDate;//结束的时间,入库时间
	@Column(length=36)
	private String overer;//入库的人
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
	public String getStore_out_id() {
		return store_out_id;
	}
	public void setStore_out_id(String store_out_id) {
		this.store_out_id = store_out_id;
	}
	public String getStore_in_id() {
		return store_in_id;
	}
	public void setStore_in_id(String store_in_id) {
		this.store_in_id = store_in_id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Date getOverDate() {
		return overDate;
	}
	public void setOverDate(Date overDate) {
		this.overDate = overDate;
	}
	public String getOverer() {
		return overer;
	}
	public void setOverer(String overer) {
		this.overer = overer;
	}

}
