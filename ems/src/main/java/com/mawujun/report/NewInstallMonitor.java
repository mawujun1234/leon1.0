package com.mawujun.report;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 安装监控报表
 * @author mawujun 16064988@qq.com  
 *
 */
public class NewInstallMonitor {
	private String id;
	private String name;
	private BigDecimal pole_nums;//杆位数
	
	private BigDecimal currt_new;// 本期新建任务
	private BigDecimal currt_complete;// 本期完成任务
	private BigDecimal currt_avgcompletetime;//本期完成平均时间
	private BigDecimal currt_submited;//本期提交&完成任务数
	private BigDecimal currt_avgsubmitedtime;//本期提交&完成平均时间
	
	
	private BigDecimal total_nums;//总任务数
	private BigDecimal total_complete;//总的完成数
	//private BigDecimal total_complete_rate;//总完成率
	private BigDecimal total_avgcompletetime;// 平均完成时间 
	private BigDecimal total_submited;//提交&完成任务数
	private BigDecimal total_avgsubmitedtime;//平均提交时间
	
	
	public String formateTime(BigDecimal time){
		if(time==null || time.intValue()==0){
			return "";
		}
		BigDecimal day=time.divide(new BigDecimal(60*24),0,RoundingMode.HALF_DOWN);
		BigDecimal hour=time.remainder(new BigDecimal(60*24)).divide(new BigDecimal(60),0,RoundingMode.HALF_DOWN);
		return day+"天"+hour+"小时";
	}
	public String getCurrt_avgsubmitedtime() {
		return formateTime(currt_avgsubmitedtime);
	}
	public String getCurrt_avgcompletetime() {
		return formateTime(currt_avgcompletetime);
	}
	public String getTotal_avgcompletetime() {
		return formateTime(total_avgcompletetime);
	}
	public String getTotal_avgsubmitedtime() {
		return formateTime(total_avgsubmitedtime);
	}
	public String getTotal_complete_rate() {
		if(this.getTotal_complete()==null || this.getTotal_complete().intValue()==0){
			return "";
		}
		return this.getTotal_complete().divide(this.getTotal_nums(),2,RoundingMode.HALF_UP).multiply(new BigDecimal(100))+"%";
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getPole_nums() {
		return pole_nums;
	}
	public void setPole_nums(BigDecimal pole_nums) {
		this.pole_nums = pole_nums;
	}
	public BigDecimal getCurrt_new() {
		return currt_new;
	}
	public void setCurrt_new(BigDecimal currt_new) {
		this.currt_new = currt_new;
	}
	public BigDecimal getCurrt_complete() {
		return currt_complete;
	}
	public void setCurrt_complete(BigDecimal currt_complete) {
		this.currt_complete = currt_complete;
	}
	
	public void setCurrt_avgcompletetime(BigDecimal currt_avgcompletetime) {
		this.currt_avgcompletetime = currt_avgcompletetime;
	}
	public BigDecimal getCurrt_submited() {
		return currt_submited;
	}
	public void setCurrt_submited(BigDecimal currt_submited) {
		this.currt_submited = currt_submited;
	}
	
	public void setCurrt_avgsubmitedtime(BigDecimal currt_avgsubmitedtime) {
		this.currt_avgsubmitedtime = currt_avgsubmitedtime;
	}
	public BigDecimal getTotal_nums() {
		return total_nums;
	}
	public void setTotal_nums(BigDecimal total_nums) {
		this.total_nums = total_nums;
	}
	public BigDecimal getTotal_complete() {
		return total_complete;
	}
	public void setTotal_complete(BigDecimal total_complete) {
		this.total_complete = total_complete;
	}
	
	
	public void setTotal_avgcompletetime(BigDecimal total_avgcompletetime) {
		this.total_avgcompletetime = total_avgcompletetime;
	}
	
	public void setTotal_avgsubmitedtime(BigDecimal total_avgsubmitedtime) {
		this.total_avgsubmitedtime = total_avgsubmitedtime;
	}
	public BigDecimal getTotal_submited() {
		return total_submited;
	}
	public void setTotal_submited(BigDecimal total_submited) {
		this.total_submited = total_submited;
	}
	
	//private Integer uncomplete_num=0;//未结束的任务
	//private Integer submited;//已提交未完成
	//private Integer pole_num;//提交完成率 
	

//	/**
//	 * 获取完成率
//	 * @author mawujun 16064988@qq.com 
//	 * @return
//	 */
//	public String getComplete_rate(){
//		Integer total= getTotalnum();
//		if(total==0 || total==null){
//			return "";
//		}
//		DecimalFormat df=new DecimalFormat("0.00");
//		return df.format((complete*100)/Double.valueOf(total+""));
//		
//	}
	
	
}
