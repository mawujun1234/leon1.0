package com.mawujun.mobile.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.baseinfo.Equipment;
import com.mawujun.baseinfo.EquipmentCycleService;
import com.mawujun.baseinfo.EquipmentPlace;
import com.mawujun.baseinfo.EquipmentPole;
import com.mawujun.baseinfo.EquipmentPolePK;
import com.mawujun.baseinfo.EquipmentPoleRepository;
import com.mawujun.baseinfo.EquipmentPoleType;
import com.mawujun.baseinfo.EquipmentProdService;
import com.mawujun.baseinfo.EquipmentProdVO;
import com.mawujun.baseinfo.EquipmentRepository;
import com.mawujun.baseinfo.EquipmentService;
import com.mawujun.baseinfo.EquipmentStatus;
import com.mawujun.baseinfo.EquipmentWorkunit;
import com.mawujun.baseinfo.EquipmentWorkunitPK;
import com.mawujun.baseinfo.EquipmentWorkunitRepository;
import com.mawujun.baseinfo.EquipmentWorkunitType;
import com.mawujun.baseinfo.OperateType;
import com.mawujun.baseinfo.Pole;
import com.mawujun.baseinfo.PoleRepository;
import com.mawujun.baseinfo.PoleStatus;
import com.mawujun.baseinfo.TargetType;
import com.mawujun.exception.BusinessException;
import com.mawujun.install.B2INotify;
import com.mawujun.install.B2INotifyRepository;
import com.mawujun.install.BorrowListType;
import com.mawujun.install.BorrowRepository;
import com.mawujun.install.InstallOutListType;
import com.mawujun.install.InstallOutRepository;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.shiro.ShiroUtils;
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
	static Logger logger=LogManager.getLogger(TaskService.class);

	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private TaskEquipmentListRepository taskEquipmentListRepository;
	@Autowired
	private EquipmentRepository equipmentRepository;
	@Autowired
	private EquipmentWorkunitRepository equipmentWorkunitRepository;
	@Autowired
	private EquipmentPoleRepository equipmentPoleRepository;
	@Autowired
	private PoleRepository poleRepository;
	@Resource
	private EquipmentService equipmentService;
	@Resource
	private OvertimeService overtimeService;
	@Resource
	private HitchTypeRepository hitchTypeRepository;
	
	@Autowired
	private InstallOutRepository installOutRepository;
	@Autowired
	private BorrowRepository borrowRepository;
	
	@Autowired
	private EquipmentCycleService equipmentCycleService;
	@Autowired
	private LockEquipmentService lockEquipmentService;
	@Autowired
	private EquipmentProdService equipmentProdService;
	@Autowired
	private B2INotifyRepository b2INotifyRepository;
	
	@Override
	public TaskRepository getRepository() {
		return taskRepository;
	}
	
	SimpleDateFormat ymdHmsDateFormat=new SimpleDateFormat("yyyyMMdd");
	
	public List<TaskEquipmentListVO> queryTaskEquipmentListVO(String task_id) {
		return taskRepository.queryTaskEquipmentListVO(task_id);
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
		if(task.getStatus()==TaskStatus.submited){
			throw new BusinessException("已提交的任务不能取消!");
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
//	/**
//	 * 管理人员 确认任务单,同时修改设备的状态
//	 * @author mawujun email:160649888@163.com qq:16064988
//	 * @param id
//	 * @return
//	 */
//	public void confirm(String id) {
//		//taskRepository.update(Cnd.update().set(M.Task.status, TaskStatus.complete).set(M.Task.completeDate, new Date()).andEquals(M.Task.id, id));
//		
//		Task task=taskRepository.get(id);
//		task.setStatus( TaskStatus.complete);
//		task.setCompleteDate(new Date());
//		taskRepository.update(task);
//		//修改杆位状态为"已安装"
//		if(task.getType()==TaskType.newInstall){
//			poleRepository.update(Cnd.update().set(M.Pole.status, PoleStatus.using).andEquals(M.Pole.id, task.getPole_id()));	
//		} else if(task.getType()==TaskType.repair){
//			poleRepository.update(Cnd.update().set(M.Pole.status, PoleStatus.using).andEquals(M.Pole.id, task.getPole_id()));	
//		}
//		
//		
//		String task_type=task.getType().toString();
//		//所有这次任务相关的设备清单
//		List<TaskEquipmentList> taskEquipmentListes=taskEquipmentListRepository.query(Cnd.select().andEquals(M.TaskEquipmentList.task_id,id));
//		for(TaskEquipmentList taskEquipmentList:taskEquipmentListes){
//			String ecode=taskEquipmentList.getEcode();
//			if(TaskType.newInstall.toString().equals(task_type)){
//				//更改设备的位置到该杆位上,把设备从昨夜单位身上移动到杆位上
//				equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.using).set(M.Equipment.isnew, false)
//						.set(M.Equipment.place, EquipmentPlace.pole)
//						.set(M.Equipment.last_install_date, new Date())
//						.set(M.Equipment.last_pole_id, task.getPole_id())
//						.set(M.Equipment.last_task_id, task.getId())
//						.set(M.Equipment.currt_task_id, null)
//						.andEquals(M.Equipment.ecode, ecode));	
//				
//				//插入到杆位中
//				EquipmentPole equipmentStore=new EquipmentPole();
//				equipmentStore.setEcode(taskEquipmentList.getEcode());
//				equipmentStore.setPole_id(task.getPole_id());
//				equipmentStore.setNum(1);
//				equipmentStore.setInDate(new Date());
//				equipmentStore.setType(EquipmentPoleType.install);
//				equipmentStore.setType_id(task.getId());
//				equipmentStore.setFrom_id(task.getWorkunit_id());
//				equipmentPoleRepository.create(equipmentStore);
//				//workunit减掉这个设备
//				EquipmentWorkunitPK equipmentWorkunitPK=new EquipmentWorkunitPK();
//				equipmentWorkunitPK.setEcode(taskEquipmentList.getEcode());
//				equipmentWorkunitPK.setWorkunit_id(task.getWorkunit_id());
//				equipmentWorkunitRepository.deleteById(equipmentWorkunitPK);
//			} else if(TaskType.repair.toString().equals(task_type)){
//				//维修的时候，设备的状态，可能是 损坏或者是安装出库 
//				//如果设备原来的状态是正在使用，你把设备下架的
//				if( taskEquipmentList.getType()==TaskListTypeEnum.install){
//					equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.using).set(M.Equipment.isnew, false)
//							.set(M.Equipment.place, EquipmentPlace.pole)
//							.set(M.Equipment.last_install_date, new Date())
//							.set(M.Equipment.last_pole_id, task.getPole_address())
//							.set(M.Equipment.last_task_id, task.getId())
//							.set(M.Equipment.currt_task_id, null)
//							.andEquals(M.Equipment.ecode, ecode));	
//					
//					//插入到杆位中
//					EquipmentPole equipmentStore=new EquipmentPole();
//					equipmentStore.setEcode(taskEquipmentList.getEcode());
//					equipmentStore.setPole_id(task.getPole_id());
//					equipmentStore.setNum(1);
//					equipmentStore.setInDate(new Date());
//					equipmentStore.setType(EquipmentPoleType.install);
//					equipmentStore.setType_id(task.getId());
//					equipmentStore.setFrom_id(task.getWorkunit_id());
//					equipmentPoleRepository.create(equipmentStore);
//					//workunit减掉这个设备
//					EquipmentWorkunitPK equipmentWorkunitPK=new EquipmentWorkunitPK();
//					equipmentWorkunitPK.setEcode(taskEquipmentList.getEcode());
//					equipmentWorkunitPK.setWorkunit_id(task.getWorkunit_id());
//					equipmentWorkunitRepository.deleteById(equipmentWorkunitPK);
//				} else {
//					//设备从杆位上卸载下来的情况
//					equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.out_storage).set(M.Equipment.isnew, false)
//							.set(M.Equipment.place, EquipmentPlace.workunit)
//							//.set(M.Equipment.last_install_date, new Date())
//							.set(M.Equipment.last_workunit_id, task.getWorkunit_id())
//							.set(M.Equipment.last_task_id, task.getId())
//							.set(M.Equipment.currt_task_id, null)
//							.andEquals(M.Equipment.ecode, ecode));	
//					
//
//					//插入到workunit中
//					EquipmentWorkunit equipmentWorkunit=new EquipmentWorkunit();
//					equipmentWorkunit.setEcode(taskEquipmentList.getEcode());
//					equipmentWorkunit.setWorkunit_id(task.getWorkunit_id());
//					equipmentWorkunit.setInDate(new Date());
//					equipmentWorkunit.setNum(1);
//					equipmentWorkunit.setType(EquipmentWorkunitType.task);
//					equipmentWorkunit.setType_id(task.getId());
//					equipmentWorkunit.setFrom_id(task.getPole_id());
//					equipmentWorkunitRepository.create(equipmentWorkunit);
//					//从杆位中移除这个设备
//					EquipmentPolePK equipmentPolePK=new EquipmentPolePK();
//					equipmentPolePK.setEcode(taskEquipmentList.getEcode());
//					equipmentPolePK.setPole_id(task.getPole_id());
//					equipmentPoleRepository.deleteById(equipmentPolePK);
//				}
//						
//			} else if(TaskType.cancel.toString().equals(task_type)){
//				//设备从杆位上卸载下来的情况
//				equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.out_storage).set(M.Equipment.isnew, false)
//						.set(M.Equipment.place, EquipmentPlace.workunit)
//						//.set(M.Equipment.last_install_date, new Date())
//						.set(M.Equipment.last_workunit_id, task.getWorkunit_id())
//						.set(M.Equipment.last_task_id, task.getId())
//						.set(M.Equipment.currt_task_id, null)
//						.andEquals(M.Equipment.ecode, ecode));	
//				
//
//				//插入到workunit中
//				EquipmentWorkunit equipmentWorkunit=new EquipmentWorkunit();
//				equipmentWorkunit.setEcode(taskEquipmentList.getEcode());
//				equipmentWorkunit.setWorkunit_id(task.getWorkunit_id());
//				equipmentWorkunit.setInDate(new Date());
//				equipmentWorkunit.setNum(1);
//				equipmentWorkunit.setType(EquipmentWorkunitType.task);
//				equipmentWorkunit.setType_id(task.getId());
//				equipmentWorkunit.setFrom_id(task.getPole_id());
//				equipmentWorkunitRepository.create(equipmentWorkunit);
//				//从杆位中移除这个设备
//				EquipmentPolePK equipmentPolePK=new EquipmentPolePK();
//				equipmentPolePK.setEcode(taskEquipmentList.getEcode());
//				equipmentPolePK.setPole_id(task.getPole_id());
//				equipmentPoleRepository.deleteById(equipmentPolePK);
//
//			}else if(TaskType.patrol.toString().equals(task_type)){
//
//			}
//
//		}
//		
//	}

	
	
	public Page mobile_queryPage(Page page) {
		return this.getRepository().mobile_queryPage(page);
	}
	
	public Page mobile_search(Page page) {
		return this.getRepository().mobile_search(page);
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
	public void checkEquipStatus(String task_type,String pole_id,Equipment equipmentVO){
		//Task task=taskRepository.get(task_id);
		
		
		
		if(TaskType.newInstall.equals(task_type)){
			if(EquipmentStatus.out_storage!=equipmentVO.getStatus()){
				throw new BusinessException(equipmentVO.getEcode()+":该设备不是'安装出库'状态,不能安装!");
			} else if(equipmentRepository.check_in_workunit_by_ecode(equipmentVO.getEcode(), ShiroUtils.getAuthenticationInfo().getId())==0 ){
				//判断是不是当前用户持有的设备，否则就报错
				throw new BusinessException(equipmentVO.getEcode()+":该设备不是你所持有的设备,请确认!");
			}
			//检查这个设备室不是在这个作业单位上
			
		} else if(TaskType.repair.equals(task_type)){
			//如果是using状态的设备就判断是个是这个任务关联的杆位,防止出现串号
			//否则就判断是不是安装出库状态,
			if(EquipmentStatus.using==equipmentVO.getStatus()){
				if(equipmentRepository.check_in_pole_by_ecode(equipmentVO.getEcode(), pole_id)==0){
					throw new BusinessException(equipmentVO.getEcode()+":该设备不是当前任务指定杆位上的设备!");
				}
			} else if(EquipmentStatus.out_storage!=equipmentVO.getStatus()){
				throw new BusinessException(equipmentVO.getEcode()+":该设备不是'安装出库'状态,不能安装!");
			} else {
				if(equipmentRepository.check_in_workunit_by_ecode(equipmentVO.getEcode(), ShiroUtils.getAuthenticationInfo().getId())==0 ){
					//判断是不是当前用户持有的设备，否则就报错
					throw new BusinessException(equipmentVO.getEcode()+":该设备不是你所持有的设备,请确认!");
				}	
			}
		} else if(TaskType.patrol.equals(task_type)){
			//巡检扫描的设备必须是using章台的设备，并且杆位也必须是相同的
			if(EquipmentStatus.using!=equipmentVO.getStatus()){
				throw new BusinessException(equipmentVO.getEcode()+":该设备不是'使用中'状态,不能作为巡检标识!");
			} else {
				if(equipmentRepository.check_in_pole_by_ecode(equipmentVO.getEcode(), pole_id)==0){
					throw new BusinessException(equipmentVO.getEcode()+":该设备不是当前任务指定杆位上的设备!");
				}
			}
		} else if(TaskType.cancel.equals(task_type)){
			//巡检扫描的设备必须是using章台的设备，并且杆位也必须是相同的
			if(EquipmentStatus.using!=equipmentVO.getStatus()){
				throw new BusinessException(equipmentVO.getEcode()+":该设备不是'使用中'状态,请检查是否扫描错了!");
			} else {
				if(equipmentRepository.check_in_pole_by_ecode(equipmentVO.getEcode(), pole_id)==0){
					throw new BusinessException(equipmentVO.getEcode()+":该设备不是当前任务指定杆位上的设备!");
				}
			}
		}
					
	}
	
	public void mobile_updateTaskEquipmentListType(String taskEquipmentList_id,String ecode,TaskListTypeEnum type){
		taskEquipmentListRepository.update(Cnd.update().set(M.TaskEquipmentList.type, type).andEquals(M.TaskEquipmentList.id, taskEquipmentList_id));
		
	}
	public void mobile_deleteTaskEquipmentList(String ecode,String taskEquipmentList_id) {
		//先判断，如果该设备已经入库了，那该设备就不能删除
		Equipment equipment=equipmentRepository.getEquipmentInfo(ecode);
		
		//无论是安装，维修，取消，还是巡检，只要是设备不在杆位上或者不在作业单位手上，就不能从任务中删除了
		if(equipment.getStatus()!=EquipmentStatus.using && equipment.getStatus()!=EquipmentStatus.out_storage){
			throw new BusinessException("该设备已经入库,不能删除!");
		}
		
		taskEquipmentListRepository.deleteById(taskEquipmentList_id);
		//更新equipment的curr_task_id
		equipmentRepository.update(Cnd.update()
				.set(M.Equipment.currt_task_id, null)
				.andEquals(M.Equipment.ecode, ecode));
		
		lockEquipmentService.unlock(ecode);
	}
	public TaskEquipmentListVO mobile_getAndCreateTaskEquipmentList(String ecode,String task_id,TaskType task_type,String pole_id){
		//判断设备是否被不是这个任务的设备锁定了
		lockEquipmentService.check_locked(ecode, task_id);
		
		//判断设备是否在该作业单位手上
		TaskListTypeEnum taskListTypeEnum=TaskListTypeEnum.install;
		EquipmentInstalloutType installoutType=EquipmentInstalloutType.other;
		if(task_type==TaskType.cancel){
			int count=equipmentRepository.check_in_pole_by_ecode(ecode, pole_id);
			//判断是不是作业单位上的设备
			if(count==0){
				throw new BusinessException(ecode+"不在这个点位上!");
			}
			taskListTypeEnum=TaskListTypeEnum.uninstall;
		}  else if(task_type==TaskType.patrol){
			int count=equipmentRepository.check_in_pole_by_ecode(ecode, pole_id);
			//判断是不是作业单位上的设备
			if(count==0){
				throw new BusinessException(ecode+"不在这个点位上!");
			}
			taskListTypeEnum=TaskListTypeEnum.patrol;
		} else if(task_type==TaskType.newInstall){
			//int count=equipmentRepository.check_in_workunit_by_ecode(ecode, ShiroUtils.getUserId());
			EquipmentWorkunitPK id=new EquipmentWorkunitPK(ecode,ShiroUtils.getUserId());
			EquipmentWorkunit equipmentWorkunit=equipmentWorkunitRepository.get(id);
			//判断是不是作业单位上的设备
			if(equipmentWorkunit==null){
				throw new BusinessException(ecode+"不在这个作业单位上!");
			}
			taskListTypeEnum=TaskListTypeEnum.install;
			if(equipmentWorkunit.getType()==EquipmentWorkunitType.borrow){
				installoutType=EquipmentInstalloutType.borrow;
			}
			if(equipmentWorkunit.getType()==EquipmentWorkunitType.installout){
				installoutType=EquipmentInstalloutType.installout;
			}
		}else if(task_type==TaskType.repair){
			EquipmentWorkunitPK id=new EquipmentWorkunitPK(ecode,ShiroUtils.getUserId());
			EquipmentWorkunit equipmentWorkunit=equipmentWorkunitRepository.get(id);

			if(equipmentWorkunit!=null){
				taskListTypeEnum=TaskListTypeEnum.install;
			}
			//如果是维修任务,并且不在作业单位上，那要判断是不是在点位上
			if(equipmentWorkunit==null){
				logger.info("这里可能出现为null的情况:ecode={},,pole_id={},task_id={}",ecode,pole_id,task_id);
				int count=equipmentRepository.check_in_pole_by_ecode(ecode, pole_id);
				if(count==0){
					throw new BusinessException(ecode+"不在该作业单位或点位上!");
				} else {
					//如果是维修任务，并且这个设备在点位上，那就是卸载这个设备
					taskListTypeEnum=TaskListTypeEnum.uninstall;
				}
			} else {
				if(equipmentWorkunit.getType()==EquipmentWorkunitType.borrow){
					installoutType=EquipmentInstalloutType.borrow;
				}
				if(equipmentWorkunit.getType()==EquipmentWorkunitType.installout){
					installoutType=EquipmentInstalloutType.installout;
				}
			}
			
		}
//		if(task_type!=TaskType.cancel){
//			//int count=equipmentRepository.check_in_workunit_by_ecode(ecode,ShiroUtils.getUserId());
//			EquipmentWorkunitPK id=new EquipmentWorkunitPK(ecode,ShiroUtils.getUserId());
//			EquipmentWorkunit equipmentWorkunit=equipmentWorkunitRepository.get(id);
//	
//			if(equipmentWorkunit!=null){
//				taskListTypeEnum=TaskListTypeEnum.install;
//			}
//			//如果是维修任务,并且不在作业单位上，那要判断是不是在点位上
//			if(taskListTypeEnum==null && task_type==TaskType.repair){
//				int count=equipmentRepository.check_in_pole_by_ecode(ecode, pole_id);
//				if(count==0){
//					throw new BusinessException(ecode+"不在该作业单位或点位上!");
//				} else {
//					//如果是维修任务，并且这个设备在点位上，那就是卸载这个设备
//					taskListTypeEnum=TaskListTypeEnum.uninstall;
//				}
//			}
//			
//		}
		
		//更新设备状态为处理中
		taskRepository.update_to_handling_status(task_id);

		TaskEquipmentList list=new TaskEquipmentList();
		list.setEcode(ecode);
		//list.setEquipment_status(equipmentVO.getStatus());
		list.setTask_id(task_id);
		list.setType(taskListTypeEnum);
		list.setInstalloutType(installoutType);
//		//list.setType(type);
//		if(TaskType.newInstall==task_type){
//			list.setType(TaskListTypeEnum.install);
//		} else if(TaskType.repair==task_type){
//			list.setType(taskListTypeEnum);
//			
//		} else if(TaskType.patrol==task_type){
//			list.setType(TaskListTypeEnum.patrol);
//		}else if(TaskType.cancel==task_type){
//			list.setType(TaskListTypeEnum.uninstall);
//		}
		list.setScanDate(new Date());
		taskEquipmentListRepository.create(list);
		
		//对设备进行锁定
		lockEquipmentService.lockByTask(ecode, task_id);
		//更新equipment的curr_task_id
		equipmentRepository.update(Cnd.update()
				.set(M.Equipment.currt_task_id, task_id)
				.andEquals(M.Equipment.ecode, ecode));
		
		//获取某个品名的相关信息
		EquipmentProdVO equipmentProd=equipmentProdService.getEquipmentProdVO(equipmentProdService.splitEcode(ecode));
		
		TaskEquipmentListVO vo=new TaskEquipmentListVO();
		BeanUtils.copyProperties(list, vo);
		vo.setSubtype_name(equipmentProd.getSubtype_name());
		vo.setProd_style(equipmentProd.getStyle());
		//vo.setSupplier_name(equipmentVO.getSupplier_name());
		vo.setProd_name(equipmentProd.getName());
		vo.setBrand_name(equipmentProd.getBrand_name());
		
		
		return vo;
	}
	
//	public void checkEquipStatus(Task task,Equipment equipmentVO){
//	//Task task=taskRepository.get(task_id);
//	
//	
//	
//	if(TaskType.newInstall.equals(task.getType())){
//		if(EquipmentStatus.out_storage!=equipmentVO.getStatus()){
//			throw new BusinessException(equipmentVO.getEcode()+":该设备不是'安装出库'状态,不能安装!");
//		} else if(!ShiroUtils.getAuthenticationInfo().getId().equals(equipmentVO.getWorkUnit_id()) ){
//			//判断是不是当前用户持有的设备，否则就报错
//			throw new BusinessException(equipmentVO.getEcode()+":该设备不是你所持有的设备,请确认!");
//		}
//	} else if(TaskType.repair.equals(task.getType())){
//		//如果是using状态的设备就判断是个是这个任务关联的杆位,防止出现串号
//		//否则就判断是不是安装出库状态,
//		if(EquipmentStatus.using==equipmentVO.getStatus()){
//			if(!task.getPole_id().equals(equipmentVO.getPole_id())){
//				throw new BusinessException(equipmentVO.getEcode()+":该设备不是当前任务指定杆位上的设备!");
//			}
//		} else if(EquipmentStatus.out_storage!=equipmentVO.getStatus()){
//			throw new BusinessException(equipmentVO.getEcode()+":该设备不是'安装出库'状态,不能安装!");
//		} else {
//			if(!ShiroUtils.getAuthenticationInfo().getId().equals(equipmentVO.getWorkUnit_id()) ){
//				//判断是不是当前用户持有的设备，否则就报错
//				throw new BusinessException(equipmentVO.getEcode()+":该设备不是你所持有的设备,请确认!");
//			}	
//		}
//	} else if(TaskType.patrol.equals(task.getType())){
//		//巡检扫描的设备必须是using章台的设备，并且杆位也必须是相同的
//		if(EquipmentStatus.using!=equipmentVO.getStatus()){
//			throw new BusinessException(equipmentVO.getEcode()+":该设备不是'使用中'状态,不能作为巡检标识!");
//		} else {
//			if(!task.getPole_id().equals(equipmentVO.getPole_id())){
//				throw new BusinessException(equipmentVO.getEcode()+":该设备不是当前任务指定杆位上的设备!");
//			}
//		}
//	} else if(TaskType.cancel.equals(task.getType())){
//		//巡检扫描的设备必须是using章台的设备，并且杆位也必须是相同的
//		if(EquipmentStatus.using!=equipmentVO.getStatus()){
//			throw new BusinessException(equipmentVO.getEcode()+":该设备不是'使用中'状态,请检查是否扫描错了!");
//		} else {
//			if(!task.getPole_id().equals(equipmentVO.getPole_id())){
//				throw new BusinessException(equipmentVO.getEcode()+":该设备不是当前任务指定杆位上的设备!");
//			}
//		}
//	}
//				
//}
//	public TaskEquipmentListVO getEquipmentInfo(String ecode,String task_id){
//		EquipmentVO equipmentVO=equipmentService.getEquipmentInfo(ecode);
//		if(equipmentVO==null){
//			throw new BusinessException("没有这个设备");
//		}
//		Task task=taskRepository.get(task_id);
//		//
//		checkEquipStatus(task,equipmentVO);
//		//还要判断是否有其他任务扫描过这个设备了，其他还是处理中和提交状态的
//		if(!TaskType.patrol.equals(task.getType())){
//			List<String> taskes=taskRepository.query_other_task_have_scaned(ShiroUtils.getAuthenticationInfo().getId(), ecode);
//			if(taskes!=null && taskes.size()>0){
//				throw new BusinessException("任务"+taskes.get(0)+"已经扫描了这个设备");
//			}
//		}
//		
//		TaskEquipmentListVO vo=new TaskEquipmentListVO();
//		BeanUtils.copyProperties(equipmentVO, vo);
//		
//		if(task.getType()==TaskType.repair){
//			if(equipmentVO.getStatus()==EquipmentStatus.using){
//				//如果是证在使用的设备，默认替换下来的设备就设置为损坏out_storage
//				//vo.setEquipment_status(EquipmentStatus.breakdown.getValue());
//				//从点位上拆下来也变成是 施工单位持有的状态 2015.06.03,这个是即将变成的状态
//				vo.setEquipment_status(EquipmentStatus.out_storage);
//			} else if(equipmentVO.getStatus()==EquipmentStatus.out_storage){
//				//如果是安装出库状态的设备，默认就设置外i使用中，这里都是临时的
//				vo.setEquipment_status(EquipmentStatus.using);
//			} 
//		} else {
//			//安装和巡检的时候，都是使用中的状态
//			vo.setEquipment_status(EquipmentStatus.using);
//		}
//		return vo;
//	}
	
//	public Page queryRepairTaskesReport(Page page) {
//		//获取超期时间
//		Overtime overtime=overtimeService.get("overtime");
//		Page result= this.getRepository().queryRepairTaskesReport(page);
//		List<TaskRepairReport> list=result.getResult();
//		for(TaskRepairReport task:list){
//			task.checkIsOverTime(overtime.getHandling());
//		}
//		return result;
//	}
//	public Page queryUnrepairPoleReport(Page page) {
//		//获取超期时间
//		//Overtime overtime=overtimeService.get("overtime");
//		Page result= this.getRepository().queryUnrepairPoleReport(page);
////		List<Task> list=result.getResult();
////		for(Task task:list){
////			task.checkIsOverTime(overtime.getHandling());
////		}
//		return result;
//	}
//	
//	
//	public List<Task> exportUnrepairPoleReport(String workunit_id,String customer_id,String date_start,String date_end) {
//		//Overtime overtime=overtimeService.get("overtime");
//		Params params=Params.init().add(M.Task.workunit_id, workunit_id).add("customer_id", customer_id)
//				.add("date_start", date_start).add("date_end", date_end);
//		List<Task> list= this.getRepository().exportUnrepairPoleReport(params);
////		for(TaskRepairReport task:list){
////			task.checkIsOverTime(overtime.getHandling());
////		}
//		return list;
//	}
	/**
	 * 主要用于维修任务的保存
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param task_id
	 * @param hitchType_id
	 * @param hitchReasonTpl_id
	 * @param hitchReason
	 * @param ecodes
	 * @param equipment_statuses
	 * @return
	 */
	public void mobile_save(String task_id,Integer hitchType_id,Integer hitchReasonTpl_id,String hitchReason,String handleMethod_id,String handle_contact) {
		//hitchType_id=0 表示是维修任务传递过来的
		if(hitchType_id!=null && hitchType_id==0){
			throw new BusinessException("请选择故障类型");
		}
		//hitchType_id=0 表示是维修任务传递过来的
		if(hitchReasonTpl_id!=null && hitchReasonTpl_id==0){
			throw new BusinessException("请选择故障原因模板");
		}
		
		Task task=taskRepository.get(task_id);
		
		task.setHitchType_id(hitchType_id);
		if(hitchType_id!=null){
			task.setHitchType(hitchTypeRepository.get(hitchType_id).getName());
		}
		task.setHitchReasonTpl_id(hitchReasonTpl_id);
		task.setHitchReason(hitchReason);
		
		task.setHandleMethod_id(handleMethod_id);
		task.setHandle_contact(handle_contact);

		//主要用在维修任务，没有扫描，然后直接点保存的时候
		if(task.getStatus()==TaskStatus.newTask || task.getStatus()==TaskStatus.read){
			task.setStatus(TaskStatus.handling);
			task.setStartHandDate(new Date());
		}
		
		taskRepository.update(task);

		//return existinsert;
		
	}
	
	
	/**
	 * 任务提交，不修改设备状态，等管理端确认后，才修改设备的状态
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param task_id
	 * @param task_type
	 * @param ecodes
	 */
	public void mobile_submit(String task_id,Integer hitchType_id,Integer hitchReasonTpl_id,String hitchReason,String handleMethod_id,String handle_contact) {
		Task task=taskRepository.get(task_id);
		if(task.getStatus()==TaskStatus.submited){
			throw new BusinessException("任务已经提交,不能再提交!");
		}
		//当写好就马上提交了，没有经过保存的时候
		this.mobile_save(task_id, hitchType_id, hitchReasonTpl_id, hitchReason,handleMethod_id,handle_contact);
		//如果是取消杆位，提交前进行判断，扫描了的设备数量和杆位上实际具有的数量是否一致
		//是否是该杆位上的设备，已经在扫描的时候就判断了
		
		if(TaskType.cancel==task.getType()){
			//查找杆位上的数量
			int pole_eqips=poleRepository.query_count_equipment_in_pole(task.getPole_id());
			int scan_count=taskRepository.query_count_tasklist_by_task(task_id);
			//获取这个任务少苗的设备数量
			if(pole_eqips!=scan_count){
				throw new BusinessException("该杆位上实际的设备数量为:"+pole_eqips+",但现在只扫描了"+scan_count+"!");
			}
		}
		
		//更新任务状态
		task.setStatus( TaskStatus.submited);
		task.setSubmitDate(new Date());
		taskRepository.update(task);
		
		
//		//String task_type = task.getType().toString();
//		// 所有这次任务相关的设备清单
//		List<TaskEquipmentList> taskEquipmentListes = taskEquipmentListRepository.query(Cnd.select().andEquals(M.TaskEquipmentList.task_id, task_id));
//		for (TaskEquipmentList taskEquipmentList : taskEquipmentListes) {
//			String ecode = taskEquipmentList.getEcode();
//			//对任务中的所有设备进行锁定
//			lockEquipmentService.lockByTask(ecode,task_id);
//		}
	}
	/**
	 * 后台进行任务确认的
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param task_id
	 * @param task_type
	 * @param ecodes
	 */
	public void confirm(String task_id) {
		Task task=taskRepository.get(task_id);
		if(task.getStatus()==TaskStatus.complete){
			throw new BusinessException("任务已经完成,不能再确认!");
		}
		if(task.getStatus()!=TaskStatus.submited){
			throw new BusinessException("任务不是提交状态,不能确认!");
		}
		//当写好就马上提交了，没有经过保存的时候
//		this.mobile_save(task_id, hitchType_id, hitchReasonTpl_id, hitchReason);
		//如果是取消杆位，提交前进行判断，扫描了的设备数量和杆位上实际具有的数量是否一致
		//是否是该杆位上的设备，已经在扫描的时候就判断了
//		
//		if(TaskType.cancel==task.getType()){
//			//查找杆位上的数量
//			int pole_eqips=poleRepository.query_count_equipment_in_pole(task.getPole_id());
//			int scan_count=taskRepository.query_count_tasklist_by_task(task_id);
//			//获取这个任务少苗的设备数量
//			if(pole_eqips!=scan_count){
//				throw new BusinessException("该杆位上实际的设备数量为:"+pole_eqips+",但现在只扫描了"+scan_count+"!");
//			}
//		}
		
		//更新任务状态
		task.setStatus(TaskStatus.complete);
		task.setCompleteDate(new Date());
		taskRepository.update(task);
		
		// 修改杆位状态为"使用中"
		if (task.getType() == TaskType.newInstall) {
			poleRepository.update(Cnd.update().set(M.Pole.status, PoleStatus.using).andEquals(M.Pole.id, task.getPole_id()));
		} else if (task.getType() == TaskType.repair) {
			poleRepository.update(Cnd.update().set(M.Pole.status, PoleStatus.using).andEquals(M.Pole.id, task.getPole_id()));
		} else if (task.getType() == TaskType.cancel) {
			poleRepository.update(Cnd.update().set(M.Pole.status, PoleStatus.cancel).andEquals(M.Pole.id, task.getPole_id()));
		}

		//String task_type = task.getType().toString();
		// 所有这次任务相关的设备清单
		List<TaskEquipmentList> taskEquipmentListes = taskEquipmentListRepository.query(Cnd.select().andEquals(M.TaskEquipmentList.task_id, task_id));
		for (TaskEquipmentList taskEquipmentList : taskEquipmentListes) {
			String ecode = taskEquipmentList.getEcode();
			
			//对设备进行解锁
			lockEquipmentService.unlock(ecode);
			//修改对应的设备为领用，因为设备只有当真正被使用过了才叫领用，否则都还是借用
			
			if (TaskType.newInstall== task.getType()) {
				// 更改设备的位置到该杆位上,把设备从昨夜单位身上移动到杆位上
				equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.using).set(M.Equipment.isnew, false)
						.set(M.Equipment.place, EquipmentPlace.pole).set(M.Equipment.last_install_date, new Date())
						.set(M.Equipment.last_pole_id, task.getPole_id()).set(M.Equipment.last_task_id, task.getId())
						.set(M.Equipment.currt_task_id, null).andEquals(M.Equipment.ecode, ecode));
				//要放在最前面，把设备从借用变成领用
				changeInstallOutListType2installout(taskEquipmentList.getEcode(),task.getWorkunit_id(),task.getPole_id(),task_id);
				
				// 杆位绑定这个设备
				EquipmentPole equipmentPole = new EquipmentPole();
				equipmentPole.setEcode(taskEquipmentList.getEcode());
				equipmentPole.setPole_id(task.getPole_id());
				equipmentPole.setNum(1);
				equipmentPole.setInDate(new Date());
				equipmentPole.setType(EquipmentPoleType.install);
				equipmentPole.setType_id(task.getId());
				equipmentPole.setFrom_id(task.getWorkunit_id());
				equipmentPoleRepository.create(equipmentPole);
				// workunit减掉这个设备
				EquipmentWorkunitPK equipmentWorkunitPK = new EquipmentWorkunitPK();
				equipmentWorkunitPK.setEcode(taskEquipmentList.getEcode());
				equipmentWorkunitPK.setWorkunit_id(task.getWorkunit_id());
				equipmentWorkunitRepository.deleteById(equipmentWorkunitPK);
				
				//记录设备入库的生命周期
				equipmentCycleService.logEquipmentCycle(taskEquipmentList.getEcode(), OperateType.task_install, task.getId(),TargetType.pole,task.getPole_id());
			} else if (TaskType.repair== task.getType()) {
				// 维修的时候，设备的状态，可能是 损坏或者是安装出库
				// 如果设备原来的状态是正在使用，你把设备下架的
				if (taskEquipmentList.getType() == TaskListTypeEnum.install) {
					equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.using).set(M.Equipment.isnew, false)
							.set(M.Equipment.place, EquipmentPlace.pole).set(M.Equipment.last_install_date, new Date())
							.set(M.Equipment.last_pole_id, task.getPole_address()).set(M.Equipment.last_task_id, task.getId())
							.set(M.Equipment.currt_task_id, null).andEquals(M.Equipment.ecode, ecode));
					//要放在最前面，把设备从借用变成领用
					changeInstallOutListType2installout(taskEquipmentList.getEcode(),task.getWorkunit_id(),task.getPole_id(),task_id);
					
					// 杆位绑定这个设备
					EquipmentPole equipmentPole = new EquipmentPole();
					equipmentPole.setEcode(taskEquipmentList.getEcode());
					equipmentPole.setPole_id(task.getPole_id());
					equipmentPole.setNum(1);
					equipmentPole.setInDate(new Date());
					equipmentPole.setType(EquipmentPoleType.install);
					equipmentPole.setType_id(task.getId());
					equipmentPole.setFrom_id(task.getWorkunit_id());
					equipmentPoleRepository.create(equipmentPole);
					// workunit减掉这个设备
					EquipmentWorkunitPK equipmentWorkunitPK = new EquipmentWorkunitPK();
					equipmentWorkunitPK.setEcode(taskEquipmentList.getEcode());
					equipmentWorkunitPK.setWorkunit_id(task.getWorkunit_id());
					equipmentWorkunitRepository.deleteById(equipmentWorkunitPK);
					
					//记录设备入库的生命周期
					equipmentCycleService.logEquipmentCycle(taskEquipmentList.getEcode(), OperateType.task_install, task.getId(),TargetType.pole,task.getPole_id());
					
				} else if(taskEquipmentList.getType() == TaskListTypeEnum.uninstall){
					// 设备从杆位上卸载下来的情况
					equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.out_storage).set(M.Equipment.isnew, false)
							.set(M.Equipment.place, EquipmentPlace.workunit)
							// .set(M.Equipment.last_install_date, new Date())
							.set(M.Equipment.last_workunit_id, task.getWorkunit_id()).set(M.Equipment.last_task_id, task.getId())
							.set(M.Equipment.currt_task_id, null).andEquals(M.Equipment.ecode, ecode));

					Pole pole=poleRepository.get(task.getPole_id());
					// 把设备挂到这个作业单位身上
					EquipmentWorkunit equipmentWorkunit = new EquipmentWorkunit();
					equipmentWorkunit.setEcode(taskEquipmentList.getEcode());
					equipmentWorkunit.setWorkunit_id(task.getWorkunit_id());
					equipmentWorkunit.setInDate(new Date());
					equipmentWorkunit.setNum(1);
					equipmentWorkunit.setType(EquipmentWorkunitType.task);
					equipmentWorkunit.setType_id(task.getId());
					equipmentWorkunit.setFrom_id(task.getPole_id());
					equipmentWorkunit.setProject_id(pole.getProject_id());
					equipmentWorkunitRepository.create(equipmentWorkunit);
					// 从杆位中移除这个设备
					EquipmentPolePK equipmentPolePK = new EquipmentPolePK();
					equipmentPolePK.setEcode(taskEquipmentList.getEcode());
					equipmentPolePK.setPole_id(task.getPole_id());
					equipmentPoleRepository.deleteById(equipmentPolePK);
					
					//记录设备入库的生命周期
					equipmentCycleService.logEquipmentCycle(taskEquipmentList.getEcode(), OperateType.task_cancel, task.getId(),TargetType.workunit,task.getWorkunit_id());
				} else {
					//设备是巡检的时候就不做任何处理，
				}

			} else if (TaskType.cancel== task.getType()) {
				// 设备从杆位上卸载下来的情况
				equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.out_storage).set(M.Equipment.isnew, false)
						.set(M.Equipment.place, EquipmentPlace.workunit)
						// .set(M.Equipment.last_install_date, new Date())
						.set(M.Equipment.last_workunit_id, task.getWorkunit_id()).set(M.Equipment.last_task_id, task.getId())
						.set(M.Equipment.currt_task_id, null).andEquals(M.Equipment.ecode, ecode));

				Pole pole=poleRepository.get(task.getPole_id());
				// 插入到workunit中
				EquipmentWorkunit equipmentWorkunit = new EquipmentWorkunit();
				equipmentWorkunit.setEcode(taskEquipmentList.getEcode());
				equipmentWorkunit.setWorkunit_id(task.getWorkunit_id());
				equipmentWorkunit.setInDate(new Date());
				equipmentWorkunit.setNum(1);
				equipmentWorkunit.setType(EquipmentWorkunitType.task);
				equipmentWorkunit.setType_id(task.getId());
				equipmentWorkunit.setFrom_id(task.getPole_id());
				equipmentWorkunit.setProject_id(pole.getProject_id());
				equipmentWorkunitRepository.create(equipmentWorkunit);
				// 从杆位中移除这个设备
				EquipmentPolePK equipmentPolePK = new EquipmentPolePK();
				equipmentPolePK.setEcode(taskEquipmentList.getEcode());
				equipmentPolePK.setPole_id(task.getPole_id());
				equipmentPoleRepository.deleteById(equipmentPolePK);

				//记录设备入库的生命周期
				equipmentCycleService.logEquipmentCycle(taskEquipmentList.getEcode(), OperateType.task_cancel, task.getId(),TargetType.pole,task.getPole_id());
			} else if (TaskType.patrol== task.getType()) {

			}

		}
	}
	/**
	 * 任务退回工功能
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param task_id
	 */
	public void sendBack(String task_id) {
		Task task=taskRepository.get(task_id);
		if(task.getStatus()!=TaskStatus.submited){
			throw new BusinessException("任务不是提交状态,不能退回!");
		}
		task.setStatus(TaskStatus.handling);
		taskRepository.update(task);
	}
	
//	/**
//	 * 任务提交，不修改设备状态，等管理端确认后，才修改设备的状态
//	 * @author mawujun email:160649888@163.com qq:16064988
//	 * @param task_id
//	 * @param task_type
//	 * @param ecodes
//	 */
//	public void mobile_submit(String task_id,Integer hitchType_id,Integer hitchReasonTpl_id,String hitchReason) {
//		Task task=taskRepository.get(task_id);
//		if(task.getStatus()==TaskStatus.submited){
//			throw new BusinessException("任务已经提交,不能再提交!");
//		}
//		//当写好就马上提交了，没有经过保存的时候
//		this.mobile_save(task_id, hitchType_id, hitchReasonTpl_id, hitchReason);
//		//如果是取消杆位，提交前进行判断，扫描了的设备数量和杆位上实际具有的数量是否一致
//		//是否是该杆位上的设备，已经在扫描的时候就判断了
//		
//		if(TaskType.cancel==task.getType()){
//			//查找杆位上的数量
//			int pole_eqips=poleRepository.query_count_equipment_in_pole(task.getPole_id());
//			int scan_count=taskRepository.query_count_tasklist_by_task(task_id);
//			//获取这个任务少苗的设备数量
//			if(pole_eqips!=scan_count){
//				throw new BusinessException("该杆位上实际的设备数量为:"+pole_eqips+",但现在只扫描了"+scan_count+"!");
//			}
//		}
//		
//		//更新任务状态
//		task.setStatus( TaskStatus.submited);
//		task.setSubmitDate(new Date());
//		taskRepository.update(task);
//		
//		// 修改杆位状态为"使用中"
//		if (task.getType() == TaskType.newInstall) {
//			poleRepository.update(Cnd.update().set(M.Pole.status, PoleStatus.using).andEquals(M.Pole.id, task.getPole_id()));
//		} else if (task.getType() == TaskType.repair) {
//			poleRepository.update(Cnd.update().set(M.Pole.status, PoleStatus.using).andEquals(M.Pole.id, task.getPole_id()));
//		}
//
//		//String task_type = task.getType().toString();
//		// 所有这次任务相关的设备清单
//		List<TaskEquipmentList> taskEquipmentListes = taskEquipmentListRepository.query(Cnd.select().andEquals(M.TaskEquipmentList.task_id, task_id));
//		for (TaskEquipmentList taskEquipmentList : taskEquipmentListes) {
//			String ecode = taskEquipmentList.getEcode();
//			//修改对应的设备为领用，因为设备只有当真正被使用过了才叫领用，否则都还是借用
//			
//			if (TaskType.newInstall== task.getType()) {
//				// 更改设备的位置到该杆位上,把设备从昨夜单位身上移动到杆位上
//				equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.using).set(M.Equipment.isnew, false)
//						.set(M.Equipment.place, EquipmentPlace.pole).set(M.Equipment.last_install_date, new Date())
//						.set(M.Equipment.last_pole_id, task.getPole_id()).set(M.Equipment.last_task_id, task.getId())
//						.set(M.Equipment.currt_task_id, null).andEquals(M.Equipment.ecode, ecode));
//				//要放在最前面，把设备从借用变成领用
//				changeInstallOutListType2installout(taskEquipmentList.getEcode(),task.getWorkunit_id(),task.getPole_id());
//				
//				// 杆位绑定这个设备
//				EquipmentPole equipmentPole = new EquipmentPole();
//				equipmentPole.setEcode(taskEquipmentList.getEcode());
//				equipmentPole.setPole_id(task.getPole_id());
//				equipmentPole.setNum(1);
//				equipmentPole.setInDate(new Date());
//				equipmentPole.setType(EquipmentPoleType.install);
//				equipmentPole.setType_id(task.getId());
//				equipmentPole.setFrom_id(task.getWorkunit_id());
//				equipmentPoleRepository.create(equipmentPole);
//				// workunit减掉这个设备
//				EquipmentWorkunitPK equipmentWorkunitPK = new EquipmentWorkunitPK();
//				equipmentWorkunitPK.setEcode(taskEquipmentList.getEcode());
//				equipmentWorkunitPK.setWorkunit_id(task.getWorkunit_id());
//				equipmentWorkunitRepository.deleteById(equipmentWorkunitPK);
//				
//				//记录设备入库的生命周期
//				equipmentCycleService.logEquipmentCycle(taskEquipmentList.getEcode(), OperateType.task_install, task.getId(),TargetType.pole,task.getPole_id());
//			} else if (TaskType.repair== task.getType()) {
//				// 维修的时候，设备的状态，可能是 损坏或者是安装出库
//				// 如果设备原来的状态是正在使用，你把设备下架的
//				if (taskEquipmentList.getType() == TaskListTypeEnum.install) {
//					equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.using).set(M.Equipment.isnew, false)
//							.set(M.Equipment.place, EquipmentPlace.pole).set(M.Equipment.last_install_date, new Date())
//							.set(M.Equipment.last_pole_id, task.getPole_address()).set(M.Equipment.last_task_id, task.getId())
//							.set(M.Equipment.currt_task_id, null).andEquals(M.Equipment.ecode, ecode));
//					//要放在最前面，把设备从借用变成领用
//					changeInstallOutListType2installout(taskEquipmentList.getEcode(),task.getWorkunit_id(),task.getPole_id());
//					
//					// 杆位绑定这个设备
//					EquipmentPole equipmentPole = new EquipmentPole();
//					equipmentPole.setEcode(taskEquipmentList.getEcode());
//					equipmentPole.setPole_id(task.getPole_id());
//					equipmentPole.setNum(1);
//					equipmentPole.setInDate(new Date());
//					equipmentPole.setType(EquipmentPoleType.install);
//					equipmentPole.setType_id(task.getId());
//					equipmentPole.setFrom_id(task.getWorkunit_id());
//					equipmentPoleRepository.create(equipmentPole);
//					// workunit减掉这个设备
//					EquipmentWorkunitPK equipmentWorkunitPK = new EquipmentWorkunitPK();
//					equipmentWorkunitPK.setEcode(taskEquipmentList.getEcode());
//					equipmentWorkunitPK.setWorkunit_id(task.getWorkunit_id());
//					equipmentWorkunitRepository.deleteById(equipmentWorkunitPK);
//					
//					//记录设备入库的生命周期
//					equipmentCycleService.logEquipmentCycle(taskEquipmentList.getEcode(), OperateType.task_install, task.getId(),TargetType.pole,task.getPole_id());
//					
//				} else {
//					// 设备从杆位上卸载下来的情况
//					equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.out_storage).set(M.Equipment.isnew, false)
//							.set(M.Equipment.place, EquipmentPlace.workunit)
//							// .set(M.Equipment.last_install_date, new Date())
//							.set(M.Equipment.last_workunit_id, task.getWorkunit_id()).set(M.Equipment.last_task_id, task.getId())
//							.set(M.Equipment.currt_task_id, null).andEquals(M.Equipment.ecode, ecode));
//
//					// 把设备挂到这个作业单位身上
//					EquipmentWorkunit equipmentWorkunit = new EquipmentWorkunit();
//					equipmentWorkunit.setEcode(taskEquipmentList.getEcode());
//					equipmentWorkunit.setWorkunit_id(task.getWorkunit_id());
//					equipmentWorkunit.setInDate(new Date());
//					equipmentWorkunit.setNum(1);
//					equipmentWorkunit.setType(EquipmentWorkunitType.task);
//					equipmentWorkunit.setType_id(task.getId());
//					equipmentWorkunit.setFrom_id(task.getPole_id());
//					equipmentWorkunitRepository.create(equipmentWorkunit);
//					// 从杆位中移除这个设备
//					EquipmentPolePK equipmentPolePK = new EquipmentPolePK();
//					equipmentPolePK.setEcode(taskEquipmentList.getEcode());
//					equipmentPolePK.setPole_id(task.getPole_id());
//					equipmentPoleRepository.deleteById(equipmentPolePK);
//					
//					//记录设备入库的生命周期
//					equipmentCycleService.logEquipmentCycle(taskEquipmentList.getEcode(), OperateType.task_cancel, task.getId(),TargetType.pole,task.getPole_id());
//				}
//
//			} else if (TaskType.cancel== task.getType()) {
//				// 设备从杆位上卸载下来的情况
//				equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.out_storage).set(M.Equipment.isnew, false)
//						.set(M.Equipment.place, EquipmentPlace.workunit)
//						// .set(M.Equipment.last_install_date, new Date())
//						.set(M.Equipment.last_workunit_id, task.getWorkunit_id()).set(M.Equipment.last_task_id, task.getId())
//						.set(M.Equipment.currt_task_id, null).andEquals(M.Equipment.ecode, ecode));
//
//				// 插入到workunit中
//				EquipmentWorkunit equipmentWorkunit = new EquipmentWorkunit();
//				equipmentWorkunit.setEcode(taskEquipmentList.getEcode());
//				equipmentWorkunit.setWorkunit_id(task.getWorkunit_id());
//				equipmentWorkunit.setInDate(new Date());
//				equipmentWorkunit.setNum(1);
//				equipmentWorkunit.setType(EquipmentWorkunitType.task);
//				equipmentWorkunit.setType_id(task.getId());
//				equipmentWorkunit.setFrom_id(task.getPole_id());
//				equipmentWorkunitRepository.create(equipmentWorkunit);
//				// 从杆位中移除这个设备
//				EquipmentPolePK equipmentPolePK = new EquipmentPolePK();
//				equipmentPolePK.setEcode(taskEquipmentList.getEcode());
//				equipmentPolePK.setPole_id(task.getPole_id());
//				equipmentPoleRepository.deleteById(equipmentPolePK);
//
//				//记录设备入库的生命周期
//				equipmentCycleService.logEquipmentCycle(taskEquipmentList.getEcode(), OperateType.task_cancel, task.getId(),TargetType.pole,task.getPole_id());
//			} else if (TaskType.patrol== task.getType()) {
//
//			}
//
//		}
//	}
	/**
	 * 如果这个设备是领用出来的，那就把该设备变为变为领用(因为默认是借用)
	 * 如果该设备是借用出来的，，那也把该设备变为领用
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param ecode
	 * @param workunit_id
	 */
	public void changeInstallOutListType2installout(String ecode,String workunit_id,String pole_id,String task_id){
		EquipmentWorkunitPK equipmentWorkunitPK = new EquipmentWorkunitPK();
		equipmentWorkunitPK.setEcode(ecode);
		equipmentWorkunitPK.setWorkunit_id(workunit_id);
		EquipmentWorkunit equipmentWorkunit=equipmentWorkunitRepository.get(equipmentWorkunitPK);
		if(equipmentWorkunit==null){
			throw new BusinessException(ecode+"不在该作业单位手上!");
		}
		//表示这个设备是领用出来的，那就把该设备变成是领用的，因为领用单的明细数据默认是借用(因为领用会返回，直接领用的话，领用数据会虚高)
		if(equipmentWorkunit.getType()==EquipmentWorkunitType.installout ){
			installOutRepository.changeInstallOutListType2installout(equipmentWorkunit.getType_id(),InstallOutListType.installout, ecode,pole_id);
		}
		if(equipmentWorkunit.getType()==EquipmentWorkunitType.borrow){
			//把该条码的借用单明细类型改成是领用
			borrowRepository.changeBorrowListType2installout(equipmentWorkunit.getType_id(),BorrowListType.installout, ecode,pole_id);
			//判断该借用单是否已经全部归还了
			borrowRepository.updateBorrowIsAllReturn(equipmentWorkunit.getType_id());
			//给出结转领的提示信息,让仓库去填写领用类型
			B2INotify b2INotify=new B2INotify();
			b2INotify.setEcode(ecode);
			b2INotify.setStore_id(equipmentWorkunit.getFrom_id());
			b2INotify.setBorrow_id(equipmentWorkunit.getType_id());
			b2INotify.setWorkunit_id(workunit_id);
			b2INotify.setPole_id(pole_id);
			b2INotify.setTask_id(task_id);
			b2INotify.setCreateDate(new Date());
			
			b2INotifyRepository.create(b2INotify);
			
		}
	}
//	/**
//	 * 任务提交，不修改设备状态，等管理端确认后，才修改设备的状态
//	 * @author mawujun email:160649888@163.com qq:16064988
//	 * @param task_id
//	 * @param task_type
//	 * @param ecodes
//	 */
//	public void mobile_submit(String task_id,Integer hitchType_id,Integer hitchReasonTpl_id,String hitchReason,String[] ecodes,EquipmentStatus[] equipment_statuses) {
//
//		Set<String> existEcodes=this.mobile_save(task_id, hitchType_id, hitchReasonTpl_id, hitchReason, ecodes, equipment_statuses);
//		//如果是取消杆位，提交前进行判断，扫描了的设备数量和杆位上实际具有的数量是否一致
//		//是否是该杆位上的设备，已经在扫描的时候就判断了
//		Task task=taskRepository.get(task_id);
//		if(TaskType.cancel==task.getType()){
//			//查找杆位上的数量
//			Long pole_eqips=equipmentRepository.queryCount(Cnd.count("ecode").andEquals(M.Equipment.pole_id, task.getPole_id()));
//			if(pole_eqips!=existEcodes.size()){
//				throw new BusinessException("该杆位上实际的设备数量为:"+pole_eqips+",但现在只扫描了"+existEcodes.size()+"!");
//			}
//		}
//		
//		//修改任务状态为"已提交"
//		taskRepository.update(Cnd.update().set(M.Task.status, TaskStatus.submited).set(M.Task.submitDate, new Date()).andEquals(M.Task.id, task_id));
//		
//		
//	}
	
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
	
	
	public Page querySubmited(Page page) {
		return taskRepository.querySubmited(page);
	}
	
	
	public void finishRepairTask(String task_id,Integer hitchType_id,String hitchType,Integer hitchReasonTpl_id,String hitchReason,String handleMethod_id) {
		//判断是否有扫描了，如果已经有扫描了就不能处理了
		int count=taskRepository.query_count_tasklist_by_task(task_id);
		if(count>0){
			throw new BusinessException("该任务已经有扫描设备，不能在后台结束!");
		}
		
		Task task=taskRepository.get(task_id);
		
		task.setHitchType_id(hitchType_id);
		task.setHitchType(hitchType);
		task.setHitchReasonTpl_id(hitchReasonTpl_id);
		task.setHitchReason(hitchReason);
		task.setHandleMethod_id(handleMethod_id);
		
		Date date=new Date();
		task.setStartHandDate(date);
		task.setSubmitDate(date);
		task.setCompleteDate(date);
		
		task.setStatus(TaskStatus.complete);
		
		taskRepository.update(task);
	}
}
