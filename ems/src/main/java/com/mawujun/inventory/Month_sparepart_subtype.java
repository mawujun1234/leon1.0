package com.mawujun.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Month_sparepart_subtype {
	private String subtype_id;
	private String subtype_name;
	private List<Day_sparepart_prod> prodes;
	//key 是prod_id,value是按天进行排序的数据,value_key是daykey
	private List<Map<Integer,Day_sparepart_prod>> prodes_list;
	
	/**
	 * 对设备进行行列转换，转换成
	 * @author mawujun email:160649888@163.com qq:16064988
	 */
	public void changeProdes() {
		prodes_list=new ArrayList<Map<Integer,Day_sparepart_prod>>();
		if(prodes==null){
			return;
		}
		String key_temp="";
		Map<Integer,Day_sparepart_prod> prodes_map_temp=null;
		for(Day_sparepart_prod prod:prodes){
			String key=prod.getkey();
			if(!key_temp.equals(key)){
				key_temp=key;		
				HashMap<Integer,Day_sparepart_prod> map=new LinkedHashMap<Integer,Day_sparepart_prod>();
				//prodes_list.put(key_temp,map);
				prodes_list.add(map);
			}
			prodes_map_temp=prodes_list.get(prodes_list.size()-1);
			prodes_map_temp.put(prod.getDaykey(), prod);
		}
		
		//再对每一个按时间进行排序
		
	}
	
//	int index=0;
//	public Day_sparepart_prod next(Integer daykey) {
//		Day_sparepart_prod prod= prodes_list.get(index).get(daykey);
//		index++;
//		return prod;
//	}
//	/**
//	 * 获取第一行
//	 * @author mawujun email:160649888@163.com qq:16064988
//	 * @return
//	 */
//	public Day_sparepart_prod first() {
//		return prodes_list.values().iterator().next().values().iterator().next();
//	}
//	
//	public Integer getProdSize() {
//		return prodes_list.size();
//	}
//
//	public Day_sparepart_prod getProd(Integer daykey) {
//		return prodes_list.get(prod_id).get(daykey);
//	}
	
	
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
	public void setProdes(List<Day_sparepart_prod> prodes) {
		this.prodes = prodes;
	}

	public List<Map<Integer, Day_sparepart_prod>> getProdes_list() {
		return prodes_list;
	}
}
