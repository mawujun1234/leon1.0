package com.mawujun.inventory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.repository1.IRepository;
import com.mawujun.service.AbstractService;

@Service
@Transactional
public class DayInventoryService extends AbstractService<DayInventory, DayInventory_PK> {
	@Autowired
	private DayInventoryRepository dayInventoryRepository;
	SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
	@Override
	public IRepository<DayInventory, DayInventory_PK> getRepository() {
		// TODO Auto-generated method stub
		return dayInventoryRepository;
	}
//	/**
//	 * 创建月结库存
//	 * @author mawujun 16064988@qq.com
//	 */
//	public void callProc(){	
//		Map<String,Object> params=new HashMap<String,Object>();
//		//获取当前月，格式为 201409
//		//Calendar cal=Calendar.getInstance();
//		String day_in=format.format(new Date());
//		params.put("day_in", day_in);
//		Calendar cal=Calendar.getInstance();
//		cal.add(Calendar.DAY_OF_MONTH, -1);
//		params.put("lastday_in", format.format(cal.getTime()));
//
//		
//		dayInventoryRepository.callProc(params);
//		
//		
//	}
}
