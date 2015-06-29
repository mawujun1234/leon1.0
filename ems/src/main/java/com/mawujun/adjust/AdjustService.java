package com.mawujun.adjust;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;















import org.springframework.web.bind.annotation.RequestBody;

import com.mawujun.repair.RepairVO;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.shiro.ShiroUtils;
import com.mawujun.utils.BeanUtils;
import com.mawujun.utils.M;
import com.mawujun.utils.Params;
import com.mawujun.utils.page.Page;
import com.mawujun.adjust.Adjust;
import com.mawujun.adjust.AdjustRepository;
import com.mawujun.baseinfo.EquipmentPlace;
import com.mawujun.baseinfo.EquipmentRepository;
import com.mawujun.baseinfo.EquipmentStatus;
import com.mawujun.baseinfo.EquipmentStore;
import com.mawujun.baseinfo.EquipmentStorePK;
import com.mawujun.baseinfo.EquipmentStoreRepository;
import com.mawujun.baseinfo.EquipmentStoreType;
import com.mawujun.baseinfo.Store;
import com.mawujun.baseinfo.StoreRepository;
import com.mawujun.exception.BusinessException;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class AdjustService extends AbstractService<Adjust, String>{

	@Autowired
	private AdjustRepository adjustRepository;
	@Autowired
	private AdjustListRepository adjustListRepository;
	@Autowired
	private EquipmentRepository equipmentRepository;
	@Autowired
	private EquipmentStoreRepository equipmentStoreRepository;
	@Autowired
	private StoreRepository storeRepository;
	
	SimpleDateFormat ymdHmsDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Override
	public AdjustRepository getRepository() {
		return adjustRepository;
	}
	
	public AdjustListVO getAdjustVOByEcode(String ecode,String store_id) {
		return adjustRepository.getAdjustVOByEcode(ecode, store_id);
	}
	
	public void newAdjuest(Adjust adjust,AdjustList[] adjuestLists) {
		//创建调拨单
		//Adjust adjust=BeanUtils.copyOrCast(adjuestVOs[0], Adjust.class);//adjuestVOs[0];//每条记录的主单内容都是一样的
		adjust.setId(ymdHmsDateFormat.format(new Date()));
		adjust.setStr_out_date(new Date());
		adjust.setStatus(AdjustStatus.carry);
		adjust.setStr_in_oper_id(ShiroUtils.getAuthenticationInfo().getId());

		adjustRepository.create(adjust);
		
		
		for(AdjustList adjustList:adjuestLists){
			//创建调拨明细
			//AdjustList adjustList=new AdjustList();
			adjustList.setAdjust_id(adjust.getId());
			adjustList.setAdjustListStatus(AdjustListStatus.noin);
			//adjustList.setEcode(adjustVO.getEcode());
			//adjustList.setOut_num(1);
			adjustListRepository.create(adjustList);
			
			//修改设备状态为"在途"
			equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.in_transit).andEquals(M.Equipment.ecode, adjustList.getEcode()));
		}
		//
	}
	
	public Page query4InStr(Page page){
		List<Store> stores=storeRepository.queryAll();
		
		Page results=adjustRepository.query4InStr(page);
		List<AdjustVO> list=results.getResult();
		for(AdjustVO adjustVO:list){
			for(Store store:stores){
				if(store.getId().equals(adjustVO.getStr_out_id())){
					adjustVO.setStr_out_name(store.getName());
				} else if(store.getId().equals(adjustVO.getStr_in_id())){
					adjustVO.setStr_in_name(store.getName());
				}
			}
		}
		return results;
	}
	
	
	public List<AdjustListVO> query4InStrList(String adjust_id) {
		return adjustRepository.query4InStrList(adjust_id);
	}
	/**
	 * 当按部分入库按钮的时候，并且判断当入库数和实际要入库数一样的时候，就当做事全部入库，走的是就是全部入库的路线了
	 * @author mawujun 16064988@qq.com 
	 * @return
	 */
	public void adjustInStore(AdjustList[] adjustLists,String adjust_id) {
		Adjust adjust=adjustRepository.get(adjust_id);
		
		//获取当前调拨下的设备总数,本来应该是sum的，但现在是sum和count一样的
		Long out_num_total=adjustListRepository.queryCount(Cnd.count(M.AdjustList.id).andEquals(M.AdjustList.adjust_id, adjust_id));
		//Long out_num_total=(Long)adjustListRepository.querySum(Cnd.sum(M.AdjustList.out_num).andEquals(M.AdjustList.adjust_id, adjust_id));
		
		//int total=0;
		for(AdjustList adjustList:adjustLists) {
			//adjustList.setStatus(true);
			//adjustListRepository.update(adjustList);
			//更新调拨单明细的状态为已经入库
			adjustListRepository.update(Cnd.update().set(M.AdjustList.adjustListStatus, AdjustListStatus.in)
					//.set(M.AdjustList.indate, new Date())
					.andEquals(M.AdjustList.id, adjustList.getId()));
			
			//同时更改设备状态，从A仓库到B仓库
			//同时修改设备状态
			//修改设备状态为"在库"
//			equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.in_storage)
//					.set(M.Equipment.store_id, str_in_id).andEquals(M.Equipment.ecode, adjustList.getEcode()));
			equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.in_storage)
					.set(M.Equipment.place, EquipmentPlace.store)
					.andEquals(M.Equipment.ecode, adjustList.getEcode()));
			
			//从仓库中删除
			EquipmentStorePK equipmentStorePK=new EquipmentStorePK();
			equipmentStorePK.setEcode( adjustList.getEcode());
			equipmentStorePK.setStore_id(adjust.getStr_out_id());
			equipmentStoreRepository.deleteById(equipmentStorePK);
			
			//插入到仓库
			EquipmentStore equipmentStore=new EquipmentStore();
			equipmentStore.setEcode( adjustList.getEcode());
			equipmentStore.setStore_id(adjust.getStr_in_id());
			equipmentStore.setNum(1);
			equipmentStore.setInDate(new Date());
			equipmentStore.setType(EquipmentStoreType.adjust);
			equipmentStore.setType_id(adjustList.getAdjust_id());
			equipmentStore.setFrom_id(adjust.getStr_out_id());
			equipmentStoreRepository.create(equipmentStore);
		
		}
		//这个时候就表示是都选择了，修改整个调拨单的状态
		//Long in_num_total=(Long)adjustListRepository.querySum(Cnd.sum(M.AdjustList.in_num).andEquals(M.AdjustList.adjust_id, adjust_id));
		//还有仓库迁移要做
		Long in_num_total=(Long)adjustListRepository.queryCount(Cnd.sum(M.AdjustList.id)
				.andEquals(M.AdjustList.adjust_id, adjust_id).andIn(M.AdjustList.adjustListStatus, AdjustListStatus.in.toString(),AdjustListStatus.installout.toString()));
		
		
		//if(adjustRepository.sumInnumByadjust_id(adjust_id)==out_num_total){
		//入的数量和出的数量
		if(in_num_total==out_num_total){
			
			if(adjust.getAdjustType()==AdjustType.borrow){
				//adjustRepository.update(Cnd.update().set(M.Adjust.status, AdjustStatus.noreturn).andEquals(M.Adjust.id, adjust_id));
				adjust.setStatus(AdjustStatus.noreturn);
			} else {
				//adjustRepository.update(Cnd.update().set(M.Adjust.status, AdjustStatus.over).andEquals(M.Adjust.id, adjust_id));
				adjust.setStatus(AdjustStatus.over);
			}
			
		} else {
			//这里表示未完全入库
			//adjustRepository.update(Cnd.update().set(M.Adjust.status, AdjustStatus.noallin).andEquals(M.Adjust.id, adjust_id));
			adjust.setStatus(AdjustStatus.noallin);
		}
		
		adjustRepository.update(adjust);
	}
//	/**
//	 * 当按全部入库按钮的时候，当要入库的数量和实际要入库的数量不一致的时候，要给出提醒，如果还是要强制入库，就表示某个设备丢失了
//	 * @author mawujun 16064988@qq.com 
//	 * @return
//	 */
//	public void allInStr(AdjustList[] adjustLists,String str_in_id) {
//		Long out_num_total=adjustListRepository.queryCount(Cnd.count(M.AdjustList.id).andEquals(M.AdjustList.adjust_id, adjustLists[0].getAdjust_id()));
//		if(adjustLists.length!=out_num_total){
//			throw new BusinessException("设备没有全部到货，确定要结束这个调拨单吗?");
//		}
//		
//	}
	
	public Page queryPage(Page page) {
		List<Store> stores=storeRepository.queryAll();
		
		Page results=adjustRepository.queryPage(page);
		List<AdjustVO> list=results.getResult();
		for(AdjustVO adjustVO:list){
			for(Store store:stores){
				if(store.getId().equals(adjustVO.getStr_out_id())){
					adjustVO.setStr_out_name(store.getName());
				} else if(store.getId().equals(adjustVO.getStr_in_id())){
					adjustVO.setStr_in_name(store.getName());
				}
			}
		}
		return results;
	}
	public List<AdjustListVO> queryList(String adjust_id) {
		return adjustRepository.query4InStrList(adjust_id);
	}

	
	public void change2installout(String adjust_id) {
		Adjust adjust=adjustRepository.get(adjust_id);
		//入过本来就是领用单，就不进行转换
		if(adjust.getAdjustType()==AdjustType.installout){
			return;
		}
		
		//入过该借用单已经有过入库了，那也不能转换
		
		//入过当前用户没有权限操作这个仓库，那也不能转换
		
		//同时把明细单的所有状态，改成 install_out
	}
}
