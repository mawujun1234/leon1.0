package com.mawujun.generator;


public class ExtenConfig_Service {
	private Boolean create=true;
	private Boolean update=true;
	private Boolean destroy=true;
	
	public static ExtenConfig_Service getInstance(){
		return new ExtenConfig_Service();
	}
	
	public Boolean getCreate() {
		return create;
	}
	public ExtenConfig_Service setCreate(Boolean create) {
		this.create = create;
		return this;
	}
	public Boolean getUpdate() {
		return update;
	}
	public ExtenConfig_Service setUpdate(Boolean update) {
		this.update = update;
		return this;
	}
	public Boolean getDestroy() {
		return destroy;
	}
	public ExtenConfig_Service setDestroy(Boolean destroy) {
		this.destroy = destroy;
		return this;
	}

}
