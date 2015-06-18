package com.mawujun.inventory;

import java.io.Serializable;

public class Month_build_PK  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 123L;

	private Integer monthkey;//20140101这种形式

	private String prod_id;//品名id

	private String store_id;//仓库id，所属仓库

	
	public Month_build_PK() {  
        super();
    } 
	public Month_build_PK(Integer monthkey, String prod_id, String store_id) {
		super();
		this.monthkey = monthkey;
		this.prod_id = prod_id;
		this.store_id = store_id;
	}
	public Integer getMonthkey() {
		return monthkey;
	}
	public void setMonthkey(Integer monthkey) {
		this.monthkey = monthkey;
	}
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	
}
