package com.mawujun.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day_sparepart_subtype {
	private String subtype_id;
	private String subtype_name;
	private List<Day_sparepart_prod> prodes;
	//key 是prod_id,value是按天进行排序的数据,value_key是daykey
	private Map<String,Map<Integer,Day_sparepart_prod>> prodes_map;
	/**
	 * 对设备进行行列转换，转换成
	 * @author mawujun email:160649888@163.com qq:16064988
	 */
	public void changeProdes() {
		prodes_map=new HashMap<String,Map<Integer,Day_sparepart_prod>>();
		if(prodes==null){
			return;
		}
		String prod_id_temp="";
		Map<Integer,Day_sparepart_prod> prodes_map_temp=null;
		for(Day_sparepart_prod prod:prodes){
			String key=prod.getkey();
			if(!prod_id_temp.equals(key)){
				prod_id_temp=key;		
				HashMap<Integer,Day_sparepart_prod> map=new HashMap<Integer,Day_sparepart_prod>();
				prodes_map.put(prod_id_temp,map);
			}
			prodes_map_temp=prodes_map.get(prod_id_temp);
			prodes_map_temp.put(prod.getDaykey(), prod);
		}
		
		//再对每一个按时间进行排序
		
	}
	
//	int index=0;
//	public Day_sparepart_prod next(Integer daykey) {
//		Day_sparepart_prod prod= prodes_map.get(index).get(daykey);
//		index++;
//		return prod;
//	}
	/**
	 * 获取第一行
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	public Day_sparepart_prod first() {
		return prodes_map.values().iterator().next().values().iterator().next();
	}
	
	public Integer getProdSize() {
		return prodes_map.size();
	}
	public Day_sparepart_prod getProd(String prod_id,Integer daykey) {
		return prodes_map.get(prod_id).get(daykey);
	}
	
	
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
}
