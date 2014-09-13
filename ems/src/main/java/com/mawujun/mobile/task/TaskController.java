package com.mawujun.mobile.task;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.baseinfo.EquipmentService;
import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.baseinfo.Pole;
import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.shiro.ShiroUtils;
import com.mawujun.utils.M;
import com.mawujun.utils.page.Page;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/task")
public class TaskController {

	@Resource
	private TaskService taskService;
	
	@Resource
	private EquipmentService equipmentService;


	/**
	 * 请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param id 是父节点的id
	 * @return
	 */
	@RequestMapping("/task/queryPoles.do")
	@ResponseBody
	public Page queryPoles(Integer start,Integer limit,String customer_id,String area_id,String workunit_id,String pole_name) {
		Page page=Page.getInstance(start,limit);
		page.addParam("customer_id", customer_id);
		page.addParam("area_id", area_id);
		page.addParam("workunit_id", workunit_id);
		if(pole_name!=null){
			page.addParam("pole_name", "%"+pole_name+"%");
		}
		Page result= taskService.queryPoles(page);
		
		//因为一个杆位会有多个任务
		int i=0;
		for(Object obj:result.getResult()){
			Pole pole=(Pole)obj;
			pole.setId(pole.getId()+"-"+i);
			i++;
		}
		return result;
	}
	
	@RequestMapping("/task/query.do")
	@ResponseBody
	public Page query(Integer start,Integer limit,String customer_id,String status,String workunit_id,String pole_name) {
		Page page=Page.getInstance(start,limit);
		page.addParam("customer_id", customer_id);
		page.addParam("status", status);
		page.addParam("workunit_id", workunit_id);
		if(pole_name!=null){
			page.addParam("pole_name", "%"+pole_name+"%");
		}
		return taskService.queryPage(page);
	}
	@RequestMapping("/task/confirm.do")
	@ResponseBody
	public String confirm(String id) {
		taskService.confirm(id);
		return "success";
	}
	
	@RequestMapping("/task/back.do")
	@ResponseBody
	public String backs(String id) {
		taskService.update(Cnd.update().set(M.Task.status, TaskStatus.handling).andEquals(M.Task.id, id));
		return "success";
	}
	@RequestMapping("/task/cancel.do")
	@ResponseBody
	public String cancel(String id) {
		taskService.deleteBatch(Cnd.delete().andEquals(M.Task.id, id));
		return "success";
	}
//
//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/task/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.Task.sampleName, "%"+sampleName+"%");
//		return taskService.queryPage(page);
//	}
	
	
//	@RequestMapping("/task/create.do")
//	@ResponseBody
//	public Task create(@RequestBody Task task) {
//		taskService.create(new Task[]{task});
//		return task;
//	}
	
	@RequestMapping("/task/create.do")
	@ResponseBody
	public String create(@RequestBody Task[] taskes) {
		taskService.create(taskes);
		return "success";
	}
	
	
	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/task/mobile/query.do")
	@ResponseBody
	public Page mobile_query(Integer start,Integer limit,String status,String searchStr){
		Page page=Page.getInstance(start,limit);//.addParam(M.Task.sampleName, "%"+sampleName+"%");
		page.addParam(M.Task.status, status);
		page.addParam(M.Task.workunit_id, ShiroUtils.getAuthenticationInfo().getId());
		return taskService.mobile_queryPage(page);
	}
	/**
	 * 查询某个任务下的设备挂载情况
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param task_id
	 * @return
	 */
	@RequestMapping("/task/mobile/queryTaskEquipmentInfos.do")
	@ResponseBody
	public List<EquipmentVO> mobile_queryTaskEquipmentInfos(String task_id){
		List<EquipmentVO> equipmentVOs=taskService.mobile_queryTaskEquipmentInfos(task_id);
		
		return equipmentVOs;
	}
	/**
	 * 获取某个设备的信息，主要用于扫描的额时候
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param ecode
	 * @return
	 */
	@RequestMapping("/task/mobile/getEquipmentInfo.do")
	@ResponseBody
	public EquipmentVO getEquipmentInfo(String ecode){
		EquipmentVO equipmentVO=equipmentService.getEquipmentInfo(ecode);
		
		//Map<String,String> map=new HashMap<String,String>();
		//map.put("prod_name", value);
		//map.put("style", value);
		if(equipmentVO==null){
			throw new BusinessException("没有这个设备");
		}
		return equipmentVO;
	}
	
	/**
	 * 移动端的设备进行保存
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param task
	 * @return
	 */
	@RequestMapping("/task/mobile/save.do")
	@ResponseBody
	public String mobile_save(String task_id,String[] ecodes) {
		//jquery 2 json地方有文图，，不能将数组正确的转换
		taskService.mobile_save(task_id,ecodes);
		return "success";
	}
	
	/**
	 * 移动端的设备进行保存
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param task
	 * @return
	 */
	@RequestMapping("/task/mobile/submit.do")
	@ResponseBody
	public String mobile_submit(String task_id,String task_type,String[] ecodes) {
		//jquery 2 json地方有文图，，不能将数组正确的转换
		taskService.mobile_submit(task_id,task_type,ecodes);
		return "success";
	}
	

//	@RequestMapping("/task/query.do")
//	@ResponseBody
//	public List<Task> query() {	
//		List<Task> taskes=taskService.queryAll();
//		return taskes;
//	}
//	
//
//	@RequestMapping("/task/load.do")
//	public Task load(String id) {
//		return taskService.get(id);
//	}
//	
//	@RequestMapping("/task/create.do")
//	@ResponseBody
//	public Task create(@RequestBody Task task) {
//		taskService.create(task);
//		return task;
//	}
//	
//	@RequestMapping("/task/update.do")
//	@ResponseBody
//	public  Task update(@RequestBody Task task) {
//		taskService.update(task);
//		return task;
//	}
//	
//	@RequestMapping("/task/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		taskService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/task/destroy.do")
//	@ResponseBody
//	public Task destroy(@RequestBody Task task) {
//		taskService.delete(task);
//		return task;
//	}
	
	
}
