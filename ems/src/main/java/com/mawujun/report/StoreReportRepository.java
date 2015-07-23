package com.mawujun.report;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreReportRepository {
	public List<InstalloutListReport> queryInstalloutListReport(@Param("store_id")String store_id,@Param("project_id")String project_id,
			@Param("date_start")String date_start,@Param("date_end")String date_end);
	public List<BorrowListReport> queryBorrowListReport(@Param("store_id")String store_id,@Param("project_id")String project_id,
			@Param("date_start")String date_start,@Param("date_end")String date_end);
	public List<InstoreListReport> queryInstoreListReport(@Param("store_id")String store_id,@Param("project_id")String project_id,
			@Param("date_start")String date_start,@Param("date_end")String date_end);
	
	public List<InstallOutReport_type> queryInstalloutReport(@Param("store_id")String store_id,@Param("use_in")Boolean use_in,
			@Param("date_start")String date_start,@Param("date_end")String date_end);
}
