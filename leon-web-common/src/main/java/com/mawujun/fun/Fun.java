package com.mawujun.fun;

import java.util.ArrayList;
import java.util.List;

import com.mawujun.menu.Menu;
import com.mawujun.repository.idEntity.UUIDEntity;

public class Fun extends UUIDEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String code;//帮助记码
	
	private String text;
	private String url;
	//private String iconCls;
	private String reportCode;//等级关系代码
	
	private List<Fun> children=new ArrayList<Fun>();
	
	public void addChild(Fun child) {
		this.children.add(child);
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getReportCode() {
		return reportCode;
	}
	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<Fun> getChildren() {
		return children;
	}
	public void setChildren(List<Fun> children) {
		this.children = children;
	}

}
