package com.mawujun.store;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.baseinfo.Equipment;
import com.mawujun.utils.BeanUtils;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/inStore")
public class InStoreController {

	@Resource
	private InStoreService inStoreService;
//	@Resource
//	private BarcodeService barcodeService;

//
//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/inStore/query.do")
//	@ResponseBody
//	public List<InStore> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.InStore.parent.id, "root".equals(id)?null:id);
//		List<InStore> inStorees=inStoreService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(InStore.class,M.InStore.parent.name());
//		return inStorees;
//	}
//
//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/inStore/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.InStore.sampleName, "%"+sampleName+"%");
//		return inStoreService.queryPage(page);
//	}

	@RequestMapping("/inStore/query.do")
	@ResponseBody
	public List<InStore> query() {	
		List<InStore> inStorees=inStoreService.queryAll();
		return inStorees;
	}
	

	@RequestMapping("/inStore/load.do")
	public InStore load(String id) {
		return inStoreService.get(id);
	}
	
	@RequestMapping("/inStore/create.do")
	@ResponseBody
	public InStore create(@RequestBody InStore inStore) {
		inStoreService.create(inStore);
		return inStore;
	}
	
	@RequestMapping("/inStore/update.do")
	@ResponseBody
	public  InStore update(@RequestBody InStore inStore) {
		inStoreService.update(inStore);
		return inStore;
	}
	
	@RequestMapping("/inStore/deleteById.do")
	@ResponseBody
	public String deleteById(String id) {
		inStoreService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/inStore/destroy.do")
	@ResponseBody
	public InStore destroy(@RequestBody InStore inStore) {
		inStoreService.delete(inStore);
		return inStore;
	}
//	/**
//	 * 主要用于新品入库的时候
//	 * @author mawujun 16064988@qq.com 
//	 * @param ecode
//	 * @return
//	 */
//	@RequestMapping("/inStore/getEquipFromBarcode.do")
//	@ResponseBody
//	public Equipment getEquipFromBarcode(String ecode) {	
//		Barcode barcode= barcodeService.getBarcodeByEcode(ecode);
//		barcode.setStatus(null);
//		Equipment equipment= BeanUtils.copyOrCast(barcode, Equipment.class);
//		equipment.setStatus(barcode.getIsInStore()?255:0);//设备如果已经入过库了，就设置为255，否则就使用0
//		return equipment;
//	}
	
	
	@RequestMapping("/inStore/newInStore.do")
	@ResponseBody
	//public String newInStore(@RequestBody Equipment[] equipments,String memo,String inStore_type,String store_id) throws  IOException{
	public String newInStore(@RequestBody Equipment[] equipments,InStore inStore) throws  IOException{
		inStoreService.newInStore(equipments,inStore);
		return "success";
	}
}
