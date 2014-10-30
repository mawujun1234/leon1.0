package com.mawujun.report;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.repository1.IRepository;
import com.mawujun.service.AbstractService;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class SparepartMonthReportService extends AbstractService<SparepartMonthReport, SparepartMonthReport_PK>{
	private SparepartMonthReportRepository sparepartMonthReportRepository;

	@Override
	public IRepository<SparepartMonthReport, SparepartMonthReport_PK> getRepository() {
		// TODO Auto-generated method stub
		return sparepartMonthReportRepository;
	}
	SimpleDateFormat format=new SimpleDateFormat("yyyyMM");
	/**
	 * 每个月月底，定时生成报表
	 * @author mawujun 16064988@qq.com
	 */
	public void createSparepartMonthReport(){
		
	}

}
