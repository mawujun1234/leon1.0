package com.mawujun.install;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.ObjectNotFoundException;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.baseinfo.EquipmentCycleService;
import com.mawujun.baseinfo.EquipmentPlace;
import com.mawujun.baseinfo.EquipmentRepository;
import com.mawujun.baseinfo.EquipmentStatus;
import com.mawujun.baseinfo.EquipmentStorePK;
import com.mawujun.baseinfo.EquipmentStoreRepository;
import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.baseinfo.EquipmentWorkunit;
import com.mawujun.baseinfo.EquipmentWorkunitRepository;
import com.mawujun.baseinfo.EquipmentWorkunitType;
import com.mawujun.baseinfo.OperateType;
import com.mawujun.baseinfo.TargetType;
import com.mawujun.baseinfo.WorkUnitService;
import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.shiro.ShiroUtils;
import com.mawujun.user.UserService;
import com.mawujun.utils.M;
import com.mawujun.utils.page.Page;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class InstallOutService extends AbstractService<InstallOut, String>{
	private static Logger logger = LogManager.getLogger(InstallOutService.class);

	@Autowired
	private InstallOutRepository installOutRepository;
	@Autowired
	private InstallOutListRepository installOutListRepository;
	@Autowired
	private EquipmentRepository equipmentRepository;
	@Autowired
	private EquipmentStoreRepository equipmentStoreRepository;
	@Autowired
	private EquipmentWorkunitRepository equipmentWorkunitRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private WorkUnitService workUnitService;
	@Autowired
	private EquipmentCycleService equipmentCycleService;
	
	SimpleDateFormat ymdHmsDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Override
	public InstallOutRepository getRepository() {
		return installOutRepository;
	}
	
	public InstallOutListVO getInstallOutListVOByEcode(String ecode,String store_id) {
		return installOutRepository.getInstallOutListVOByEcode(ecode, store_id);
	}

	/**
	 * 先进行订单的保存，但是设备不转移到作业单位上去
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param installOutListes
	 * @param outStore
	 * @return
	 */
	public String equipOutStoreSaveAndPrint(InstallOutList[] installOutListes, InstallOut outStore,String installOut_id) {
		if(installOut_id!=null && !"".equals(installOut_id)){
			
			installOutListRepository.deleteBatch(Cnd.delete().andEquals(M.InstallOutList.installOut_id, installOut_id));
			installOutRepository.deleteBatch(Cnd.delete().andEquals(M.InstallOut.id, installOut_id));
			outStore.setId(installOut_id);
		} else {
			installOut_id = ymdHmsDateFormat.format(new Date());
			// InStore inStore=new InStore();
			outStore.setId(installOut_id);
		}
		outStore.setStatus(InstallOutStatus.edit);
		// 插入入库单
		
		outStore.setOperateDate(new Date());
		outStore.setOperater(ShiroUtils.getAuthenticationInfo().getId());
		// outStore.setType(1);
		installOutRepository.create(outStore);
		
		for(InstallOutList inStoreList:installOutListes){
			inStoreList.setInstallOut_id(installOut_id);
			inStoreList.setInstallOutListType(InstallOutListType.borrow);
			installOutListRepository.create(inStoreList);
		}
		return 	installOut_id;
	}
	public String equipOutStore(InstallOutList[] installOutListes, InstallOut outStore,String installOut_id) {
//		if(installOut_id==null || "".equals(installOut_id.trim())){
//			throw new BusinessException("请先选择一个'编辑中'的领用单!");
//		}

		
		if(installOut_id!=null && !"".equals(installOut_id)){
			//先删除所有的原来的明细数据
			installOutListRepository.deleteBatch(Cnd.delete().andEquals(M.InstallOutList.installOut_id, installOut_id));
			installOutRepository.deleteBatch(Cnd.delete().andEquals(M.InstallOut.id, installOut_id));
			
		} else {
			installOut_id = ymdHmsDateFormat.format(new Date());
			// InStore inStore=new InStore();
			outStore.setId(installOut_id);
		}

		outStore.setId(installOut_id);
		outStore.setOperateDate(new Date());
		outStore.setOperater(ShiroUtils.getAuthenticationInfo().getId());
		outStore.setStatus(InstallOutStatus.over);
		installOutRepository.create(outStore);
		
		//String outstore_id =outStore.getId();
		
		
		for(InstallOutList inStoreList:installOutListes){
			//更新设备状态为出库待安装
			//把设备绑定到作业单位上面
			//把仓库中的该设备移除
			equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.out_storage)
					.set(M.Equipment.last_workunit_id, outStore.getWorkUnit_id())
					//.set(M.Equipment.store_id, null)
					.set(M.Equipment.place, EquipmentPlace.workunit)
					.andEquals(M.Equipment.ecode, inStoreList.getEcode()));
			
			//从仓库中删除
			try {
			EquipmentStorePK equipmentStorePK=new EquipmentStorePK();
			equipmentStorePK.setEcode(inStoreList.getEcode());
			equipmentStorePK.setStore_id(outStore.getStore_id());
			equipmentStoreRepository.deleteById(equipmentStorePK);
			}catch(Exception e){
				logger.warn(inStoreList.getEcode()+"该设备已经不在仓库"+outStore.getStore_id(),e);
				//logger.warn
				throw new BusinessException("该设备已经不在仓库!");
			}
//			equipmentStore.setNum(1);
//			equipmentStore.setInDate(new Date());
//			equipmentStore.setType(EquipmentStoreType.installin);
//			equipmentStore.setType_id(install_in.getId());
//			equipmentStoreRepository.create(equipmentStore);
			//插入到workunit中
			EquipmentWorkunit equipmentWorkunit=new EquipmentWorkunit();
			equipmentWorkunit.setEcode(inStoreList.getEcode());
			equipmentWorkunit.setWorkunit_id(outStore.getWorkUnit_id());
			equipmentWorkunit.setInDate(new Date());
			equipmentWorkunit.setNum(1);
			equipmentWorkunit.setType(EquipmentWorkunitType.installout);
			equipmentWorkunit.setType_id(installOut_id);
			equipmentWorkunit.setFrom_id(outStore.getStore_id());
			equipmentWorkunit.setProject_id(outStore.getProject_id());
			equipmentWorkunitRepository.create(equipmentWorkunit);
			
			
//			//插入入库单明细
//			InstallOutList inStoreList=new InstallOutList();
//			inStoreList.setEcode(equipment.getEcode());
			inStoreList.setInstallOut_id(installOut_id);
			inStoreList.setInstallOutListType(InstallOutListType.borrow);
			installOutListRepository.create(inStoreList);
			
			//记录设备入库的生命周期
			equipmentCycleService.logEquipmentCycle(inStoreList.getEcode(), OperateType.install_out, installOut_id,TargetType.workunit,outStore.getWorkUnit_id(),outStore.getMemo());
		}
		
		
		return installOut_id;
	}
//	public String equipOutStore(InstallOutList[] installOutListes, InstallOut outStore) {
//		outStore.setStatus(InstallOutStatus.over);
//		// 插入入库单
//		String outstore_id = ymdHmsDateFormat.format(new Date());
//		// InStore inStore=new InStore();
//		outStore.setId(outstore_id);
//		outStore.setOperateDate(new Date());
//		outStore.setOperater(ShiroUtils.getAuthenticationInfo().getId());
//		//outStore.setType(1);
//		installOutRepository.create(outStore);
//		
//		for(InstallOutList inStoreList:installOutListes){
//			//更新设备状态为出库待安装
//			//把设备绑定到作业单位上面
//			//把仓库中的该设备移除
//			equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.out_storage)
//					.set(M.Equipment.last_workunit_id, outStore.getWorkUnit_id())
//					//.set(M.Equipment.store_id, null)
//					.set(M.Equipment.place, EquipmentPlace.workunit)
//					.andEquals(M.Equipment.ecode, inStoreList.getEcode()));
//			
//			//从仓库中删除
//			EquipmentStorePK equipmentStorePK=new EquipmentStorePK();
//			equipmentStorePK.setEcode(inStoreList.getEcode());
//			equipmentStorePK.setStore_id(outStore.getStore_id());
//			equipmentStoreRepository.deleteById(equipmentStorePK);
////			equipmentStore.setNum(1);
////			equipmentStore.setInDate(new Date());
////			equipmentStore.setType(EquipmentStoreType.installin);
////			equipmentStore.setType_id(install_in.getId());
////			equipmentStoreRepository.create(equipmentStore);
//			//插入到workunit中
//			EquipmentWorkunit equipmentWorkunit=new EquipmentWorkunit();
//			equipmentWorkunit.setEcode(inStoreList.getEcode());
//			equipmentWorkunit.setWorkunit_id(outStore.getWorkUnit_id());
//			equipmentWorkunit.setInDate(new Date());
//			equipmentWorkunit.setNum(1);
//			equipmentWorkunit.setType(EquipmentWorkunitType.installout);
//			equipmentWorkunit.setType_id(outstore_id);
//			equipmentWorkunit.setFrom_id(outStore.getStore_id());
//			equipmentWorkunitRepository.create(equipmentWorkunit);
//			
//			
////			//插入入库单明细
////			InstallOutList inStoreList=new InstallOutList();
////			inStoreList.setEcode(equipment.getEcode());
//			inStoreList.setInstallOut_id(outstore_id);
//			installOutListRepository.create(inStoreList);
//			
//			//记录设备入库的生命周期
//			equipmentCycleService.logEquipmentCycle(inStoreList.getEcode(), OperateType.install_out, outstore_id,TargetType.workunit,outStore.getWorkUnit_id());
//		}
//		
//		
//		return outstore_id;
//	}
	
	public Page queryMain(Page page){
		return installOutRepository.queryMain(page);

		
	}
	
	public InstallOutVO getInstallOutVO(String installOut_id){
		InstallOutVO installOutVO= installOutRepository.getInstallOutVO(installOut_id);
		installOutVO.setOperater_name(userService.get(installOutVO.getOperater()).getName());
		return installOutVO;
	}
	public List<InstallOutListVO> queryList(String installOut_id) {

		return installOutRepository.queryList(installOut_id);

	}
	
	public List<InstallOutVO> queryEditInstallOut() {
		return installOutRepository.queryEditInstallOut();
	}
	
	public Page queryDifference(Page page){
		return installOutRepository.queryDifference(page);

		
	}
}
