package com.mawujun.mobile.others;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.baseinfo.EquipmentService;
import com.mawujun.baseinfo.EquipmentStatus;
import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.baseinfo.PoleService;
import com.mawujun.baseinfo.StoreService;
import com.mawujun.baseinfo.WorkUnitService;
import com.mawujun.mobile.task.Task;
import com.mawujun.shiro.ShiroUtils;
import com.mawujun.utils.M;

@Controller
public class OthersController {
	@Resource
	private EquipmentService equipmentService;
	@Resource
	private StoreService storeService;
	@Resource
	private PoleService poleService;
	@Resource
	private WorkUnitService workUnitService;
	
	@RequestMapping("/others/mobile/getEquipmentInfo.do")
	@ResponseBody
	public EquipmentVO getEquipmentInfo(String ecode){
		EquipmentVO vo= equipmentService.getEquipmentInfo(ecode);
		if(vo.getWorkUnit_id()!=null){
			vo.setWorkUnit_name(workUnitService.get(vo.getWorkUnit_id()).getName());
		} else if(vo.getStore_id()!=null){
			vo.setStore_name(storeService.get(vo.getStore_id()).getName());
		} else if(vo.getPole_id()!=null){
			vo.setPole_address(poleService.get(vo.getPole_id()).returnFullAddress());
		}
		for (EquipmentStatus status : EquipmentStatus.values()) {
			if (status.getValue() == vo.getStatus()) {
				vo.setStatus_name(status.getName());
				break;
			}
		}
		vo.setFisData(null);
		vo.setIsInStore(null);
		vo.setMemo(null);
		vo.setUnitPrice(null);
		//vo.set
		return vo;
		//return equipmentVO;
	}
	
	@RequestMapping("/others/mobile/queryHaveEquipmentInfos.do")
	@ResponseBody
	public List<Map<String,Object>> queryHaveEquipmentInfos(){
		List<EquipmentVO> equipments=workUnitService.queryEquipments(ShiroUtils.getAuthenticationInfo().getId());
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		for(EquipmentVO vo:equipments){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put(M.Equipment.ecode, vo.getEcode());
			map.put(M.Equipment.style, vo.getStyle());
			map.put("subtype_name", vo.getSubtype_name());
			map.put("prod_name", vo.getProd_name());
			map.put("brand_name", vo.getBrand_name());
			map.put("supplier_name", vo.getSupplier_name());
			//map.put("", );
			//map.put("", );
			//map.put("", );
			//map.put("", );
			list.add(map);
		}
		
		
		
		return list;
	}
}
