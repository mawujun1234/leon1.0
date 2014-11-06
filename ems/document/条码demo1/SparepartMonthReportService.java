package com.mawujun.report;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.baseinfo.Store;
import com.mawujun.baseinfo.StoreRepository;
import com.mawujun.inventory.MonthInventory_PK;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.repository1.IRepository;
import com.mawujun.service.AbstractService;
import com.mawujun.utils.M;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class SparepartMonthReportService extends AbstractService<SparepartMonthReport, MonthInventory_PK>{
	@Autowired
	private SparepartMonthReportRepository sparepartMonthReportRepository;
	@Autowired
	private StoreRepository storeRepository;

	@Override
	public IRepository<SparepartMonthReport, MonthInventory_PK> getRepository() {
		// TODO Auto-generated method stub
		return sparepartMonthReportRepository;
	}
	SimpleDateFormat format=new SimpleDateFormat("yyyyMM");
	/**
	 * 每个月月底，定时生成报表，注意是备品备件仓库
	 * @author mawujun 16064988@qq.com
	 */
	public void createSparepartMonthReport() {
		// 如果是每个月的凌晨1点执行的话，就把时间调整为上一个月的最后一天
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		if (day == 1) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}

		Map<String, Object> params = new HashMap<String, Object>();
		// 获取当前月，格式为 201409
		// Calendar cal=Calendar.getInstance();
		String nowmonth_in = format.format(cal.getTime());// cal.get(Calendar.YEAR)+StringUtils.leftPad(cal.get(Calendar.MONTH)+"",2,'0');
		cal.add(Calendar.MONTH, -1);
		String lastmonth_in = format.format(cal.getTime());// cal.get(Calendar.YEAR)+StringUtils.leftPad(cal.get(Calendar.MONTH)+"",2,'0');
		params.put("nowmonth_in", nowmonth_in);
		params.put("lastmonth_in", lastmonth_in);

		List<Store> stores = storeRepository.query(Cnd.select().andEquals(M.Store.type, 3).andEquals(M.Store.status, true));
		for (Store store : stores) {
			params.put("store_id_in", store.getId());
			sparepartMonthReportRepository.proc_sparepartmonthreport(params);
		}
	}

}
