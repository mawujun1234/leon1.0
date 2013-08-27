package com.mawujun.help;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="leon_help")
public class Help extends UUIDEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String path;
	
	private String funId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFunId() {
		return funId;
	}

	public void setFunId(String funId) {
		this.funId = funId;
	}
}
