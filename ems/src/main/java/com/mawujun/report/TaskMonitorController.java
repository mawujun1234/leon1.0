package com.mawujun.report;

import java.text.SimpleDateFormat;
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
	public List<Map<String,Object>> newInstall(String queryType,Integer month,Date date_start,Date date_end){
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
		
	}
}
