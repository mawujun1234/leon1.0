package com.mawujun.report;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mawujun.baseinfo.EquipmentPlace;
import com.mawujun.baseinfo.EquipmentPoleVO;
import com.mawujun.baseinfo.EquipmentRepository;
import com.mawujun.baseinfo.EquipmentService;
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
	private EquipmentRepository equipmentRepository;
	@Resource
	private WorkUnitService workUnitService;
	@Resource
	private StoreService storeService;
	//@Resource
	//private EquipmentCycleService equipmentCycleService;

	@RequestMapping("/equipmentstatus/query.do")
	public EquipmentVO query(String ecode){
		//Map<String,Object> result=new HashMap<String,Object>();
		//获取基本信息
		EquipmentVO baseinfo= equipmentService.getEquipmentInfo(ecode);
		if(baseinfo==null){
			throw new BusinessException("不存在该设备!");
		}
		//这里要进行修改
//		if(baseinfo.getWorkUnit_id()!=null){
//			baseinfo.setWorkUnit_name(workUnitService.get(baseinfo.getWorkUnit_id()).getName());
//		} else if(baseinfo.getStore_id()!=null){
//			baseinfo.setStore_name(storeService.get(baseinfo.getStore_id()).getName());
//		} else if(baseinfo.getPole_id()!=null){
//			baseinfo.setPole_address(poleService.get(baseinfo.getPole_id()).geetFullAddress());
//		}
		if(baseinfo.getPlace()==EquipmentPlace.workunit){
			baseinfo.setWorkUnit_name(equipmentRepository.getEquipmentWorkunitVO(ecode).getWorkunit_name());
		} else if(baseinfo.getPlace()==EquipmentPlace.store ){
			baseinfo.setStore_name(equipmentRepository.getEquipmentStoreVO(ecode).getStore_name());
		} else if(baseinfo.getPlace()==EquipmentPlace.repair){
			baseinfo.setStore_name(equipmentRepository.getEquipmentRepairVO(ecode).getRepair_name());
		} else if(baseinfo.getPlace()==EquipmentPlace.pole){
			EquipmentPoleVO aaaa=equipmentRepository.getEquipmentPoleVO(ecode);
			baseinfo.setPole_address(aaaa.getPole_address());
			baseinfo.setPole_code(aaaa.getPole_code());
		}

		baseinfo.setFisData(null);
		baseinfo.setIsInStore(null);
		baseinfo.setMemo(null);
		baseinfo.setUnitPrice(null);
		//result.put("baseinfo", baseinfo);
		
//		List<EquipmentCycle> lifeCycles=equipmentCycleService.query(Cnd.select().andEquals(M.EquipmentCycle.ecode, ecode).asc(M.EquipmentCycle.operateDate));
//		StringBuilder builder=new StringBuilder();
//		for(EquipmentCycle lifeCycle:lifeCycles){
//			//builder.append(lifeCycle.getCycleInfo()+"<br/>");
//		}
//		result.put("lifeCycle", builder);

		
		//vo.set
		return baseinfo;
	}
}
