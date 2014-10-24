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
	
	private Integer currt_new;// 本期新建任务
	private Integer currt_complete;// 本期完成任务
	private Double currt_avgcompletetime;//本期完成平均时间
	private Integer currt_submited;//本期提交任务数
	private Double currt_avgsubmitedtime;//本期提交平均时间
	
	
	private Integer total_nums;//总任务数
	private Integer total_complete;//总的完成数
	private Integer total_complete_rate;//总完成率
	private Double total_avgcompletetime;// 平均完成时间 
	private Double total_avgsubmitedtime;//平均提交时间
	
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
