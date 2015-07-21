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
@Table(name = "ems_equipment_workunit")
@IdClass(EquipmentWorkunitPK.class)
public class EquipmentWorkunit implements IdEntity<EquipmentWorkunitPK>{
	@Id
	@Column(length=36)
	private String ecode;//条码ecode或品名id
	@Id
	@Column(length=36)
	private String workunit_id;

	private Integer num=1;
	private Date inDate;//到作业单位手上的时间
	
	@Enumerated(EnumType.STRING)
	@Column(length=20)
	private EquipmentWorkunitType type;//进入的类型 出库单 或者是任务(维修任务)
	@Column(length=36)
	private String type_id;//出库单id，借用单id或者是任务id，这个任务id可能是维修任务的id，维修任务会把从杆位行拆下来的设备放到这里
	@Column(length=36)
	private String from_id;//来源的id，比如从仓库来，就是仓库id，如果是从杆位上来就是杆位id
	//以后还可能会用来判断 只能用于某个项目
	@Column(length=36)
	private String project_id;//如果type是拆回入库,那这个id就是设备原来所在点位的所属项目的id，如果是领用或借用返回，那就是领用或借用出去时指定的项目的id
	
	public EquipmentWorkunitPK getId() {
		return new EquipmentWorkunitPK(ecode,workunit_id);
	}
	public void setId(EquipmentWorkunitPK id) {
		//this.id = id;
		this.ecode=id.getEcode();
		this.workunit_id=id.getWorkunit_id();
	}
	public void setId(String  ecode,String workunit_id) {
		//this.id = id;
		this.ecode=ecode;
		this.workunit_id=workunit_id;
	}
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}

	public String getWorkunit_id() {
		return workunit_id;
	}
	public void setWorkunit_id(String workunit_id) {
		this.workunit_id = workunit_id;
	}
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
	public EquipmentWorkunitType getType() {
		return type;
	}
	public void setType(EquipmentWorkunitType type) {
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
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

}
