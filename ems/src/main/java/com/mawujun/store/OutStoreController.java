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
import com.mawujun.baseinfo.EquipmentService;
import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.page.Page;
import com.mawujun.utils.BeanUtils;
import com.mawujun.utils.M;
import com.mawujun.store.OutStore;
import com.mawujun.store.OutStoreService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/outStore")
public class OutStoreController {

	@Resource
	private OutStoreService outStoreService;
	@Resource
	private EquipmentService equipmentService;

//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/outStore/query.do")
//	@ResponseBody
//	public List<OutStore> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.OutStore.parent.id, "root".equals(id)?null:id);
//		List<OutStore> outStorees=outStoreService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(OutStore.class,M.OutStore.parent.name());
//		return outStorees;
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
//	@RequestMapping("/outStore/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.OutStore.sampleName, "%"+sampleName+"%");
//		return outStoreService.queryPage(page);
//	}

	@RequestMapping("/outStore/query.do")
	@ResponseBody
	public List<OutStore> query() {	
		List<OutStore> outStorees=outStoreService.queryAll();
		return outStorees;
	}
	

	@RequestMapping("/outStore/load.do")
	public OutStore load(String id) {
		return outStoreService.get(id);
	}
	
	@RequestMapping("/outStore/create.do")
	@ResponseBody
	public OutStore create(@RequestBody OutStore outStore) {
		outStoreService.create(outStore);
		return outStore;
	}
	
	@RequestMapping("/outStore/update.do")
	@ResponseBody
	public  OutStore update(@RequestBody OutStore outStore) {
		outStoreService.update(outStore);
		return outStore;
	}
	
	@RequestMapping("/outStore/deleteById.do")
	@ResponseBody
	public String deleteById(String id) {
		outStoreService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/outStore/destroy.do")
	@ResponseBody
	public OutStore destroy(@RequestBody OutStore outStore) {
		outStoreService.delete(outStore);
		return outStore;
	}
	
	/**
	 * 主要用于新品入库的时候
	 * @author mawujun 16064988@qq.com 
	 * @param ecode
	 * @return
	 */
	@RequestMapping("/outStore/getEquipmentByEcode.do")
	@ResponseBody
	public Equipment getEquipmentByEcode(String ecode) {	
		Equipment equipment= equipmentService.getEquipmentByEcode(ecode);
		if(equipment==null){
			equipment=new Equipment();
			equipment.setStatus(0);
		}
		return equipment;
	}
	
	/**
	 * 设备出库，设备领用
	 * @author mawujun 16064988@qq.com 
	 * @param equipments
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/outStore/equipmentOutStore.do")
	@ResponseBody
	//public String equipOutStore(@RequestBody Equipment[] equipments,String store_id,String workUnit_id,String type,String memo) {
	public String equipOutStore(@RequestBody Equipment[] equipments, OutStore outStore) { 
		//inStoreService.newInStore(equipments);
		outStoreService.equipOutStore(equipments, outStore);
		return "success";
	}
	
}
