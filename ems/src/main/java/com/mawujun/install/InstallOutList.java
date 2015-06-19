package com.mawujun.install;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="ems_installoutlist")
public class InstallOutList  extends UUIDEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(length=15)
	private String installOut_id;//入库单id
	@Column(length=25)
	private String ecode;//设备编码
	
	@Column(length=36)
	private String installOutType_id;//领用类型的id

	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}
	public String getInstallOut_id() {
		return installOut_id;
	}
	public void setInstallOut_id(String installOut_id) {
		this.installOut_id = installOut_id;
	}
	public String getInstallOutType_id() {
		return installOutType_id;
	}
	public void setInstallOutType_id(String installOutType_id) {
		this.installOutType_id = installOutType_id;
	}

}
