package com.mawujun.install;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="ems_installinlist")
public class InstallInList  extends UUIDEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(length=15)
	private String installIn_id;//入库单id
	@Column(length=20)
	private String ecode;//设备编码
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean isBad;//在返回的时候，设备是否已经损坏

	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}
	public String getInstallIn_id() {
		return installIn_id;
	}
	public void setInstallIn_id(String installIn_id) {
		this.installIn_id = installIn_id;
	}
	public Boolean getIsBad() {
		return isBad;
	}
	public void setIsBad(Boolean isBad) {
		this.isBad = isBad;
	}
}
