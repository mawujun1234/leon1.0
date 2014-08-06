package com.mawujun.install;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

/**
 * 工作组持有的设备
 * @author mawujun 16064988@qq.com  
 *
 */
@Entity
@Table(name="ems_workunitequipment")
public class WorkUnitEquipment  extends UUIDEntity{
	@Column(length=36)
	private String workunit_id;
	@Column(length=20)
	private String ecode;
	@Column(length=15)
	private String outStore_id;//入库单号
	
	public String getWorkunit_id() {
		return workunit_id;
	}
	public void setWorkunit_id(String workunit_id) {
		this.workunit_id = workunit_id;
	}
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}
	public String getOutStore_id() {
		return outStore_id;
	}
	public void setOutStore_id(String outStore_id) {
		this.outStore_id = outStore_id;
	}


}
