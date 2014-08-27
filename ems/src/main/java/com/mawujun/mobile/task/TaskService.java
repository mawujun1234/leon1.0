package com.mawujun.mobile.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import com.mawujun.service.AbstractService;


import com.mawujun.utils.page.Page;
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
	
	@Override
	public TaskRepository getRepository() {
		return taskRepository;
	}

	public Page queryPoles(Page page) {
		return taskRepository.queryPoles(page);
	}
}
