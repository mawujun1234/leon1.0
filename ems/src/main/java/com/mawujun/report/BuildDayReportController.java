package com.mawujun.report;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;
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


	/**
	 * 请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param id 是父节点的id
	 * @return
	 */
	@RequestMapping("/buildDayReport/query.do")
	@ResponseBody
	public List<BuildDayReport> query(String id) {
		Cnd cnd=Cnd.select().andEquals(M.BuildDayReport.parent.id, "root".equals(id)?null:id);
		List<BuildDayReport> buildDayReportes=buildDayReportService.query(cnd);
		//JsonConfigHolder.setFilterPropertys(BuildDayReport.class,M.BuildDayReport.parent.name());
		return buildDayReportes;
	}

	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/buildDayReport/query.do")
	@ResponseBody
	public Page query(Integer start,Integer limit,String sampleName){
		Page page=Page.getInstance(start,limit);//.addParam(M.BuildDayReport.sampleName, "%"+sampleName+"%");
		return buildDayReportService.queryPage(page);
	}

	@RequestMapping("/buildDayReport/query.do")
	@ResponseBody
	public List<BuildDayReport> query() {	
		List<BuildDayReport> buildDayReportes=buildDayReportService.queryAll();
		return buildDayReportes;
	}
	

	@RequestMapping("/buildDayReport/load.do")
	public BuildDayReport load(BuildDayReport_PK id) {
		return buildDayReportService.get(id);
	}
	
	@RequestMapping("/buildDayReport/create.do")
	@ResponseBody
	public BuildDayReport create(@RequestBody BuildDayReport buildDayReport) {
		buildDayReportService.create(buildDayReport);
		return buildDayReport;
	}
	
	@RequestMapping("/buildDayReport/update.do")
	@ResponseBody
	public  BuildDayReport update(@RequestBody BuildDayReport buildDayReport) {
		buildDayReportService.update(buildDayReport);
		return buildDayReport;
	}
	
	@RequestMapping("/buildDayReport/deleteById.do")
	@ResponseBody
	public BuildDayReport_PK deleteById(BuildDayReport_PK id) {
		buildDayReportService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/buildDayReport/destroy.do")
	@ResponseBody
	public BuildDayReport destroy(@RequestBody BuildDayReport buildDayReport) {
		buildDayReportService.delete(buildDayReport);
		return buildDayReport;
	}
	
	
}
