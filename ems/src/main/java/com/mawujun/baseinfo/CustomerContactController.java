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

import com.mawujun.baseinfo.CustomerContact;
import com.mawujun.baseinfo.CustomerContactService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/customerContact")
public class CustomerContactController {

	@Resource
	private CustomerContactService customerContactService;


//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/customerContact/query.do")
//	@ResponseBody
//	public List<CustomerContact> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.CustomerContact.parent.id, "root".equals(id)?null:id);
//		List<CustomerContact> customerContactes=customerContactService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(CustomerContact.class,M.CustomerContact.parent.name());
//		return customerContactes;
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
//	@RequestMapping("/customerContact/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.CustomerContact.sampleName, "%"+sampleName+"%");
//		return customerContactService.queryPage(page);
//	}

	@RequestMapping("/customerContact/query.do")
	@ResponseBody
	public List<CustomerContact> query() {	
		List<CustomerContact> customerContactes=customerContactService.queryAll();
		return customerContactes;
	}
	

	@RequestMapping("/customerContact/load.do")
	public CustomerContact load(String id) {
		return customerContactService.get(id);
	}
	
	@RequestMapping("/customerContact/create.do")
	@ResponseBody
	public CustomerContact create(@RequestBody CustomerContact customerContact) {
		customerContactService.create(customerContact);
		return customerContact;
	}
	
	@RequestMapping("/customerContact/update.do")
	@ResponseBody
	public  CustomerContact update(@RequestBody CustomerContact customerContact) {
		customerContactService.update(customerContact);
		return customerContact;
	}
	
	@RequestMapping("/customerContact/deleteById.do")
	@ResponseBody
	public String deleteById(String id) {
		customerContactService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/customerContact/destroy.do")
	@ResponseBody
	public CustomerContact destroy(CustomerContact customerContact) {
		customerContactService.delete(customerContact);
		return customerContact;
	}
	
	
}
