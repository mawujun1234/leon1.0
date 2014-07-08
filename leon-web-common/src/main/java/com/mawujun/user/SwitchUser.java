package com.mawujun.user;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="leon_switchuser")
public class SwitchUser extends UUIDEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch=FetchType.LAZY)
	private User master;
	@ManyToOne(fetch=FetchType.LAZY)
	private User switchUser;
	
	public SwitchUser(){}
	
	public SwitchUser(String masterId,String switchUserId){
		this.master=new User(masterId);
		this.switchUser=new User(switchUserId);
	}
	
	
	public User getMaster() {
		return master;
	}
	public void setMaster(User master) {
		this.master = master;
	}
	public User getSwitchUser() {
		return switchUser;
	}
	public void setSwitchUser(User switchUser) {
		this.switchUser = switchUser;
	}
	
	
}
