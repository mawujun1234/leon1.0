package com.mawujun.baseinfo;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;
import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.store.Barcode;
import com.mawujun.store.BarcodeService;
import com.mawujun.utils.page.Page;
import com.mawujun.utils.BeanUtils;
import com.mawujun.utils.M;
import com.mawujun.baseinfo.Equipment;
import com.mawujun.baseinfo.EquipmentService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/equipment")
public class EquipmentController {

	@Resource
	private EquipmentService equipmentService;
	
	@Resource
	private BarcodeService barcodeService;


//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/equipment/query.do")
//	@ResponseBody
//	public List<Equipment> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.Equipment.parent.id, "root".equals(id)?null:id);
//		List<Equipment> equipmentes=equipmentService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(Equipment.class,M.Equipment.parent.name());
//		return equipmentes;
//	}
//
//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/equipment/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.Equipment.sampleName, "%"+sampleName+"%");
//		return equipmentService.queryPage(page);
//	}

	@RequestMapping("/equipment/query.do")
	@ResponseBody
	public List<Equipment> query() {	
		List<Equipment> equipmentes=equipmentService.queryAll();
		return equipmentes;
	}
	

	@RequestMapping("/equipment/load.do")
	public Equipment load(String id) {
		return equipmentService.get(id);
	}
	
	@RequestMapping("/equipment/create.do")
	@ResponseBody
	public Equipment create(@RequestBody Equipment equipment) {
		equipmentService.create(equipment);
		return equipment;
	}
	
	@RequestMapping("/equipment/update.do")
	@ResponseBody
	public  Equipment update(@RequestBody Equipment equipment) {
		equipmentService.update(equipment);
		return equipment;
	}
	
	@RequestMapping("/equipment/deleteById.do")
	@ResponseBody
	public String deleteById(String id) {
		equipmentService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/equipment/destroy.do")
	@ResponseBody
	public Equipment destroy(@RequestBody Equipment equipment) {
		equipmentService.delete(equipment);
		return equipment;
	}
	
	
	
	
}
