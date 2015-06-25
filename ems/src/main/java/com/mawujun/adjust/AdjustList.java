package com.mawujun.adjust;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="ems_adjustlist",uniqueConstraints=@UniqueConstraint(columnNames={"adjust_id","ecode"}))
public class AdjustList extends UUIDEntity{
	private String adjust_id;
	private String ecode;
//	private Integer out_num=1;//申请出库的数量,永远是1，主要用来比较入库的数量
//	private Integer in_num=0;//入库的数量,永远是1
	//@org.hibernate.annotations.Type(type="yes_no")
	//private Boolean status=false;//是否已经入库，true表示已经入库
	@Enumerated(EnumType.STRING)
	@Column(length=15)
	private AdjustListStatus adjustListStatus=AdjustListStatus.noin;
	
	private Date indate;//设备入库时间
	
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
	public AdjustListStatus getAdjustListStatus() {
		return adjustListStatus;
	}
	public void setAdjustListStatus(AdjustListStatus adjustListStatus) {
		this.adjustListStatus = adjustListStatus;
	}
	public Date getIndate() {
		return indate;
	}
	public void setIndate(Date indate) {
		this.indate = indate;
	}


}
