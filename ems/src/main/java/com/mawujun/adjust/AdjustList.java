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
	private Integer out_num=1;//申请出库的数量
	private Integer in_num=1;//入库的数量
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
	public Integer getOut_num() {
		return out_num;
	}
	public void setOut_num(Integer out_num) {
		this.out_num = out_num;
	}
	public Integer getIn_num() {
		return in_num;
	}
	public void setIn_num(Integer in_num) {
		this.in_num = in_num;
	}
}
