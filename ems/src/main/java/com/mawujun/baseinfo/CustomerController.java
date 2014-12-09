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
import com.mawujun.baseinfo.Customer;
import com.mawujun.baseinfo.CustomerService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/customer")
public class CustomerController {

	@Resource
	private CustomerService customerService;


//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/customer/query.do")
//	@ResponseBody
//	public List<Customer> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.Customer.parent.id, "root".equals(id)?null:id);
//		List<Customer> customeres=customerService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(Customer.class,M.Customer.parent.name());
//		return customeres;
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
//	@RequestMapping("/customer/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.Customer.sampleName, "%"+sampleName+"%");
//		return customerService.queryPage(page);
//	}

	@RequestMapping("/customer/query.do")
	@ResponseBody
	public List<Customer> query() {	
		List<Customer> customeres=customerService.queryAll();
		return customeres;
	}
	

	@RequestMapping("/customer/load.do")
	public Customer load(String id) {
		return customerService.get(id);
	}
	
	@RequestMapping("/customer/create.do")
	@ResponseBody
	public Customer create(@RequestBody Customer customer) {
		customerService.create(customer);
		return customer;
	}
	
	@RequestMapping("/customer/update.do")
	@ResponseBody
	public  Customer update(@RequestBody Customer customer) {
		customerService.update(customer);
		return customer;
	}
	
//	@RequestMapping("/customer/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		customerService.deleteById(id);
//		return id;
//	}
//	
	@RequestMapping("/customer/destroy.do")
	@ResponseBody
	public Customer destroy(Customer customer) {
		//customerService.delete(customer);
		customer.setStatus(false);
		customerService.update(customer);
		return customer;
	}
	
	@RequestMapping("/customer/queryCombo.do")
	@ResponseBody
	public List<Customer> queryCombo(String name) {	
		
		return customerService.query(Cnd.select().andEquals(M.Customer.status, true).andLike(M.Customer.name, name));	
	}
	
//	@RequestMapping("/customer/queryPole.do")
//	@ResponseBody
//	public List<Customer> query() {	
//		List<Customer> customeres=customerService.queryAll();
//		return customeres;
//	}
	
	
}
