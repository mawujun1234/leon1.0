package com.mawujun.mobile.task;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;










import com.mawujun.service.AbstractService;


import com.mawujun.mobile.message.Message;
import com.mawujun.mobile.message.MessageRepository;
import com.mawujun.mobile.message.MessageService;
import com.mawujun.mobile.message.MessageType;
import com.mawujun.mobile.task.Overtime;
import com.mawujun.mobile.task.OvertimeRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
@Lazy(value=false)
public class OvertimeService extends AbstractService<Overtime, String>{

	@Autowired
	private OvertimeRepository overtimeRepository;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private MessageService messageService;
	
	@Override
	public OvertimeRepository getRepository() {
		return overtimeRepository;
	}

	/**
	 * 每隔5分钟去扫描,判断有没有任务过期了，如果有就发消息过去
	 * @author mawujun 16064988@qq.com
	 */
	//@Scheduled(cron = "0 0/10 * * * ?")  
    public void createOvertimeMessage() {  
		//System.out.println("111111111111111111111111111111111111111");
		//首先获取过期时间
		Overtime overtime=overtimeRepository.get("overtime");
//		//处理未阅读的任务,要按人进行发送
//		List<Task> list=taskRepository.queryReadOvertimeTask(overtime.getRead());
//		//如果已发送的怎么办，
//		for(Task task:list){
//			Message msg=new Message();
//			msg.setCreateDate(new Date());
//			msg.setIsNew(true);
//			String task_type="";
//			if(task.getType()==TaskType.newInstall){
//				task_type="新安装";
//			} else if(task.getType()==TaskType.repair){ 
//				task_type="维修";
//			} else if(task.getType()==TaskType.patrol){
//				task_type="巡检";
//			}
//			msg.setContent(task_type+"任务"+task.getId()+"已经超期，请尽快处理!");
//			msg.setTask_id(task.getId());
//			msg.setType(MessageType.overtime);
//			msg.setWorkunit_id(task.getWorkunit_id());
//			
//			messageService.create(msg);
//		}
		//处理未处理的任务
		List<Task> list=taskRepository.queryHandlingOvertimeTask(overtime.getHandling());
		//如果已发送的怎么办，
		for(Task task:list){
			Message msg=new Message();
			msg.setCreateDate(new Date());
			msg.setIsNew(true);
			String task_type="";
			if(task.getType()==TaskType.newInstall){
				task_type="新安装";
			} else if(task.getType()==TaskType.repair){ 
				task_type="维修";
			} else if(task.getType()==TaskType.patrol){
				task_type="巡检";
			}
			msg.setContent(task_type+"任务"+task.getId()+"已经超期，请尽快处理!");
			msg.setTask_id(task.getId());
			msg.setType(MessageType.overtime);
			msg.setWorkunit_id(task.getWorkunit_id());
			messageService.create(msg);
		}
		
		
    }  
}
