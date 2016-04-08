package com.mawujun.inventory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
	 * 计算设备的净值
	 * @author mawujun email:160649888@163.com qq:16064988
	 */
	public void proc_equipment_unitprice(){	
		day_sparepart_Repository.proc_equipment_unitprice();
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
	
	/**
	 * 定时调用计算设备的残值
	 * @author mawujun email:160649888@163.com qq:16064988
	 */
	public void proc_report_assetclean(){	
		String day_in=yyyyMMdd_format.format(new Date());
		day_sparepart_Repository.proc_report_day_sparepart_all(day_in);
	}
	
	
	public List<Day_sparepart_type> queryDay_sparepart(String store_id,Integer store_type,String day_start,String day_end){
		if(!StringUtils.hasText(store_id) && store_type!=null){
			List<Store> stores=storeService.queryCombo(new Integer[]{store_type},true,true);
			StringBuilder builder=new StringBuilder();
			for(Store store:stores){
				builder.append(",'");
				builder.append(store.getId());
				builder.append("'");
				
			}
			//查询具体某个一仓库
			return day_sparepart_Repository.queryDay_sparepartVO(builder.substring(1),true, day_start,day_end);
		} else {
			//查询具体某个一仓库
			return day_sparepart_Repository.queryDay_sparepartVO(store_id,false, day_start,day_end);
		}
		
	}
	public List<Month_sparepart_type> queryMonth_sparepartVO(String store_id,Integer store_type,String day_start,String day_end){
		List<Month_sparepart_type> result;
		if(!StringUtils.hasText(store_id) && store_type!=null){
			List<Store> stores=storeService.queryCombo(new Integer[]{store_type},true,true);
			StringBuilder builder=new StringBuilder();
			for(Store store:stores){
				builder.append(",'");
				builder.append(store.getId());
				builder.append("'");
				
			}
			//查询具体某个一仓库
			result= day_sparepart_Repository.queryMonth_sparepartVO(builder.substring(1),true, day_start,day_end);
			 
		} else {
			//查询具体某个一仓库
			result= day_sparepart_Repository.queryMonth_sparepartVO(store_id,false, day_start,day_end);
			//return result;
		}
		return result;
	}
	/**
	 * 返回以prodid+store_id为key, yesterdaynum为值的map
	 * @author mawujun 16064988@qq.com 
	 * @param store_id
	 * @param store_type
	 * @param day_start
	 * @return
	 */
	public Map<String,Integer> queryMonth_yesterdaynum(String store_id,Integer store_type,String day_start) {
		List<Month_sparepart_prod> yesterdaynums_list;
		if(!StringUtils.hasText(store_id) && store_type!=null){
			List<Store> stores=storeService.queryCombo(new Integer[]{store_type},true,true);
			StringBuilder builder=new StringBuilder();
			for(Store store:stores){
				builder.append(",'");
				builder.append(store.getId());
				builder.append("'");
				
			}
			 //获取昨天的数据
			yesterdaynums_list=day_sparepart_Repository.queryMonth_yesterdaynum(builder.substring(1),true, day_start);
		} else {
			//获取昨天的数据
			yesterdaynums_list=day_sparepart_Repository.queryMonth_yesterdaynum(store_id,false, day_start);
		}
		//再转换成使用store_id+prod_id为key，yesterdaynum为value的map，方便后面读取出来填充
		Map<String,Integer> result=new HashMap<String,Integer>();
		if(yesterdaynums_list!=null) {
			for(Month_sparepart_prod prod:yesterdaynums_list) {
				result.put(prod.getProd_id()+"_"+prod.getStore_id(), prod.getYesterdaynum());
			}
		}
		
		return result;
	}
}
