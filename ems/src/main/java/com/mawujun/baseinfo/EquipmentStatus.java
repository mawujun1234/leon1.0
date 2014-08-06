package com.mawujun.baseinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

/**
 * 0:未入库
 * 1：已入库
 * 2:正常出库(等待安装)
 * 3:使用中
 * 4:损坏----实施人员把换下来的设备标记为损坏
 * 5:入库待维修
 * 6:发往维修中心-----仓库出库--维修中心还未入库的状态还有其他任何在途
 * 7:外修中---当维修中心把维修单改为外修的时候，就变为外修中
 * 8:维修中 -----维修中心入库后，就变成了维修中
 * 9:维修后出库----维修完成后，维修中心出库，等待仓库入库，仓库入库后就变成了“在库”
 * 
 * 
 * 
 * 30：报废
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
@Entity
@Table(name="ems_equipmentstatus")
public class EquipmentStatus implements IdEntity<Integer>{
	@Id
	private Integer status;
	@Column(length=20)
	private String name;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		this.status=id;
	}
	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return status;
	}

}
