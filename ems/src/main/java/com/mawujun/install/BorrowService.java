package com.mawujun.install;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;




import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;


import com.mawujun.shiro.ShiroUtils;
import com.mawujun.utils.M;
import com.mawujun.utils.page.Page;
import com.mawujun.baseinfo.Equipment;
import com.mawujun.baseinfo.EquipmentRepository;
import com.mawujun.baseinfo.EquipmentStatus;
import com.mawujun.install.Borrow;
import com.mawujun.install.BorrowRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class BorrowService extends AbstractService<Borrow, String>{

	@Autowired
	private BorrowRepository borrowRepository;
	@Autowired
	private BorrowListRepository borrowListRepository;
	@Autowired
	private EquipmentRepository equipmentRepository;
	
	@Override
	public BorrowRepository getRepository() {
		return borrowRepository;
	}

	SimpleDateFormat ymdHmsDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
	

	public void borrow(Equipment[] equipments, Borrow borrow) {
		// 插入入库单
		String borrow_id = ymdHmsDateFormat.format(new Date());
		// InStore inStore=new InStore();
		borrow.setId(borrow_id);
		borrow.setOperateDate(new Date());
		borrow.setOperater(ShiroUtils.getAuthenticationInfo().getId());
		//borrow.setType(1);
		borrowRepository.create(borrow);
		
		for(Equipment equipment:equipments){
			//更新设备状态为出库待安装
			//把设备绑定到作业单位上面
			//把仓库中的该设备移除
			equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.out_storage)
					.set(M.Equipment.workUnit_id, borrow.getWorkUnit_id())
					.set(M.Equipment.store_id, null)
					.andEquals(M.Equipment.ecode, equipment.getEcode()));
			
			
			//插入入库单明细
			BorrowList inStoreList=new BorrowList();
			inStoreList.setEcode(equipment.getEcode());
			inStoreList.setBorrow_id(borrow_id);
			borrowListRepository.create(inStoreList);
		}
	}
	
	public Page queryMain(Page page){

		return borrowRepository.queryMain(page);

		
	}
	public List<InstallOutListVO> queryList(String installOut_id) {

		return borrowRepository.queryList(installOut_id);

	}
}
