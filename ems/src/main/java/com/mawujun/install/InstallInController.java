package com.mawujun.install;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.baseinfo.Equipment;
import com.mawujun.baseinfo.EquipmentService;
import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.exception.BusinessException;
import com.mawujun.utils.M;
import com.mawujun.utils.StringUtils;
import com.mawujun.utils.page.Page;
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

//	@RequestMapping("/installIn/query.do")
//	@ResponseBody
//	public List<InstallIn> query() {	
//		List<InstallIn> installInes=installInService.queryAll();
//		return installInes;
//	}
//	
//
//	@RequestMapping("/installIn/load.do")
//	public InstallIn load(String id) {
//		return installInService.get(id);
//	}
//	
//	@RequestMapping("/installIn/create.do")
//	@ResponseBody
//	public InstallIn create(@RequestBody InstallIn installIn) {
//		installInService.create(installIn);
//		return installIn;
//	}
//	
//	@RequestMapping("/installIn/update.do")
//	@ResponseBody
//	public  InstallIn update(@RequestBody InstallIn installIn) {
//		installInService.update(installIn);
//		return installIn;
//	}
//	
//	@RequestMapping("/installIn/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		installInService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/installIn/destroy.do")
//	@ResponseBody
//	public InstallIn destroy(@RequestBody InstallIn installIn) {
//		installInService.delete(installIn);
//		return installIn;
//	}
	
	@RequestMapping("/installIn/getEquipmentByEcode.do")
	@ResponseBody
	public EquipmentVO getEquipmentByEcode(String ecode,String workunit_id) {
		EquipmentVO equipment= installInService.getEquipmentByEcode(ecode,workunit_id);
		
		return equipment;
	}
	
	@RequestMapping("/installIn/equipmentInStore.do")
	@ResponseBody
	//public String equipOutStore(@RequestBody Equipment[] equipments,String store_id,String workUnit_id,String type,String memo) {
	public String equipmentInStore(@RequestBody Equipment[] equipments, InstallIn installin) { 
		installInService.equipmentInStore(equipments, installin);
		return "success";
	}
	
	/**
	 * 
	 * @author mawujun 16064988@qq.com 
	 * @param start
	 * @param limit
	 * @param operateDate_start
	 * @param operateDate_end
	 * @param store_id
	 * @param type 是出库单还是入库单
	 * @return
	 */
	@RequestMapping("/inoutvo/queryMain.do")
	@ResponseBody
	public Page queryMain(Integer start,Integer limit,Date operateDate_start,Date operateDate_end,String store_id,String workUnit_id,String type) { 
		Page page=Page.getInstance(start, limit);
		page.addParam("operateDate_start", operateDate_start);
		page.addParam("operateDate_end", operateDate_end);
		page.addParam(M.InstallIn.store_id, store_id);
		page.addParam(M.InstallIn.workUnit_id, workUnit_id);
		page=installInService.queryMain(page, type);
		return page;
	}
	
	@RequestMapping("/inoutvo/queryList.do")
	@ResponseBody
	public List<InOutListVO> queryList(String inOut_id,String type) { 
		if(!StringUtils.hasText(inOut_id)){
			throw new BusinessException("请先选择一条单据!");
		}
		if(!StringUtils.hasText(type)){
			throw new BusinessException("单据类型没有选!");
		}
		List<InOutListVO> page=installInService.queryList(inOut_id, type);
		return page;
	}
}
