package com.mawujun.inventory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.baseinfo.Store;
import com.mawujun.baseinfo.StoreService;
import com.mawujun.repository1.IRepository;
import com.mawujun.service.AbstractService;

@Service
@Transactional
public class Day_sparepart_Service extends AbstractService<Day_sparepart, Day_sparepart_PK> {
	@Autowired
	private Day_sparepart_Repository day_sparepart_Repository;
	@Autowired
	private StoreService storeService;
	SimpleDateFormat yyyyMMdd_format=new SimpleDateFormat("yyyyMMdd");
	@Override
	public IRepository<Day_sparepart, Day_sparepart_PK> getRepository() {
		// TODO Auto-generated method stub
		return day_sparepart_Repository;
	}
	/**
	 * 计算指定仓库，指定日期的的库存结余情况
	 * @author mawujun 16064988@qq.com
	 */
	private void proc_report_day_sparepart(String store_id,Date date){	
		String day_in=yyyyMMdd_format.format(date);
		day_sparepart_Repository.proc_report_day_sparepart(store_id,day_in);
	}
	/**
	 * 计算当天的库存结余
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param store_id
	 */
	public void proc_report_day_sparepart(String store_id){	
		proc_report_day_sparepart(store_id,new Date());
	}
	/**
	 * 只对在当前用户可以编辑的备品备件仓库进行查询
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param type  数据仓库的类型，1：在建仓库。3：备品备件仓库
	 */
	public void proc_report_day_sparepart(Integer store_type){	
		String day_in=yyyyMMdd_format.format(new Date());
		//查询所有可以编辑的备品备件查库
		List<Store> stores=storeService.queryCombo(new Integer[]{store_type},true,true);
		for(Store store:stores){
			day_sparepart_Repository.proc_report_day_sparepart(store.getId(),day_in);
		}

	}
	
	
	//=========================================================
	/**
	 * 定时调用计算所有仓库的当日库存结余，每天定时调用
	 * @author mawujun email:160649888@163.com qq:16064988
	 */
	public void proc_report_day_sparepart_all(){	
		String day_in=yyyyMMdd_format.format(new Date());
		day_sparepart_Repository.proc_report_day_sparepart_all(day_in);
	}
	
	
	public List<Day_sparepartVO> queryDay_sparepart(String store_id,String day_start,String day_end){
		return day_sparepart_Repository.queryDay_sparepartVO(store_id, day_start,day_end);
	}
}
