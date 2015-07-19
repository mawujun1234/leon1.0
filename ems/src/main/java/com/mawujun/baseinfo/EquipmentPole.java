package com.mawujun.baseinfo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

@Entity
@Table(name = "ems_equipment_pole")
@IdClass(EquipmentPolePK.class)
public class EquipmentPole implements IdEntity<EquipmentPolePK>{
	@Id
	@Column(length=36)
	private String ecode;//条码ecode或品名id
	@Id
	@Column(length=36)
	private String pole_id;

	//@Column(length=36)
	//private String workunit_id;//安装的作业单位
	private Integer num=1;
	private Date inDate;//安装时间或者是维修安装上去的时间
	
	@Enumerated(EnumType.STRING)
	@Column(length=20)
	private EquipmentPoleType type;//安装到杆位上的类型 是新安装上去的，还是维修安装上去的
	@Column(length=36)
	private String type_id;//任务id
	@Column(length=36)
	private String from_id;//来源的id，比如从仓库来，就是仓库id，如果是从杆位上来就是杆位id
	
	public EquipmentPolePK getId() {
		return new EquipmentPolePK(ecode,pole_id);
	}
	public void setId(EquipmentPolePK id) {
		//this.id = id;
		this.ecode=id.getEcode();
		this.pole_id=id.getPole_id();
	}
	public void setId(String  ecode,String pole_id) {
		//this.id = id;
		this.ecode=ecode;
		this.pole_id=pole_id;
	}
	
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}
	public String getPole_id() {
		return pole_id;
	}
	public void setPole_id(String pole_id) {
		this.pole_id = pole_id;
	}
//	public String getWorkunit_id() {
//		return workunit_id;
//	}
//	public void setWorkunit_id(String workunit_id) {
//		this.workunit_id = workunit_id;
//	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Date getInDate() {
		return inDate;
	}
	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}
	public EquipmentPoleType getType() {
		return type;
	}
	public void setType(EquipmentPoleType type) {
		this.type = type;
	}
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getFrom_id() {
		return from_id;
	}
	public void setFrom_id(String from_id) {
		this.from_id = from_id;
	}

}
