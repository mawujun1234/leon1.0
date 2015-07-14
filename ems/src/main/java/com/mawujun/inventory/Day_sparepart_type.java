package com.mawujun.inventory;

import java.util.List;

public class Day_sparepart_type{
	private String type_id;
	private String type_name;
	private List<Day_sparepart_subtype> subtypes;
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public List<Day_sparepart_subtype> getSubtypes() {
		return subtypes;
	}
	public void setSubtypes(List<Day_sparepart_subtype> subtypes) {
		this.subtypes = subtypes;
	}
}
