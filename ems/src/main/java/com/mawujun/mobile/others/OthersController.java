package com.mawujun.mobile.others;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.baseinfo.EquipmentPlace;
import com.mawujun.baseinfo.EquipmentRepository;
import com.mawujun.baseinfo.EquipmentService;
import com.mawujun.baseinfo.EquipmentSubtype;
import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.baseinfo.PoleService;
import com.mawujun.baseinfo.StoreService;
import com.mawujun.baseinfo.WorkUnitService;
import com.mawujun.shiro.ShiroUtils;
import com.mawujun.utils.M;

@Controller
public class OthersController {
	@Resource
	private EquipmentRepository equipmentRepository;
	@Resource
	private StoreService storeService;
	@Resource
	private PoleService poleService;
	@Resource
	private WorkUnitService workUnitService;
	
	@RequestMapping("/others/mobile/getEquipmentInfo.do")
	@ResponseBody
	public EquipmentVO getEquipmentInfo(String ecode){
		EquipmentVO vo= equipmentRepository.getEquipmentInfo(ecode);
		if(vo.getPlace()==EquipmentPlace.workunit){
			vo.setWorkUnit_name(equipmentRepository.getEquipmentWorkunitVO(ecode).getWorkunit_name());
		} else if(vo.getPlace()==EquipmentPlace.store ){
			vo.setStore_name(equipmentRepository.getEquipmentStoreVO(ecode).getStore_name());
		} else if(vo.getPlace()==EquipmentPlace.repair){
			vo.setPole_address(equipmentRepository.getEquipmentRepairVO(ecode).getRepair_name());
		} else if(vo.getPlace()==EquipmentPlace.pole){
			vo.setPole_address(equipmentRepository.getEquipmentPoleVO(ecode).getPole_address());
		}

		vo.setFisData(null);
		vo.setIsInStore(null);
		vo.setMemo(null);
		//vo.setUnitPrice(null);
		vo.setValue_original(null);
		//vo.set
		return vo;
		//return equipmentVO;
	}
	
	/**
	 * 获取类型，品名，及该品名下有多少设备的汇总信息
	 * @author mawujun 16064988@qq.com 
	 * @return
	 */
	@RequestMapping("/others/mobile/queryHaveEquipmentInfosTotal.do")
	@ResponseBody
	public List<EquipmentSubtype> queryHaveEquipmentInfosTotal(){
		return workUnitService.queryHaveEquipmentInfosTotal(ShiroUtils.getAuthenticationInfo().getId());
	}
	/**
	 * 获取某个品名下面的详细的设备条码
	 * @author mawujun 16064988@qq.com 
	 * @return
	 */
	@RequestMapping("/others/mobile/queryHaveEquipmentInfosDetail.do")
	@ResponseBody
	public List<Map<String,Object>> queryHaveEquipmentInfosDetail(String prod_id){
		List<EquipmentVO> equipments=workUnitService.queryHaveEquipmentInfosDetail(ShiroUtils.getAuthenticationInfo().getId(),prod_id);
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		for(EquipmentVO vo:equipments){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put(M.Equipment.ecode, vo.getEcode());
			//map.put(M.Equipment.style, vo.getStyle());
			//map.put("subtype_name", vo.getSubtype_name());
			//map.put("prod_name", vo.getProd_name());
			map.put("brand_name", vo.getBrand_name());
			//map.put("supplier_name", vo.getSupplier_name());

			list.add(map);
		}
		
		
		
		return list;
	}
}
