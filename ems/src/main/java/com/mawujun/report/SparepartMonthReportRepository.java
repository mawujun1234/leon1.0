package com.mawujun.report;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;

@Repository
public interface SparepartMonthReportRepository extends IRepository<SparepartMonthReport, SparepartMonthReport_PK>{
	public void proc_buildsparepartmonthreport(Map<String,Object> params);
}
