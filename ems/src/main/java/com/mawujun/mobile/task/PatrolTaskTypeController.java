package com.mawujun.mobile.task;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.M;
import com.mawujun.utils.page.PageParam;
import com.mawujun.utils.page.PageResult;

import com.mawujun.mobile.task.PatrolTaskType;
import com.mawujun.mobile.task.PatrolTaskTypeService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/patrolTaskType")
public class PatrolTaskTypeController {

	@Resource
	private PatrolTaskTypeService patrolTaskTypeService;
	@Resource
	private TaskRepository taskRepository;

//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/patrolTaskType/query.do")
//	@ResponseBody
//	public List<PatrolTaskType> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.PatrolTaskType.id, "root".equals(id)?null:id);
//		List<PatrolTaskType> patrolTaskTypees=patrolTaskTypeService.query(cnd);
//		return patrolTaskTypees;
//	}


	@RequestMapping("/patrolTaskType/queryAll.do")
	@ResponseBody
	public List<PatrolTaskType> queryAll() {	
		List<PatrolTaskType> patrolTaskTypees=patrolTaskTypeService.queryAll();
		return patrolTaskTypees;
	}
	

	@RequestMapping("/patrolTaskType/load.do")
	public PatrolTaskType load(String id) {
		return patrolTaskTypeService.get(id);
	}
	
	@RequestMapping("/patrolTaskType/create.do")
	@ResponseBody
	public PatrolTaskType create(@RequestBody PatrolTaskType patrolTaskType) {
		patrolTaskTypeService.create(patrolTaskType);
		return patrolTaskType;
	}
	
	@RequestMapping("/patrolTaskType/update.do")
	@ResponseBody
	public  PatrolTaskType update(@RequestBody PatrolTaskType patrolTaskType) {
		patrolTaskTypeService.update(patrolTaskType);
		return patrolTaskType;
	}
	
//	@RequestMapping("/patrolTaskType/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		patrolTaskTypeService.deleteById(id);
//		return id;
//	}
	
	@RequestMapping("/patrolTaskType/destroy.do")
	@ResponseBody
	public PatrolTaskType destroy(@RequestBody PatrolTaskType patrolTaskType) {
		//先判断这个类型有没有被引用，如果有，就不能删除
		int count=taskRepository.count_patrolTaskType_id(patrolTaskType.getId());
		if(count>0){
			throw new BusinessException("不能删除，已经被任务引用!");
		}
		
		patrolTaskTypeService.delete(patrolTaskType);
		return patrolTaskType;
	}
	
	
}
