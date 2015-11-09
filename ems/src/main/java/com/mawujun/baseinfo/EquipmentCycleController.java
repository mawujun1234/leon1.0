package com.mawujun.baseinfo;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;
import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.page.Page;
import com.mawujun.utils.M;

import com.mawujun.baseinfo.EquipmentCycle;
import com.mawujun.baseinfo.EquipmentCycleService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/equipmentCycle")
public class EquipmentCycleController {

	@Resource
	private EquipmentCycleService equipmentCycleService;


	/**
	 * 请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param id 是父节点的id
	 * @return
	 */
	@RequestMapping("/equipmentCycle/query.do")
	@ResponseBody
	public List<EquipmentCycle> query(String ecode) {
		List<EquipmentCycle> lifeCycles=equipmentCycleService.query(Cnd.select().andEquals(M.EquipmentCycle.ecode, ecode).asc(M.EquipmentCycle.operateDate));
		JsonConfigHolder.setDatePattern("yyyy-MM-dd HH:mm:ss");
		return lifeCycles;
	}

//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/equipmentCycle/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.EquipmentCycle.sampleName, "%"+sampleName+"%");
//		return equipmentCycleService.queryPage(page);
//	}
//
//	@RequestMapping("/equipmentCycle/query.do")
//	@ResponseBody
//	public List<EquipmentCycle> query() {	
//		List<EquipmentCycle> equipmentCyclees=equipmentCycleService.queryAll();
//		return equipmentCyclees;
//	}
//	
//
//	@RequestMapping("/equipmentCycle/load.do")
//	public EquipmentCycle load(String id) {
//		return equipmentCycleService.get(id);
//	}
//	
//	@RequestMapping("/equipmentCycle/create.do")
//	@ResponseBody
//	public EquipmentCycle create(@RequestBody EquipmentCycle equipmentCycle) {
//		equipmentCycleService.create(equipmentCycle);
//		return equipmentCycle;
//	}
//	
//	@RequestMapping("/equipmentCycle/update.do")
//	@ResponseBody
//	public  EquipmentCycle update(@RequestBody EquipmentCycle equipmentCycle) {
//		equipmentCycleService.update(equipmentCycle);
//		return equipmentCycle;
//	}
//	
//	@RequestMapping("/equipmentCycle/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		equipmentCycleService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/equipmentCycle/destroy.do")
//	@ResponseBody
//	public EquipmentCycle destroy(@RequestBody EquipmentCycle equipmentCycle) {
//		equipmentCycleService.delete(equipmentCycle);
//		return equipmentCycle;
//	}
//	
//	
}
