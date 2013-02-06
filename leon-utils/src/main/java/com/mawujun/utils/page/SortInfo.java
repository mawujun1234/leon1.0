package com.mawujun.utils.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

/**
 * 排序的列
 * for PageRequest.getSortInfos()
 * @author badqiu
 * @see PageRequest#getSortInfos()
 */
public class SortInfo implements Serializable{
	
	private static final long serialVersionUID = 6959974032209696722L;
	
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	
	private String property;
	private String direction;
	
	public SortInfo() {
	}
	
	public SortInfo(String property, String direction) {
		super();
		this.property = property;
		this.direction = direction;
	}



	/**
	 * 如果没有设置 默认是升序
	 * @return
	 */
	public String getDirection() {
		if(StringUtils.hasText(this.direction)){
			return direction;
		} else {
			return SortInfo.ASC;
		}
		
	}

	public void setDirection(String dir) {
		this.direction = dir;
	}
	public void setDir(String dir) {
		this.direction = dir;
	}

	
	public String getProperty() {
		return property;
	}
	public void setProp(String prop) {
		this.property = prop;
	}
	public void setProperty(String prop) {
		this.property = prop;
	}

}
