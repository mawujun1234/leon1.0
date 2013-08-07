package com.mawujun.group;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

@Entity  
@Table(name="leon_group_role") 
public class GroupRole  implements IdEntity<GroupRolePK> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EmbeddedId  
	private GroupRolePK id;
	private Date createDate;

	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Override
	public void setId(GroupRolePK id) {
		// TODO Auto-generated method stub
		this.id=id;
	}
	@Override
	public GroupRolePK getId() {
		// TODO Auto-generated method stub
		return this.id;
	}


}
