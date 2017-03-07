package com.mawujun.baseinfo;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.shiro.ShiroUtils;
import com.mawujun.utils.M;


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
	private static HashMap<String,Customer> customers_cache=new HashMap<String,Customer>();
	@Override
	public Customer get(String id) {
		if(id==null){
			return null;
		}
		Customer customer=customers_cache.get(id);
		if(customer==null){
			return customerRepository.get(id);
		} else {
			return customer;
		}
	}
	@Override
	public CustomerRepository getRepository() {
		return customerRepository;
	}

	public List<CustomerVO> queryChildren(String parent_id,String name) {
		return customerRepository.queryChildren(parent_id,name,ShiroUtils.getUserId());
	}
	
	public List<Customer> queryCombo(String name) {
		return customerRepository.queryCombo(name,ShiroUtils.getUserId());
	}
	
	public List<Customer> queryAreaCombo() {
		return customerRepository.queryAreaCombo(ShiroUtils.getUserId());
	}
	
	public void transform(String parent_id,String[] customer_ids) {
		if(parent_id==null || customer_ids==null || customer_ids.length==0){
			return;
		}
		for(String customer_id:customer_ids){
			customerRepository.update(Cnd.update().set(M.Customer.parent_id, parent_id).andEquals(M.Customer.id, customer_id));
		}
	}
}
