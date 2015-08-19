package com.mawujun.report;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mawujun.mobile.task.Task;
import com.mawujun.utils.Params;
import com.mawujun.utils.page.Page;

@Repository
public interface TaskReportRepository {
	public List<Task> exportUnrepairPoleReport(Params params);
	public Page queryUnrepairPoleReport(Page page);
	
	public Page queryRepairReport(Page page);
	public Page exportRepairReport(Params params);
}
