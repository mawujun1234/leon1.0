package com.mawujun.group;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

@Entity  
@Table(name="leon_user_group") 
public class UserGroup  implements IdEntity<UserGroupPK>{
	@EmbeddedId  
	private UserGroupPK id;
	private Date createDate;

	@Override
	public void setId(UserGroupPK id) {
		// TODO Auto-generated method stub
		this.id=id;
	}

	@Override
	public UserGroupPK getId() {
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
