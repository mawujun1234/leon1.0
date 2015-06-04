package com.mawujun.install;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

/**
 * 领用类型，如有：日常维护领用，被盗领用，等
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
@Entity
@Table(name="ems_installouttype")
public class InstallOutType extends UUIDEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String memo;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

}
