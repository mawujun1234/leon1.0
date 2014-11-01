package com.mawujun.report;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mawujun.inventory.MonthInventory_PK;
import com.mawujun.repository1.IRepository;

@Repository
public interface SparepartMonthReportRepository extends IRepository<SparepartMonthReport, MonthInventory_PK>{
	public void proc_sparepartmonthreport(Map<String,Object> params);
}
