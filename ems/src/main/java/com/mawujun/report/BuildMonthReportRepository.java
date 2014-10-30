package com.mawujun.report;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;

@Repository
public interface BuildMonthReportRepository extends IRepository<BuildMonthReport, BuildMonthReport_PK> {
	/**
	 * 执行存储过程
	 * @author mawujun 16064988@qq.com 
	 * @param params
	 */
	public void proc_buildmonthreport(Map<String,Object> params);
}
