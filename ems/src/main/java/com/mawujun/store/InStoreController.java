package com.mawujun.store;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;
import com.mawujun.baseinfo.Equipment;
import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.page.Page;
import com.mawujun.utils.BeanUtils;
import com.mawujun.utils.M;
import com.mawujun.store.InStore;
import com.mawujun.store.InStoreService;
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
	@Resource
	private BarcodeService barcodeService;

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
	
	@RequestMapping("/inStore/getEquipFromBarcode.do")
	@ResponseBody
	public Equipment getEquipFromBarcode(String ecode) {	
		Barcode barcode= barcodeService.getBarcodeByEcode(ecode);
		barcode.setStatus(null);
		Equipment equipment= BeanUtils.copyOrCast(barcode, Equipment.class);
		equipment.setStatus(0);
		return equipment;
	}
	
	
	@RequestMapping("/inStore/newInStore.do")
	@ResponseBody
	public String newInStore(@RequestBody Equipment[] equipments) throws  IOException{
		inStoreService.newInStore(equipments);
		return "success";
	}
}
