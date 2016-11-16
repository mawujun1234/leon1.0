package com.mawujun.check;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;


import com.mawujun.repository.idEntity.IdEntity;

/**
 * 盘点单的明细数据，明细的设备
 * @author mawujun
 *
 */
@Entity
@Table(name="ems_checklist")
@IdClass(CheckList.PK.class)
public class CheckList implements IdEntity<CheckList.PK>{
	@Id
	private String check_id;
	@Id
	@Column(length=25)
	private String ecode;
	
	public String getCheck_id() {
		return check_id;
	}

	public void setCheck_id(String check_id) {
		this.check_id = check_id;
	}

	public String getEcode() {
		return ecode;
	}

	public void setEcode(String ecode) {
		this.ecode = ecode;
	}

	public static class PK implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String check_id;
		private String ecode;
		
		public PK() {
			super();
		}
		public PK(String check_id, String ecode) {
			super();
			this.check_id = check_id;
			this.ecode = ecode;
		}
		public String getCheck_id() {
			return check_id;
		}
		public void setCheck_id(String check_id) {
			this.check_id = check_id;
		}
		public String getEcode() {
			return ecode;
		}
		public void setEcode(String ecode) {
			this.ecode = ecode;
		}
	}

	@Override
	public void setId(PK id) {
		// TODO Auto-generated method stub
		this.check_id=id.getCheck_id();
		this.ecode=id.getEcode();
		
	}

	@Override
	public PK getId() {
		// TODO Auto-generated method stub
		return new CheckList.PK(this.check_id,this.ecode);
	}

}
