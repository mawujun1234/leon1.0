package com.mawujun.panera.customerProperty;
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

import com.mawujun.panera.customerProperty.CustomerProperty;
import com.mawujun.panera.customerProperty.CustomerPropertyService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/customerProperty")
public class CustomerPropertyController {

	@Resource
	private CustomerPropertyService customerPropertyService;
//
//
//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/customerProperty/query")
//	@ResponseBody
//	public List<CustomerProperty> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.CustomerProperty.parent.id, "root".equals(id)?null:id);
//		List<CustomerProperty> customerPropertyes=customerPropertyService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(CustomerProperty.class,M.CustomerProperty.parent.name());
//		return customerPropertyes;
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
//	@RequestMapping("/customerProperty/query")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.CustomerProperty.sampleName, "%"+sampleName+"%");
//		return customerPropertyService.queryPage(page);
//	}

	@RequestMapping("/customerProperty/query")
	@ResponseBody
	public List<CustomerProperty> query() {	
		List<CustomerProperty> customerPropertyes=customerPropertyService.queryAll();
		return customerPropertyes;
	}
	

	@RequestMapping("/customerProperty/load")
	public CustomerProperty load(String id) {
		return customerPropertyService.get(id);
	}
	
	@RequestMapping("/customerProperty/create")
	@ResponseBody
	public CustomerProperty create(@RequestBody CustomerProperty customerProperty) {
		customerPropertyService.create(customerProperty);
		return customerProperty;
	}
	
	@RequestMapping("/customerProperty/update")
	@ResponseBody
	public  CustomerProperty update(@RequestBody CustomerProperty customerProperty) {
		customerPropertyService.update(customerProperty);
		return customerProperty;
	}
	
	@RequestMapping("/customerProperty/deleteById")
	@ResponseBody
	public String deleteById(String id) {
		customerPropertyService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/customerProperty/destroy")
	@ResponseBody
	public CustomerProperty destroy(@RequestBody CustomerProperty customerProperty) {
		customerPropertyService.delete(customerProperty);
		return customerProperty;
	}
	
	
}
