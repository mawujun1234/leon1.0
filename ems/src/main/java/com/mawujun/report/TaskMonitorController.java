package com.mawujun.report;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mawujun.exception.BusinessException;
import com.mawujun.utils.StringUtils;

@Controller
public class TaskMonitorController {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
	/**
	 * 月只是0~11这几个值
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param queryType
	 * @param month
	 * @param date_start
	 * @param date_end
	 * @return
	 */
	@RequestMapping("/taskmonitor/newInstall.do")
	public List<NewInstallMonitor> newInstall(String queryType,Integer month,Date date_start,Date date_end){
		//如果是按月查
		if("month".equals(queryType)){
			if(month==null){
				throw new BusinessException("请选择月份!");
			}
			Calendar cal=Calendar.getInstance();
			cal.set(Calendar.MONTH, month);
			cal.set(cal.get(Calendar.YEAR),month, 1, 0, 0, 0);
			date_start=cal.getTime();
			
			int MaxDay=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			cal.set( cal.get(Calendar.YEAR),month, MaxDay, 23, 59, 59);
			date_end=cal.getTime();
		} else {
			if(date_start==null){
				throw new BusinessException("请选择开始日期!");
			}
			if(date_end==null){
				date_end=new Date();
			}
		}
		
		//开始查询数据
		//获取作业单位拥有的杆位数
		String sql="select c.id,c.name,count(a.id) as pole_nums from ems_pole a,ems_area b,ems_workunit c"
				+ " where a.area_id=b.id and b.workunit_id=c.id "
				+ " group by c.id,c.name ";
		List<Map<String,Object>> polenums=jdbcTemplate.queryForList(sql);
		
		//获取新建的任务数
		sql="select workunit_id,count(id) currt_new from ems_task "
				+ " where to_char(createdate,'yyyymmdd') between '"+format.format(date_start)+"' and '"+format.format(date_end)+"' "
						+ " and type ='newInstall'"
				+ "group by workunit_id ";
		List<Map<String,Object>> currt_news=jdbcTemplate.queryForList(sql);
		//获取完成任务数和平均完成时间
		sql="select workunit_id,count(id) currt_complete,"
				+ " avg(to_date(to_char(completeDate,'yyyy-mm-dd hh24-mi-ss'),'yyyy-mm-dd hh24-mi-ss')-to_date(to_char(createDate,'yyyy-mm-dd hh24-mi-ss'),'yyyy-mm-dd hh24-mi-ss'))*24*60 as currt_avgcompletetime"
				+ " from ems_task "
				+ " where  status='complete' and to_char(completeDate,'yyyymmdd') between '"+format.format(date_start)+"' and '"+format.format(date_end)+"'"
						+ " and type ='newInstall' "
				+ " group by workunit_id";
		List<Map<String,Object>> currt_completes=jdbcTemplate.queryForList(sql);
		//获取提交任务数和平均完成时间
		sql="select workunit_id,count(id) currt_submited,"
				+ " avg(to_date(to_char(submitDate,'yyyy-mm-dd hh24-mi-ss'),'yyyy-mm-dd hh24-mi-ss')-to_date(to_char(createDate,'yyyy-mm-dd hh24-mi-ss'),'yyyy-mm-dd hh24-mi-ss'))*24*60 currt_avgsubmitedtime"
				+ " from ems_task "
				+ " where  status='complete' and to_char(submitDate,'yyyymmdd') between '"+format.format(date_start)+"' and '"+format.format(date_end)+"'"
						+ " and type ='newInstall'"
				+ " group by workunit_id";
		List<Map<String,Object>> currt_submiteds=jdbcTemplate.queryForList(sql);
		
		
		
		sql="select workunit_id,count(id) currt_new from ems_task "
				+ " where type ='newInstall'"
				+ "group by workunit_id ";
		List<Map<String,Object>> total_nums=jdbcTemplate.queryForList(sql);
		//获取完成任务数和平均完成时间
		sql="select workunit_id,count(id) currt_complete,"
				+ " avg(to_date(to_char(completeDate,'yyyy-mm-dd hh24-mi-ss'),'yyyy-mm-dd hh24-mi-ss')-to_date(to_char(createDate,'yyyy-mm-dd hh24-mi-ss'),'yyyy-mm-dd hh24-mi-ss'))*24*60 as currt_avgcompletetime"
				+ " from ems_task "
				+ " where  status='complete' "
						+ " and type ='newInstall' "
				+ " group by workunit_id";
		List<Map<String,Object>> total_completes=jdbcTemplate.queryForList(sql);
		//获取提交任务数和平均完成时间
		sql="select workunit_id,count(id) currt_submited,"
				+ " avg(to_date(to_char(submitDate,'yyyy-mm-dd hh24-mi-ss'),'yyyy-mm-dd hh24-mi-ss')-to_date(to_char(createDate,'yyyy-mm-dd hh24-mi-ss'),'yyyy-mm-dd hh24-mi-ss'))*24*60 currt_avgsubmitedtime"
				+ " from ems_task "
				+ " where  (status='complete' or status='submited') "
						+ " and type ='newInstall'"
				+ " group by workunit_id";
		List<Map<String,Object>> total_avgcompletetime=jdbcTemplate.queryForList(sql);
		
		List<NewInstallMonitor> result=new ArrayList<NewInstallMonitor>();
		for(Map<String,Object> polenum:polenums){
			NewInstallMonitor monitor=new NewInstallMonitor();
			monitor.setId(polenum.get("id").toString());
			monitor.setName(polenum.get("name").toString());
			monitor.setPole_nums((BigDecimal)polenum.get("pole_nums"));
			for(Map<String,Object> map:currt_news){
				if(polenum.get("id").equals(map.get("workunit_id"))){
					monitor.setCurrt_new((BigDecimal)map.get("currt_new"));
				}
			}
			for(Map<String,Object> map:currt_completes){
				if(polenum.get("id").equals(map.get("workunit_id"))){
					monitor.setCurrt_complete((BigDecimal)map.get("currt_complete"));
					monitor.setCurrt_avgcompletetime((BigDecimal)map.get("currt_avgcompletetime"));
				}
			}
			for(Map<String,Object> map:currt_submiteds){
				if(polenum.get("id").equals(map.get("workunit_id"))){
					monitor.setCurrt_submited((BigDecimal)map.get("currt_submited"));
					monitor.setCurrt_avgsubmitedtime((BigDecimal)map.get("currt_avgsubmitedtime"));
				}
			}
			//=================================
			for(Map<String,Object> map:total_nums){
				if(polenum.get("id").equals(map.get("workunit_id"))){
					monitor.setTotal_nums((BigDecimal)map.get("currt_new"));
				}
			}
			for(Map<String,Object> map:total_completes){
				if(polenum.get("id").equals(map.get("workunit_id"))){
					monitor.setTotal_complete((BigDecimal)map.get("currt_complete"));
					monitor.setTotal_avgcompletetime((BigDecimal)map.get("currt_avgcompletetime"));
				}
			}
			for(Map<String,Object> map:total_avgcompletetime){
				if(polenum.get("id").equals(map.get("workunit_id"))){
					monitor.setTotal_submited((BigDecimal)map.get("currt_submited"));
					monitor.setTotal_avgsubmitedtime((BigDecimal)map.get("currt_avgsubmitedtime"));
				}
			}
			result.add(monitor);
		}
		
		return result;
	}
	
	
	
	
	@RequestMapping("/taskmonitor/repair.do")
	public List<NewInstallMonitor> repair(String queryType,Integer month,Date date_start,Date date_end){
		//如果是按月查
		if("month".equals(queryType)){
			if(month==null){
				throw new BusinessException("请选择月份!");
			}
			Calendar cal=Calendar.getInstance();
			cal.set(Calendar.MONTH, month);
			cal.set(cal.get(Calendar.YEAR),month, 1, 0, 0, 0);
			date_start=cal.getTime();
			
			int MaxDay=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			cal.set( cal.get(Calendar.YEAR),month, MaxDay, 23, 59, 59);
			date_end=cal.getTime();
		} else {
			if(date_start==null){
				throw new BusinessException("请选择开始日期!");
			}
			if(date_end==null){
				date_end=new Date();
			}
		}
		
		//开始查询数据
		//获取作业单位拥有的杆位数
		String sql="select c.id,c.name,count(a.id) as pole_nums from ems_pole a,ems_area b,ems_workunit c"
				+ " where a.area_id=b.id and b.workunit_id=c.id "
				+ " group by c.id,c.name ";
		List<Map<String,Object>> polenums=jdbcTemplate.queryForList(sql);
		
		//获取新建的任务数
		sql="select workunit_id,count(id) currt_new from ems_task "
				+ " where to_char(createdate,'yyyymmdd') between '"+format.format(date_start)+"' and '"+format.format(date_end)+"' "
						+ " and type ='repair'"
				+ "group by workunit_id ";
		List<Map<String,Object>> currt_news=jdbcTemplate.queryForList(sql);
		//获取完成任务数和平均完成时间
		sql="select workunit_id,count(id) currt_complete,"
				+ " avg(to_date(to_char(completeDate,'yyyy-mm-dd hh24-mi-ss'),'yyyy-mm-dd hh24-mi-ss')-to_date(to_char(createDate,'yyyy-mm-dd hh24-mi-ss'),'yyyy-mm-dd hh24-mi-ss'))*24*60 as currt_avgcompletetime"
				+ " from ems_task "
				+ " where  status='complete' and to_char(completeDate,'yyyymmdd') between '"+format.format(date_start)+"' and '"+format.format(date_end)+"'"
						+ " and type ='repair' "
				+ " group by workunit_id";
		List<Map<String,Object>> currt_completes=jdbcTemplate.queryForList(sql);
		//获取提交任务数和平均完成时间
		sql="select workunit_id,count(id) currt_submited,"
				+ " avg(to_date(to_char(submitDate,'yyyy-mm-dd hh24-mi-ss'),'yyyy-mm-dd hh24-mi-ss')-to_date(to_char(createDate,'yyyy-mm-dd hh24-mi-ss'),'yyyy-mm-dd hh24-mi-ss'))*24*60 currt_avgsubmitedtime"
				+ " from ems_task "
				+ " where  status='complete' and to_char(submitDate,'yyyymmdd') between '"+format.format(date_start)+"' and '"+format.format(date_end)+"'"
						+ " and type ='repair'"
				+ " group by workunit_id";
		List<Map<String,Object>> currt_submiteds=jdbcTemplate.queryForList(sql);
		
		
		
		sql="select workunit_id,count(id) currt_new from ems_task "
				+ " where type ='repair'"
				+ "group by workunit_id ";
		List<Map<String,Object>> total_nums=jdbcTemplate.queryForList(sql);
		//获取完成任务数和平均完成时间
		sql="select workunit_id,count(id) currt_complete,"
				+ " avg(to_date(to_char(completeDate,'yyyy-mm-dd hh24-mi-ss'),'yyyy-mm-dd hh24-mi-ss')-to_date(to_char(createDate,'yyyy-mm-dd hh24-mi-ss'),'yyyy-mm-dd hh24-mi-ss'))*24*60 as currt_avgcompletetime"
				+ " from ems_task "
				+ " where  status='complete' "
						+ " and type ='repair' "
				+ " group by workunit_id";
		List<Map<String,Object>> total_completes=jdbcTemplate.queryForList(sql);
		//获取提交任务数和平均完成时间
		sql="select workunit_id,count(id) currt_submited,"
				+ " avg(to_date(to_char(submitDate,'yyyy-mm-dd hh24-mi-ss'),'yyyy-mm-dd hh24-mi-ss')-to_date(to_char(createDate,'yyyy-mm-dd hh24-mi-ss'),'yyyy-mm-dd hh24-mi-ss'))*24*60 currt_avgsubmitedtime"
				+ " from ems_task "
				+ " where  (status='complete' or status='submited') "
						+ " and type ='repair'"
				+ " group by workunit_id";
		List<Map<String,Object>> total_avgcompletetime=jdbcTemplate.queryForList(sql);
		
		List<NewInstallMonitor> result=new ArrayList<NewInstallMonitor>();
		for(Map<String,Object> polenum:polenums){
			NewInstallMonitor monitor=new NewInstallMonitor();
			monitor.setId(polenum.get("id").toString());
			monitor.setName(polenum.get("name").toString());
			monitor.setPole_nums((BigDecimal)polenum.get("pole_nums"));
			for(Map<String,Object> map:currt_news){
				if(polenum.get("id").equals(map.get("workunit_id"))){
					monitor.setCurrt_new((BigDecimal)map.get("currt_new"));
				}
			}
			for(Map<String,Object> map:currt_completes){
				if(polenum.get("id").equals(map.get("workunit_id"))){
					monitor.setCurrt_complete((BigDecimal)map.get("currt_complete"));
					monitor.setCurrt_avgcompletetime((BigDecimal)map.get("currt_avgcompletetime"));
				}
			}
			for(Map<String,Object> map:currt_submiteds){
				if(polenum.get("id").equals(map.get("workunit_id"))){
					monitor.setCurrt_submited((BigDecimal)map.get("currt_submited"));
					monitor.setCurrt_avgsubmitedtime((BigDecimal)map.get("currt_avgsubmitedtime"));
				}
			}
			//=================================
			for(Map<String,Object> map:total_nums){
				if(polenum.get("id").equals(map.get("workunit_id"))){
					monitor.setTotal_nums((BigDecimal)map.get("currt_new"));
				}
			}
			for(Map<String,Object> map:total_completes){
				if(polenum.get("id").equals(map.get("workunit_id"))){
					monitor.setTotal_complete((BigDecimal)map.get("currt_complete"));
					monitor.setTotal_avgcompletetime((BigDecimal)map.get("currt_avgcompletetime"));
				}
			}
			for(Map<String,Object> map:total_avgcompletetime){
				if(polenum.get("id").equals(map.get("workunit_id"))){
					monitor.setTotal_submited((BigDecimal)map.get("currt_submited"));
					monitor.setTotal_avgsubmitedtime((BigDecimal)map.get("currt_avgsubmitedtime"));
				}
			}
			result.add(monitor);
		}
		
		return result;
	}
	
	@RequestMapping("/taskmonitor/patrol.do")
	public List<PatrolMonitor> patrol(String queryType,Integer month,Date date_start,Date date_end){
		// 如果是按月查
		//if ("month".equals(queryType)) {
			if (month == null) {
				throw new BusinessException("请选择月份!");
			}
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, month);
			cal.set(cal.get(Calendar.YEAR), month, 1, 0, 0, 0);
			date_start = cal.getTime();

			int MaxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			cal.set(cal.get(Calendar.YEAR), month, MaxDay, 23, 59, 59);
			date_end = cal.getTime();
//		} else {
//			if (date_start == null) {
//				throw new BusinessException("请选择开始日期!");
//			}
//			if (date_end == null) {
//				date_end = new Date();
//			}
//		}
		// 开始查询数据
		// 获取作业单位拥有的杆位数
		String sql = "select c.id,c.name,count(a.id) as pole_nums from ems_pole a,ems_area b,ems_workunit c"
				+ " where a.area_id=b.id and b.workunit_id=c.id "
				+ " group by c.id,c.name ";
		List<Map<String, Object>> polenums = jdbcTemplate.queryForList(sql);
		
		//获取已巡检的杆位，即任务提交的杆位
		sql="select workunit_id,count(pole_id) as patrols from ems_task where  (status='submited' or status='complete')  and type='patrol'"
				+ " and to_char(submitDate,'yyyymmdd') between '"+format.format(date_start)+"' and '"+format.format(date_end)+"'"
				+ " group by workunit_id";
		List<Map<String, Object>> patrols = jdbcTemplate.queryForList(sql);
		
		
		List<PatrolMonitor> result=new ArrayList<PatrolMonitor>();
		for(Map<String,Object> polenum:polenums){
			PatrolMonitor monitor=new PatrolMonitor();
			monitor.setId(polenum.get("id").toString());
			monitor.setName(polenum.get("name").toString());
			monitor.setPole_nums((BigDecimal)polenum.get("pole_nums"));
			
			for(Map<String,Object> map:patrols){
				if(polenum.get("id").equals(map.get("workunit_id"))){
					monitor.setPatrols((BigDecimal)map.get("patrols"));
				}
			}
			result.add(monitor);
		}
		return result;
	}
}
