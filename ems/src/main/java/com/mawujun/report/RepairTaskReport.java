package com.mawujun.report;

import java.util.Date;

public class RepairTaskReport {
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
	
	private String overtime;//超时,空着不填任何内容
	
	private String hitchType;
	private String hitchReason;//故障原因，
	
	private String handleMethod_name;//处理方法
	private String handle_contact;
	
	

}
