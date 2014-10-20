package com.mawujun.repair;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.exception.BusinessException;
import com.mawujun.shiro.ShiroUtils;
import com.mawujun.user.User;
import com.mawujun.utils.page.Page;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/repair")
public class RepairController {

	@Resource
	private RepairService repairService;


//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/repair/query.do")
//	@ResponseBody
//	public List<Repair> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.Repair.parent.id, "root".equals(id)?null:id);
//		List<Repair> repaires=repairService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(Repair.class,M.Repair.parent.name());
//		return repaires;
//	}

//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/repair/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.Repair.sampleName, "%"+sampleName+"%");
//		return repairService.queryPage(page);
//	}

//	@RequestMapping("/repair/query.do")
//	@ResponseBody
//	public List<Repair> query() {	
//		List<Repair> repaires=repairService.queryAll();
//		return repaires;
//	}
//	

//	@RequestMapping("/repair/load.do")
//	public Repair load(String id) {
//		return repairService.get(id);
//	}
	
//	@RequestMapping("/repair/create.do")
//	@ResponseBody
//	public Repair create(@RequestBody Repair repair) {
//		repairService.create(repair);
//		return repair;
//	}

	
	@RequestMapping("/repair/update.do")
	@ResponseBody
	public  Repair update(@RequestBody Repair repair) {
//		if("".equals(repair.getRpa_type())){
//			
//		}
		repairService.update(repair);
		return repair;
	}
	
//	@RequestMapping("/repair/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		repairService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/repair/destroy.do")
//	@ResponseBody
//	public Repair destroy(@RequestBody Repair repair) {
//		repairService.delete(repair);
//		return repair;
//	}
	
	
	@RequestMapping("/repair/getRepairVOByEcode.do")
	@ResponseBody
	public RepairVO getRepairVOByEcode(String ecode,String store_id) {	
		RepairVO repairvo= repairService.getRepairVOByEcode(ecode,store_id);
		if(repairvo==null){
			throw new BusinessException("对不起，该条码对应的设备不存在，或者该设备挂在其他仓库中!");
		}
		repairvo.setStatus(RepairStatus.One.getValue());
		return repairvo;
	}
	
	/**
	 * 创建信的维修单
	 * @author mawujun 16064988@qq.com 
	 * @param createBatch
	 * @return
	 */
	@RequestMapping("/repair/newRepair.do")
	@ResponseBody
	public String newRepair(@RequestBody Repair[] repairs) {
		//repairService.createBatch(createBatch);
		repairService.newRepair(repairs);
		return "success";
	}
	
	@RequestMapping("/repair/storeMgrQuery.do")
	@ResponseBody
	public Page storeMgrQuery(Integer start,Integer limit, String str_out_id,String rpa_id,String str_out_date_start,String str_out_date_end
			,String ecode,Integer status,Boolean only_have_scap){
		Page page=Page.getInstance(start,limit);//.addParam(M.Repair.sampleName, "%"+sampleName+"%");
		page.addParam("str_out_id", str_out_id).addParam("rpa_id", rpa_id).addParam("str_out_date_start", str_out_date_start).addParam("str_out_date_end", str_out_date_end)
		.addParam("ecode", ecode).addParam("status", status)
		.addParam("only_have_scap", only_have_scap);
		page.addParam("user_id", ShiroUtils.getAuthenticationInfo().getId());
		//page.addParam("edit", true);
		return repairService.storeMgrQuery(page);
	}
	
	/**
	 * 仓库进行管理的时候进行的查询
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/repair/repairInQuery.do")
	@ResponseBody
	public Page repairInQuery(Integer start,Integer limit, String str_out_id,String rpa_id,String str_out_date_start,String str_out_date_end
			,String ecode,Integer status){
		Page page=Page.getInstance(start,limit);//.addParam(M.Repair.sampleName, "%"+sampleName+"%");
		page.addParam("str_out_id", str_out_id).addParam("rpa_id", rpa_id).addParam("str_out_date_start", str_out_date_start).addParam("str_out_date_end", str_out_date_end)
		.addParam("ecode", ecode).addParam("status", status);
		page.addParam("user_id", ShiroUtils.getAuthenticationInfo().getId());
		return repairService.repairInQuery(page);
	}

	
	/**
	 * 仓库进行管理的时候进行的查询
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/repair/repairMgrQuery.do")
	@ResponseBody
	public Page repairMgrQuery(Integer start,Integer limit, String str_out_id,String rpa_id,String rpa_in_date_start,String rpa_in_date_end
			,String ecode,Integer status,Boolean only_have_scap){
		Page page=Page.getInstance(start,limit);//.addParam(M.Repair.sampleName, "%"+sampleName+"%");
		page.addParam("str_out_id", str_out_id).addParam("rpa_id", rpa_id).addParam("rpa_in_date_start", rpa_in_date_start).addParam("rpa_in_date_end", rpa_in_date_end)
		.addParam("ecode", ecode).addParam("status", status)
		.addParam("only_have_scap", only_have_scap);
		page.addParam("user_id", ShiroUtils.getAuthenticationInfo().getId());
		return repairService.repairMgrQuery(page);
	}
	
	/**
	 * 维修中心入库
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/repair/repairInStore.do")
	@ResponseBody
	public String repairInStore(@RequestBody Repair[] repairs){
		repairService.repairInStore(repairs);
		return "success";
	}
	
	/**
	 * 维修中心出库
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/repair/repairOutStore.do")
	@ResponseBody
	public String repairOutStore(@RequestBody Repair[] repairs){
		repairService.repairOutStore(repairs);
		return "success";
	}
	
	/**
	 * 维修完返库，仓库入库
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/repair/storeInStore.do")
	@ResponseBody
	public String storeInStore(@RequestBody Repair[] repairs,String str_in_id){
		for(Repair repair:repairs){
			if(str_in_id==null || !str_in_id.equalsIgnoreCase(repair.getStr_in_id())){
				throw new BusinessException("产品要发往的仓库和要入库的仓库部一致!");
			}
		}
		repairService.storeInStore(repairs,str_in_id);
		return "success";
	}
	

	
}
