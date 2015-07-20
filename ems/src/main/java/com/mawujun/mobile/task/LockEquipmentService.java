package com.mawujun.mobile.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;




import com.mawujun.service.AbstractService;


import com.mawujun.exception.BusinessException;
import com.mawujun.mobile.task.LockEquipment;
import com.mawujun.mobile.task.LockEquipmentRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class LockEquipmentService extends AbstractService<LockEquipment, String>{

	@Autowired
	private LockEquipmentRepository lockEquipmentRepository;
	
	@Override
	public LockEquipmentRepository getRepository() {
		return lockEquipmentRepository;
	}

	public void lockByTask(String ecode,String task_id) {
		LockEquipment lockEquipment=new LockEquipment();
		lockEquipment.setCreateDate(new Date());
		lockEquipment.setEcode(ecode);
		lockEquipment.setId(ecode);
		lockEquipment.setLockType(LockType.task);
		lockEquipment.setType_id(task_id);
		lockEquipmentRepository.create(lockEquipment);
		
	}
	
	public void unlock(String ecode) {
		lockEquipmentRepository.deleteById(ecode);
		
	}
	/**
	 * 判断设备是不是被某个任务锁定了，
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param ecode
	 * @param type_id
	 */
	public void check_locked(String ecode,String type_id){
		LockEquipment lockEquipment=lockEquipmentRepository.get(ecode);
		//==null表示没有被锁定
		if(ecode==null){
			return;
		}
		if(!lockEquipment.getType_id().equals(type_id)){
			throw new BusinessException("设备已经被任务"+lockEquipment.getType_id()+"锁定,不能扫描");
		}
	}
}
