package com.mawujun.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TaskRepairReportController {
	@Autowired
	private TaskReportRepository taskReportRepository;
	
	
	@RequestMapping("/repare/taskrepair/queryRepairReport.do")
	@ResponseBody
	public String queryRepairReport(String date_start,String date_end,String pole_code,String hitchType_id) {
		
	}
	@RequestMapping("/repare/taskrepair/exportRepairReport.do")
	@ResponseBody
	public String exportRepairReport(String date_start,String date_end,String pole_code,String hitchType_id) {
		
	}

}
