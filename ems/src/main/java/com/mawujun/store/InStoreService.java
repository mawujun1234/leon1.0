package com.mawujun.store;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.baseinfo.Equipment;
import com.mawujun.baseinfo.EquipmentRepository;
import com.mawujun.baseinfo.EquipmentStatus;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.shiro.ShiroUtils;
import com.mawujun.utils.M;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class InStoreService extends AbstractService<InStore, String>{

	@Autowired
	private InStoreRepository inStoreRepository;
	@Autowired
	private InStoreListRepository inStoreListRepository;
	@Autowired
	private EquipmentRepository equipmentRepository;
	@Autowired
	private BarcodeRepository barcodeRepository;
	@Autowired
	private OrderRepository orderRepository;
	
	SimpleDateFormat ymdHmsDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Override
	public InStoreRepository getRepository() {
		return inStoreRepository;
	}

	/**
	 * 新设备入库
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param equipments
	 */
	public void newInStore(Equipment[] equipments,InStore inStore) {
		//插入入库单
		String instore_id=ymdHmsDateFormat.format(new Date());
		//InStore inStore=new InStore();
		inStore.setId(instore_id);
		inStore.setOperateDate(new Date());
		inStore.setOperater(ShiroUtils.getAuthenticationInfo().getId());
		//inStore.setType(1);
		inStoreRepository.create(inStore);
		
		Map<String,Integer> totalnumMap=new HashMap<String,Integer>();
		//插入设备表,同时设置仓库，入库时间，是否新设备
		for(Equipment equipment:equipments){
			equipment.setFisData(new Date());
			//equipment.setLastInDate(new Date());
			equipment.setStatus(EquipmentStatus.in_storage.getValue());
			equipment.setIsnew(true);
			equipment.setStore_id(inStore.getStore_id());
			equipment.setMemo("");
			equipmentRepository.create(equipment);
			////修改条码状态
			//barcodeRepository.update(Cnd.update().set(M.Barcode.isInStore, true).andEquals(M.Barcode.ecode, equipment.getEcode()));
			//更新订单明细中的累计入库数量
			//orderRepository.updateTotalNum(equipment.getOrder_id(), M.Order.totalNum+"+1");
			if(totalnumMap.containsKey(equipment.getOrderlist_id())){
				totalnumMap.put(equipment.getOrderlist_id(), totalnumMap.get(equipment.getOrderlist_id())+1);
			} else {
				totalnumMap.put(equipment.getOrderlist_id(), 1);
			}
			
			
			//插入入库单明细
			InStoreList inStoreList=new InStoreList();
			inStoreList.setEncode(equipment.getEcode());
			inStoreList.setInStore_id(instore_id);
			inStoreListRepository.create(inStoreList);
			
			//更新条码的状态
			barcodeRepository.update(Cnd.update().set(M.Barcode.status, 1).andEquals(M.Barcode.ecode, equipment.getEcode()));
		}
		
		for(Entry<String,Integer> entry:totalnumMap.entrySet()){
			orderRepository.updateTotalNum(entry.getKey(), M.OrderList.totalNum+"+"+entry.getValue());
		}
		
	}
	/**
	 * 获取某个入库单中的明细数据
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param inStore_id
	 * @return
	 */
	public List<InStoreListVO> queryList(String inStore_id) {
		return inStoreRepository.queryList(inStore_id);
	}
}
