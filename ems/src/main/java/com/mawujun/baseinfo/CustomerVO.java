package com.mawujun.baseinfo;

public class CustomerVO extends Customer {
	private Boolean expanded=false;
	private Boolean leaf=false;
	
	public Boolean getLeaf() {
		if(this.getType()!=2){
			return true;
		} else {
			return leaf;
		}
		
	}
	
	
	public Boolean getExpanded() {
		return expanded;
	}
	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}
	
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	

}
