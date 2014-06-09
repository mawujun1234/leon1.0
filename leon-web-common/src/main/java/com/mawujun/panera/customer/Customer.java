package com.mawujun.panera.customer;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



import com.mawujun.panera.continents.Country;
import com.mawujun.panera.customerProperty.CustomerProperty;
import com.mawujun.panera.customerSource.CustomerSource;
import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="panera_Customer")
public class Customer extends UUIDEntity {
	@Column(length=100)
	private String name;
	@ManyToOne(fetch=FetchType.EAGER)
	private CustomerSource customerSource;
	@ManyToOne(fetch=FetchType.EAGER)
	private CustomerProperty customerProperty;
	@Column(length=200)
	private String website;	
	private Country country;
	@Column(length=200)
	private String address;
	@Column(length=100)
	private String businessPhase;
	@Column(length=20)
	private String followNum;//跟进次数，这个是只读的，是在跟进记录里面设置的
	private Date inquiryDate;//初次询盘时间
	@Column(length=1500)
	private String inquiryContent;//初次询盘内容
	
	
	
	
	private String star;//星级，还要加很多评判的字段	

	@OneToMany(mappedBy="parent",fetch=FetchType.LAZY,cascade={CascadeType.REFRESH})
	private List<Contact> contacts;
	
	
	
	
	

}
