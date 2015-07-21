package com.mawujun.install;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	@Column(length=25)
	private String ecode;//设备编码
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean isBad=false;//在返回的时候，设备是否已经损坏	
	
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean isnew=false;//
	
	@Enumerated(EnumType.STRING)
	@Column(length=20)
	private InstallInListType installInListType;//返回的设备是领用出去，没使用就马上返回 ，还是从点位上拆下来返回的设备
	@Column(length=36)
	private String installout_id;//如果type是领用返回，那这个id就是领用单的id，否则就为null
	@Column(length=36)
	private String project_id;//如果type是拆回入库,那这个id就是设备原来所在点位的所属项目的id，如果是领用或借用返回，那就是领用或借用出去时指定的项目的id


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
	public InstallInListType getInstallInListType() {
		return installInListType;
	}
	public void setInstallInListType(InstallInListType type) {
		this.installInListType = type;
	}
	public String getInstallout_id() {
		return installout_id;
	}
	public void setInstallout_id(String installout_id) {
		this.installout_id = installout_id;
	}
	public Boolean getIsnew() {
		return isnew;
	}
	public void setIsnew(Boolean isnew) {
		this.isnew = isnew;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
}
