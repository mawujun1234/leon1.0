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

import com.mawujun.baseinfo.WorkUnitContact;
import com.mawujun.baseinfo.WorkUnitContactService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/workUnitContact")
public class WorkUnitContactController {

	@Resource
	private WorkUnitContactService workUnitContactService;


//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/workUnitContact/query.do")
//	@ResponseBody
//	public List<WorkUnitContact> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.WorkUnitContact.parent.id, "root".equals(id)?null:id);
//		List<WorkUnitContact> workUnitContactes=workUnitContactService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(WorkUnitContact.class,M.WorkUnitContact.parent.name());
//		return workUnitContactes;
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
//	@RequestMapping("/workUnitContact/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.WorkUnitContact.sampleName, "%"+sampleName+"%");
//		return workUnitContactService.queryPage(page);
//	}

	@RequestMapping("/workUnitContact/query.do")
	@ResponseBody
	public List<WorkUnitContact> query(String workunit_id) {	
		Cnd cnd=Cnd.select().andEquals(M.WorkUnitContact.workunit_id, workunit_id);
		List<WorkUnitContact> workUnitContactes=workUnitContactService.query(cnd);
		return workUnitContactes;
	}
	

	@RequestMapping("/workUnitContact/load.do")
	public WorkUnitContact load(String id) {
		return workUnitContactService.get(id);
	}
	
	@RequestMapping("/workUnitContact/create.do")
	@ResponseBody
	public WorkUnitContact create(@RequestBody WorkUnitContact workUnitContact) {
		workUnitContactService.create(workUnitContact);
		return workUnitContact;
	}
	
	@RequestMapping("/workUnitContact/update.do")
	@ResponseBody
	public  WorkUnitContact update(@RequestBody WorkUnitContact workUnitContact) {
		workUnitContactService.update(workUnitContact);
		return workUnitContact;
	}
	
	@RequestMapping("/workUnitContact/deleteById.do")
	@ResponseBody
	public String deleteById(String id) {
		workUnitContactService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/workUnitContact/destroy.do")
	@ResponseBody
	public WorkUnitContact destroy(WorkUnitContact workUnitContact) {
		workUnitContactService.delete(workUnitContact);
		return workUnitContact;
	}
	
	
}
