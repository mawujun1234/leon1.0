package com.mawujun.generator;

public class ExtenConfig {
	boolean rowediting=false;
	boolean editable=false;
	//boolean pageable=true;
	boolean userModel=true;
	boolean createDelUpd=true;
	
	
	public boolean isRowediting() {
		return rowediting;
	}

	public void setRowediting(boolean rowediting) {
		this.rowediting = rowediting;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isUserModel() {
		return userModel;
	}

	public void setUserModel(boolean userModel) {
		this.userModel = userModel;
	}


	public boolean isCreateDelUpd() {
		return createDelUpd;
	}

	public void setCreateDelUpd(boolean createDelUpd) {
		this.createDelUpd = createDelUpd;
	}

}
