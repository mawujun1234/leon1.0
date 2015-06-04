package com.mawujun.install;

import java.util.Date;

/**
 * 领用返库的查询的对象
 * @author mawujun 16064988@qq.com  
 *
 */
public class InOutVO {
	
	private String id;//单号，年日日时分秒
	private String store_id;//仓库id
	private String operater;//仓管id
	private Date operateDate;//入库时间
	private String workUnit_id;//作业单位
	private String memo;
	
	private String type;
	
	private String store_name;
	private String workUnit_name;
	//private String type_name;
	
	public String getType_name() {
		if("in".equals(type)){
			return "返库";
		} else if("out".equals(type)){
			return "领用";
		} else {
			return null;
		}
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getOperater() {
		return operater;
	}
	public void setOperater(String operater) {
		this.operater = operater;
	}
	public Date getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}
	public String getWorkUnit_id() {
		return workUnit_id;
	}
	public void setWorkUnit_id(String workUnit_id) {
		this.workUnit_id = workUnit_id;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	public String getWorkUnit_name() {
		return workUnit_name;
	}
	public void setWorkUnit_name(String workUnit_name) {
		this.workUnit_name = workUnit_name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
