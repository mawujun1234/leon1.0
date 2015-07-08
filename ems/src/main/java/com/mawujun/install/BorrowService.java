package com.mawujun.install;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;























import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.shiro.ShiroUtils;
import com.mawujun.user.UserService;
import com.mawujun.utils.M;
import com.mawujun.utils.page.Page;
import com.mawujun.baseinfo.Equipment;
import com.mawujun.baseinfo.EquipmentCycleService;
import com.mawujun.baseinfo.EquipmentPlace;
import com.mawujun.baseinfo.EquipmentRepository;
import com.mawujun.baseinfo.EquipmentStatus;
import com.mawujun.baseinfo.EquipmentStore;
import com.mawujun.baseinfo.EquipmentStorePK;
import com.mawujun.baseinfo.EquipmentStoreRepository;
import com.mawujun.baseinfo.EquipmentStoreType;
import com.mawujun.baseinfo.EquipmentWorkunit;
import com.mawujun.baseinfo.EquipmentWorkunitPK;
import com.mawujun.baseinfo.EquipmentWorkunitRepository;
import com.mawujun.baseinfo.EquipmentWorkunitType;
import com.mawujun.baseinfo.OperateType;
import com.mawujun.baseinfo.StoreService;
import com.mawujun.baseinfo.TargetType;
import com.mawujun.baseinfo.WorkUnitService;
import com.mawujun.exception.BusinessException;
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
	@Autowired
	private EquipmentStoreRepository equipmentStoreRepository;
	@Autowired
	private EquipmentWorkunitRepository equipmentWorkunitRepository;
	@Autowired
	private UserService userService;
//	@Autowired
//	private WorkUnitService workUnitService;
	@Autowired
	private EquipmentCycleService equipmentCycleService;
//	@Autowired
//	private StoreService storeService;
	
	@Override
	public BorrowRepository getRepository() {
		return borrowRepository;
	}

	SimpleDateFormat ymdHmsDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
	
	
	public String borrowSaveAndPrint(BorrowList[] borrowListes, Borrow borrow, String borrow_id) {
		if(borrow_id!=null && !"".equals(borrow_id)){
			
			borrowListRepository.deleteBatch(Cnd.delete().andEquals(M.BorrowList.borrow_id, borrow_id));
			borrowRepository.deleteBatch(Cnd.delete().andEquals(M.Borrow.id, borrow_id));
			borrow.setId(borrow_id);
		} else {
			borrow_id = ymdHmsDateFormat.format(new Date());
			// InStore inStore=new InStore();
			borrow.setId(borrow_id);
		}
		borrow.setStatus(BorrowStatus.edit);
		// 插入入库单
		
		borrow.setOperateDate(new Date());
		borrow.setOperater(ShiroUtils.getAuthenticationInfo().getId());
		// outStore.setType(1);
		borrowRepository.create(borrow);
		
		for(BorrowList borrowList:borrowListes){
			borrowList.setBorrow_id(borrow_id);
			borrowListRepository.create(borrowList);
		}
		return 	borrow_id;
	}

	public String borrow(BorrowList[] borrowListes, Borrow borrow, String borrow_id) {
		//// 插入入库单
		//String borrow_id = ymdHmsDateFormat.format(new Date());
		if(borrow_id!=null && !"".equals(borrow_id)){
			
			borrowListRepository.deleteBatch(Cnd.delete().andEquals(M.BorrowList.borrow_id, borrow_id));
			borrowRepository.deleteBatch(Cnd.delete().andEquals(M.Borrow.id, borrow_id));
			borrow.setId(borrow_id);
		} else {
			borrow_id = ymdHmsDateFormat.format(new Date());
			// InStore inStore=new InStore();
			borrow.setId(borrow_id);
		}

		// InStore inStore=new InStore();
		borrow.setId(borrow_id);
		borrow.setOperateDate(new Date());
		borrow.setOperater(ShiroUtils.getAuthenticationInfo().getId());
		borrow.setStatus(BorrowStatus.noreturn);
		borrowRepository.create(borrow);
		
		for(BorrowList borrowlist:borrowListes){
			//更新设备状态为出库待安装
			//把设备绑定到作业单位上面
			//把仓库中的该设备移除
			equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.out_storage)
					//.set(M.Equipment.workUnit_id, borrow_out.getWorkUnit_id())
					//.set(M.Equipment.store_id, null)
					.set(M.Equipment.last_workunit_id, borrow.getWorkUnit_id())
					.set(M.Equipment.last_borrow_id, borrow_id)
					.set(M.Equipment.place, EquipmentPlace.workunit)
					.andEquals(M.Equipment.ecode, borrowlist.getEcode()));
			
			//从仓库中删除
			EquipmentStorePK equipmentStorePK=new EquipmentStorePK();
			equipmentStorePK.setEcode(borrowlist.getEcode());
			equipmentStorePK.setStore_id(borrow.getStore_id());
			equipmentStoreRepository.deleteById(equipmentStorePK);

			//插入到workunit中
			EquipmentWorkunit equipmentWorkunit=new EquipmentWorkunit();
			equipmentWorkunit.setEcode(borrowlist.getEcode());
			equipmentWorkunit.setWorkunit_id(borrow.getWorkUnit_id());
			equipmentWorkunit.setInDate(new Date());
			equipmentWorkunit.setNum(1);
			equipmentWorkunit.setType(EquipmentWorkunitType.borrow);
			equipmentWorkunit.setType_id(borrow_id);
			equipmentWorkunit.setFrom_id(borrow.getStore_id());
			equipmentWorkunitRepository.create(equipmentWorkunit);
			
//			//插入入库单明细
//			BorrowList borrowlist=new BorrowList();
//			borrowlist.setEcode(equipment.getEcode());
			borrowlist.setBorrow_id(borrow_id);
			borrowListRepository.create(borrowlist);
			
			//记录设备入库的生命周期
			equipmentCycleService.logEquipmentCycle(borrowlist.getEcode(), OperateType.borrow_out, borrow_id, TargetType.workunit,borrow.getWorkUnit_id());
		}
		return borrow_id;
	}
	
	public Page queryMain(Page page){

		return borrowRepository.queryMain(page);

		
	}
	
	public BorrowVO getBorrowVO(String borrow_id) {
		BorrowVO borrowVO= borrowRepository.getBorrowVO(borrow_id);
		borrowVO.setOperater_name(userService.get(borrowVO.getOperater()).getName());
		return borrowVO;
	}
	public List<BorrowListVO> queryList(String borrow_id) {

		return borrowRepository.queryList(borrow_id);

	}
	
	public BorrowListVO getBorrowEquipmentByEcode(String ecode,String store_id) {
		//先检查改设备是不是该仓库借出去的,顺便判断是不是领用设备，好提醒仓管,该设备不是借用设备
		//Equipment equipment=equipmentRepository.get(ecode);
		//EquipmentWorkunitPK equipmentworkunitpk=new EquipmentWorkunitPK();
		EquipmentWorkunit equipmentWorkunit=equipmentRepository.getBorrowEquipmentWorkunit(ecode);
		if(equipmentWorkunit==null){
			throw new BusinessException("该设备不是借用设备,不能在这里进行返还!");
		}
		if(!equipmentWorkunit.getFrom_id().equals(store_id)){
			throw new BusinessException("该设备不是从这个仓库借出去的,不能归还到这个仓库!");
		}
		//再判断该设备是不是从这个仓库借出去的
		BorrowListVO borrowListVO=borrowRepository.getBorrowListVO(equipmentWorkunit.getType_id(), ecode);
		return borrowListVO;
		
	}
	/**
	 * 借用返回
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param borrowlists
	 * @param store_id
	 */
	public void borrowReturn(BorrowList borrowlists[],String store_id) {
		//别忘记修改isAllReturn字段信息
		for(BorrowList borrowList:borrowlists){
			//更改对应的借用明细的数据
			borrowRepository.returnBorrowList(borrowList.getId());
			//判断该借用单是否已经全部归还了
			borrowRepository.updateBorrowIsAllReturn(borrowList.getBorrow_id());
			
			Borrow borrow= borrowRepository.get(borrowList.getBorrow_id());
			if(!borrow.getStore_id().equals(store_id)){
				throw new BusinessException("条码为"+borrowList.getEcode()+"的设备不是从这个仓库出去的!");
			}
			
			equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.in_storage)
					.set(M.Equipment.place, EquipmentPlace.store)
					//.set(M.Equipment.last_workunit_id,borrow_out.getWorkUnit_id())
					.andEquals(M.Equipment.ecode, borrowList.getEcode()));
			//同时从作业单位专业到仓库
			//插入仓库中
			EquipmentStore equipmentStore=new EquipmentStore();
			equipmentStore.setEcode(borrowList.getEcode());
			equipmentStore.setStore_id(borrow.getStore_id());
			equipmentStore.setNum(1);
			equipmentStore.setInDate(new Date());
			equipmentStore.setType(EquipmentStoreType.borrowreturn);
			equipmentStore.setType_id(borrowList.getBorrow_id());
			equipmentStore.setFrom_id(borrow.getWorkUnit_id());
			equipmentStoreRepository.create(equipmentStore);
			//workunit减掉这个设备
			EquipmentWorkunitPK equipmentWorkunitPK=new EquipmentWorkunitPK();
			equipmentWorkunitPK.setEcode(borrowList.getEcode());
			equipmentWorkunitPK.setWorkunit_id(borrow.getWorkUnit_id());
			equipmentWorkunitRepository.deleteById(equipmentWorkunitPK);
			
			//记录设备入库的生命周期
			equipmentCycleService.logEquipmentCycle(borrowList.getEcode(), OperateType.borrow_return, borrowList.getBorrow_id(), TargetType.store,borrow.getStore_id());
			
		}
	}
	
	
	public List<BorrowVO> queryEditBorrow() {
		return borrowRepository.queryEditBorrow();
	}
}
