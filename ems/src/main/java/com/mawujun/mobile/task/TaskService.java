package com.mawujun.mobile.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;





















import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.baseinfo.Equipment;
import com.mawujun.baseinfo.EquipmentRepository;
import com.mawujun.baseinfo.EquipmentService;
import com.mawujun.baseinfo.EquipmentStatus;
import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.baseinfo.Pole;
import com.mawujun.baseinfo.PoleRepository;
import com.mawujun.baseinfo.PoleStatus;
import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.shiro.ShiroUtils;
import com.mawujun.utils.AssertUtils;
import com.mawujun.utils.M;
import com.mawujun.utils.StringUtils;
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
	@Resource
	private EquipmentService equipmentService;
	
	@Override
	public TaskRepository getRepository() {
		return taskRepository;
	}
	
	SimpleDateFormat ymdHmsDateFormat=new SimpleDateFormat("yyyyMMdd");
	
	public List<EquipmentVO> queryEquipList(String task_id) {
		return taskRepository.queryEquipList(task_id);
	}

	public Page queryPoles(Page page) {
		return taskRepository.queryPoles(page);
	}
	public Page queryPoles_no_send_task(Page page) {
		return taskRepository.queryPoles_no_send_task(page);
	}
	
	public void create(Task[] taskes) {
		Date createDate=new Date();
		//获取当天的id的最大值
		String max_id_str=taskRepository.queryMax_id(ymdHmsDateFormat.format(createDate));
		Integer max_id=1;
		if(max_id_str==null){
			max_id=1;
		} else {
			max_id_str=max_id_str.split("-")[1];
			max_id=(Integer.parseInt(max_id_str)+1);
		}
		//max_id=StringUtils.leftPad(max_id, 4, '0');
		for(Task task:taskes){
			task.setCreateDate(createDate);
			task.setStatus(TaskStatus.newTask);
			task.setId(ymdHmsDateFormat.format(createDate)+"-"+StringUtils.leftPad(max_id+"", 4, '0'));
			super.create(task);
			max_id++;
			
			//修改改为状态
			//如果是新安装的设备，那就改为安装中
			if(task.getType()==TaskType.newInstall){
				poleRepository.update(Cnd.update().set(M.Pole.status, PoleStatus.installing).andEquals(M.Pole.id, task.getPole_id()));
			}else if(task.getType()==TaskType.repair){
				poleRepository.update(Cnd.update().set(M.Pole.status, PoleStatus.hitch).andEquals(M.Pole.id, task.getPole_id()));	
			}
			
		}
	}
	public void cancel(String id) {
		Task task=this.get(id);
		if(task.getStatus()==TaskStatus.complete){
			throw new BusinessException("已提交和已完成的任务不能取消!");
		}
		//判断任务里面的设备是否已经如果入库，不是安装出库，手持和使用中状态就不能取消
		//因为提交后就修改了设备的状态
		int count=taskRepository.count_task_quip_status(id);
		if(count>0){
			throw new BusinessException("不能取消这个任务,里面的设备已经入库!");
		}
		//如果是新安装任务，取消，那就把状态改回 "未安装"
		if(task.getType()==TaskType.newInstall){
			poleRepository.update(Cnd.update().set(M.Pole.status, PoleStatus.uninstall).andEquals(M.Pole.id, task.getPole_id()));
		}else if(task.getType()==TaskType.repair){
			//如果是维修任务，就改回正常的状态
			poleRepository.update(Cnd.update().set(M.Pole.status, PoleStatus.using).andEquals(M.Pole.id, task.getPole_id()));	
		}
		this.deleteBatch(Cnd.delete().andEquals(M.Task.id, id));
	}
	/**
	 * 管理人员 确认任务单,同时修改设备的状态
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param id
	 * @return
	 */
	public void confirm(String id) {
		//taskRepository.update(Cnd.update().set(M.Task.status, TaskStatus.complete).set(M.Task.completeDate, new Date()).andEquals(M.Task.id, id));
		
		Task task=taskRepository.get(id);
		task.setStatus( TaskStatus.complete);
		task.setCompleteDate(new Date());
		//修改杆位状态为"已安装"
		if(task.getType()==TaskType.newInstall){
			poleRepository.update(Cnd.update().set(M.Pole.status, PoleStatus.using).andEquals(M.Pole.id, task.getPole_id()));	
		} else if(task.getType()==TaskType.repair){
			poleRepository.update(Cnd.update().set(M.Pole.status, PoleStatus.using).andEquals(M.Pole.id, task.getPole_id()));	
		}
		
		
		String task_type=task.getType().toString();
		//修改设备的状态
		List<TaskEquipmentList> taskEquipmentListes=taskEquipmentListRepository.query(Cnd.select().andEquals(M.TaskEquipmentList.task_id,id));
		for(TaskEquipmentList taskEquipmentList:taskEquipmentListes){
			String ecode=taskEquipmentList.getEcode();
			if(TaskType.newInstall.toString().equals(task_type)){
			//更改设备的位置到该杆位上,把设备从昨夜单位身上移动到杆位上
				equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.using.getValue()).set(M.Equipment.isnew, false)
						.set(M.Equipment.pole_id, task.getPole_id())
						.set(M.Equipment.last_install_date, new Date())
						.set(M.Equipment.workUnit_id, null)
						.set(M.Equipment.store_id, null)
						.andEquals(M.Equipment.ecode, ecode));	
			} else if(TaskType.repair.toString().equals(task_type)){
				//维修的时候，设备的状态，可能是 损坏或者是安装出库 
				if( taskEquipmentList.getEquipment_status()==EquipmentStatus.using.getValue()){
					equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.using.getValue()).set(M.Equipment.isnew, false)	
							.set(M.Equipment.pole_id, task.getPole_id())
							.set(M.Equipment.last_install_date, new Date())
							.set(M.Equipment.workUnit_id, null)
							.set(M.Equipment.store_id, null)
							.andEquals(M.Equipment.ecode, ecode)
							.andEquals(M.Equipment.status, EquipmentStatus.out_storage.getValue()));
				} else {
					equipmentRepository.update(Cnd.update().set(M.Equipment.status, taskEquipmentList.getEquipment_status())
							.set(M.Equipment.workUnit_id, task.getWorkunit_id())
							.set(M.Equipment.pole_id, null)
							.set(M.Equipment.store_id, null)
							.andEquals(M.Equipment.ecode, ecode)
							.andEquals(M.Equipment.status, EquipmentStatus.using.getValue()));	
				}
						
			} else if(TaskType.patrol.toString().equals(task_type)){

			}

		}
		
		
	}
	
	
	
	public Page mobile_queryPage(Page page) {
		return this.getRepository().mobile_queryPage(page);
	}
	
	public List<Task> mobile_search(String status,String searchStr,String workunit_id) {
		return this.getRepository().mobile_search(status, searchStr, workunit_id);
	}

	public List<TaskEquipmentListVO> mobile_queryTaskEquipmentInfos(String task_id) {
		
		//任务查看过后，就修改状态为“已阅”,只有任务状态为 newTask的才会被修改
		taskRepository.update(Cnd.update().set(M.Task.status, TaskStatus.read).andEquals(M.Task.id, task_id).andEquals(M.Task.status, TaskStatus.newTask));
		//修改杆位状态为"安装中"
		//Task task=taskRepository.get(task_id);
		//poleRepository.update(Cnd.update().set(M.Pole.status, PoleStatus.installing).andEquals(M.Pole.id, task.getPole_id()));
		
		return taskRepository.mobile_queryTaskEquipmentInfos(task_id);
	}
	
	/**
	 * 检查任务和设备的状态
	 * 并且判断是不是当前用户持有的设备
	 * @author mawujun email:160649888@163.com qq:16064988
	 */
	public void checkEquipStatus(Task task,Equipment equipmentVO){
		//Task task=taskRepository.get(task_id);
		
		
		
		if(TaskType.newInstall.equals(task.getType())){
			if(EquipmentStatus.out_storage.getValue()!=equipmentVO.getStatus()){
				throw new BusinessException(equipmentVO.getEcode()+":该设备不是'安装出库'状态,不能安装!");
			} else if(!ShiroUtils.getAuthenticationInfo().getId().equals(equipmentVO.getWorkUnit_id()) ){
				//判断是不是当前用户持有的设备，否则就报错
				throw new BusinessException(equipmentVO.getEcode()+":该设备不是你所持有的设备,请确认!");
			}
		} else if(TaskType.repair.equals(task.getType())){
			//如果是using状态的设备就判断是个是这个任务关联的杆位,防止出现串号
			//否则就判断是不是安装出库状态,
			if(EquipmentStatus.using.getValue()==equipmentVO.getStatus()){
				if(!task.getPole_id().equals(equipmentVO.getPole_id())){
					throw new BusinessException(equipmentVO.getEcode()+":该设备不是当前任务指定杆位上的设备!");
				}
			} else if(EquipmentStatus.out_storage.getValue()!=equipmentVO.getStatus()){
				throw new BusinessException(equipmentVO.getEcode()+":该设备不是'安装出库'状态,不能安装!");
			} else {
				if(!ShiroUtils.getAuthenticationInfo().getId().equals(equipmentVO.getWorkUnit_id()) ){
					//判断是不是当前用户持有的设备，否则就报错
					throw new BusinessException(equipmentVO.getEcode()+":该设备不是你所持有的设备,请确认!");
				}	
			}
		} else if(TaskType.patrol.equals(task.getType())){
			//巡检扫描的设备必须是using章台的设备，并且杆位也必须是相同的
			if(EquipmentStatus.using.getValue()!=equipmentVO.getStatus()){
				throw new BusinessException(equipmentVO.getEcode()+":该设备不是'使用中'状态,不能作为巡检标识!");
			} else {
				if(!task.getPole_id().equals(equipmentVO.getPole_id())){
					throw new BusinessException(equipmentVO.getEcode()+":该设备不是当前任务指定杆位上的设备!");
				}
			}
		} else if(TaskType.cancel.equals(task.getType())){
			//巡检扫描的设备必须是using章台的设备，并且杆位也必须是相同的
			if(EquipmentStatus.using.getValue()!=equipmentVO.getStatus()){
				throw new BusinessException(equipmentVO.getEcode()+":该设备不是'使用中'状态,请检查是否扫描错了!");
			} else {
				if(!task.getPole_id().equals(equipmentVO.getPole_id())){
					throw new BusinessException(equipmentVO.getEcode()+":该设备不是当前任务指定杆位上的设备!");
				}
			}
		}
		
		
		
				
	}
	public TaskEquipmentListVO getEquipmentInfo(String ecode,String task_id){
		EquipmentVO equipmentVO=equipmentService.getEquipmentInfo(ecode);
		if(equipmentVO==null){
			throw new BusinessException("没有这个设备");
		}
		Task task=taskRepository.get(task_id);
		//
		checkEquipStatus(task,equipmentVO);
		//还要判断是否有其他任务扫描过这个设备了，其他还是处理中和提交状态的
		if(!TaskType.patrol.equals(task.getType())){
			List<String> taskes=taskRepository.query_other_task_have_scaned(ShiroUtils.getAuthenticationInfo().getId(), ecode);
			if(taskes!=null && taskes.size()>0){
				throw new BusinessException("任务"+taskes.get(0)+"已经扫描了这个设备");
			}
		}
		
		TaskEquipmentListVO vo=new TaskEquipmentListVO();
		BeanUtils.copyProperties(equipmentVO, vo);
		
		if(task.getType()==TaskType.repair){
			if(equipmentVO.getStatus()==EquipmentStatus.using.getValue()){
				//如果是证在使用的设备，默认替换下来的设备就设置为损坏
				vo.setEquipment_status(EquipmentStatus.breakdown.getValue());
			} else if(equipmentVO.getStatus()==EquipmentStatus.out_storage.getValue()){
				//如果是安装出库状态的设备，默认就设置外i使用中，这里都是临时的
				vo.setEquipment_status(EquipmentStatus.using.getValue());
			} 
		} else {
			//安装和巡检的时候，都是使用中的状态
			vo.setEquipment_status(EquipmentStatus.using.getValue());
		}
		return vo;
	}
	
	/**
	 * 返回的值是  去掉重复的条码后的 扫描了的条码
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param task_id
	 * @param hitchType_id
	 * @param hitchReasonTpl_id
	 * @param hitchReason
	 * @param ecodes
	 * @param equipment_statuses
	 * @return
	 */
	public Set<String> mobile_save(String task_id,Integer hitchType_id,Integer hitchReasonTpl_id,String hitchReason,String[] ecodes,Integer[] equipment_statuses) {
		//已入库的不能删除
		check_equip_status(task_id,ecodes);
		
		taskEquipmentListRepository.deleteBatch(Cnd.delete().andEquals(M.TaskEquipmentList.task_id, task_id));
		
		Task task=taskRepository.get(task_id);
		task.setHitchType_id(hitchType_id);
		task.setHitchReasonTpl_id(hitchReasonTpl_id);
		task.setHitchReason(hitchReason);
		task.setStatus(TaskStatus.handling);
		if(task.getStartHandDate()==null){
			task.setStartHandDate(new Date());
		}
		
		taskRepository.update(task);
		//修改任务状态为"处理中",无论哪种任务类型
		//taskRepository.update(Cnd.update().set(M.Task.status, TaskStatus.handling).set(M.Task.startHandDate,new Date()).andEquals(M.Task.id, task_id));
		if(ecodes==null){
			return null;
		}
		Set<String> existinsert=new HashSet<String>();
		int i=0;
		for(String ecode:ecodes){
			if(existinsert.contains(ecode)){
				i++;
				continue;//防止一个设备多次扫描的情况，在这里进行过滤掉
			}
			TaskEquipmentList tel=new TaskEquipmentList();
			tel.setEcode(ecode);
			tel.setTask_id(task_id);
			tel.setEquipment_status(equipment_statuses[i]);
			//tel.setType(TaskListTypeEnum.install);
			
			if(TaskType.newInstall==task.getType()){
				tel.setType(TaskListTypeEnum.install);
			} else if(TaskType.repair==task.getType()){
				//如果设备将会变成使用中的状态，那这个设备就是安装
				if(equipment_statuses[i]==EquipmentStatus.using.getValue()){
					tel.setType(TaskListTypeEnum.install);
				} else {
					//退换下来的，可能是损坏，也可能是 安装出库状态的设备就是 卸载下来的
					tel.setType(TaskListTypeEnum.uninstall);
				}
			} else if(TaskType.patrol==task.getType()){
				tel.setType(TaskListTypeEnum.patrol);
			}
			taskEquipmentListRepository.create(tel);
			
			i++;
			existinsert.add(ecode);
		}
		return existinsert;
		
	}
	/**
	 * 这个判断主要用于，//已入库的不能删除
	 * 返回值是删除的并且已入库的条码
	 * @author mawujun 16064988@qq.com 
	 * @param task_id
	 * @param ecodes
	 */
	private List<String> check_equip_status(String task_id ,String[] ecodes){
		//首先判断要删除的设备是否存在不是指定状态的设备
				//获取现有的设备和已经存在的设备的差异，判断差异设备的状态就行了
				//查询当前任务中拥有的设备
				List<String> eqip_list=taskRepository.query_task_equip_list(task_id);
				//循环出删掉的设备
				List<String> delEcodes=new ArrayList<String>();
				
				if(ecodes==null){
					delEcodes=eqip_list;
				} else {
					boolean isDel=true;
					for(String ecode:eqip_list){
						isDel=true;
						for(String newecode:ecodes){
							if(ecode.equals(newecode)){
								isDel=false;
								break;
							}
						}
						if(isDel){
							delEcodes.add(ecode);
						}
						
					}
				}
				
				if(delEcodes.size()>0){
					List<String> count=taskRepository.count_task_quip_status1(task_id,delEcodes);
					if(count.size()>0){
						throw new BusinessException("不能保存或提交,"+count.get(0)+"已经入库不能删除!");
					}
					return count;
				} else {
					return new ArrayList<String>();
				}
				 
	}
	
	/**
	 * 任务提交，不修改设备状态，等管理端确认后，才修改设备的状态
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param task_id
	 * @param task_type
	 * @param ecodes
	 */
	public void mobile_submit(String task_id,Integer hitchType_id,Integer hitchReasonTpl_id,String hitchReason,String[] ecodes,Integer[] equipment_statuses) {

		Set<String> existEcodes=this.mobile_save(task_id, hitchType_id, hitchReasonTpl_id, hitchReason, ecodes, equipment_statuses);
		//如果是取消杆位，提交前进行判断，扫描了的设备数量和杆位上实际具有的数量是否一致
		//是否是该杆位上的设备，已经在扫描的时候就判断了
		Task task=taskRepository.get(task_id);
		if(TaskType.cancel==task.getType()){
			//查找杆位上的数量
			Long pole_eqips=equipmentRepository.queryCount(Cnd.count("ecode").andEquals(M.Equipment.pole_id, task.getPole_id()));
			if(pole_eqips!=existEcodes.size()){
				throw new BusinessException("该杆位上实际的设备数量为:"+pole_eqips+",但现在只扫描了"+existEcodes.size()+"!");
			}
		}
		
		//修改任务状态为"已提交"
		taskRepository.update(Cnd.update().set(M.Task.status, TaskStatus.submited).set(M.Task.submitDate, new Date()).andEquals(M.Task.id, task_id));
		
		
	}
	
//	/**
//	 * 还要判断在安装和维修时，同个设备被扫描了两次的情况，这个时候在提交的时候还要进行判断
//	 * @author mawujun email:160649888@163.com qq:16064988
//	 * @param task_id
//	 * @param task_type
//	 * @param ecodes
//	 */
//	public void mobile_submit(String task_id,String task_type,String[] ecodes,Integer[] equipment_statuses) {
//		AssertUtils.notNull(task_type);
//		AssertUtils.notEmpty(ecodes);
//		Task task=taskRepository.get(task_id);
//		
//		//已入库的不能删除
//		check_equip_status(task_id,ecodes);
//
//		List<Equipment> equipments_temp=equipmentRepository.query(Cnd.select().andIn(M.Equipment.ecode, ecodes));
//		Map<String,Equipment> equipments=new HashMap<String,Equipment>();
//		for(Equipment equ:equipments_temp){
//			equipments.put(equ.getEcode(), equ);
//		}
//		
//		
//		
//		//全部重新保存，因为不知道哪些是更新过的
//		taskEquipmentListRepository.deleteBatch(Cnd.delete().andEquals(M.TaskEquipmentList.task_id, task_id));			
//		Set<String> existinsert=new HashSet<String>();
//		int i=0;
//		for(String ecode:ecodes){
//			if(existinsert.contains(ecode)){
//				i++;
//				continue;//防止一个设备多次扫描的情况，在这里进行过滤掉
//			}
//			TaskEquipmentList tel=new TaskEquipmentList();
//			tel.setEcode(ecode);
//			tel.setTask_id(task_id);
//
//			
//			existinsert.add(ecode);
//			//修改设备为“使用中”,修改设备为旧设备
//			//equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.using.getValue()).set(M.Equipment.isnew, false).andEquals(M.Equipment.ecode, ecode));		
//			if(TaskType.newInstall.toString().equals(task_type)){
//				//更改设备的位置到该杆位上,把设备从昨夜单位身上移动到杆位上
//				equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.using.getValue()).set(M.Equipment.isnew, false)
//						.set(M.Equipment.pole_id, task.getPole_id())
//						.set(M.Equipment.workUnit_id, null)
//						.set(M.Equipment.store_id, null)
//						.andEquals(M.Equipment.ecode, ecode));	
//				tel.setType(TaskListTypeEnum.install);
//				tel.setEquipment_status( EquipmentStatus.using.getValue());
//			} else if(TaskType.repair.toString().equals(task_type)){
////				//如果设备是使用中，就修改为已损坏，如果是安装出库，就修改为使用中，同时修改设备为旧设备
////				Equipment equp=equipments.get(ecode);
////				if(equp.getStatus()==EquipmentStatus.using.getValue()){
////					//替换下来的设备
////					equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.breakdown.getValue())
////							.set(M.Equipment.workUnit_id, task.getWorkunit_id())
////							.set(M.Equipment.pole_id, null)
////							.set(M.Equipment.store_id, null)
////							.andEquals(M.Equipment.ecode, ecode)
////							.andEquals(M.Equipment.status, EquipmentStatus.using.getValue()));	
////					tel.setType(TaskListTypeEnum.uninstall);
////				} else if(equp.getStatus()==EquipmentStatus.out_storage.getValue()){
////					//要安装上去的设备
////					equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.using.getValue()).set(M.Equipment.isnew, false)
////							.set(M.Equipment.workUnit_id, null)
////							.set(M.Equipment.pole_id, task.getPole_id())
////							.set(M.Equipment.store_id, null)
////							.andEquals(M.Equipment.ecode, ecode)
////							.andEquals(M.Equipment.status, EquipmentStatus.out_storage.getValue()));
////					tel.setType(TaskListTypeEnum.install);
////				} 
//				
//				tel.setEquipment_status( equipment_statuses[i]);
//				//将要使用的，就是安装上去的
//				if(equipment_statuses[i]==EquipmentStatus.using.getValue()){
//					//安装上去的
//					equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.using.getValue()).set(M.Equipment.isnew, false)
//							.set(M.Equipment.workUnit_id, null)
//							.set(M.Equipment.pole_id, task.getPole_id())
//							.set(M.Equipment.store_id, null)
//							.andEquals(M.Equipment.ecode, ecode));
//					tel.setType(TaskListTypeEnum.install);
//				} else {
//					//退换下来的，可能是损坏，也可能是 安装出库状态
//					equipmentRepository.update(Cnd.update().set(M.Equipment.status, equipment_statuses[i])
//						.set(M.Equipment.workUnit_id, task.getWorkunit_id())
//						.set(M.Equipment.pole_id, null)
//						.set(M.Equipment.store_id, null)
//						.andEquals(M.Equipment.ecode, ecode));	
//					tel.setType(TaskListTypeEnum.uninstall);
//				}
//				
//			} else if(TaskType.patrol.toString().equals(task_type)){
//				tel.setType(TaskListTypeEnum.patrol);
//				tel.setEquipment_status( EquipmentStatus.using.getValue());
//			}
//			i++;
//			//taskEquipmentListRepository.create(tel);
//		}
//		
//		//修改任务状态为"已提交"
//		taskRepository.update(Cnd.update().set(M.Task.status, TaskStatus.submited).set(M.Task.submitDate, new Date()).andEquals(M.Task.id, task_id));
//		
//		
//	}
	
	public List<Pole> mobile_queryPoles(String pole_name,String workunit_id) {
		return taskRepository.mobile_queryPoles(pole_name, workunit_id);
	}
	
}
