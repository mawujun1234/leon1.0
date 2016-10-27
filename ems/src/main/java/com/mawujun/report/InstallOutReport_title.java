package com.mawujun.report;

/**
 * 动态标题
 * @author mawujun
 *
 */
public class InstallOutReport_title {
	private String installouttype_name;//一级领用类型
	private String installouttype_content;//二级领用类型
	private Integer cell_index;//显示在哪一列
	
	
	public String getInstallouttype_name() {
		return installouttype_name;
	}
	public void setInstallouttype_name(String installouttype_name) {
		this.installouttype_name = installouttype_name;
	}
	public String getInstallouttype_content() {
		return installouttype_content;
	}
	public void setInstallouttype_content(String installouttype_content) {
		this.installouttype_content = installouttype_content;
	}
	public Integer getCell_index() {
		return cell_index;
	}
	public void setCell_index(Integer cell_index) {
		this.cell_index = cell_index;
	}
	
	
}
