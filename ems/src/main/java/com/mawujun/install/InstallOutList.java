package com.mawujun.install;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;
import com.mawujun.store.IEcodeCache;

@Entity
@Table(name="ems_installoutlist")
public class InstallOutList  extends UUIDEntity  implements IEcodeCache {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(length=15)
	private String installOut_id;//领用单id
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
	private String pole_id;//点位id，当InstallOutListType==installout的时候，这个pole_id就有值，就表示这个设备被领用安装在哪个点位上了
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean isReturn=false;
	private Date returnDate;//归还时间。领用时间(任务提交的时间)
	
	@Column(length=36)
	private String installOutType_id;//领用类型的id,是损坏领用，还是被盗领用
	@Column(length=50)
	private String installOutType_content;//领用类型的二级
	
	@Column(length=100)
	private String memo;
	
	

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
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public String getInstallOutType_content() {
		return installOutType_content;
	}
	public void setInstallOutType_content(String installOutType_content) {
		this.installOutType_content = installOutType_content;
	}
	public Boolean getIsReturn() {
		return isReturn;
	}
	public void setIsReturn(Boolean isReturn) {
		this.isReturn = isReturn;
	}
	public String getPole_id() {
		return pole_id;
	}
	public void setPole_id(String pole_id) {
		this.pole_id = pole_id;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

}
