package com.mawujun.panera.customer;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.repository.cnd.Cnd;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/contact")
public class ContactController {

	@Resource
	private ContactService contactService;


//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/contact/query")
//	@ResponseBody
//	public List<Contact> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.Contact.parent.id, "root".equals(id)?null:id);
//		List<Contact> contactes=contactService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(Contact.class,M.Contact.parent.name());
//		return contactes;
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
//	@RequestMapping("/contact/query")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.Contact.sampleName, "%"+sampleName+"%");
//		return contactService.queryPage(page);
//	}

	@RequestMapping("/contact/query")
	@ResponseBody
	public List<Contact> query(String customer_id) {	
		List<Contact> contactes=contactService.query(Cnd.select().andEquals("customer_id", customer_id));
		return contactes;
	}
	

	@RequestMapping("/contact/load")
	public Contact load(String id) {
		return contactService.get(id);
	}
	
	@RequestMapping("/contact/create")
	@ResponseBody
	public Contact create(Contact contact) {
		//contact.setIsDefault(false);
		contactService.create(contact);
		return contact;
	}
	
	@RequestMapping("/contact/update")
	@ResponseBody
	public  Contact update(Contact contact) {
		contactService.update(contact);
		return contact;
	}
	@RequestMapping("/contact/setDefault")
	@ResponseBody
	public  Contact setDefault(Contact contact) {
		contactService.update(Cnd.update().set("isDefault", false).andEquals("customer_id", contact.getCustomer_id()));
		contactService.update(contact);
		
		return contact;
	}
	
	@RequestMapping("/contact/deleteById")
	@ResponseBody
	public String deleteById(String id) {
		contactService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/contact/destroy")
	@ResponseBody
	public Contact destroy(@RequestBody Contact contact) {
		contactService.delete(contact);
		return contact;
	}
	
	
}
