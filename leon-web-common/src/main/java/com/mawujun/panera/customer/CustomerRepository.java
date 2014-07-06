package com.mawujun.panera.customer;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
import com.mawujun.panera.customer.Customer;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface CustomerRepository extends IRepository<Customer, String>{

	public List<ReportEntity> queryByContinent();
	public List<ReportEntity> queryByCountry();
	public List<ReportEntity> queryByBusinessPhase();
	public List<ReportEntity> queryByCustomerSource();
	public List<ReportEntity> queryByCustomerProperty();
	public List<ReportEntity> queryByStar();
}
