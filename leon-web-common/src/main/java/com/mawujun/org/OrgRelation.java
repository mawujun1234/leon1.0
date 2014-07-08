package com.mawujun.org;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 组织节点关系
 * @author mawujun 16064988@qq.com  
 *
 */
@Entity
@Table(name="leon_orgrelation")
public class OrgRelation {
	@EmbeddedId
	private Id id = new Id();
	@ManyToOne
	@JoinColumn(name="parent_id",insertable = false,updatable = false)
	private Org parent;

	@ManyToOne
	@JoinColumn(name="child_id",insertable = false,updatable = false)
	private Org child;
	
	@ManyToOne
	@JoinColumn(name="orgDimenssion_id",insertable = false,updatable = false)
	private OrgDimenssion orgDimenssion;
	
	@Embeddable
	public static class Id implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@Column(name = "parent_id")
		private String parent_id;
		@Column(name = "child_id")
		private String child_id;
		@Column(name = "orgDimenssion_id")
		private String orgDimenssion_id;

		public Id() {
		}

		public Id(String parent_id, String child_id,String orgDimenssion_id) {
			this.parent_id = parent_id;
			this.child_id = child_id;
			this.orgDimenssion_id=orgDimenssion_id;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((child_id == null) ? 0 : child_id.hashCode());
			result = prime
					* result
					+ ((orgDimenssion_id == null) ? 0 : orgDimenssion_id
							.hashCode());
			result = prime * result
					+ ((parent_id == null) ? 0 : parent_id.hashCode());
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
			Id other = (Id) obj;
			if (child_id == null) {
				if (other.child_id != null)
					return false;
			} else if (!child_id.equals(other.child_id))
				return false;
			if (orgDimenssion_id == null) {
				if (other.orgDimenssion_id != null)
					return false;
			} else if (!orgDimenssion_id.equals(other.orgDimenssion_id))
				return false;
			if (parent_id == null) {
				if (other.parent_id != null)
					return false;
			} else if (!parent_id.equals(other.parent_id))
				return false;
			return true;
		}

		public String getParent_id() {
			return parent_id;
		}

		public void setParent_id(String parent_id) {
			this.parent_id = parent_id;
		}

		public String getChild_id() {
			return child_id;
		}

		public void setChild_id(String child_id) {
			this.child_id = child_id;
		}

		public String getOrgDimenssion_id() {
			return orgDimenssion_id;
		}

		public void setOrgDimenssion_id(String orgDimenssion_id) {
			this.orgDimenssion_id = orgDimenssion_id;
		}


		

	}
	
	

}
