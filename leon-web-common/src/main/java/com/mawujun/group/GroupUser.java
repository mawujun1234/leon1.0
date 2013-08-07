package com.mawujun.group;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

@Entity  
@Table(name="leon_group_user") 
public class GroupUser  implements IdEntity<GroupUserPK>{
	@EmbeddedId  
	private GroupUserPK id;
	private Date createDate;

	@Override
	public void setId(GroupUserPK id) {
		// TODO Auto-generated method stub
		this.id=id;
	}

	@Override
	public GroupUserPK getId() {
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
