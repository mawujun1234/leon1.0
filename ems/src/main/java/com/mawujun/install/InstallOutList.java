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
	@Column(length=20)
	private String ecode;//设备编码

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

}
