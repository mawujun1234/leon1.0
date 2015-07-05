package com.mawujun.install;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	//在领用的时候时候是新设备
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean isnew=false;//
	//默认为借用，当任务提交后，该设备就变成领用
	//这个设备最终是被领用的还是借用的，判断条件是是否归还，即在返还的时候判断该设备是否正常归还了
	@Enumerated(EnumType.STRING)
	@Column(length=20)
	private InstallOutListType installOutListType=InstallOutListType.borrow; 
	
	@Column(length=36)
	private String installOutType_id;//领用类型的id,是损坏领用，还是被盗领用

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
	public InstallOutListType getInstallOutListType() {
		return installOutListType;
	}
	public void setInstallOutListType(InstallOutListType installOutListType) {
		this.installOutListType = installOutListType;
	}
	public Boolean getIsnew() {
		return isnew;
	}
	public void setIsnew(Boolean isnew) {
		this.isnew = isnew;
	}

}
