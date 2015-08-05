package com.mawujun.report;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repair.RepairVO;
import com.mawujun.utils.Params;
import com.mawujun.utils.page.Page;

@Repository
public interface RepairReportRepository {
	public Page queryRepairReport(Page page);
	public List<RepairReport> queryRepairReport(Params params);
	
	public List<RepeatRepairReport> queryRepeatRepairReport(@Param("date_start")String date_start,@Param("date_end")String date_end,@Param("repeatnum")Integer repeatnum);
	
//	public Page queryCompleteRepairReport(Page page);
//	public List<RepairVO> queryCompleteRepairReport(Params params);
	
	public Page queryScrapReport(Page page);
	public List<RepairVO> queryScrapReport(Params params);
}
