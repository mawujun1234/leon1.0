package com.mawujun.report;

import java.util.Date;

import com.mawujun.repair.RepairStatus;

public class RepairReport {
	private String ecode;//
	private String str_out_name;//出库仓库
	private String brand_name;//品牌
	//private String prod_name;//品名
	private String prod_style;//型号
	private String subtype_name;//类型
	
	//private String repair_take_time;//维修时间，维修花费掉的时间
	private Date str_out_date;//送修时间
	private String broken_reson;//故障原因
	private String handler_method;//解决方案
	private RepairStatus status;//维修结果，如果是scrap就是报废，否则就是修好了
	
	private Date send_date;//返厂时间，就是寄到厂方的时间
	private Date receive_date;//返回时间，外修的时候
	
	private Date str_in_date;//入库时间

	public String getStatus_name() {
		if(status==null){
			return null;
		}
		return status.getName();
	}
	
	/**
	 * 总耗时
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	public String getRepair_take_time(){
		if(this.getStr_in_date()!=null){
			long ltime=this.getStr_in_date().getTime()-this.getStr_out_date().getTime();
			//String day=(ltime/(60*60*1000))+"小时"+(ltime%(60*60*1000))/(60*1000)+"分钟";
			long day=ltime/(24*60*60*1000);
			long hour=(ltime-(day*(24*60*60*1000)))/(60*60*1000);
			
			return day+"天"+hour+"小时";
			//String day=(ltime/(60*60*1000))+"小时"+(ltime%(60*60*1000))/(60*1000)+"分钟";
			//return day;
		}
		return "";
	}
	
	
	public String getEcode() {
		return ecode;
	}

	public void setEcode(String ecode) {
		this.ecode = ecode;
	}

	public String getStr_out_name() {
		return str_out_name;
	}

	public void setStr_out_name(String str_out_name) {
		this.str_out_name = str_out_name;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getProd_style() {
		return prod_style;
	}

	public void setProd_style(String prod_style) {
		this.prod_style = prod_style;
	}

	public String getSubtype_name() {
		return subtype_name;
	}

	public void setSubtype_name(String subtype_name) {
		this.subtype_name = subtype_name;
	}

	public Date getStr_out_date() {
		return str_out_date;
	}

	public void setStr_out_date(Date str_out_date) {
		this.str_out_date = str_out_date;
	}

	public String getBroken_reson() {
		return broken_reson;
	}

	public void setBroken_reson(String broken_reson) {
		this.broken_reson = broken_reson;
	}

	public String getHandler_method() {
		return handler_method;
	}

	public void setHandler_method(String handler_method) {
		this.handler_method = handler_method;
	}

	public RepairStatus getStatus() {
		return status;
	}

	public void setStatus(RepairStatus status) {
		this.status = status;
	}

	public Date getSend_date() {
		return send_date;
	}

	public void setSend_date(Date send_date) {
		this.send_date = send_date;
	}

	public Date getReceive_date() {
		return receive_date;
	}

	public void setReceive_date(Date receive_date) {
		this.receive_date = receive_date;
	}

	public Date getStr_in_date() {
		return str_in_date;
	}

	public void setStr_in_date(Date str_in_date) {
		this.str_in_date = str_in_date;
	}
}
