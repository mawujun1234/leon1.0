package com.mawujun.install;
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
import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.page.Page;
import com.mawujun.utils.M;
import com.mawujun.install.InstallIn;
import com.mawujun.install.InstallInService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/installIn")
public class InstallInController {

	@Resource
	private InstallInService installInService;
	@Resource
	private EquipmentService equipmentService;


//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/installIn/query.do")
//	@ResponseBody
//	public List<InstallIn> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.InstallIn.parent.id, "root".equals(id)?null:id);
//		List<InstallIn> installInes=installInService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(InstallIn.class,M.InstallIn.parent.name());
//		return installInes;
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
//	@RequestMapping("/installIn/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.InstallIn.sampleName, "%"+sampleName+"%");
//		return installInService.queryPage(page);
//	}

	@RequestMapping("/installIn/query.do")
	@ResponseBody
	public List<InstallIn> query() {	
		List<InstallIn> installInes=installInService.queryAll();
		return installInes;
	}
	

	@RequestMapping("/installIn/load.do")
	public InstallIn load(String id) {
		return installInService.get(id);
	}
	
	@RequestMapping("/installIn/create.do")
	@ResponseBody
	public InstallIn create(@RequestBody InstallIn installIn) {
		installInService.create(installIn);
		return installIn;
	}
	
	@RequestMapping("/installIn/update.do")
	@ResponseBody
	public  InstallIn update(@RequestBody InstallIn installIn) {
		installInService.update(installIn);
		return installIn;
	}
	
	@RequestMapping("/installIn/deleteById.do")
	@ResponseBody
	public String deleteById(String id) {
		installInService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/installIn/destroy.do")
	@ResponseBody
	public InstallIn destroy(@RequestBody InstallIn installIn) {
		installInService.delete(installIn);
		return installIn;
	}
	
	@RequestMapping("/installIn/getEquipmentByEcode.do")
	@ResponseBody
	public EquipmentVO getEquipmentByEcode(String ecode,String workunit_id) {
		EquipmentVO equipment= installInService.getEquipmentByEcode(ecode,workunit_id);
		if(equipment==null){
			//equipment=new Equipment();
			//equipment.setStatus(0);
			throw new BusinessException("对不起，该条码对应的设备不存在，或者该设备挂在其他作业单位或已经入库了!");
		}
		return equipment;
	}
	
	@RequestMapping("/installIn/equipmentInStore.do")
	@ResponseBody
	//public String equipOutStore(@RequestBody Equipment[] equipments,String store_id,String workUnit_id,String type,String memo) {
	public String equipmentInStore(@RequestBody Equipment[] equipments, InstallIn installin) { 
		installInService.equipmentInStore(equipments, installin);
		return "success";
	}
	
}
