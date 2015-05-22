package com.mawujun.baseinfo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
@MappedSuperclass
public class EquipmentTypeAbstract {

	@Id
	@Column(length=3)
	private String id;
	@Column(length=30)
	private String name;
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean status=true;
	
//	@Column(updatable=false)
//	private Integer levl;
	@Column(length=2)
	private String parent_id;
	@Column(length = 100)
	private String memo;//描述信息，例如 国内标配，样品等信息,先放着，现在只有 品名里面会用到
	
	
	
	
	
	@Transient
	private Boolean leaf;
	
//	@Transient
//	private String unit; //有用，收集前端信息的时候
//	@Transient
//	private String spec;//有用，收集前端信息的时候
//	@Transient
//	private String style;// 型号
//	@Transient
//	private String brand_id;//有用，收集前端信息的时候
	
	
	public String getStatus_name() {
		if(this.getStatus()==true){
			return "有效";
		} else {
			return "无效";
		}
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return this.getName()+"("+this.getId()+")";
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public Boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}

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
