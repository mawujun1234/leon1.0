package com.mawujun.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.utils.page.Page;

@Controller
public class TaskRepairReportController {
	@Autowired
	private TaskReportRepository taskReportRepository;
	
	
	@RequestMapping("/report/taskrepair/queryRepairReport.do")
	@ResponseBody
	public Page queryRepairReport(Integer start,Integer limit,String date_start,String date_end,String pole_code,String hitchType_id) {
		Page page=Page.getInstance(start, limit).addParam("date_start", date_start)
				.addParam("date_end", date_end)
				.addParam("pole_code", pole_code)
				.addParam("hitchType_id", hitchType_id);
		
		JsonConfigHolder.setDatePattern("yyyy-MM-dd HH:mm:ss");
		return taskReportRepository.queryRepairReport(page);
	}
	@RequestMapping("/report/taskrepair/exportRepairReport.do")
	@ResponseBody
	public List<TaskRepairReport> exportRepairReport(String date_start,String date_end,String pole_code,String hitchType_id) {
		return taskReportRepository.queryRepairReport(date_start, date_end, pole_code, hitchType_id);
	}

}
