package com.mawujun.report;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.baseinfo.Store;
import com.mawujun.baseinfo.StoreRepository;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.repository1.IRepository;
import com.mawujun.service.AbstractService;
import com.mawujun.utils.M;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class BuildDayReportService extends AbstractService<BuildDayReport, BuildDayReport_PK>{
	@Autowired
	private BuildDayReportRepository buildDayReportRepository;
	@Autowired
	private StoreRepository storeRepository;
	@Override
	public IRepository<BuildDayReport, BuildDayReport_PK> getRepository() {
		// TODO Auto-generated method stub
		return buildDayReportRepository;
	}
	/**
	 * 每个月月底，定时生成报表
	 * @author mawujun 16064988@qq.com
	 */
	public void createBuildDayReport(){
		Map<String,Object> params=new HashMap<String,Object>();
		//获取当前月，格式为 201409
		Calendar cal=Calendar.getInstance();
		String nowmonth_in=cal.get(Calendar.YEAR)+StringUtils.leftPad(cal.get(Calendar.MONTH)+"",2,'0');
		cal.add(Calendar.MONTH, -1);
		String lastmonth_in=cal.get(Calendar.YEAR)+StringUtils.leftPad(cal.get(Calendar.MONTH)+"",2,'0');
		params.put("nowmonth_in", nowmonth_in);
		params.put("lastmonth_in", lastmonth_in);
		
		List<Store> stores=storeRepository.query(Cnd.select().andEquals(M.Store.type, 1).andEquals(M.Store.status,true ));
		for(Store store:stores){
			params.put("store_id_in", store.getId());
			buildDayReportRepository.proc_builddayreport(params);
		}
		
		
	}

}
