package com.mawujun.mobile.task;
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

import com.mawujun.mobile.task.Task;
import com.mawujun.mobile.task.TaskService;
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
		return taskService.queryPoles(page);
	}

	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/task/query.do")
	@ResponseBody
	public Page query(Integer start,Integer limit,String sampleName){
		Page page=Page.getInstance(start,limit);//.addParam(M.Task.sampleName, "%"+sampleName+"%");
		return taskService.queryPage(page);
	}

//	@RequestMapping("/task/query.do")
//	@ResponseBody
//	public List<Task> query() {	
//		List<Task> taskes=taskService.queryAll();
//		return taskes;
//	}
	

	@RequestMapping("/task/load.do")
	public Task load(String id) {
		return taskService.get(id);
	}
	
	@RequestMapping("/task/create.do")
	@ResponseBody
	public Task create(@RequestBody Task task) {
		taskService.create(task);
		return task;
	}
	
	@RequestMapping("/task/update.do")
	@ResponseBody
	public  Task update(@RequestBody Task task) {
		taskService.update(task);
		return task;
	}
	
	@RequestMapping("/task/deleteById.do")
	@ResponseBody
	public String deleteById(String id) {
		taskService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/task/destroy.do")
	@ResponseBody
	public Task destroy(@RequestBody Task task) {
		taskService.delete(task);
		return task;
	}
	
	
}
