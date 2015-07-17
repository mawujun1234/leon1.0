package com.mawujun.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Month_sparepart_subtype {
	private String subtype_id;
	private String subtype_name;
	private List<Month_sparepart_prod> prodes;

	
	
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
//	public List<Day_sparepart_prod> getProdes() {
//		return prodes;
//	}
	public void setProdes(List<Month_sparepart_prod> prodes) {
		this.prodes = prodes;
	}

	public List<Month_sparepart_prod> getProdes() {
		return prodes;
	}
}
