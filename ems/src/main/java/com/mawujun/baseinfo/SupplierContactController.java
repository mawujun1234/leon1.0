package com.mawujun.baseinfo;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;
import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.page.Page;
import com.mawujun.utils.M;

import com.mawujun.baseinfo.SupplierContact;
import com.mawujun.baseinfo.SupplierContactService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/supplierContact")
public class SupplierContactController {

	@Resource
	private SupplierContactService supplierContactService;


//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/supplierContact/query.do")
//	@ResponseBody
//	public List<SupplierContact> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.SupplierContact.parent.id, "root".equals(id)?null:id);
//		List<SupplierContact> supplierContactes=supplierContactService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(SupplierContact.class,M.SupplierContact.parent.name());
//		return supplierContactes;
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
//	@RequestMapping("/supplierContact/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.SupplierContact.sampleName, "%"+sampleName+"%");
//		return supplierContactService.queryPage(page);
//	}

	@RequestMapping("/supplierContact/query.do")
	@ResponseBody
	public List<SupplierContact> query(String supplier_id) {	
		Cnd cnd=Cnd.select().andEquals(M.SupplierContact.supplier_id,supplier_id);
		List<SupplierContact> supplierContactes=supplierContactService.query(cnd);
		return supplierContactes;
	}
	

	@RequestMapping("/supplierContact/load.do")
	public SupplierContact load(String id) {
		return supplierContactService.get(id);
	}
	
	@RequestMapping("/supplierContact/create.do")
	@ResponseBody
	public SupplierContact create(@RequestBody SupplierContact supplierContact) {
		supplierContactService.create(supplierContact);
		return supplierContact;
	}
	
	@RequestMapping("/supplierContact/update.do")
	@ResponseBody
	public  SupplierContact update(@RequestBody SupplierContact supplierContact) {
		supplierContactService.update(supplierContact);
		return supplierContact;
	}
	
	@RequestMapping("/supplierContact/deleteById.do")
	@ResponseBody
	public String deleteById(String id) {
		supplierContactService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/supplierContact/destroy.do")
	@ResponseBody
	public SupplierContact destroy(SupplierContact supplierContact) {
		supplierContactService.delete(supplierContact);
		return supplierContact;
	}
	
	
}
