package com.mawujun.generator;


public class ExtenConfig_Repository {
	private Boolean create=true;
	private Boolean update=true;
	private Boolean destroy=true;
	
	public static ExtenConfig_Repository getInstance(){
		return new ExtenConfig_Repository();
	}
	
	public Boolean getCreate() {
		return create;
	}
	public ExtenConfig_Repository setCreate(Boolean create) {
		this.create = create;
		return this;
	}
	public Boolean getUpdate() {
		return update;
	}
	public ExtenConfig_Repository setUpdate(Boolean update) {
		this.update = update;
		return this;
	}
	public Boolean getDestroy() {
		return destroy;
	}
	public ExtenConfig_Repository setDestroy(Boolean destroy) {
		this.destroy = destroy;
		return this;
	}

}
