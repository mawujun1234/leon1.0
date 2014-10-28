package com.mawujun.report;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;
import com.mawujun.baseinfo.Store;
import com.mawujun.baseinfo.StoreService;
import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.page.Page;
import com.mawujun.utils.M;
import com.mawujun.report.BuildDayReport;
import com.mawujun.report.BuildDayReportService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/buildDayReport")
public class BuildDayReportController {

	@Resource
	private BuildDayReportService buildDayReportService;
	@Autowired
	private StoreService storeService;

	public List<BuildDayReport> query(String year,String month,String store_id){
		List<BuildDayReport> list=buildDayReportService.query(Cnd.select().andEquals(M.BuildDayReport.daykey, year+month).andEquals(M.BuildDayReport.store_id, store_id));
		
		return list;
		
	}
	@RequestMapping("/builddayreport/export.do")
	public void export(HttpServletResponse response,String year,String month,String store_id) throws IOException{
		
		
		
	}
	
	
}
