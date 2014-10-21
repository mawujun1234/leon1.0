package com.mawujun.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mawujun.baseinfo.EquipmentService;
import com.mawujun.baseinfo.EquipmentStatus;
import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.baseinfo.PoleService;
import com.mawujun.baseinfo.StoreService;
import com.mawujun.baseinfo.WorkUnitService;
import com.mawujun.exception.BusinessException;
/**
 * 
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
@Controller
public class EquipmentStatusController {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Resource
	private PoleService poleService;
	@Resource
	private EquipmentService equipmentService;
	@Resource
	private WorkUnitService workUnitService;
	@Resource
	private StoreService storeService;

	@RequestMapping("/equipmentstatus/query.do")
	public Map<String,Object> query(String ecode){
		Map<String,Object> result=new HashMap<String,Object>();
		//获取基本信息
		EquipmentVO baseinfo= equipmentService.getEquipmentInfo(ecode);
		if(baseinfo==null){
			throw new BusinessException("不存在该设备!");
		}
		if(baseinfo.getWorkUnit_id()!=null){
			baseinfo.setWorkUnit_name(workUnitService.get(baseinfo.getWorkUnit_id()).getName());
		} else if(baseinfo.getStore_id()!=null){
			baseinfo.setStore_name(storeService.get(baseinfo.getStore_id()).getName());
		} else if(baseinfo.getPole_id()!=null){
			baseinfo.setPole_address(poleService.get(baseinfo.getPole_id()).geetFullAddress());
		}
		for (EquipmentStatus status : EquipmentStatus.values()) {
			if (status.getValue() == baseinfo.getStatus()) {
				baseinfo.setStatus_name(status.getName());
				break;
			}
		}
		baseinfo.setFisData(null);
		baseinfo.setIsInStore(null);
		baseinfo.setMemo(null);
		baseinfo.setUnitPrice(null);
		result.put("baseinfo", baseinfo);
		
		
		
		//获取首次入库信息
		Map<String,Object> firstinstore=new HashMap<String,Object>();
		String sql="select b.orderNo,b.orderDate from ems_equipment a,ems_order b where a.order_id=b.id and a.ecode='"+ecode+"'";
		Map<String,Object> order_map=jdbcTemplate.queryForMap(sql);
		firstinstore.put("orderNo", order_map.get("orderNo"));
		firstinstore.put("orderDate", order_map.get("orderDate"));
		//获取首次入库的信息
		sql="select a.operateDate,c.name as store_name,a.operater from ems_instore a,ems_instorelist b,ems_store c where a.id=b.inStore_id and a.store_id=c.id and b.encode='"+ecode+"'";
		Map<String,Object> instore_map=jdbcTemplate.queryForMap(sql);
		firstinstore.put("instore_date",  instore_map.get("operateDate"));
		firstinstore.put("instore_name",  instore_map.get("store_name"));
		firstinstore.put("instore_operater", instore_map.get("operater"));
		
		//获取最新一次的领用信息
		sql="select a.*,rownum rn from ("
				+ " select distinct a.operatedate,a.operater,a.workUnit_id,a.store_id from ems_installout a,ems_installoutlist b ,ems_store c"
				+ " where a.id=b.installOut_id and b.ecode='"+ecode+"'"
				+ " order by a.operatedate"
				+ " ) a where rownum=1 ";
		Map<String,Object> installout_map=jdbcTemplate.queryForMap(sql);
		firstinstore.put("installout_date", installout_map.get("operatedate"));
		firstinstore.put("installout_operater", installout_map.get("operater"));
		firstinstore.put("installout_workunit_name", workUnitService.get(installout_map.get("workUnit_id").toString()).getName());
		
		result.put("firstinstore", firstinstore);
		//获取安装信息
		
		//vo.set
		return result;
	}
}
