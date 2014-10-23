package com.mawujun.report;

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
	
	private Integer pole_nums=0;//杆位数
	private Integer currt_new=0;// 本期新建任务
	private Integer prev_new=0;//以前新建任务
	//private Integer total_num;//总任务数
	private Integer complete=0;//已结束
	//private Integer complete_rate;//完成率
	private Double avgcompletetime;// 平均完成时间 
	
	//private Integer uncomplete_num=0;//未结束的任务
	//private Integer submited;//已提交未完成
	//private Integer pole_num;//提交完成率 
	private Double avgsubmitedtime;//平均提交时间

	/**
	 * 获取总任务数
	 * @author mawujun 16064988@qq.com 
	 * @return
	 */
	public Integer getTotalnum(){
		return currt_new+prev_new;
	}
	/**
	 * 获取完成率
	 * @author mawujun 16064988@qq.com 
	 * @return
	 */
	public String getComplete_rate(){
		Integer total= getTotalnum();
		if(total==0 || total==null){
			return "";
		}
		DecimalFormat df=new DecimalFormat("0.00");
		return df.format((complete*100)/Double.valueOf(total+""));
		
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

	public Integer getPole_nums() {
		return pole_nums;
	}

	public void setPole_nums(Integer pole_nums) {
		this.pole_nums = pole_nums;
	}

	public Integer getCurrt_new() {
		return currt_new;
	}

	public void setCurrt_new(Integer currt_new) {
		this.currt_new = currt_new;
	}

	public Integer getPrev_new() {
		return prev_new;
	}

	public void setPrev_new(Integer prev_new) {
		this.prev_new = prev_new;
	}

	public Integer getComplete() {
		return complete;
	}

	public void setComplete(Integer complete) {
		this.complete = complete;
	}
	public Double getAvgcompletetime() {
		return avgcompletetime;
	}
	public void setAvgcompletetime(Double avgcompletetime) {
		this.avgcompletetime = avgcompletetime;
	}
	public Double getAvgsubmitedtime() {
		return avgsubmitedtime;
	}
	public void setAvgsubmitedtime(Double avgsubmitedtime) {
		this.avgsubmitedtime = avgsubmitedtime;
	}

	
	
}
