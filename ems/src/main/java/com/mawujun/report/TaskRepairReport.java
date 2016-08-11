package com.mawujun.report;

import java.util.Date;

public class TaskRepairReport {
	private String customer_name;
	private String pole_id;
	private String pole_code;
	private String pole_name;
	private String workunit_name;
	
	
	private String memo;//故障现象
	private Date hitchDate;//故障时间
	private Date createDate;//任务下发时间
	private Date startHandDate;//开始处理时间，第一次保存的时候
	private Date submitDate;//提交时间
	private Date completeDate;//完成时间
	
	private String usedTime;//总耗时=完成时间-任务下发时间
	private String repairUsedTime;//修复耗时=提交时间-任务下发时间
	
	private String result;//维修结果
	
	private Integer handling;//超时,空着不填任何内容
	
	private String hitchType;
	private String hitchReason;//故障原因，
	
	private String handleMethod_name;//处理方法
	private String handle_contact;
	
	/**
	 * 总耗时=完成时间-任务下发时间
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	public String getUsedTime() {
		if(completeDate!=null){
			long diff=completeDate.getTime()-this.createDate.getTime();
			//long diffSeconds = diff / 1000 % 60;
	        long diffMinutes = diff / (60 * 1000) % 60;
	        long diffHours = diff / (60 * 60 * 1000) % 24;
	        long diffDays = diff / (24 * 60 * 60 * 1000);
	        return diffDays+"天"+diffHours+"小时"+diffMinutes+"分";
	        
//	        long ltime=completeDate.getTime()-this.createDate.getTime();
//			//String day=(ltime/(60*60*1000))+"小时"+(ltime%(60*60*1000))/(60*1000)+"分钟";
//			long day=ltime/(24*60*60*1000);
//			long hour=(ltime-(day*(24*60*60*1000)))/(60*60*1000);
//			
//			return day+"天"+hour+"小时";
//			//String day=(ltime/(60*60*1000))+"小时"+(ltime%(60*60*1000))/(60*1000)+"分钟";
//			//return day;
		}
		return "";
	}
	/**
	 * 获取超时的时间,超时=总耗时-超时设置
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	public String getOvertime() {
		if(handling==null || handling==0) {
			return "请设置超时";
		}
		if(completeDate==null){
			return "";
		}
		long diff=completeDate.getTime()-this.createDate.getTime();
		diff=diff-handling*1000*60;
		if(diff<=0){
			return "未超时";
		}
		
		//long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays+"天"+diffHours+"小时"+diffMinutes+"分";
	}
	/**
	 * 修复耗时=提交时间-任务下发时间
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	public String getRepairUsedTime() {
		if(submitDate!=null){
			long diff=submitDate.getTime()-this.createDate.getTime();
			//long diffSeconds = diff / 1000 % 60;
	        long diffMinutes = diff / (60 * 1000) % 60;
	        long diffHours = diff / (60 * 60 * 1000) % 24;
	        long diffDays = diff / (24 * 60 * 60 * 1000);
	        return diffDays+"天"+diffHours+"小时"+diffMinutes+"分";
	        
//			long ltime=submitDate.getTime()-this.createDate.getTime();
//			//String day=(ltime/(60*60*1000))+"小时"+(ltime%(60*60*1000))/(60*1000)+"分钟";
//			long day=ltime/(24*60*60*1000);
//			long hour=(ltime-(day*(24*60*60*1000)))/(60*60*1000);
//			
//			return day+"天"+hour+"小时";
//			//String day=(ltime/(60*60*1000))+"小时"+(ltime%(60*60*1000))/(60*1000)+"分钟";
//			//return day;
		}
		return "";
	}
	
	
	
	
	
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getPole_id() {
		return pole_id;
	}
	public void setPole_id(String pole_id) {
		this.pole_id = pole_id;
	}
	public String getPole_code() {
		return pole_code;
	}
	public void setPole_code(String pole_code) {
		this.pole_code = pole_code;
	}
	public String getPole_name() {
		return pole_name;
	}
	public void setPole_name(String pole_name) {
		this.pole_name = pole_name;
	}
	public String getWorkunit_name() {
		return workunit_name;
	}
	public void setWorkunit_name(String workunit_name) {
		this.workunit_name = workunit_name;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Date getHitchDate() {
		return hitchDate;
	}
	public void setHitchDate(Date hitchDate) {
		this.hitchDate = hitchDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getStartHandDate() {
		return startHandDate;
	}
	public void setStartHandDate(Date startHandDate) {
		this.startHandDate = startHandDate;
	}
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	public Date getCompleteDate() {
		return completeDate;
	}
	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public void setUsedTime(String usedTime) {
		this.usedTime = usedTime;
	}
	
	public void setRepairUsedTime(String repairUsedTime) {
		this.repairUsedTime = repairUsedTime;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

	public String getHitchType() {
		return hitchType;
	}
	public void setHitchType(String hitchType) {
		this.hitchType = hitchType;
	}
	public String getHitchReason() {
		return hitchReason;
	}
	public void setHitchReason(String hitchReason) {
		this.hitchReason = hitchReason;
	}
	public String getHandleMethod_name() {
		return handleMethod_name;
	}
	public void setHandleMethod_name(String handleMethod_name) {
		this.handleMethod_name = handleMethod_name;
	}
	public String getHandle_contact() {
		return handle_contact;
	}
	public void setHandle_contact(String handle_contact) {
		this.handle_contact = handle_contact;
	}
	public Integer getHandling() {
		return handling;
	}
	public void setHandling(Integer handling) {
		this.handling = handling;
	}
	
	

}
