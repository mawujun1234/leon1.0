package com.mawujun.panera.customerSource;
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
import com.mawujun.panera.customerSource.CustomerSource;
import com.mawujun.panera.customerSource.CustomerSourceService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/customerSource")
public class CustomerSourceController {

	@Resource
	private CustomerSourceService customerSourceService;


//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/customerSource/query")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.CustomerSource.sampleName, "%"+sampleName+"%");
//		return customerSourceService.queryPage(page);
//	}

	@RequestMapping("/customerSource/query")
	@ResponseBody
	public List<CustomerSource> query() {	
		List<CustomerSource> customerSourcees=customerSourceService.queryAll();
		return customerSourcees;
	}
	

	@RequestMapping("/customerSource/load")
	public CustomerSource load(String id) {
		return customerSourceService.get(id);
	}
	
	@RequestMapping("/customerSource/create")
	@ResponseBody
	public CustomerSource create(@RequestBody CustomerSource customerSource) {
		customerSourceService.create(customerSource);
		return customerSource;
	}
	
	@RequestMapping("/customerSource/update")
	@ResponseBody
	public  CustomerSource update(@RequestBody CustomerSource customerSource) {
		customerSourceService.update(customerSource);
		return customerSource;
	}
	
	@RequestMapping("/customerSource/deleteById")
	@ResponseBody
	public String deleteById(String id) {
		customerSourceService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/customerSource/destroy")
	@ResponseBody
	public CustomerSource destroy(@RequestBody CustomerSource customerSource) {
		customerSourceService.delete(customerSource);
		return customerSource;
	}
	
	
}
