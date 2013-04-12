package com.mawujun.fun;

import com.mawujun.repository.idEntity.UUIDEntity;

public class Fun extends UUIDEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String text;
	private String url;
	//private String iconCls;
	private String reportCode;//等级关系代码
	
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

}
