package com.mawujun.mobile.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.baseinfo.EquipmentRepository;
import com.mawujun.baseinfo.EquipmentStatus;
import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.baseinfo.PoleRepository;
import com.mawujun.baseinfo.PoleStatus;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.utils.M;
import com.mawujun.utils.page.Page;


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
	@Autowired
	private EquipmentRepository equipmentRepository;
	@Autowired
	private PoleRepository poleRepository;
	
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
	/**
	 * 管理人员 确认任务单
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param id
	 * @return
	 */
	public void confirm(String id) {
		taskRepository.update(Cnd.update().set(M.Task.status, TaskStatus.complete).set(M.Task.completeDate, new Date()).andEquals(M.Task.id, id));
		
		Task task=taskRepository.get(id);
		//修改杆位状态为"已安装"
		poleRepository.update(Cnd.update().set(M.Pole.status, PoleStatus.using).andEquals(M.Pole.id, task.getPole_id()));
	}
	
	
	
	public Page mobile_queryPage(Page page) {
		return this.getRepository().mobile_queryPage(page);
	}

	public List<EquipmentVO> mobile_queryTaskEquipmentInfos(String task_id) {
		Task task=taskRepository.get(task_id);
		//任务查看过后，就修改状态为“已阅”,只有任务状态为 newTask的才会被修改
		taskRepository.update(Cnd.update().set(M.Task.status, TaskStatus.handling).andEquals(M.Task.id, task_id).andEquals(M.Task.status, TaskStatus.newTask));
		//修改杆位状态为"安装中"
		poleRepository.update(Cnd.update().set(M.Pole.status, PoleStatus.installing).andEquals(M.Pole.id, task.getPole_id()));
		
		return taskRepository.mobile_queryTaskEquipmentInfos(task_id);
	}
	
	public void mobile_save(String task_id,String[] ecodes) {
		taskEquipmentListRepository.deleteBatch(Cnd.delete().andEquals(M.TaskEquipmentList.task_id, task_id));
		Set<String> existinsert=new HashSet<String>();
		for(String ecode:ecodes){
			if(existinsert.contains(ecode)){
				continue;//防止一个设备多次扫描的情况，在这里进行过滤掉
			}
			TaskEquipmentList tel=new TaskEquipmentList();
			tel.setEcode(ecode);
			tel.setTask_id(task_id);
			tel.setType(TaskListTypeEnum.install);
			taskEquipmentListRepository.create(tel);
			
			
			existinsert.add(ecode);
		}
		//修改任务状态为"处理中"
		taskRepository.update(Cnd.update().set(M.Task.status, TaskStatus.handling).set(M.Task.startHandDate,new Date()).andEquals(M.Task.id, task_id));
	}
	
	public void mobile_submit(String task_id,String[] ecodes) {
		
		//全部重新保存，因为不知道哪些是更新过的
		taskEquipmentListRepository.deleteBatch(Cnd.delete().andEquals(M.TaskEquipmentList.task_id, task_id));
		Set<String> existinsert=new HashSet<String>();
		for(String ecode:ecodes){
			if(existinsert.contains(ecode)){
				continue;//防止一个设备多次扫描的情况，在这里进行过滤掉
			}
			TaskEquipmentList tel=new TaskEquipmentList();
			tel.setEcode(ecode);
			tel.setTask_id(task_id);
			tel.setType(TaskListTypeEnum.install);
			taskEquipmentListRepository.create(tel);
			
			
			existinsert.add(ecode);
			//修改设备为“使用中”
			//修改设备为旧设备
			equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.using.getValue()).set(M.Equipment.isnew, false).andEquals(M.Equipment.ecode, ecode));		
		}
		
		//修改任务状态为"已提交"
		taskRepository.update(Cnd.update().set(M.Task.status, TaskStatus.submited).set(M.Task.submitDate, new Date()).andEquals(M.Task.id, task_id));
		
		
	}
	
}
