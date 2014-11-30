package com.mawujun.inventory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.baseinfo.Store;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.repository1.IRepository;
import com.mawujun.service.AbstractService;
import com.mawujun.utils.M;

@Service
@Transactional
public class MonthInventoryService extends AbstractService<MonthInventory, MonthInventory_PK> {
	@Autowired
	private MonthInventoryRepository monthInventoryRepository;
	SimpleDateFormat format=new SimpleDateFormat("yyyyMM");
	@Override
	public IRepository<MonthInventory, MonthInventory_PK> getRepository() {
		// TODO Auto-generated method stub
		return monthInventoryRepository;
	}
	/**
	 * 计算在建仓库月结库存
	 * @author mawujun 16064988@qq.com
	 */
	public void call_proc(String store_id,boolean isbuild){
		
		//如果是每个月的凌晨1点执行的话，就把时间调整为上一个月的最后一天
		Calendar cal=Calendar.getInstance();
		//int day=cal.get(Calendar.DAY_OF_MONTH);
		//if(day==1){
		//	cal.add(Calendar.DAY_OF_MONTH, -1);
		//}
		
		//Map<String,Object> params=new HashMap<String,Object>();
		String nowmonth_in=format.format(cal.getTime());
		//params.put("month_in", nowmonth_in);
		//cal.add(Calendar.DAY_OF_MONTH, -1);
		//params.put("lastmonth_in", format.format(cal.getTime()));
		if(isbuild){
			monthInventoryRepository.proc_monthinventory1(store_id,nowmonth_in);
		} else {
			monthInventoryRepository.proc_monthinventory(store_id,nowmonth_in);
		}
			
	}
	
	public void updateField(Map<String,Object> params) {
		monthInventoryRepository.updateField(params);
	}
	
	public List<MonthInventoryVO> queryMonthReport(String store_id,String month_in) {
		return monthInventoryRepository.queryMonthReport(store_id, month_in);
	}
	public List<MonthInventoryVO> queryNullMonthReport(String store_id) {
		return monthInventoryRepository.queryNullMonthReport(store_id);
	}
	
//	public List<MonthInventoryVO> querySparepartMonthReport(String store_id,String day_in) {
//		return monthInventoryRepository.querySparepartMonthReport(store_id, day_in);
//	}
}
