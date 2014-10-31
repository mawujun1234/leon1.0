package com.mawujun.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.M;
/**
 * 备品备件库的控制器
 * @author mawujun 16064988@qq.com  
 *
 */
@Controller
public class SparepartMonthReportController {
	@Autowired
	private SparepartMonthReportService sparepartMonthReportService;
	
	@RequestMapping("/sparepartmonthreport/query.do")
	public List<SparepartMonthReport> query(String year,String month,String store_id){
		//String sql="select * from report_buildmonthreport where month='"+month+"' and store_id='"+store_id+"'";
		
		List<SparepartMonthReport> list=sparepartMonthReportService.query(Cnd.select().andEquals(M.SparepartMonthReport.monthkey, year+month).andEquals(M.SparepartMonthReport.store_id, store_id));
		return list;
		
	}
	@RequestMapping("/sparepartmonthreport/update.do")
	public String update(SparepartMonthReport sparepartMonthReport){
		sparepartMonthReportService.update(sparepartMonthReport);
		return "success";
	}
}
