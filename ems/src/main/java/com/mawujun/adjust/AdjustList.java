package com.mawujun.adjust;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="ems_adjustlist",uniqueConstraints=@UniqueConstraint(columnNames={"adjust_id","ecode"}))
public class AdjustList extends UUIDEntity{
	private String adjust_id;
	private String ecode;
//	private Integer out_num=1;//申请出库的数量,永远是1
//	private Integer in_num=1;//入库的数量,永远是1
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean status=false;//是否已经入库，true表示已经入库
	
	public String getAdjust_id() {
		return adjust_id;
	}
	public void setAdjust_id(String adjust_id) {
		this.adjust_id = adjust_id;
	}
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}

	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
}
