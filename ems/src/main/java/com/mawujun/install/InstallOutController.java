package com.mawujun.install;
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
import com.mawujun.exception.BusinessException;
import com.mawujun.install.InstallOut;
import com.mawujun.install.InstallOutService;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.page.Page;
import com.mawujun.utils.BeanUtils;
import com.mawujun.utils.M;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/installOut")
public class InstallOutController {

	@Resource
	private InstallOutService outStoreService;
	@Resource
	private EquipmentService equipmentService;

//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/installOut/query.do")
//	@ResponseBody
//	public List<InstallOut> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.OutStore.parent.id, "root".equals(id)?null:id);
//		List<InstallOut> outStorees=outStoreService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(InstallOut.class,M.OutStore.parent.name());
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
//	@RequestMapping("/installOut/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.OutStore.sampleName, "%"+sampleName+"%");
//		return outStoreService.queryPage(page);
//	}

	@RequestMapping("/installOut/query.do")
	@ResponseBody
	public List<InstallOut> query() {	
		List<InstallOut> outStorees=outStoreService.queryAll();
		return outStorees;
	}
	

	@RequestMapping("/installOut/load.do")
	public InstallOut load(String id) {
		return outStoreService.get(id);
	}
	
	@RequestMapping("/installOut/create.do")
	@ResponseBody
	public InstallOut create(@RequestBody InstallOut outStore) {
		outStoreService.create(outStore);
		return outStore;
	}
	
	@RequestMapping("/installOut/update.do")
	@ResponseBody
	public  InstallOut update(@RequestBody InstallOut outStore) {
		outStoreService.update(outStore);
		return outStore;
	}
	
	@RequestMapping("/installOut/deleteById.do")
	@ResponseBody
	public String deleteById(String id) {
		outStoreService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/installOut/destroy.do")
	@ResponseBody
	public InstallOut destroy(@RequestBody InstallOut outStore) {
		outStoreService.delete(outStore);
		return outStore;
	}
	
	/**
	 * 主要用于新品入库的时候
	 * @author mawujun 16064988@qq.com 
	 * @param ecode
	 * @return
	 */
	@RequestMapping("/installOut/getEquipmentByEcode.do")
	@ResponseBody
	public Equipment getEquipmentByEcode(String ecode,String store_id) {	
		Equipment equipment= equipmentService.getEquipmentByEcode(ecode,store_id);
		if(equipment==null){
			//equipment=new Equipment();
			//equipment.setStatus(0);
			throw new BusinessException("对不起，该条码对应的设备不存在，或者该设备挂在其他仓库中!");
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
	@RequestMapping("/installOut/equipmentOutStore.do")
	@ResponseBody
	//public String equipOutStore(@RequestBody Equipment[] equipments,String store_id,String workUnit_id,String type,String memo) {
	public String equipOutStore(@RequestBody Equipment[] equipments, InstallOut outStore) { 
		//inStoreService.newInStore(equipments);
		outStoreService.equipOutStore(equipments, outStore);
		return "success";
	}
	
}
