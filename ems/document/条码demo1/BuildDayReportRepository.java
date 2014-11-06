package com.mawujun.report;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mawujun.inventory.DayInventory_PK;
import com.mawujun.repository1.IRepository;
import com.mawujun.report.BuildDayReport;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface BuildDayReportRepository extends IRepository<BuildDayReport, DayInventory_PK>{

	public void proc_builddayreport(Map<String,Object> params);
}
