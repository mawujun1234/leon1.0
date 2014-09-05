package com.mawujun.mobile.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import org.springframework.web.bind.annotation.RequestBody;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;


import com.mawujun.utils.M;
import com.mawujun.utils.page.Page;
import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.mobile.task.Task;
import com.mawujun.mobile.task.TaskRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class TaskService extends AbstractService<Task, String>{

	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private TaskEquipmentListRepository taskEquipmentListRepository;
	
	@Override
	public TaskRepository getRepository() {
		return taskRepository;
	}
	
	SimpleDateFormat ymdHmsDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");

	public Page queryPoles(Page page) {
		return taskRepository.queryPoles(page);
	}
	
	public String create(Task task) {
		Date createDate=new Date();
		task.setCreateDate(createDate);
		task.setStatus(TaskStatus.newTask);
		task.setId(ymdHmsDateFormat.format(createDate)+"-001");
		super.create(task);
		return task.getId();
	}

	public List<EquipmentVO> queryTaskEquipmentInfos(String task_id) {
		return taskRepository.queryTaskEquipmentInfos(task_id);
	}
	
	public void save(String task_id,String[] ecodes) {
		taskEquipmentListRepository.deleteBatch(Cnd.delete().andEquals(M.TaskEquipmentList.task_id, task_id));
		//for(String,ecodes){
			
		//}
	}
	
}
