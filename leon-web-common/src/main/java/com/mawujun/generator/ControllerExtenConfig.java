package com.mawujun.generator;

public class ControllerExtenConfig {
	private ShowModel showModel=ShowModel.page;
	public static ControllerExtenConfig getInstance(){
		return new ControllerExtenConfig();
	}
	//以树形的结构返回数据，还是grid分页的形式返回数据
	public static enum ShowModel {  
		  tree,page ,normal
	}
	public ShowModel getShowModel() {
		return showModel;
	}
	public void setShowModel(ShowModel showModel) {
		this.showModel = showModel;
	} 
}
