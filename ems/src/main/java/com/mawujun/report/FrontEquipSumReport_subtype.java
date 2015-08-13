package com.mawujun.report;

import java.util.List;

/**
 * 这里存放的是子类
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public class FrontEquipSumReport_subtype {
	private String subtype_id;
	private String subtype_name;
	private List<FrontEquipSumReport_prod> prods;
	
	public String getSubtype_id() {
		return subtype_id;
	}
	public void setSubtype_id(String subtype_id) {
		this.subtype_id = subtype_id;
	}
	public String getSubtype_name() {
		return subtype_name;
	}
	public void setSubtype_name(String subtype_name) {
		this.subtype_name = subtype_name;
	}
	public List<FrontEquipSumReport_prod> getProds() {
		return prods;
	}
	public void setProds(List<FrontEquipSumReport_prod> prods) {
		this.prods = prods;
	}
	
}
