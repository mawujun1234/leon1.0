package com.mawujun.report;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;

@Repository
public interface BuildDayReportRepository extends IRepository<BuildDayReport, BuildDayReport_PK> {
	public void proc_builddayreport(Map<String,Object> params);
}
