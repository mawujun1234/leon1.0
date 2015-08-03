package com.mawujun.report;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mawujun.utils.Params;
import com.mawujun.utils.page.Page;

@Repository
public interface RepairReportRepository {
	public Page queryRepairReport(Page page);
	public List<RepairReport> queryRepairReport(Params params);
	
//	public Page queryCompleteRepairReport(Page page);
//	public List<RepairVO> queryCompleteRepairReport(Params params);
}
