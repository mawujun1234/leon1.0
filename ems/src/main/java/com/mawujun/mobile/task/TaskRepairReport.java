package com.mawujun.mobile.task;

import java.util.Date;

/**
 * 用于维修报表的展示
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public class TaskRepairReport extends Task {
	public boolean isOverTime=false;
	/**
	 * 总耗时
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	public String getFinishTime(){
		if(this.getSubmitDate()!=null){
			long ltime=this.getSubmitDate().getTime()-this.getCreateDate().getTime();
			String day=(ltime/(60*60*1000))+"小时"+(ltime%(60*60*1000))/(60*1000)+"分钟";
			return day;
		}
		return "";
	}
	/**
	 * 维修时间
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	public String getRepairTime(){
		if(this.getSubmitDate()!=null){
			long ltime=this.getSubmitDate().getTime()-this.getStartHandDate().getTime();
			String day=(ltime/(60*60*1000))+"小时"+(ltime%(60*60*1000))/(60*1000)+"分钟";
			return day;
		}
		return "";
	}
	/**
	 * 判断这个工作是否超期了
	 * @author mawujun email:160649888@163.com qq:16064988
	 */
	public void checkIsOverTime(Integer time){
		if(this.getSubmitDate()!=null){
			long ltime=this.getSubmitDate().getTime()-this.getCreateDate().getTime();
			if(ltime>time*60*1000){
				isOverTime= true;
			}
		} else {
			long ltime=(new Date()).getTime()-this.getCreateDate().getTime();
			if(ltime>time*60*1000){
				isOverTime= true;
			}
		}
		
	}
	public boolean getIsOverTime() {
		return isOverTime;
	}
	public void setOverTime(boolean isOverTime) {
		this.isOverTime = isOverTime;
	}

}
