package com.mawujun.report;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.mobile.task.TaskVO;
import com.mawujun.utils.Params;
import com.mawujun.utils.page.Page;

@Repository
public interface TaskReportRepository {
	public List<TaskVO> exportUnrepairPoleReport(Params params);
	public Page queryUnrepairPoleReport(Page page);
	
	public Page queryTaskRepairReport(Page page);
	public List<TaskRepairReport> queryTaskRepairReport(@Param("date_start")String date_start,@Param("date_end")String date_end
			,@Param("pole_code")String pole_code,@Param("hitchType_id")String hitchType_id);
}
