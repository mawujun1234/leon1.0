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



import javax.persistence.Transient;

import com.mawujun.panera.continents.Country;
import com.mawujun.panera.customerProperty.CustomerProperty;
import com.mawujun.panera.customerSource.CustomerSource;
import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="panera_Customer")
public class Customer extends UUIDEntity {
	@Column(length=100)
	private String name;
	//@ManyToOne(fetch=FetchType.EAGER)
	//private CustomerSource customerSource;
	@Column(length=36)
	private String customerSource_id;
//	@ManyToOne(fetch=FetchType.EAGER)
//	private CustomerProperty customerProperty;
	@Column(length=36)
	private String customerProperty_id;
	
		
//	private Country country;
	//@Column(length=36)
	@Transient
	private String continent_id;
	@Column(length=36)
	private String country_id;

	@Column(length=200)
	private String address;
	
	@Column(length=200)
	private String website;
	
	@Column(length=100)
	private String businessPhase_id;
	@Column(length=20)
	private String followNum;//跟进次数，这个是只读的，是在跟进记录里面设置的
	private Date inquiryDate;//初次询盘时间
	@Column(length=1500)
	private String inquiryContent;//初次询盘内容
	
	
	
	
	private int star;//星级，还要加很多评判的字段	
	private int expYear;//光带几年经验
	private int proportion;//光带占比
	private int customerType;//客户类型
	private int empNum;//员工人数
	private int buyMoney;//光带年采购额
	private int quality;//质量档次
	private int price;//价格档次
	private int moq;//每单每款MOQ
	private int paymentTerms;//付款条款
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}

	public String getFollowNum() {
		return followNum;
	}
	public void setFollowNum(String followNum) {
		this.followNum = followNum;
	}
	public Date getInquiryDate() {
		return inquiryDate;
	}
	public void setInquiryDate(Date inquiryDate) {
		this.inquiryDate = inquiryDate;
	}
	public String getInquiryContent() {
		return inquiryContent;
	}
	public void setInquiryContent(String inquiryContent) {
		this.inquiryContent = inquiryContent;
	}

	public int getExpYear() {
		return expYear;
	}
	public void setExpYear(int expYear) {
		this.expYear = expYear;
	}
	public int getProportion() {
		return proportion;
	}
	public void setProportion(int proportion) {
		this.proportion = proportion;
	}
	public int getCustomerType() {
		return customerType;
	}
	public void setCustomerType(int customerType) {
		this.customerType = customerType;
	}
	public int getEmpNum() {
		return empNum;
	}
	public void setEmpNum(int empNum) {
		this.empNum = empNum;
	}
	public int getBuyMoney() {
		return buyMoney;
	}
	public void setBuyMoney(int buyMoney) {
		this.buyMoney = buyMoney;
	}
	public int getQuality() {
		return quality;
	}
	public void setQuality(int quality) {
		this.quality = quality;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getMoq() {
		return moq;
	}
	public void setMoq(int moq) {
		this.moq = moq;
	}
	public int getPaymentTerms() {
		return paymentTerms;
	}
	public void setPaymentTerms(int paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public String getCustomerSource_id() {
		return customerSource_id;
	}
	public void setCustomerSource_id(String customerSource_id) {
		this.customerSource_id = customerSource_id;
	}
	public String getCustomerProperty_id() {
		return customerProperty_id;
	}
	public void setCustomerProperty_id(String customerProperty_id) {
		this.customerProperty_id = customerProperty_id;
	}

	public String getContinent_id() {
		return continent_id;
	}
	public void setContinent_id(String continent_id) {
		this.continent_id = continent_id;
	}
	public String getCountry_id() {
		return country_id;
	}
	public void setCountry_id(String country_id) {
		this.country_id = country_id;
	}
	public String getBusinessPhase_id() {
		return businessPhase_id;
	}
	public void setBusinessPhase_id(String businessPhase_id) {
		this.businessPhase_id = businessPhase_id;
	}
	

//	@OneToMany(mappedBy="customer",fetch=FetchType.LAZY,cascade={CascadeType.REFRESH})
//	private List<Contact> contacts;
	
	
	
	
	

}
