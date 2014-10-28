package com.mawujun.report;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;

@Repository
public interface BuildMonthReportRepository extends IRepository<BuildMonthReport, BuildMonthReport_PK> {
	public void proc_buildmonthreport(Map<String,Object> params);
}
