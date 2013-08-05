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
	
	private String prop;
	private String dir;
	
	public SortInfo() {
	}
	
	public SortInfo(String property, String direction) {
		super();
		this.prop = property;
		this.dir = direction;
	}



	/**
	 * 如果没有设置 默认是升序
	 * @return
	 */
	public String getDir() {
		if(StringUtils.hasText(this.dir)){
			return dir;
		} else {
			return SortInfo.ASC;
		}
		
	}

	public void setDir(String dir) {
		this.dir = dir;
	}
//	public void setDir(String dir) {
//		this.dir = dir;
//	}

	
	public String getProp() {
		return prop;
	}
//	public void setProp(String prop) {
//		this.prop = prop;
//	}
	public void setProp(String prop) {
		this.prop = prop;
	}

}
