package com.mawujun.panera.customer;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;







import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;


import com.mawujun.exception.BusinessException;
import com.mawujun.panera.customer.Customer;
import com.mawujun.panera.customer.CustomerRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class CustomerService extends AbstractService<Customer, String>{

	@Autowired
	private CustomerRepository customerRepository;
	
	@Resource
	private ContactService contactService;
	
	@Override
	public CustomerRepository getRepository() {
		return customerRepository;
	}
	
	public String create(Customer customer) {
		customer.setCreateDate(new Date());
		
		checkDuplicate(customer,false);
//		//检查是否重复了
//		//先检查名称是否相同
//		Long count=0L;
//		count=customerRepository.queryCount(Cnd.count("id").andEquals("name", customer.getName()));
//		if(count>0){
//			throw new BusinessException("客户已经存在,已经存在相同的客户名称。");
//		}
//		if(customer.getCode()!=null && !"".equalsIgnoreCase(customer.getCode().trim())){
//			count=customerRepository.queryCount(Cnd.count("id").andEquals("code", customer.getCode()));
//			if(count>0){
//				throw new BusinessException("客户编号已经存在。");
//			}
//		}
//		
//		
//		//如果email是gmail.com或者是@yahoo.com就进行全局的匹配，否者就只进行域名的匹配
//		String email=customer.getContact_email();
//		
//		if(email.toLowerCase().indexOf("@gmail.com")!=-1 || email.toLowerCase().indexOf("@yahoo.com")!=-1){
//			count=contactService.queryCount(Cnd.count("id").andEquals("email", email));
//			if(count>0){
//				throw new BusinessException("客户已经存在,已经存在相同的邮箱。");
//			}
//		} else {
//			String emails[]=email.split("@");
//			count=contactService.queryCount(Cnd.count("id").andLike("email", "%"+emails[emails.length-1]));
//			if(count>0){
//				throw new BusinessException("客户已经存在,已经存在相同的邮箱后缀。");
//			}
//		}
		
				
		//计算星级
		int star=customer.calculate();
		customer.setStar(star);
		super.create(customer);		
			
		Contact contact=customer.geetContact();
		contact.setCustomer_id(customer.getId());
		contactService.create(contact);
				
		
		return customer.getId();
	}
	
	public void checkDuplicate(Customer customer,boolean update){
		//检查是否重复了
				//先检查名称是否相同
				Long count=0L;
				Cnd cnd=Cnd.count("id").andEquals("name", customer.getName());
				if(update){
					cnd.andNotEquals("id", customer.getId());
				}
				count=customerRepository.queryCount(cnd);
				if(count>0){
					throw new BusinessException("客户已经存在,已经存在相同的客户名称。");
				}
				if(customer.getCode()!=null && !"".equalsIgnoreCase(customer.getCode().trim())){
					cnd=Cnd.count("id").andEquals("code", customer.getCode());
					if(update){
						cnd.andNotEquals("id", customer.getId());
					}
					count=customerRepository.queryCount(cnd);
					if(count>0){
						throw new BusinessException("客户编号已经存在。");
					}
				}
				
				
				//如果email是gmail.com或者是@yahoo.com就进行全局的匹配，否者就只进行域名的匹配
				String email=customer.getContact_email();
				
				if(email.toLowerCase().indexOf("@gmail.com")!=-1 || email.toLowerCase().indexOf("@yahoo.com")!=-1){
					cnd=Cnd.count("id").andEquals("email", email);
					if(update){
						cnd.andNotEquals("customer_id", customer.getId());
					}
					count=contactService.queryCount(cnd);
					if(count>0){
						throw new BusinessException("客户已经存在,已经存在相同的邮箱。");
					}
				} else {
					String emails[]=email.split("@");
					cnd=Cnd.count("id").andLike("email", "%"+emails[emails.length-1]);
					if(update){
						cnd.andNotEquals("customer_id", customer.getId());
					}
					count=contactService.queryCount(cnd);
					if(count>0){
						throw new BusinessException("客户已经存在,已经存在相同的邮箱后缀。");
					}
				}
	}
	
	public  void update(Customer customer) {
		checkDuplicate(customer,true);
		
		//计算星级
				int star=customer.calculate();
				customer.setStar(star);
				super.update(customer);
						
				Contact contact=customer.geetContact();
				contact.setCustomer_id(customer.getId());
				contactService.update(contact);
				
	}

}
