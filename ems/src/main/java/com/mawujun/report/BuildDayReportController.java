package com.mawujun.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.M;

@Controller
@Transactional
public class BuildDayReportController {
	//@Autowired
	//private JdbcTemplate jdbcTemplate;
	@Autowired
	private BuildDayReportService buildDayReportService;
	
	@RequestMapping("/builddayreport/query.do")
	public List<BuildDayReport> query(String year,String month,String store_id){
		//String sql="select * from report_builddayreport where month='"+month+"' and store_id='"+store_id+"'";
		
		List<BuildDayReport> list=buildDayReportService.query(Cnd.select().andEquals(M.BuildDayReport.month, year+month).andEquals(M.BuildDayReport.store_id, store_id));
		return list;
		
	}
	
	@RequestMapping("/builddayreport/updateMemo.do")
	public String updateMemo(BuildDayReport buildDayReport){
		buildDayReportService.update(buildDayReport);
		return "success";
	}
}
