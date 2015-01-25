package com.mawujun.baseinfo;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;
import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.page.Page;
import com.mawujun.utils.M;
import com.mawujun.baseinfo.WorkUnit;
import com.mawujun.baseinfo.WorkUnitService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/workUnit")
public class WorkUnitController {

	@Resource
	private WorkUnitService workUnitService;


//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/workUnit/query.do")
//	@ResponseBody
//	public List<WorkUnit> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.WorkUnit.parent.id, "root".equals(id)?null:id);
//		List<WorkUnit> workUnites=workUnitService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(WorkUnit.class,M.WorkUnit.parent.name());
//		return workUnites;
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
//	@RequestMapping("/workUnit/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.WorkUnit.sampleName, "%"+sampleName+"%");
//		return workUnitService.queryPage(page);
//	}

	@RequestMapping("/workUnit/query.do")
	@ResponseBody
	public List<WorkUnit> query() {	
		List<WorkUnit> workUnites=workUnitService.queryAll();
		return workUnites;
	}
	

	@RequestMapping("/workUnit/load.do")
	public WorkUnit load(String id) {
		return workUnitService.get(id);
	}
	
	@RequestMapping("/workUnit/create.do")
	@ResponseBody
	public WorkUnit create(@RequestBody WorkUnit workUnit) {
		workUnitService.create(workUnit);
		return workUnit;
	}
	
	@RequestMapping("/workUnit/update.do")
	@ResponseBody
	public  WorkUnit update(@RequestBody WorkUnit workUnit) {
		workUnitService.update(workUnit);
		return workUnit;
	}
	
//	@RequestMapping("/workUnit/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		workUnitService.deleteById(id);
//		return id;
//	}
	
	@RequestMapping("/workUnit/destroy.do")
	@ResponseBody
	public WorkUnit destroy(WorkUnit workUnit) {
		//workUnitService.delete(workUnit);
		workUnit.setStatus(false);
		workUnitService.update(workUnit);
		return workUnit;
	}
	
	@RequestMapping("/workUnit/queryCombo.do")
	@ResponseBody
	public List<WorkUnit> queryCombo(String name) {	
		List<WorkUnit> workUnites=null;
		if(name==null){
			workUnites=workUnitService.queryAll();
		} else {
			workUnites=workUnitService.query(Cnd.select().andLike(M.WorkUnit.name, name));
		}
		//List<WorkUnit> workUnites=workUnitService.query(Cnd.select().andLike(M.WorkUnit.name, name));
		return workUnites;
	}
	
	@RequestMapping("/workUnit/queryEquipments.do")
	@ResponseBody
	public List<EquipmentVO> queryEquipments(Integer level,String workUnit_id) {	
		List<EquipmentVO> equipments=workUnitService.queryEquipments(workUnit_id);
		return equipments;
	}
	
	
}
