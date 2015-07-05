package com.mawujun.adjust;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="ems_adjustlist",uniqueConstraints=@UniqueConstraint(columnNames={"adjust_id","ecode"}))
public class AdjustList extends UUIDEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String adjust_id;
	@Column(length=25)
	private String ecode;
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean isnew=false;//
//	private Integer out_num=1;//申请出库的数量,永远是1，主要用来比较入库的数量
//	private Integer in_num=0;//入库的数量,永远是1
	//@org.hibernate.annotations.Type(type="yes_no")
	//private Boolean status=false;//是否已经入库，true表示已经入库
	@Enumerated(EnumType.STRING)
	@Column(length=15)
	private AdjustListStatus adjustListStatus=AdjustListStatus.noin;
	
	private Date indate;//设备入库时间
	
	
	//下面两个字段只有在AdjustType.borrow的时候有值
	//和主表中的adjust_id_borrow是相反的，这个有值，adjust_id_borrow就没有值
	@Column(length=36)
	private String adjust_id_returnback;//归还单明细的id，存在多次归还，所以放在这里了
	@Column(length=25)
	private String ecode_returnback;//归还单的条码
	
	@Transient
	private String prod_id;//在归还的时候，需要判断设备类型的时候用的
	
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
	public Boolean getIsnew() {
		return isnew;
	}
	public void setIsnew(Boolean isnew) {
		this.isnew = isnew;
	}
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}

	public String getEcode_returnback() {
		return ecode_returnback;
	}
	public void setEcode_returnback(String ecode_returnback) {
		this.ecode_returnback = ecode_returnback;
	}
	public String getAdjust_id_returnback() {
		return adjust_id_returnback;
	}
	public void setAdjust_id_returnback(String adjust_id_returnback) {
		this.adjust_id_returnback = adjust_id_returnback;
	}


}
