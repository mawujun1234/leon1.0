package com.mawujun.generator;


public class ServiceExtenConfig {
	private Boolean create=true;
	private Boolean update=true;
	private Boolean destroy=true;
	
	public static ServiceExtenConfig getInstance(){
		return new ServiceExtenConfig();
	}
	
	public Boolean getCreate() {
		return create;
	}
	public ServiceExtenConfig setCreate(Boolean create) {
		this.create = create;
		return this;
	}
	public Boolean getUpdate() {
		return update;
	}
	public ServiceExtenConfig setUpdate(Boolean update) {
		this.update = update;
		return this;
	}
	public Boolean getDestroy() {
		return destroy;
	}
	public ServiceExtenConfig setDestroy(Boolean destroy) {
		this.destroy = destroy;
		return this;
	}

}
