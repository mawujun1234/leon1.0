package com.mawujun.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.mawujun.baseinfo.WorkUnit;

@Entity(name="sys_user_workunit")
public class UserWorkunit implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @ManyToOne
	private WorkUnit workunit;
	@Id
    @ManyToOne
	private User user;
	public UserWorkunit() {
		super();
	}

	public UserWorkunit(WorkUnit workunit, User user) {
		super();
		this.workunit = workunit;
		this.user = user;
	}

	public WorkUnit getWorkunit() {
		return workunit;
	}

	public void setWorkunit(WorkUnit workunit) {
		this.workunit = workunit;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((workunit == null) ? 0 : workunit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserWorkunit other = (UserWorkunit) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (workunit == null) {
			if (other.workunit != null)
				return false;
		} else if (!workunit.equals(other.workunit))
			return false;
		return true;
	}
	
}
