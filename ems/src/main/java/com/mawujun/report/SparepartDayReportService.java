package com.mawujun.report;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;





import com.mawujun.service.AbstractService;


import com.mawujun.utils.M;
import com.mawujun.baseinfo.Store;
import com.mawujun.baseinfo.StoreRepository;
import com.mawujun.inventory.DayInventory_PK;
import com.mawujun.report.SparepartDayReport;
import com.mawujun.report.BuildDayReportRepository;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.repository1.IRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class SparepartDayReportService extends AbstractService<SparepartDayReport, DayInventory_PK>{

	@Autowired
	private SparepartDayReportRepository sparepartDayReportRepository;
	@Autowired
	private StoreRepository storeRepository;
	
	@Override
	public SparepartDayReportRepository getRepository() {
		return sparepartDayReportRepository;
	}


	SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
	/**
	 * 每个月月底，定时生成报表
	 * @author mawujun 16064988@qq.com
	 */
	public void createSparepartDayReport(){
		Map<String,Object> params=new HashMap<String,Object>();
		//获取当前月，格式为 201409
		Calendar cal=Calendar.getInstance();
		String nowday_in=format.format(cal.getTime());//cal.get(Calendar.YEAR)+StringUtils.leftPad(cal.get(Calendar.MONTH)+"",2,'0');
		
		//cal.add(Calendar.DAY_OF_MONTH, -1);
		//String lastday_in=format.format(cal.getTime());//cal.get(Calendar.YEAR)+StringUtils.leftPad(cal.get(Calendar.MONTH)+"",2,'0');
		cal.add(Calendar.MONTH, -1);
		String lastmonth_in=cal.get(Calendar.YEAR)+StringUtils.leftPad(cal.get(Calendar.MONTH)+"",2,'0');
		params.put("nowday_in", nowday_in);
		params.put("lastmonth_in", lastmonth_in);
		
		List<Store> stores=storeRepository.query(Cnd.select().andEquals(M.Store.type, 1).andEquals(M.Store.status,true ));
		for(Store store:stores){
			params.put("store_id_in", store.getId());
			sparepartDayReportRepository.proc_buildsparepartdayreport(params);
		}
		
		
	}

}
