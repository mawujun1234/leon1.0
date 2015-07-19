package com.mawujun.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Sheet;

public class HideColumn {
	private Map<Integer,Integer> hideColumn=new HashMap<Integer,Integer>();
	
	public void add(Integer index,Integer value){
		if(value==null){
			value=0;
		}
		if(hideColumn.get(index)==null){
			hideColumn.put(index, value);
		} else {
			hideColumn.put(index, hideColumn.get(index)+value);
		}
	}
	
	public int size(){
		return hideColumn.size();
	}
	
	public void hiddenColumn(Sheet sheet) {
		for(Entry<Integer,Integer> entry:hideColumn.entrySet()){
			if(entry.getValue()==0){
				sheet.setColumnHidden(entry.getKey(), true);
			}
		}
	}
}
