package com.mawujun.user;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

@Entity  
@Table(name="leon_user_role")  
public class UserRole implements IdEntity<UserRolePK>{
	
	@EmbeddedId  
	private UserRolePK id;
	private Date createDate;

	@Override
	public void setId(UserRolePK id) {
		// TODO Auto-generated method stub
		this.id=id;
	}

	@Override
	public UserRolePK getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	

}
