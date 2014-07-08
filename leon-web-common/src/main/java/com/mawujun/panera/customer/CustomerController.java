package com.mawujun.panera.customer;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.M;
import com.mawujun.utils.page.Page;
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
//	@RequestMapping("/customer/query")
//	@ResponseBody
//	public List<Customer> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.Customer.parent.id, "root".equals(id)?null:id);
//		List<Customer> customeres=customerService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(Customer.class,M.Customer.parent.name());
//		return customeres;
//	}

	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/customer/query")
	@ResponseBody
	public Page query(Integer start,Integer limit,String name,String contact_name,String contact_email,Boolean deleted){
		Page page=Page.getInstance(start,limit).addParam("name", name).addParam("contact_name", contact_name)
				.addParam("contact_email", contact_email)
				.addParam("deleted", deleted==null?false:deleted);
		return customerService.queryPage(page);
	}

//	@RequestMapping("/customer/query")
//	@ResponseBody
//	public List<Customer> query() {	
//		List<Customer> customeres=customerService.queryAll();
//		return customeres;
//	}
	

	@RequestMapping("/customer/load")
	public Customer load(String id) {
		return customerService.get(id);
	}
	
	@RequestMapping("/customer/create")
	@ResponseBody
	public Customer create(Customer customer) {
		
		customerService.create(customer);
		
		return customer;
	}
	
	
	
	@RequestMapping("/customer/update")
	@ResponseBody
	public  Customer update(Customer customer) {
		customerService.update(customer);
		return customer;
	}
	
	@RequestMapping("/customer/deleteById")
	@ResponseBody
	public String deleteById(String id) {
		//contactService.deleteBatch(Cnd.delete().andEquals("customer.id", id));
		//customerService.deleteById(id);
		
		customerService.update(Cnd.update().set("deleted", true).andEquals("id",id));
		return id;
	}
//	
//	@RequestMapping("/customer/destroy")
//	@ResponseBody
//	public Customer destroy(@RequestBody Customer customer) {
//		customerService.delete(customer);
//		return customer;
//	}
	
	
}
