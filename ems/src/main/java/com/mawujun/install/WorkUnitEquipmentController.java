package com.mawujun.install;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;
import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.install.WorkUnitEquipment;
import com.mawujun.install.WorkUnitEquipmentService;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.page.Page;
import com.mawujun.utils.M;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/workUnitEquipment")
public class WorkUnitEquipmentController {

	@Resource
	private WorkUnitEquipmentService workUnitEquipmentService;

//
//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/workUnitEquipment/query.do")
//	@ResponseBody
//	public List<WorkUnitEquipment> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.WorkUnitEquipment.parent.id, "root".equals(id)?null:id);
//		List<WorkUnitEquipment> workUnitEquipmentes=workUnitEquipmentService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(WorkUnitEquipment.class,M.WorkUnitEquipment.parent.name());
//		return workUnitEquipmentes;
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
//	@RequestMapping("/workUnitEquipment/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.WorkUnitEquipment.sampleName, "%"+sampleName+"%");
//		return workUnitEquipmentService.queryPage(page);
//	}

	@RequestMapping("/workUnitEquipment/query.do")
	@ResponseBody
	public List<WorkUnitEquipment> query() {	
		List<WorkUnitEquipment> workUnitEquipmentes=workUnitEquipmentService.queryAll();
		return workUnitEquipmentes;
	}
	

	@RequestMapping("/workUnitEquipment/load.do")
	public WorkUnitEquipment load(String id) {
		return workUnitEquipmentService.get(id);
	}
	
	@RequestMapping("/workUnitEquipment/create.do")
	@ResponseBody
	public WorkUnitEquipment create(@RequestBody WorkUnitEquipment workUnitEquipment) {
		workUnitEquipmentService.create(workUnitEquipment);
		return workUnitEquipment;
	}
	
	@RequestMapping("/workUnitEquipment/update.do")
	@ResponseBody
	public  WorkUnitEquipment update(@RequestBody WorkUnitEquipment workUnitEquipment) {
		workUnitEquipmentService.update(workUnitEquipment);
		return workUnitEquipment;
	}
	
	@RequestMapping("/workUnitEquipment/deleteById.do")
	@ResponseBody
	public String deleteById(String id) {
		workUnitEquipmentService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/workUnitEquipment/destroy.do")
	@ResponseBody
	public WorkUnitEquipment destroy(@RequestBody WorkUnitEquipment workUnitEquipment) {
		workUnitEquipmentService.delete(workUnitEquipment);
		return workUnitEquipment;
	}
	
	
}
