package com.mawujun.mobile.task;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;
import com.mawujun.repository.idEntity.UUIDEntity;

/**
 * 巡检任务类型
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
@Entity
@Table(name="ems_patroltasktype")
public class PatrolTaskType extends UUIDEntity {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//	@Id
//	@Column(length=30)
//	private String code;
	@Column(length=50)
	private String name;
//	
//	
//	public String getCode() {
//		return code;
//	}
//	public void setCode(String code) {
//		this.code = code;
//	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


}
