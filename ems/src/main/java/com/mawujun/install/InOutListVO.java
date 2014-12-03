package com.mawujun.install;


public class InOutListVO {

	private String id;
	private String inOut_id;//领用单或返库单的id
	private String ecode;//设备编码
	private Boolean isBad;//返库的时候是否损坏了
	
	private String subtype_name;
	private String prod_name;
	private String prod_spec;
	private String brand_name;
	private String supplier_name;
	private String style;

	private Integer num=1;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInOut_id() {
		return inOut_id;
	}
	public void setInOut_id(String inOut_id) {
		this.inOut_id = inOut_id;
	}
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}
	public Boolean getIsBad() {
		return isBad;
	}
	public void setIsBad(Boolean isBad) {
		this.isBad = isBad;
	}
	public String getSubtype_name() {
		return subtype_name;
	}
	public void setSubtype_name(String subtype_name) {
		this.subtype_name = subtype_name;
	}
	public String getProd_name() {
		return prod_name;
	}
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public String getSupplier_name() {
		return supplier_name;
	}
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getProd_spec() {
		return prod_spec;
	}
	public void setProd_spec(String prod_spec) {
		this.prod_spec = prod_spec;
	}
	
}
